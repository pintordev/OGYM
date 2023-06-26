package com.ogym.project.user.oauth2;

import com.ogym.project.user.user.SiteUser;
import com.ogym.project.user.user.UserRepository;
import com.ogym.project.user.user.UserRole;
import com.ogym.project.user.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    private final SocialAccountRepository socialAccountRepository;
    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        log.info("getAttributes: {}", oAuth2User.getAttributes());
        System.out.println(oAuth2User.getAttributes());

        String provider = userRequest.getClientRegistration().getRegistrationId();

        OAuth2UserInfo oAuth2UserInfo = null;

        if (provider.equals("google")) {
            log.info("구글 로그인 요청");
            oAuth2UserInfo = new GoogleUserInfo(oAuth2User.getAttributes());
        } else if (provider.equals("kakao")) {
            log.info("카카오 로그인 요청");
            oAuth2UserInfo = new KakaoUserInfo((Map) oAuth2User.getAttributes());
        } else if (provider.equals("naver")) {
            log.info("네이버 로그인 요청");
            oAuth2UserInfo = new NaverUserInfo((Map) oAuth2User.getAttributes().get("response"));
        }

        String providerId = oAuth2UserInfo.getProviderId();
        String email = oAuth2UserInfo.getEmail();
        String loginId = provider + "_" + providerId;
        String nickname = oAuth2UserInfo.getName();

        // 추가
        Optional<SiteUser> optionalSiteUser = userRepository.findByLoginId(loginId);
        SiteUser user = null;

        // 가입된 유저정보가 없으면
        if (optionalSiteUser.isEmpty()) {
            SiteUser userInfo = new SiteUser();
            userInfo.setEmail(email);
            userInfo.setNickname(nickname);
            userInfo.setLoginId(loginId);
            user = userRepository.save(userInfo);
        } else {
            // 가입된 유저 정보가 있으면
            user = optionalSiteUser.get();
        }
        // 추가 끝

        Optional<SocialAccount> optionalSocialAccount = socialAccountRepository.findByLoginId(loginId);
        SocialAccount socialAccount = null;

        if (optionalSocialAccount.isEmpty()) {
            socialAccount = SocialAccount.builder()
                    .loginId(loginId)
                    .nickname(nickname)
                    .provider(provider)
                    .providerId(providerId)
                    .user(user) // 유저정보 담기 => user_id
                    .role(UserRole.USER)
                    .build();

            socialAccountRepository.save(socialAccount);
        } else {
            socialAccount = optionalSocialAccount.get();
        }

        UserDetails userDetails = User.builder()
                .username(loginId)
                .authorities(new ArrayList<>())
                .build();

        System.out.println("여기까지 옴");

        return new User(user.getLoginId(), user.getPassword(), authorityList);
    }
}
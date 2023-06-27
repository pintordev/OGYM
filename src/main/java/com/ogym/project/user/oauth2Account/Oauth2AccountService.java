package com.ogym.project.user.oauth2Account;

import com.ogym.project.DataNotFoundException;
import com.ogym.project.user.user.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

@RequiredArgsConstructor
@Service
public class Oauth2AccountService {

    private final Oauth2AccountRepository oauth2AccountRepository;

    private final GoogleOauth2 googleOauth2;
    private final KaKaoOauth2 kaKaoOauth2;
    private final NaverOauth2 naverOauth2;

    public String getRedirectUrlCode(String socialType) {
        if (socialType.equals("google")) {
            return this.googleOauth2.getRedirectUrlCode();
        } else if (socialType.equals("kakao")) {
            return this.kaKaoOauth2.getRedirectUrlCode();
        } else if (socialType.equals("naver")) {
            return this.naverOauth2.getRedirectUrlCode();
        } else {
            throw new IllegalArgumentException("알 수 없는 소셜 로그인 타입입니다.");
        }
    }

    public String getAccessToken(String socialType, String code, String state) {

        if (socialType.equals("google")) {
            return this.googleOauth2.getAccessToken(code, state);
        } else if (socialType.equals("kakao")) {
            return this.kaKaoOauth2.getAccessToken(code, state);
        } else if (socialType.equals("naver")) {
            return this.naverOauth2.getAccessToken(code, state);
        } else {
            throw new IllegalArgumentException("알 수 없는 소셜 로그인 타입입니다.");
        }
    }

    public Map<String, Object> getUserAttribute(String socialType, String accessToken) {

        if (socialType.equals("google")) {
            return this.googleOauth2.getUserAttribute(accessToken);
        } else if (socialType.equals("kakao")) {
            return this.kaKaoOauth2.getUserAttribute(accessToken);
        } else if (socialType.equals("naver")) {
            return this.naverOauth2.getUserAttribute(accessToken);
        } else {
            throw new IllegalArgumentException("알 수 없는 소셜 로그인 타입입니다.");
        }
    }

    public void create(String provider, String providerId, String email, String name, SiteUser parent) {
        Oauth2Account socialAccount = new Oauth2Account();
        socialAccount.setProvider(provider);
        socialAccount.setProviderId(providerId);
        socialAccount.setEmail(email);
        socialAccount.setName(name);
        socialAccount.setParent(parent);
        this.oauth2AccountRepository.save(socialAccount);
    }
}

package com.ogym.project.user.user;

import com.ogym.project.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public SiteUser create(String loginId, String password, String nickname, String username, String phone, String birthyear, String birthmonth, String birthday,
                           String email) {
        String birthDate = birthyear + birthmonth + birthday;


        SiteUser user = new SiteUser();
        user.setLoginId(loginId);
        user.setPassword(passwordEncoder.encode(password));
        user.setNickname(nickname);
        user.setUsername(username);
        user.setBirthDate(birthDate);
        user.setEmail(email);
        user.setPhoneNumber(phone);
        user.setCreateDate(LocalDateTime.now());
        user.setAuthority(UserRole.USER);
        this.userRepository.save(user);
        return user;
    }

    public String getEmailConfirmCode(String code) {
        return passwordEncoder.encode(code);
    }

    public boolean isLoginIdDuplicate(String loginId) {
        Optional<SiteUser> siteUser = userRepository.findByLoginId(loginId);
        return siteUser.isPresent();
    }

    public boolean isNickNameDuplicate(String nickname) {
        Optional<SiteUser> siteUser = userRepository.findByNickname(nickname);
        return siteUser.isPresent();
    }

    public boolean isEmailDuplicate(String email) {
        Optional<SiteUser> siteUser = userRepository.findByEmail(email);
        return siteUser.isPresent();
    }

    public boolean isPhoneNumberDuplicate(String phoneNumber) {
        Optional<SiteUser> siteUser = userRepository.findByPhoneNumber(phoneNumber);
        return siteUser.isPresent();
    }

    //비밀번호 같은 경우에는 단방향으로 처리해야 안정해서 양방향보다는 단방향을 사용하라고함
    public boolean authenticateLoginIdAndPassword(String loginId, String password) {
        Optional<SiteUser> siteUser = userRepository.findByLoginId(loginId);

        if (siteUser.isPresent()) {
            String encodedPassword = siteUser.get().getPassword();
            return passwordEncoder.matches(password, encodedPassword);
        }

        return false;
    }

    public String genConfirmCode(int length) {
        String candidateCode = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        SecureRandom secureRandom = new SecureRandom();

        String code = "";
        for (int i = 0; i < length; i++) {
            int index = secureRandom.nextInt(candidateCode.length());
            code += candidateCode.charAt(index);
        }

        return code;
    }

    public SiteUser getUser(String loginId) {
        Optional<SiteUser> siteUser = this.userRepository.findByLoginId(loginId);
        if (siteUser.isPresent()) {
            return siteUser.get();
        } else {
            throw new DataNotFoundException("siteuser not found");
        }
    }



    //email 값으로 데이터베이스에서 siteuser 조회하려는 기능
    public SiteUser getUserByEmail(String email) {
        Optional<SiteUser> siteUser = this.userRepository.findByEmail(email);
        if (siteUser.isPresent()) {
            return siteUser.get();
        } else {
            throw new DataNotFoundException("siteuser not found");
        }
    }

    //loginId 값으로 데이터베이스에서 user 조회하려는 기능
    public SiteUser getUserByLoginId(String loginId) {
        Optional<SiteUser> _siteUser = this.userRepository.findByLoginId(loginId);
        if (_siteUser.isPresent()) {
            return _siteUser.get();
        } else {
            throw new DataNotFoundException("user not found");
        }
    }
    //loginId + email 값으로 데이터베이스에서 siteuser 조회하려는 기능
    public SiteUser getUserByLoginAndEmail(String loginId, String email) {
        return this.userRepository.findByLoginIdAndEmail(loginId, email);
    }

    public void modifyPassword(String password, SiteUser user) {
        user.setPassword(passwordEncoder.encode(password));
        this.userRepository.save(user);
    }
    public boolean confirmCertificationCode(String inputCode, String genCode) {
        return passwordEncoder.matches(inputCode, genCode);
    }

    public void updateLoginDate(SiteUser user) {
        user.setLastLoginDate(LocalDateTime.now());
        this.userRepository.save(user);
    }
}

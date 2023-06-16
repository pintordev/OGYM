package com.ogym.project.user;

import com.ogym.project.DataNotFoundException;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
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



    //email 값으로 데이터베이스에서 siteuser 조회하려는 기능
    public SiteUser getUserByEmail(String email) {
        Optional<SiteUser> siteUser = this.userRepository.findByEmail(email);
        if (siteUser.isPresent()) {
            return siteUser.get();
        } else {
            throw new DataNotFoundException("siteuser not found");
        }
    }

    //login 값으로 데이터베이스에서 siteuser 조회하려는 기능
    public SiteUser getUserByLoginId(String loginId) {
        Optional<SiteUser> _siteUser = this.userRepository.findByLoginId(loginId);
        if (_siteUser.isPresent()) {
            return _siteUser.get();
        } else {
            throw new DataNotFoundException("user not found");
        }
    }





    public boolean confirmCertificationCode(String inputCode, String genCode) {
        return passwordEncoder.matches(inputCode, genCode);
    }
}

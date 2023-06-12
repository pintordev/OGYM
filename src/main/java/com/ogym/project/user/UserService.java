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

    public SiteUser create(String username, String password, String email, String birthyear, String birthmonth, String birthday, String phone, @Pattern(regexp = "\\d{11,12}", message = "휴대폰 번호에서 '-' 를 제외하고 숫자만 입력해주세요 (선택)") String userCreateFormPhone) {
        String birthDate = birthyear + birthmonth + birthday;

//        int phoneNumber = 0;
//        if (phone != null) {
//            phoneNumber = Integer.parseInt(phone.replaceAll("-", ""));
//        }
//
        SiteUser user = new SiteUser();
//        user.setUsername(username);
//        user.setPassword(passwordEncoder.encode(password));
//        user.setEmail(email);
//        user.setBirthDate(birthDate);
//        user.setPhoneNumber(phoneNumber);
//        user.setCreateDate(LocalDateTime.now());
//        this.userRepository.save(user);
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

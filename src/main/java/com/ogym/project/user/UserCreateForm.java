package com.ogym.project.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreateForm {
    @NotEmpty(message = "로그인 ID를 입력해주세요")
    @Pattern(regexp = "(?=.*[A-Za-z])[A-Za-z0-9_-]{4,20}")
    private String LoginId;

    @NotEmpty(message = "비밀번호를 입력해주세요")
    @Pattern(regexp = "(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*\\(\\)\\-_=+\\[\\{\\]}\\|;:'\",<.>\\/?])[A-Za-z\\d!@#$%^&*\\(\\)\\-_=+\\[\\{\\]}\\|;:'\",<.>\\/?]{8,30}")
    private String password;

    @NotEmpty(message = "비밀번호가 일치하지 않습니다")
    @Pattern(regexp = "(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*\\(\\)\\-_=+\\[\\{\\]}\\|;:'\",<.>\\/?])[A-Za-z\\d!@#$%^&*\\(\\)\\-_=+\\[\\{\\]}\\|;:'\",<.>\\/?]{8,30}")
    private String passwordCheck;

    @NotEmpty(message = "닉네임을 입력해주세요")
    @Pattern(regexp = "(?=.*[a-zA-Z가-힣])[a-zA-Z가-힣\\d]{2,15}")
    private String nickname;

    @NotEmpty(message = "이름을 입력해주세요")
    @Pattern(regexp = "(?=.*[a-zA-Z가-힣])[a-zA-Z가-힣\\d]{2,}")
    private String name;

    @NotEmpty(message = "생년(4자)을 입력해주세요")
    @Pattern(regexp = "\\d{4}")
    private String birthYear;

    @NotEmpty(message = "월(2자)을 입력해주세요")
    @Pattern(regexp = "\\d{2}")
    private String birthMonth;

    @NotEmpty(message = "일(2자)를 입력해주세요")
    @Pattern(regexp = "\\d{2}")
    private String birthDay;

    @NotEmpty(message = "이메일을 입력해주세요")
    @Email
    private String email;

    @NotEmpty(message = "발송된 인증번호를 입력해주세요")
    private String code;

    @Pattern(regexp = "\\d{11,12}", message = "휴대폰 번호에서 '-' 를 제외하고 숫자만 입력해주세요 (선택)")
    private String phone;

    private String genCode;

}
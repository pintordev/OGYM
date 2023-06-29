package com.ogym.project.user.user;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserPasswordForm {
    @NotEmpty(message = "현재 비밀번호를 입력해주세요")
    private String presentPW;

    @NotEmpty(message = "새 비밀번호를 입력해주세요(영문 대소문자, 숫자, 특수문자 조합)")
    @Pattern(regexp = "(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*\\(\\)\\-_=+\\[\\{\\]}\\|;:'\",<.>\\/?])[A-Za-z\\d!@#$%^&*\\(\\)\\-_=+\\[\\{\\]}\\|;:'\",<.>\\/?]{8,30}")
    private String newPW1;

    @NotEmpty(message = "새 비밀번호를 확인해주세요")
    @Pattern(regexp = "(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*\\(\\)\\-_=+\\[\\{\\]}\\|;:'\",<.>\\/?])[A-Za-z\\d!@#$%^&*\\(\\)\\-_=+\\[\\{\\]}\\|;:'\",<.>\\/?]{8,30}")
    private String newPW2;
}
package com.ogym.project.reComment;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReCommentForm {

    @NotEmpty(message = "내용이 입력되지 않았습니다")
    private String content;
}

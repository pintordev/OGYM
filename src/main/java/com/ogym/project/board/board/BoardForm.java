package com.ogym.project.board.board;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardForm {

    @NotEmpty(message = "제목이 입력되지 않았습니다")
    @Size(max = 200)
    private String title;

    @NotEmpty(message = "내용이 입력되지 않았습니다")
    private String content;

    @NotEmpty(message = "카테고리가 입력되지 않았습니다")
    private String category;
}

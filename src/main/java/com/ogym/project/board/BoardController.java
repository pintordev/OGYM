package com.ogym.project.board;

import com.ogym.project.boardCategory.BoardCategory;
import com.ogym.project.boardCategory.BoardCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/board")
@RequiredArgsConstructor
@Controller
public class BoardController {

    private final BoardService boardService;
    private final BoardCategoryService boardCategoryService;

    @GetMapping("/write")
    public String writeBoard(Model model) {

        List<BoardCategory> boardCategoryList = this.boardCategoryService.getList();
        model.addAttribute("boardCategoryList", boardCategoryList);

//        카테고리/제목
//        내용
//        하단에 가능하면 첨부파일
//        최하단에 등록/취소 버튼
//        등록 성공일 경우 작성된 글 상세 페이지로 이동
//        등록 실패일 경우 에러 알람 출력된 상태의 글쓰기 페이지 렌더링
//        취소일 경우 이전 리스트 페이지로 이동
        return "board_form";
    }
}

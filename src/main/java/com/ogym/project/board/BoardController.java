package com.ogym.project.board;

import com.ogym.project.boardCategory.BoardCategory;
import com.ogym.project.boardCategory.BoardCategoryService;
import com.ogym.project.user.SiteUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequestMapping("/board")
@RequiredArgsConstructor
@Controller
public class BoardController {

    private final BoardService boardService;
    private final BoardCategoryService boardCategoryService;

    @GetMapping("")
    public String main() {
        return "board_main";
    }

    @GetMapping("/write")
    public String writeBoard(Model model, BoardForm boardForm) {

        List<BoardCategory> boardCategoryList = this.boardCategoryService.getList();
        model.addAttribute("boardCategoryList", boardCategoryList);

        return "board_form";
    }

    @PostMapping("/write")
    public String writeBoard(Model model, @Valid BoardForm boardForm, BindingResult bindingResult) {

        // If exists Form Validation Error
        if (bindingResult.hasErrors()) {
            List<BoardCategory> boardCategoryList = this.boardCategoryService.getList();
            model.addAttribute("boardCategoryList", boardCategoryList);
            return "board_form";
        }

        // Write Board
        BoardCategory boardCategory = this.boardCategoryService.getBoardCategory(boardForm.getBoardCategory());
        SiteUser author = new SiteUser();
        Board board = this.boardService.create(boardForm.getTitle(), boardForm.getContent(), author, boardCategory);

        // Redirect to created board detail
        return String.format("redirect:/board/%s", board.getId());
    }
}

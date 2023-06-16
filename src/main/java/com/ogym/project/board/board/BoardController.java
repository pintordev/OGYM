package com.ogym.project.board.board;

import com.ogym.project.board.category.Category;
import com.ogym.project.board.category.CategoryService;
import com.ogym.project.board.comment.CommentForm;
import com.ogym.project.board.reComment.ReCommentForm;
import com.ogym.project.user.user.SiteUser;
import com.ogym.project.user.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/board")
@RequiredArgsConstructor
@Controller
public class BoardController {

    private final BoardService boardService;
    private final CategoryService categoryService;
    private final UserService userService;

    @GetMapping("")
    public String main(Model model,
                       @RequestParam(value = "boardPage", defaultValue = "0") int boardPage,
                       @RequestParam(value = "searchKeyWord", defaultValue = "") String searchKeyWord) {

        model.addAttribute("presentBoard", new Board());

        Page<Board> boardPaging = this.boardService.getList(boardPage, searchKeyWord);
        model.addAttribute("boardPaging", boardPaging);
        model.addAttribute("searchKeyWord", searchKeyWord);

        return "board_main";
    }

    @GetMapping("/{id}")
    public String detail(Model model,
                         CommentForm commentForm,
                         ReCommentForm reCommentForm,
                         @PathVariable("id") Long id,
                         @RequestParam(value = "boardPage", defaultValue = "0") int boardPage,
                         @RequestParam(value = "searchKeyWord", defaultValue = "") String searchKeyWord) {

        Board presentBoard = this.boardService.getBoard(id);
        model.addAttribute("presentBoard", presentBoard);

        Page<Board> boardPaging = this.boardService.getList(boardPage, searchKeyWord);
        model.addAttribute("boardPaging", boardPaging);
        model.addAttribute("searchKeyWord", searchKeyWord);

        return "board_detail";
    }

    @GetMapping("/write")
    public String writeBoard(Model model,
                             BoardForm boardForm) {

        List<Category> categoryList = this.categoryService.getList();
        model.addAttribute("categoryList", categoryList);

        return "board_form";
    }

    @PostMapping("/write")
    public String writeBoard(Model model,
                             @Valid BoardForm boardForm, BindingResult bindingResult) {

        // If exists Form Validation Error
        if (bindingResult.hasErrors()) {
            List<Category> categoryList = this.categoryService.getList();
            model.addAttribute("categoryList", categoryList);
            return "board_form";
        }

        // Write Board
        Category category = this.categoryService.getCategory(boardForm.getCategory());
        SiteUser author = this.userService.getUserByLoginId("ogym_admin");
        Board board = this.boardService.create(boardForm.getTitle(), boardForm.getContent(), author, category);

        // Redirect to created board detail
        return String.format("redirect:/board/%s", board.getId());
    }

    @GetMapping("/modify/{id}")
    public String modifyBoard(Model model,
                              BoardForm boardForm,
                              @PathVariable("id") Long id) {

        List<Category> categoryList = this.categoryService.getList();
        model.addAttribute("categoryList", categoryList);

        Board board = this.boardService.getBoard(id);
        boardForm.setCategory(board.getCategory().getName());
        boardForm.setTitle(board.getTitle());
        boardForm.setContent(board.getContent());

        return "board_form";
    }

    @PostMapping("/modify/{id}")
    public String modifyBoard(Model model,
                              @Valid BoardForm boardForm, BindingResult bindingResult,
                              @PathVariable("id") Long id) {

        // If exists Form Validation Error
        if (bindingResult.hasErrors()) {
            List<Category> categoryList = this.categoryService.getList();
            model.addAttribute("categoryList", categoryList);
            return "board_form";
        }

        // Modify Board
        Board board = this.boardService.getBoard(id);
        Category category = this.categoryService.getCategory(boardForm.getCategory());
        this.boardService.modify(board, boardForm.getTitle(), boardForm.getContent(), category);

        // Redirect to modified board detail
        return String.format("redirect:/board/%s", board.getId());
    }

    @GetMapping("/delete/{id}")
    public String deleteBoard(@PathVariable("id") Long id) {

        Board board = this.boardService.getBoard(id);
        this.boardService.delete(board);

        return "redirect:/board";
    }
}

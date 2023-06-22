package com.ogym.project.board.board;

import com.ogym.project.board.category.Category;
import com.ogym.project.board.category.CategoryService;
import com.ogym.project.board.comment.Comment;
import com.ogym.project.board.comment.CommentForm;
import com.ogym.project.board.comment.CommentService;
import com.ogym.project.board.reComment.ReCommentForm;
import com.ogym.project.user.user.SiteUser;
import com.ogym.project.user.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/board")
@RequiredArgsConstructor
@Controller
public class BoardController {

    private final BoardService boardService;
    private final CategoryService categoryService;
    private final UserService userService;
    private final CommentService commentService;

    @GetMapping("")
    public String main(Model model,
                       @RequestParam(value = "bPageSize", defaultValue = "20") int bPageSize,
                       @RequestParam(value = "bPage", defaultValue = "0") int bPage,
                       @RequestParam(value = "bSearch", defaultValue = "all") String bSearch,
                       @RequestParam(value = "bKw", defaultValue = "") String bKw,
                       @RequestParam(value = "bSort", defaultValue = "createDate") String bSort,
                       @RequestParam(value = "bCategory", defaultValue = "") String bCategory) {

        List<Category> categoryList = this.categoryService.getList();
        model.addAttribute("categoryList", categoryList);

        Map<Category, Long> bcc = new HashMap<>();
        for (Category category : categoryList) {
            if(!category.getName().equals("공지")) {
                Long count = this.boardService.getCount(category);
                bcc.put(category, count);
            }
        }
        model.addAttribute("bcc", bcc);

        model.addAttribute("presentBoard", new Board());

        Page<Board> boardPaging = this.boardService.getList(bPageSize, bPage, bSearch, bKw, bSort, bCategory);
        model.addAttribute("boardPaging", boardPaging);

        model.addAttribute("bPageSize", bPageSize);
        model.addAttribute("bPage", bPage);
        model.addAttribute("bSearch", bSearch);
        model.addAttribute("bKw", bKw);
        model.addAttribute("bSort", bSort);
        model.addAttribute("bCategory", bCategory);

        return "board_main";
    }

    @GetMapping("/{id}")
    public String detail(Model model,
                         CommentForm commentForm,
                         ReCommentForm reCommentForm,
                         @PathVariable("id") Long id,
                         @RequestParam(value = "bPageSize", defaultValue = "20") int bPageSize,
                         @RequestParam(value = "bPage", defaultValue = "0") int bPage,
                         @RequestParam(value = "bSearch", defaultValue = "all") String bSearch,
                         @RequestParam(value = "bKw", defaultValue = "") String bKw,
                         @RequestParam(value = "bSort", defaultValue = "createDate") String bSort,
                         @RequestParam(value = "bCategory", defaultValue = "") String bCategory,
                         @RequestParam(value = "cPage", defaultValue = "0") int cPage,
                         @RequestParam(value = "cSort", defaultValue = "createDate") String cSort) {

        List<Category> categoryList = this.categoryService.getList();
        model.addAttribute("categoryList", categoryList);

        Map<Category, Long> bcc = new HashMap<>();
        for (Category category : categoryList) {
            if(!category.getName().equals("공지")) {
                Long count = this.boardService.getCount(category);
                bcc.put(category, count);
            }
        }
        model.addAttribute("bcc", bcc);

        Board presentBoard = this.boardService.getBoard(id);
        model.addAttribute("presentBoard", presentBoard);

        Page<Board> boardPaging = this.boardService.getList(bPageSize, bPage, bSearch, bKw, bSort, bCategory);
        model.addAttribute("boardPaging", boardPaging);

        Page<Comment> commentPaging = this.commentService.getList(presentBoard, cPage, cSort);
        model.addAttribute("commentPaging", commentPaging);

        model.addAttribute("bPageSize", bPageSize);
        model.addAttribute("bPage", bPage);
        model.addAttribute("bSearch", bSearch);
        model.addAttribute("bKw", bKw);
        model.addAttribute("bSort", bSort);
        model.addAttribute("bCategory", bCategory);
        model.addAttribute("cPage", cPage);
        model.addAttribute("cSort", cSort);

        return "board_detail";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/write")
    public String writeBoard(Model model,
                             BoardForm boardForm) {

        List<Category> categoryList = this.categoryService.getList();
        model.addAttribute("categoryList", categoryList);

        Map<Category, Long> bcc = new HashMap<>();
        for (Category category : categoryList) {
            if(!category.getName().equals("공지")) {
                Long count = this.boardService.getCount(category);
                bcc.put(category, count);
            }
        }
        model.addAttribute("bcc", bcc);

        return "board_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/write")
    public String writeBoard(Model model,
                             @Valid BoardForm boardForm, BindingResult bindingResult,
                             Principal principal) {

        // If exists Form Validation Error
        if (bindingResult.hasErrors()) {
            List<Category> categoryList = this.categoryService.getList();
            model.addAttribute("categoryList", categoryList);

            Map<Category, Long> bcc = new HashMap<>();
            for (Category category : categoryList) {
                if(!category.getName().equals("공지")) {
                    Long count = this.boardService.getCount(category);
                    bcc.put(category, count);
                }
            }
            model.addAttribute("bcc", bcc);

            return "board_form";
        }

        // Write Board
        Category category = this.categoryService.getCategory(boardForm.getCategory());
        SiteUser author = this.userService.getUserByLoginId(principal.getName());
        Board board = this.boardService.create(boardForm.getTitle(), boardForm.getContent(), author, category);

        // Redirect to created board detail
        return String.format("redirect:/board/%s", board.getId());
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String modifyBoard(Model model,
                              BoardForm boardForm,
                              @PathVariable("id") Long id, Principal principal) {

        Board board = this.boardService.getBoard(id);

        if (!board.getAuthor().getLoginId().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
        }

        List<Category> categoryList = this.categoryService.getList();
        model.addAttribute("categoryList", categoryList);

        Map<Category, Long> bcc = new HashMap<>();
        for (Category category : categoryList) {
            if(!category.getName().equals("공지")) {
                Long count = this.boardService.getCount(category);
                bcc.put(category, count);
            }
        }
        model.addAttribute("bcc", bcc);

        boardForm.setCategory(board.getCategory().getName());
        boardForm.setTitle(board.getTitle());
        boardForm.setContent(board.getContent());

        return "board_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String modifyBoard(Model model,
                              @Valid BoardForm boardForm, BindingResult bindingResult,
                              @PathVariable("id") Long id, Principal principal) {

        Board board = this.boardService.getBoard(id);

        if (!board.getAuthor().getLoginId().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
        }

        // If exists Form Validation Error
        if (bindingResult.hasErrors()) {
            List<Category> categoryList = this.categoryService.getList();
            model.addAttribute("categoryList", categoryList);

            Map<Category, Long> bcc = new HashMap<>();
            for (Category category : categoryList) {
                if(!category.getName().equals("공지")) {
                    Long count = this.boardService.getCount(category);
                    bcc.put(category, count);
                }
            }
            model.addAttribute("bcc", bcc);

            return "board_form";
        }

        // Modify Board
        Category category = this.categoryService.getCategory(boardForm.getCategory());
        this.boardService.modify(board, boardForm.getTitle(), boardForm.getContent(), category);

        // Redirect to modified board detail
        return String.format("redirect:/board/%s", board.getId());
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String deleteBoard(@PathVariable("id") Long id, Principal principal) {

        Board board = this.boardService.getBoard(id);

        if (!board.getAuthor().getLoginId().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제 권한이 없습니다.");
        }

        this.boardService.delete(board);

        return "redirect:/board";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/vote/{id}")
    public String voteBoard(Model model,
                            @PathVariable("id") Long id, Principal principal) {

        Board presentBoard = this.boardService.getBoard(id);
        SiteUser voter = this.userService.getUserByLoginId(principal.getName());

        if (presentBoard.getAuthor().getLoginId().equals(voter.getLoginId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "본인이 작성한 글은 추천할 수 없습니다.");
        }

        this.boardService.vote(presentBoard, voter);

        model.addAttribute("presentBoard", presentBoard);

        return "board_detail :: #board_detail";
    }
}

package com.ogym.project.board.comment;

import com.ogym.project.board.board.Board;
import com.ogym.project.board.board.BoardService;
import com.ogym.project.board.reComment.ReCommentForm;
import com.ogym.project.user.user.SiteUser;
import com.ogym.project.user.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/comment")
@RequiredArgsConstructor
@Controller
public class CommentController {

    private final CommentService commentService;
    private final BoardService boardService;
    private final UserService userService;

    @PostMapping("/write")
    public String writeComment(Model model,
                               @Valid CommentForm commentForm, BindingResult bindingResult,
                               @RequestParam("boardId") Long boardId) {

        Board presentBoard = this.boardService.getBoard(boardId);

        // If exists Form Validation Error
        if (bindingResult.hasErrors()) {
            model.addAttribute("presentBoard", presentBoard);
            model.addAttribute("reCommentForm", new ReCommentForm());
            return "board_detail";
        }

        // Write Comment
        SiteUser author = this.userService.getUserByLoginId("ogym_admin");
        Comment comment = this.commentService.create(commentForm.getContent(), author, presentBoard);

        // Redirect to created board detail
        return String.format("redirect:/board/%s#comment_%s", presentBoard.getId(), comment.getId());
    }

    @GetMapping("/modify/{id}")
    public String modifyComment(Model model,
                                CommentForm commentForm,
                                @PathVariable("id") Long id) {

        Comment comment = this.commentService.getComment(id);
        model.addAttribute("comment", comment);
        commentForm.setContent(comment.getContent());

        return "comment_form";
    }

    @PostMapping("/modify/{id}")
    public String modifyComment(Model model,
                                @Valid CommentForm commentForm, BindingResult bindingResult,
                                @PathVariable("id") Long id) {

        // If exists Form Validation Error
        if (bindingResult.hasErrors()) {
            return "comment_form";
        }
        // Modify Comment
        Comment comment = this.commentService.getComment(id);
        Board presentBoard = comment.getBoard();
        this.commentService.modify(comment, commentForm.getContent());

        // Redirect to modified comment detail
        return String.format("redirect:/board/%s#comment_%s", presentBoard.getId(), comment.getId());
    }

    @GetMapping("/delete/{id}")
    public String deleteComment(@PathVariable("id") Long id) {

        Comment comment = this.commentService.getComment(id);
        Board presentBoard = comment.getBoard();
        this.commentService.delete(comment);

        return String.format("redirect:/board/%s", presentBoard.getId());
    }
}

package com.ogym.project.reComment;

import com.ogym.project.board.Board;
import com.ogym.project.comment.Comment;
import com.ogym.project.comment.CommentForm;
import com.ogym.project.comment.CommentService;
import com.ogym.project.user.SiteUser;
import com.ogym.project.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/reComment")
@RequiredArgsConstructor
@Controller
public class ReCommentController {

    private final ReCommentService reCommentService;
    private final CommentService commentService;
    private final UserService userService;

    @PostMapping("/write")
    public String writeReComment(Model model,
                                 @Valid ReCommentForm reCommentForm, BindingResult bindingResult,
                                 @RequestParam(value = "commentId", defaultValue = "-1") Long commentId,
                                 @RequestParam(value = "reCommentId", defaultValue = "-1") Long reCommentId) {

        Comment comment;
        ReComment parent;
        if (commentId != -1) {
            parent = null;
            comment = this.commentService.getComment(commentId);
        } else {
            parent = this.reCommentService.getReComment(reCommentId);
            comment = parent.getComment();
        }
        Board presentBoard = comment.getBoard();

        // If exists Form Validation Error
        if (bindingResult.hasErrors()) {
            model.addAttribute("presentBoard", presentBoard);
            model.addAttribute("commentForm", new CommentForm());
            return "board_detail";
        }

        // Write Comment
        SiteUser author = this.userService.getUserByLoginId("ogym_admin");
        ReComment reComment = this.reCommentService.create(reCommentForm.getContent(), author, comment, parent);

        // Redirect to created board detail
        return String.format("redirect:/board/%s#comment_%s", presentBoard.getId(), comment.getId());
    }

    @GetMapping("/modify/{id}")
    public String modifyReComment(Model model,
                                ReCommentForm reCommentForm,
                                @PathVariable("id") Long id) {

        ReComment reComment = this.reCommentService.getReComment(id);
        model.addAttribute("reComment", reComment);
        reCommentForm.setContent(reComment.getContent());

        return "reComment_form";
    }

    @PostMapping("/modify/{id}")
    public String modifyReComment(Model model,
                                @Valid ReCommentForm reCommentForm, BindingResult bindingResult,
                                @PathVariable("id") Long id) {

        // If exists Form Validation Error
        if (bindingResult.hasErrors()) {
            return "reComment_form";
        }
        // Modify ReComment
        ReComment reComment = this.reCommentService.getReComment(id);
        Comment comment = reComment.getComment();
        Board presentBoard = comment.getBoard();
        this.reCommentService.modify(reComment, reCommentForm.getContent());

        // Redirect to modified comment detail
        return String.format("redirect:/board/%s#comment_%s", presentBoard.getId(), comment.getId());
    }

    @GetMapping("/delete/{id}")
    public String deleteReComment(@PathVariable("id") Long id) {

        ReComment reComment = this.reCommentService.getReComment(id);
        Comment comment = reComment.getComment();
        Board presentBoard = comment.getBoard();
        this.reCommentService.delete(reComment);

        return String.format("redirect:/board/%s#comment_%s", presentBoard.getId(), comment.getId());
    }

}

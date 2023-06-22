package com.ogym.project.board.reComment;

import com.ogym.project.board.board.Board;
import com.ogym.project.board.comment.Comment;
import com.ogym.project.board.comment.CommentForm;
import com.ogym.project.board.comment.CommentService;
import com.ogym.project.user.user.SiteUser;
import com.ogym.project.user.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;

@RequestMapping("/reComment")
@RequiredArgsConstructor
@Controller
public class ReCommentController {

    private final ReCommentService reCommentService;
    private final CommentService commentService;
    private final UserService userService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/write")
    public String writeReComment(Model model,
                                 @Valid ReCommentForm reCommentForm, BindingResult bindingResult,
                                 @RequestParam(value = "commentId", defaultValue = "-1") Long commentId,
                                 @RequestParam(value = "reCommentId", defaultValue = "-1") Long reCommentId,
                                 Principal principal) {

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
        SiteUser author = this.userService.getUserByLoginId(principal.getName());
        ReComment reComment = this.reCommentService.create(reCommentForm.getContent(), author, comment, parent);

        // Redirect to created board detail
        return String.format("redirect:/board/%s#comment_%s", presentBoard.getId(), comment.getId());
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String modifyReComment(Model model,
                                ReCommentForm reCommentForm,
                                @PathVariable("id") Long id) {

        ReComment reComment = this.reCommentService.getReComment(id);
        model.addAttribute("reComment", reComment);
        reCommentForm.setContent(reComment.getContent());

        return "reComment_form";
    }

    @PreAuthorize("isAuthenticated()")
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

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String deleteReComment(@PathVariable("id") Long id) {

        ReComment reComment = this.reCommentService.getReComment(id);
        Comment comment = reComment.getComment();
        Board presentBoard = comment.getBoard();
        this.reCommentService.delete(reComment);

        return String.format("redirect:/board/%s#comment_%s", presentBoard.getId(), comment.getId());
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/vote/{id}")
    public String voteBoard(Model model,
                            @PathVariable("id") Long id, Principal principal) {

        ReComment reComment = this.reCommentService.getReComment(id);
        SiteUser voter = this.userService.getUserByLoginId(principal.getName());

        if (reComment.getAuthor().getLoginId().equals(voter.getLoginId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "본인이 작성한 댓글은 추천할 수 없습니다.");
        }

        this.reCommentService.vote(reComment, voter);

        model.addAttribute("reComment", reComment);

        return "board_detail :: #reComment_vote";
    }

}

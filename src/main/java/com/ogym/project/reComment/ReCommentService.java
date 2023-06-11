package com.ogym.project.reComment;

import com.ogym.project.DataNotFoundException;
import com.ogym.project.comment.Comment;
import com.ogym.project.user.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ReCommentService {

    private final ReCommentRepository reCommentRepository;

    public ReComment create(String content, SiteUser author, Comment comment, ReComment parent) {

        ReComment reComment = new ReComment();

        reComment.setContent(content);
        reComment.setAuthor(author);
        reComment.setComment(comment);
        if (parent != null) reComment.setParent(parent);
        reComment.setCreateDate(LocalDateTime.now());

        this.reCommentRepository.save(reComment);

        return reComment;
    }

    public void modify(ReComment reComment, String content) {

        reComment.setContent(content);
        reComment.setModifyDate(LocalDateTime.now());

        this.reCommentRepository.save(reComment);
    }

    public void delete(ReComment reComment) {
        this.reCommentRepository.delete(reComment);
    }

    public ReComment getReComment(Long id) {
        Optional<ReComment> orc = this.reCommentRepository.findById(id);
        if (orc.isPresent()) {
            return orc.get();
        } else {
            throw new DataNotFoundException("reComment not found");
        }
    }
}

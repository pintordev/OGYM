package com.ogym.project.board.reComment;

import com.ogym.project.DataNotFoundException;
import com.ogym.project.board.board.Board;
import com.ogym.project.board.comment.Comment;
import com.ogym.project.user.user.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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

    public void vote(ReComment reComment, SiteUser voter) {
        if (reComment.getVoter().contains(voter)) {
            reComment.getVoter().remove(voter);
        } else {
            reComment.getVoter().add(voter);
        }
        this.reCommentRepository.save(reComment);
    }

    public Page<ReComment> getListByUser(int rPage, SiteUser user) {

        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(rPage, 10, Sort.by(sorts));

        return this.reCommentRepository.findAllByAuthor(user, pageable);
    }
}

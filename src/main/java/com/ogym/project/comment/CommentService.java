package com.ogym.project.comment;

import com.ogym.project.DataNotFoundException;
import com.ogym.project.board.Board;
import com.ogym.project.user.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;

    public Comment create(String content, SiteUser author, Board board) {

        Comment comment = new Comment();

        comment.setContent(content);
        comment.setAuthor(author);
        comment.setBoard(board);
        comment.setCreateDate(LocalDateTime.now());

        this.commentRepository.save(comment);

        return comment;
    }

    public void modify(Comment comment, String content) {

        comment.setContent(content);
        comment.setModifyDate(LocalDateTime.now());

        this.commentRepository.save(comment);
    }

    public void delete(Comment comment) {
        this.commentRepository.delete(comment);
    }

    public Comment getComment(Long id) {
        Optional<Comment> oc = this.commentRepository.findById(id);
        if (oc.isPresent()) {
            return oc.get();
        } else {
            throw new DataNotFoundException("comment not found");
        }
    }
}

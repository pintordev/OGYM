package com.ogym.project.board.comment;

import com.ogym.project.DataNotFoundException;
import com.ogym.project.board.board.Board;
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

    public Page<Comment> getList(Board presentBoard, int cPage, String cSort) {

        Pageable pageable = PageRequest.of(cPage, 10);

        if (cSort.equals("vote")) {
            return this.commentRepository.findAllByBoardOrderByVoterCount(presentBoard.getId(), pageable);
        } else if (cSort.equals("reComment")) {
            return this.commentRepository.findAllByBoardOrderByReCommentCount(presentBoard.getId(), pageable);
        } else {
            return this.commentRepository.findAllByBoardOrderByCreateDate(presentBoard.getId(), pageable);
        }
    }

    public Comment getComment(Long id) {
        Optional<Comment> oc = this.commentRepository.findById(id);
        if (oc.isPresent()) {
            return oc.get();
        } else {
            throw new DataNotFoundException("comment not found");
        }
    }

    public void vote(Comment comment, SiteUser voter) {
        if (comment.getVoter().contains(voter)) {
            comment.getVoter().remove(voter);
        } else {
            comment.getVoter().add(voter);
        }
        this.commentRepository.save(comment);
    }

    public Page<Comment> getListByUser(int cPage, SiteUser user) {

        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(cPage, 10, Sort.by(sorts));

        return this.commentRepository.findAllByAuthor(user, pageable);
    }

    public int getPage(Long id, Board board) {
        int count = this.commentRepository.findAllByBoardAndCommentIdGreaterThanEqual(board.getId(), id).size();
        List<Comment> commentList = this.commentRepository.findAllByBoardAndCommentIdGreaterThanEqual(board.getId(), id);
        for (Comment comment : commentList) {
            System.out.println(comment.getId());
        }
        return (count - 1) / 10;
    }
}

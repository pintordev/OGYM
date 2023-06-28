package com.ogym.project.board.comment;

import com.ogym.project.board.board.Board;
import com.ogym.project.user.user.SiteUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    // voter count
    @Query(value = "select "
            + "distinct c.*, count(cv.comment_id) as voter_count "
            + "from comment c "
            + "left outer join comment_voter cv on c.id = cv.comment_id "
            + "where c.board_id = :board_id "
            + "group by c.id, cv.comment_id "
            + "order by voter_count desc, c.create_date asc "
            , countQuery = "select count(*) from comment"
            , nativeQuery = true)
    Page<Comment> findAllByBoardOrderByVoterCount(@Param("board_id") Long boardId, Pageable pageable);

    // reComment count
    @Query(value = "select "
            + "distinct c.*, count(rc.comment_id) as re_comment_count "
            + "from comment c "
            + "left outer join re_comment rc on c.id = rc.comment_id "
            + "where c.board_id = :board_id "
            + "group by c.id, rc.comment_id "
            + "order by re_comment_count desc, c.create_date asc "
            , countQuery = "select count(*) from comment"
            , nativeQuery = true)
    Page<Comment> findAllByBoardOrderByReCommentCount(@Param("board_id") Long boardId, Pageable pageable);

    // create date
    @Query(value = "select "
            + "distinct c.* "
            + "from comment c "
            + "where c.board_id = :board_id "
            + "order by c.create_date asc "
            , countQuery = "select count(*) from comment"
            , nativeQuery = true)
    Page<Comment> findAllByBoardOrderByCreateDate(@Param("board_id") Long boardId, Pageable pageable);

    Page<Comment> findAllByAuthor(SiteUser author, Pageable pageable);

    @Query(value = "select "
            + "* from comment c "
            + "where c.board_id = :board_id "
            + "and c.id <= :comment_id "
            , nativeQuery = true)
    List<Comment> findAllByBoardAndCommentIdGreaterThanEqual(@Param("board_id") Long boardId, @Param("comment_id") Long commentId);
}

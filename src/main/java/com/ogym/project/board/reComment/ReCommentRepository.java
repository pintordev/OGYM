package com.ogym.project.board.reComment;

import com.ogym.project.board.board.Board;
import com.ogym.project.user.user.SiteUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReCommentRepository extends JpaRepository<ReComment, Long> {
    Page<ReComment> findAllByAuthor(SiteUser author, Pageable pageable);
}

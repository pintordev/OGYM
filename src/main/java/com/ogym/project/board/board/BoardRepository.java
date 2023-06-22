package com.ogym.project.board.board;

import com.ogym.project.board.category.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BoardRepository extends JpaRepository<Board, Long> {

    // all, voter count
    @Query(value = "select "
            + "distinct b.*, count(bv.board_id) as voter_count "
            + "from board b "
            + "left outer join board_voter bv on b.id = bv.board_id "
            + "left outer join site_user u on b.author_id = u.id "
            + "left outer join comment c on b.id = c.board_id "
            + "left outer join category bc on b.category_id = bc.id "
            + "where bc.name like %:bCategory% and "
            + "(b.title like %:btKw% "
            + "or b.content like %:bcKw% "
            + "or u.nickname like %:baKw% "
            + "or c.content like %:ccKw%) "
            + "group by b.id, bv.board_id "
            + "order by voter_count desc, b.create_date desc "
            , countQuery = "select count(*) from board"
            , nativeQuery = true)
    Page<Board> findAllByOrderByVoterCount(@Param("bCategory") String bCategory,
                                                @Param("btKw") String btKw,
                                                @Param("bcKw") String bcKw,
                                                @Param("baKw") String baKw,
                                                @Param("ccKw") String ccKw,
                                                Pageable pageable);

    // all, comment count
    @Query(value = "select "
            + "distinct b.*, count(c.board_id) as comment_count "
            + "from board b "
            + "left outer join site_user u on b.author_id = u.id "
            + "left outer join comment c on b.id = c.board_id "
            + "left outer join category bc on b.category_id = bc.id "
            + "where bc.name like %:bCategory% and "
            + "(b.title like %:btKw% "
            + "or b.content like %:bcKw% "
            + "or u.nickname like %:baKw% "
            + "or c.content like %:ccKw%) "
            + "group by b.id, c.board_id "
            + "order by comment_count desc, b.create_date desc "
            , countQuery = "select count(*) from board"
            , nativeQuery = true)
    Page<Board> findAllByOrderByCommentCount(@Param("bCategory") String bCategory,
                                                  @Param("btKw") String btKw,
                                                  @Param("bcKw") String bcKw,
                                                  @Param("baKw") String baKw,
                                                  @Param("ccKw") String ccKw,
                                                  Pageable pageable);

    // all, create date
    @Query(value = "select "
            + "distinct b.* "
            + "from board b "
            + "left outer join site_user u on b.author_id = u.id "
            + "left outer join comment c on b.id = c.board_id "
            + "left outer join category bc on b.category_id = bc.id "
            + "where bc.name like %:bCategory% and "
            + "(b.title like %:btKw% "
            + "or b.content like %:bcKw% "
            + "or u.nickname like %:baKw% "
            + "or c.content like %:ccKw%) "
            + "order by b.create_date desc "
            , countQuery = "select count(*) from board"
            , nativeQuery = true)
    Page<Board> findAllByOrderByCreateDate(@Param("bCategory") String bCategory,
                                                  @Param("btKw") String btKw,
                                                  @Param("bcKw") String bcKw,
                                                  @Param("baKw") String baKw,
                                                  @Param("ccKw") String ccKw,
                                                  Pageable pageable);

    // board title, voter count
    @Query(value = "select "
            + "distinct b.*, count(bv.board_id) as voter_count "
            + "from board b "
            + "left outer join board_voter bv on b.id = bv.board_id "
            + "left outer join site_user u on b.author_id = u.id "
            + "left outer join comment c on b.id = c.board_id "
            + "left outer join category bc on b.category_id = bc.id "
            + "where bc.name like %:bCategory% and "
            + "b.title like %:btKw% "
            + "group by b.id, bv.board_id "
            + "order by voter_count desc, b.create_date desc "
            , countQuery = "select count(*) from board"
            , nativeQuery = true)
    Page<Board> findAllByBoardTitleOrderByVoterCount(@Param("bCategory") String bCategory,
                                                     @Param("btKw") String btKw,
                                                     Pageable pageable);

    // board title, comment count
    @Query(value = "select "
            + "distinct b.*, count(c.board_id) as comment_count "
            + "from board b "
            + "left outer join site_user u on b.author_id = u.id "
            + "left outer join comment c on b.id = c.board_id "
            + "left outer join category bc on b.category_id = bc.id "
            + "where bc.name like %:bCategory% and "
            + "b.title like %:btKw% "
            + "group by b.id, c.board_id "
            + "order by comment_count desc, b.create_date desc "
            , countQuery = "select count(*) from board"
            , nativeQuery = true)
    Page<Board> findAllByBoardTitleOrderByCommentCount(@Param("bCategory") String bCategory,
                                                       @Param("btKw") String btKw,
                                                       Pageable pageable);

    // board title, create date
    @Query(value = "select "
            + "distinct b.* "
            + "from board b "
            + "left outer join site_user u on b.author_id = u.id "
            + "left outer join comment c on b.id = c.board_id "
            + "left outer join category bc on b.category_id = bc.id "
            + "where bc.name like %:bCategory% and "
            + "b.title like %:btKw% "
            + "order by b.create_date desc "
            , countQuery = "select count(*) from board"
            , nativeQuery = true)
    Page<Board> findAllByBoardTitleOrderByCreateDate(@Param("bCategory") String bCategory,
                                                     @Param("btKw") String btKw,
                                                     Pageable pageable);

    // board content, voter count
    @Query(value = "select "
            + "distinct b.*, count(bv.board_id) as voter_count "
            + "from board b "
            + "left outer join board_voter bv on b.id = bv.board_id "
            + "left outer join site_user u on b.author_id = u.id "
            + "left outer join comment c on b.id = c.board_id "
            + "left outer join category bc on b.category_id = bc.id "
            + "where bc.name like %:bCategory% and "
            + "b.content like %:bcKw% "
            + "group by b.id, bv.board_id "
            + "order by voter_count desc, b.create_date desc "
            , countQuery = "select count(*) from board"
            , nativeQuery = true)
    Page<Board> findAllByBoardContentOrderByVoterCount(@Param("bCategory") String bCategory,
                                                       @Param("bcKw") String bcKw,
                                                       Pageable pageable);

    // board content, comment count
    @Query(value = "select "
            + "distinct b.*, count(c.board_id) as comment_count "
            + "from board b "
            + "left outer join site_user u on b.author_id = u.id "
            + "left outer join comment c on b.id = c.board_id "
            + "left outer join category bc on b.category_id = bc.id "
            + "where bc.name like %:bCategory% and "
            + "b.content like %:bcKw% "
            + "group by b.id, c.board_id "
            + "order by comment_count desc, b.create_date desc "
            , countQuery = "select count(*) from board"
            , nativeQuery = true)
    Page<Board> findAllByBoardContentOrderByCommentCount(@Param("bCategory") String bCategory,
                                                         @Param("bcKw") String bcKw,
                                                         Pageable pageable);

    // board content, create date
    @Query(value = "select "
            + "distinct b.* "
            + "from board b "
            + "left outer join site_user u on b.author_id = u.id "
            + "left outer join comment c on b.id = c.board_id "
            + "left outer join category bc on b.category_id = bc.id "
            + "where bc.name like %:bCategory% and "
            + "b.content like %:bcKw% "
            + "order by b.create_date desc "
            , countQuery = "select count(*) from board"
            , nativeQuery = true)
    Page<Board> findAllByBoardContentOrderByCreateDate(@Param("bCategory") String bCategory,
                                                       @Param("bcKw") String bcKw,
                                                       Pageable pageable);

    // title & content, voter count
    @Query(value = "select "
            + "distinct b.*, count(bv.board_id) as voter_count "
            + "from board b "
            + "left outer join board_voter bv on b.id = bv.board_id "
            + "left outer join site_user u on b.author_id = u.id "
            + "left outer join comment c on b.id = c.board_id "
            + "left outer join category bc on b.category_id = bc.id "
            + "where bc.name like %:bCategory% and "
            + "(b.title like %:btKw% "
            + "or b.content like %:bcKw%) "
            + "group by b.id, bv.board_id "
            + "order by voter_count desc, b.create_date desc "
            , countQuery = "select count(*) from board"
            , nativeQuery = true)
    Page<Board> findAllByBoardTitleAndBoardContentOrderByVoterCount(@Param("bCategory") String bCategory,
                                                                    @Param("btKw") String btKw,
                                                                    @Param("bcKw") String bcKw,
                                                                    Pageable pageable);

    // title & content, comment count
    @Query(value = "select "
            + "distinct b.*, count(c.board_id) as comment_count "
            + "from board b "
            + "left outer join site_user u on b.author_id = u.id "
            + "left outer join comment c on b.id = c.board_id "
            + "left outer join category bc on b.category_id = bc.id "
            + "where bc.name like %:bCategory% and "
            + "(b.title like %:btKw% "
            + "or b.content like %:bcKw%) "
            + "group by b.id, c.board_id "
            + "order by comment_count desc, b.create_date desc "
            , countQuery = "select count(*) from board"
            , nativeQuery = true)
    Page<Board> findAllByBoardTitleAndBoardContentOrderByCommentCount(@Param("bCategory") String bCategory,
                                                                      @Param("btKw") String btKw,
                                                                      @Param("bcKw") String bcKw,
                                                                      Pageable pageable);

    // title & content, create date
    @Query(value = "select "
            + "distinct b.* "
            + "from board b "
            + "left outer join site_user u on b.author_id = u.id "
            + "left outer join comment c on b.id = c.board_id "
            + "left outer join category bc on b.category_id = bc.id "
            + "where bc.name like %:bCategory% and "
            + "(b.title like %:btKw% "
            + "or b.content like %:bcKw%) "
            + "order by b.create_date desc "
            , countQuery = "select count(*) from board"
            , nativeQuery = true)
    Page<Board> findAllByBoardTitleAndBoardContentOrderByCreateDate(@Param("bCategory") String bCategory,
                                                                    @Param("btKw") String btKw,
                                                                    @Param("bcKw") String bcKw,
                                                                    Pageable pageable);

    // board author, voter count
    @Query(value = "select "
            + "distinct b.*, count(bv.board_id) as voter_count "
            + "from board b "
            + "left outer join board_voter bv on b.id = bv.board_id "
            + "left outer join site_user u on b.author_id = u.id "
            + "left outer join comment c on b.id = c.board_id "
            + "left outer join category bc on b.category_id = bc.id "
            + "where bc.name like %:bCategory% and "
            + "u.nickname like %:baKw% "
            + "group by b.id, bv.board_id "
            + "order by voter_count desc, b.create_date desc "
            , countQuery = "select count(*) from board"
            , nativeQuery = true)
    Page<Board> findAllByBoardAuthorOrderByVoterCount(@Param("bCategory") String bCategory,
                                                      @Param("baKw") String baKw,
                                                      Pageable pageable);

    // board author, comment count
    @Query(value = "select "
            + "distinct b.*, count(c.board_id) as comment_count "
            + "from board b "
            + "left outer join site_user u on b.author_id = u.id "
            + "left outer join comment c on b.id = c.board_id "
            + "left outer join category bc on b.category_id = bc.id "
            + "where bc.name like %:bCategory% and "
            + "u.nickname like %:baKw% "
            + "group by b.id, c.board_id "
            + "order by comment_count desc, b.create_date desc "
            , countQuery = "select count(*) from board"
            , nativeQuery = true)
    Page<Board> findAllByBoardAuthorOrderByCommentCount(@Param("bCategory") String bCategory,
                                                        @Param("baKw") String baKw,
                                                        Pageable pageable);

    // board author, create date
    @Query(value = "select "
            + "distinct b.* "
            + "from board b "
            + "left outer join site_user u on b.author_id = u.id "
            + "left outer join comment c on b.id = c.board_id "
            + "left outer join category bc on b.category_id = bc.id "
            + "where bc.name like %:bCategory% and "
            + "u.nickname like %:baKw% "
            + "order by b.create_date desc "
            , countQuery = "select count(*) from board"
            , nativeQuery = true)
    Page<Board> findAllByBoardAuthorOrderByCreateDate(@Param("bCategory") String bCategory,
                                                      @Param("baKw") String baKw,
                                                      Pageable pageable);

    // comment content, voter count
    @Query(value = "select "
            + "distinct b.*, count(bv.board_id) as voter_count "
            + "from board b "
            + "left outer join board_voter bv on b.id = bv.board_id "
            + "left outer join site_user u on b.author_id = u.id "
            + "left outer join comment c on b.id = c.board_id "
            + "left outer join category bc on b.category_id = bc.id "
            + "where bc.name like %:bCategory% and "
            + "c.content like %:ccKw% "
            + "group by b.id, bv.board_id "
            + "order by voter_count desc, b.create_date desc "
            , countQuery = "select count(*) from board"
            , nativeQuery = true)
    Page<Board> findAllByCommentContentOrderByVoterCount(@Param("bCategory") String bCategory, @Param("ccKw") String ccKw, Pageable pageable);

    // comment content, comment count
    @Query(value = "select "
            + "distinct b.*, count(c.board_id) as comment_count "
            + "from board b "
            + "left outer join site_user u on b.author_id = u.id "
            + "left outer join comment c on b.id = c.board_id "
            + "left outer join category bc on b.category_id = bc.id "
            + "where bc.name like %:bCategory% and "
            + "c.content like %:ccKw% "
            + "group by b.id, c.board_id "
            + "order by comment_count desc, b.create_date desc "
            , countQuery = "select count(*) from board"
            , nativeQuery = true)
    Page<Board> findAllByCommentContentOrderByCommentCount(@Param("bCategory") String bCategory, @Param("ccKw") String ccKw, Pageable pageable);

    // comment content, create date
    @Query(value = "select "
            + "distinct b.* "
            + "from board b "
            + "left outer join site_user u on b.author_id = u.id "
            + "left outer join comment c on b.id = c.board_id "
            + "left outer join category bc on b.category_id = bc.id "
            + "where bc.name like %:bCategory% and "
            + "c.content like %:ccKw% "
            + "order by b.create_date desc "
            , countQuery = "select count(*) from board"
            , nativeQuery = true)
    Page<Board> findAllByCommentContentOrderByCreateDate(@Param("bCategory") String bCategory, @Param("ccKw") String ccKw, Pageable pageable);

    Long countByCategory(Category category);
}

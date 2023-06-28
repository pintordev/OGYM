package com.ogym.project.board.board;

import com.ogym.project.DataNotFoundException;
import com.ogym.project.board.category.Category;
import com.ogym.project.board.category.CategoryRepository;
import com.ogym.project.user.user.SiteUser;
import jakarta.persistence.criteria.CriteriaBuilder;
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
public class BoardService {

    private final BoardRepository boardRepository;
    private final CategoryRepository categoryRepository;

    public Board create(String title, String content, SiteUser author, Category category) {

        Board board = new Board();

        board.setTitle(title);
        board.setContent(content);
        board.setAuthor(author);
        board.setCategory(category);
        board.setCreateDate(LocalDateTime.now());
        board.setHit(0);

        this.boardRepository.save(board);

        return board;
    }

    public void modify(Board board, String title, String content, Category category) {

        board.setTitle(title);
        board.setContent(content);
        board.setCategory(category);
        board.setModifyDate(LocalDateTime.now());

        this.boardRepository.save(board);
    }

    public void delete(Board board) {
        this.boardRepository.delete(board);
    }

    public Page<Board> getList(int bPageSize, int bPage, String bSearch, String bKw, String bSort, String bCategory) {

        String btKw = "", bcKw = "", baKw = "", ccKw = "";

        Pageable pageable = PageRequest.of(bPage, bPageSize);

        if (bSearch.equals("all")) {

            btKw = bKw;
            bcKw = bKw;
            baKw = bKw;
            ccKw = bKw;

            if (bSort.equals("vote")) {
                return this.boardRepository.findAllByOrderByVoterCount(bCategory, btKw, bcKw, baKw, ccKw, pageable);
            } else if (bSort.equals("comment")) {
                return this.boardRepository.findAllByOrderByCommentCount(bCategory, btKw, bcKw, baKw, ccKw, pageable);
            } else {
                return this.boardRepository.findAllByOrderByCreateDate(bCategory, btKw, bcKw, baKw, ccKw, pageable);
            }

        } else if (bSearch.equals("title")) {

            btKw = bKw;

            if (bSort.equals("vote")) {
                return this.boardRepository.findAllByBoardTitleOrderByVoterCount(bCategory, btKw, pageable);
            } else if (bSort.equals("comment")) {
                return this.boardRepository.findAllByBoardTitleOrderByCommentCount(bCategory, btKw, pageable);
            } else {
                return this.boardRepository.findAllByBoardTitleOrderByCreateDate(bCategory, btKw, pageable);
            }

        } else if (bSearch.equals("content")) {

            bcKw = bKw;

            if (bSort.equals("vote")) {
                return this.boardRepository.findAllByBoardContentOrderByVoterCount(bCategory, bcKw, pageable);
            } else if (bSort.equals("comment")) {
                return this.boardRepository.findAllByBoardContentOrderByCommentCount(bCategory, bcKw, pageable);
            } else {
                return this.boardRepository.findAllByBoardContentOrderByCreateDate(bCategory, bcKw, pageable);
            }

        } else if (bSearch.equals("title_content")) {

            btKw = bKw;
            bcKw = bKw;

            if (bSort.equals("vote")) {
                return this.boardRepository.findAllByBoardTitleAndBoardContentOrderByVoterCount(bCategory, btKw, bcKw, pageable);
            } else if (bSort.equals("comment")) {
                return this.boardRepository.findAllByBoardTitleAndBoardContentOrderByCommentCount(bCategory, btKw, bcKw, pageable);
            } else {
                return this.boardRepository.findAllByBoardTitleAndBoardContentOrderByCreateDate(bCategory, btKw, bcKw, pageable);
            }

        } else if (bSearch.equals("author")) {

            baKw = bKw;

            if (bSort.equals("vote")) {
                return this.boardRepository.findAllByBoardAuthorOrderByVoterCount(bCategory, baKw, pageable);
            } else if (bSort.equals("comment")) {
                return this.boardRepository.findAllByBoardAuthorOrderByCommentCount(bCategory, baKw, pageable);
            } else {
                return this.boardRepository.findAllByBoardAuthorOrderByCreateDate(bCategory, baKw, pageable);
            }

        } else if (bSearch.equals("comment")) {

            ccKw = bKw;

            if (bSort.equals("vote")) {
                return this.boardRepository.findAllByCommentContentOrderByVoterCount(bCategory, ccKw, pageable);
            } else if (bSort.equals("comment")) {
                return this.boardRepository.findAllByCommentContentOrderByCommentCount(bCategory, ccKw, pageable);
            } else {
                return this.boardRepository.findAllByCommentContentOrderByCreateDate(bCategory, ccKw, pageable);
            }

        }

        return null;
    }

    public Board getBoard(Long id) {
        Optional<Board> ob = this.boardRepository.findById(id);
        if (ob.isPresent()) {
            return ob.get();
        } else {
            throw new DataNotFoundException("board not found");
        }
    }

    public void vote(Board board, SiteUser voter) {
        if (board.getVoter().contains(voter)) {
            board.getVoter().remove(voter);
        } else {
            board.getVoter().add(voter);
        }
        this.boardRepository.save(board);
    }

    public Long getCount(Category category) {
        return this.boardRepository.countByCategory(category);
    }

    public Page<Board> getListByUser(int bPage, SiteUser user) {

        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(bPage, 10, Sort.by(sorts));

        return this.boardRepository.findAllByAuthor(user, pageable);
    }

    public List<Board> getBestBoard() {
        return this.boardRepository.findTop10OrderByVoterCount();
    }

    public List<Board> getRecentBoard() {
        return this.boardRepository.findTop10ByOrderByCreateDateDesc();
    }
}

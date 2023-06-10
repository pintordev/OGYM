package com.ogym.project.board;

import com.ogym.project.boardCategory.BoardCategory;
import com.ogym.project.boardCategory.BoardCategoryRepository;
import com.ogym.project.user.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;
    private final BoardCategoryRepository boardCategoryRepository;

    public Board create(String title, String content, SiteUser author, BoardCategory boardCategory) {

        Board board = new Board();

        board.setTitle(title);
        board.setContent(content);
        board.setAuthor(author);
        board.setBoardCategory(boardCategory);
        board.setCreateDate(LocalDateTime.now());

        this.boardRepository.save(board);

        return board;
    }
}

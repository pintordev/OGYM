package com.ogym.project.boardCategory;

import com.ogym.project.board.Board;
import com.ogym.project.user.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BoardCategoryService {

    private final BoardCategoryRepository boardCategoryRepository;

    public void create(String name) {
        BoardCategory boardCategory = new BoardCategory();
        boardCategory.setName(name);
        this.boardCategoryRepository.save(boardCategory);
    }

    public List<BoardCategory> getList() {
        return this.boardCategoryRepository.findByOrderByIdAsc();
    }

    public BoardCategory getBoardCategory(String name) {
        return this.boardCategoryRepository.findByName(name);
    }
}

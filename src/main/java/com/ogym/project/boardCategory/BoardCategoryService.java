package com.ogym.project.boardCategory;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BoardCategoryService {

    private final BoardCategoryRepository boardCategoryRepository;

    public List<BoardCategory> getList() {
        return this.boardCategoryRepository.findByOrderByIdAsc();
    }

    public void create(String name) {
        BoardCategory boardCategory = new BoardCategory();
        boardCategory.setName(name);
        this.boardCategoryRepository.save(boardCategory);
    }
}

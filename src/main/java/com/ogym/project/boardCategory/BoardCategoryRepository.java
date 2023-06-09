package com.ogym.project.boardCategory;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardCategoryRepository extends JpaRepository<BoardCategory, Integer> {
    List<BoardCategory> findByOrderByIdAsc();
}

package com.ogym.project.board.category;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    List<Category> findByOrderByIdAsc();
    Category findByName(String name);
}

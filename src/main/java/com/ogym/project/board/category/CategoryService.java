package com.ogym.project.board.category;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public void create(String name) {
        Category category = new Category();
        category.setName(name);
        this.categoryRepository.save(category);
    }

    public List<Category> getList() {
        return this.categoryRepository.findByOrderByIdAsc();
    }

    public Category getCategory(String name) {
        return this.categoryRepository.findByName(name);
    }
}

package com.ogym.project.admin;

import com.ogym.project.boardCategory.BoardCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/admin")
@RequiredArgsConstructor
@Controller
public class AdminController {

    private final BoardCategoryService boardCategoryService;

    @GetMapping("/category")
    public String createBoardCategory() {
        return "category_form";
    }

    @PostMapping("/category")
    public String createBoardCategory(@RequestParam String name) {
        this.boardCategoryService.create(name);
        return "redirect:/admin/category";
    }
}

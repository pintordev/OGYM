package com.ogym.project.trainer;

import com.ogym.project.comment.CommentService;

import com.ogym.project.user.SiteUser;

import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/trainer")
@RequiredArgsConstructor
@Controller
public class TrainerController {
    private final TrainerService trainerService;

    @GetMapping("")
    public String root() {
        return "trainer_form";
    }

    @GetMapping("/register")
    public String index() {
        return "trainer_form";
    }
}





package com.ogym.project.trainer;

import com.ogym.project.comment.CommentService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/trainer")
@RequiredArgsConstructor
@Controller
public class TrainerController {

    @GetMapping("")
    public String main() {
        return "trainer_list";
    }

    @GetMapping("/detail")
    public String detail() {
        return "trainer_detail";
    }

}


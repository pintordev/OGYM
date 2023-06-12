package com.ogym.project.trainer;

import com.ogym.project.comment.CommentService;
import com.ogym.project.user.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/trainer")
@RequiredArgsConstructor
@Controller
public class TrainerController {
    private final TrainerService trainerService;
@GetMapping
    public String list(){
    return "trainer_form";
}
@PostMapping("/regist/{id}")
    public String createTrainer(Model model, @PathVariable("id") Long id, @RequestParam SiteUser trainerInfo){

return String.format("redirect:/trainer/regist/%S",id);
}
    }


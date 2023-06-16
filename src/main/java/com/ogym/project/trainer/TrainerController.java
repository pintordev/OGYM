package com.ogym.project.trainer;

import com.ogym.project.trainerAddress.TrainerAddress;
import com.ogym.project.trainerAddress.TrainerAddressService;
import com.ogym.project.trainerContact.TrainerContactService;
import com.ogym.project.trainerField.TrainerField;
import com.ogym.project.trainerField.TrainerFieldService;
import com.ogym.project.trainerLesson.TrainerLessonService;

import jakarta.validation.Valid;
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
    private final TrainerAddressService trainerAddressService;
    private final TrainerContactService trainerContactService;
    private final TrainerFieldService trainerFieldService;
    private final TrainerLessonService trainerLessonService;

    @GetMapping("")
    public String main() {
        return "trainer_list";
    }

    @GetMapping("/detail")
    public String detail() {
        return "trainer_detail";
    }

    @GetMapping("/register")
    public String register(TrainerCreateForm trainerCreateForm) {
        return "trainer_form";
    }

    @PostMapping("/register")
    public String trainerRegister(Model model, @Valid TrainerCreateForm trainerCreateForm, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            List<TrainerField> trainerFieldList = this.trainerService.getTrainerItem();
            model.addAttribute("trainerFieldList", trainerFieldList);
            System.out.println("error");
            return "trainer_form";
        }

        System.out.println(trainerCreateForm.getName());
        System.out.println(trainerCreateForm.getCenter());
        System.out.println(trainerCreateForm.getIntroAbstract());
        System.out.println(trainerCreateForm.getIntroDetail());
        for (String add : trainerCreateForm.getAddress()) {
            System.out.println(add);
        }

//trainerService.create(trainerCreateForm.getTrainerInfo(),trainerCreateForm.getName(), trainerCreateForm.getCenter(),
//        trainerCreateForm.getAddress(), trainerCreateForm.getGender(), trainerCreateForm.getNumber(), trainerCreateForm.getIntroAbstract(),
//        trainerCreateForm.getIntroDetail(), trainerCreateForm.getCreateDate());
        return "redirect:/trainer";

    }
}





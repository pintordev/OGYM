package com.ogym.project.trainer.trainer;

import com.ogym.project.trainer.address.AddressService;
import com.ogym.project.trainer.contact.ContactService;
import com.ogym.project.trainer.field.Field;
import com.ogym.project.trainer.field.FieldService;
import com.ogym.project.trainer.lesson.LessonService;

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
    private final AddressService addressService;
    private final ContactService contactService;
    private final FieldService fieldService;
    private final LessonService lessonService;

    @GetMapping("")
    public String main() {
        return "trainer_list";
    }

    @GetMapping("/detail")
    public String detail() {
        return "trainer_detail";
    }

    @GetMapping("/register")
    public String register(TrainerForm trainerForm) {
        return "trainer_form";
    }

    @PostMapping("/register")
    public String register(Model model, @Valid TrainerForm trainerForm, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            List<Field> fieldList = this.fieldService.getList();
            model.addAttribute("fieldList", fieldList);
            System.out.println("error");
            return "trainer_form";
        }
        System.out.println(trainerForm.getName());
        System.out.println(trainerForm.getCenter());
        System.out.println(trainerForm.getGender());
        System.out.println(trainerForm.getIntroAbstract());
        System.out.println(trainerForm.getIntroDetail());
        System.out.println(trainerForm.getFieldList());
        System.out.println(trainerForm.getLessonList());
        System.out.println(trainerForm.getContactList());
        System.out.println(trainerForm.getCertificateList());
        System.out.println(trainerForm.getAddress().getZoneCode());
        System.out.println(trainerForm.getAddress().getMainAddress());
        System.out.println(trainerForm.getAddress().getSubAddress());
        System.out.println(trainerForm.getAddress().getLatitude());
        System.out.println(trainerForm.getAddress().getLongitude());


trainerService.create(trainerForm.getName(),trainerForm.getCenter(), trainerForm.getGender(),
        trainerForm.getIntroAbstract(), trainerForm.getIntroDetail(), trainerForm.getFieldList(), trainerForm.getLessonList(),
        trainerForm.getContactList(), trainerForm.getCertificateList(),trainerForm.getAddress().getZoneCode(),trainerForm.getAddress().getMainAddress(),
        trainerForm.getAddress().getSubAddress(), trainerForm.getAddress().getLatitude(), trainerForm.getAddress().getLongitude());
        return "redirect:/trainer";

    }
}





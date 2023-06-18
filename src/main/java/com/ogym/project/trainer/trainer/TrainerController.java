package com.ogym.project.trainer.trainer;

import com.ogym.project.trainer.address.Address;
import com.ogym.project.trainer.address.AddressForm;
import com.ogym.project.trainer.address.AddressService;
import com.ogym.project.trainer.certificate.CertificateForm;
import com.ogym.project.trainer.contact.ContactForm;
import com.ogym.project.trainer.contact.ContactService;
import com.ogym.project.trainer.field.Field;
import com.ogym.project.trainer.field.FieldService;
import com.ogym.project.trainer.lesson.LessonForm;
import com.ogym.project.trainer.lesson.LessonService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
    public String register(Model model, TrainerForm trainerForm) {
        List<Field> fieldList = this.fieldService.getList();
        model.addAttribute("FieldList",fieldList);
        return "trainer_form";
    }

    @PostMapping("/register")
    public String register(Model model,
                           @Valid TrainerForm trainerForm,
                           BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            List<Field> fieldList = this.fieldService.getList();
            model.addAttribute("fieldList", fieldList);
            System.out.println("error");
            return "trainer_form";
        }
        System.out.println("들어옴");
        System.out.println("name = " + trainerForm.getName());
        System.out.println("center = " + trainerForm.getCenter());
        System.out.println("gender = " + trainerForm.getGender());
        System.out.println("introAbstract = " + trainerForm.getIntroAbstract());
        System.out.println("introDetail = " + trainerForm.getIntroDetail());
        System.out.println("zoneCodeAddress = " + trainerForm.getAddress().getZoneCode());
        System.out.println("MainAddress = " + trainerForm.getAddress().getMainAddress());

        System.out.println("LatitudeAddress = " + trainerForm.getAddress().getLatitude());

        int index = 0;
        for (LessonForm lessonForm : trainerForm.getLessonList()) {
            System.out.printf("Lesson #%d: time = %s, price = %s\n", ++index, lessonForm.getTime(), lessonForm.getPrice());
        }

        index = 0;
        for (ContactForm contactForm : trainerForm.getContactList()) {
            System.out.printf("contact #%d : type = %s, content = %s\n", ++index, contactForm.getType(), contactForm.getContent());
        }



        index = 0;
        for (CertificateForm certificateForm : trainerForm.getCertificateList()) {
            System.out.printf("contact #%d : type = %s, content = %s\n", ++index, certificateForm.getName(), certificateForm.getImgUrl());
        }

        // Form 데이터를 실제 객체로 변환
        List<Field> fieldList = new ArrayList<>();
        for (String field : trainerForm.getFieldList()) {
            fieldList.add(this.fieldService.getField(field));
        }
        index = 0;
        for (String field : trainerForm.getFieldList()) {
            System.out.printf("field #%d : name = %s\n", ++index, field);
        }
        // 트레이너 기본 정보 저장
        Trainer trainer = this.trainerService.create(trainerForm.getName(), trainerForm.getCenter(),
                trainerForm.getGender(), trainerForm.getIntroAbstract(),
                trainerForm.getIntroDetail(), fieldList);

        // 레슨 정보 저장
        for (LessonForm lessonForm : trainerForm.getLessonList()) {
            this.lessonService.create(lessonForm.getTime(), lessonForm.getPrice(), trainer);
        }

        // 주소 정보 저장



        return "redirect:/trainer";
    }
}




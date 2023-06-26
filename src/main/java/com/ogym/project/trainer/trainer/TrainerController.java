package com.ogym.project.trainer.trainer;

import com.ogym.project.trainer.address.Address;
import com.ogym.project.trainer.address.AddressForm;
import com.ogym.project.trainer.address.AddressService;
import com.ogym.project.trainer.certificate.CertificateForm;
import com.ogym.project.trainer.certificate.CertificateService;
import com.ogym.project.trainer.contact.Contact;
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

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@RequestMapping("/trainer")
@RequiredArgsConstructor
@Controller
public class TrainerController {
    private final TrainerService trainerService;
    private final AddressService addressService;
    private final ContactService contactService;
    private final FieldService fieldService;
    private final LessonService lessonService;
    private final CertificateService certificateService;

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
        model.addAttribute("fieldList", fieldList);

        List<LessonForm> lessonList = new ArrayList<>();
        List<CertificateForm> certificateList = new ArrayList<>();
        List<ContactForm> contactList = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            lessonList.add(new LessonForm());
            certificateList.add(new CertificateForm());
            contactList.add(new ContactForm());
        }
        model.addAttribute("lessonList", lessonList);
        model.addAttribute("certificateList", certificateList);
        model.addAttribute("contactList", contactList);
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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isLoggedIn = authentication.isAuthenticated();
        if (!isLoggedIn) {
            model.addAttribute("popupMessage", "로그인이 필요한 서비스입니다.");

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
        System.out.println("subAddress = " + trainerForm.getAddress().getSubAddress());
        System.out.println("latitude = " + trainerForm.getAddress().getLatitude());
        System.out.println("longitude = " + trainerForm.getAddress().getLongitude());

        int index = 0;
        for (LessonForm lessonForm : trainerForm.getLessonList()) {
            if (lessonForm.getTime() != null && lessonForm.getPrice() != null) {
                System.out.printf("Lesson #%d: time = %s, price = %s\n", ++index, lessonForm.getTime(), lessonForm.getPrice());
            }
        }

        index = 0;
        for (ContactForm contactForm : trainerForm.getContactList()) {
            if (!contactForm.getType().equals("") && !contactForm.getContent().equals("")) {
                System.out.printf("contact #%d : type = %s, content = %s\n", ++index, contactForm.getType(), contactForm.getContent());
            }
        }

        index = 0;
        for (CertificateForm certificateForm : trainerForm.getCertificateList()) {
            if (!certificateForm.getName().equals("") && !certificateForm.getImgUrl().equals("")) {
                System.out.printf("name #%d : name = %s, imgUrl = %s\n", ++index, certificateForm.getName(), certificateForm.getImgUrl());
            }
        }

        // Form 데이터를 실제 객체로 변환
        List<Field> fieldList = new ArrayList<>();
        for (String field : trainerForm.getFieldList()) {
            fieldList.add(this.fieldService.getField(field));
        }

        // 트레이너 기본 정보 저장
        Trainer trainer = this.trainerService.create(trainerForm.getName(), trainerForm.getCenter(),
                trainerForm.getGender(), trainerForm.getIntroAbstract(),
                trainerForm.getIntroDetail(), fieldList);

        // 레슨 정보 저장
        for (LessonForm lessonForm : trainerForm.getLessonList()) {
            this.lessonService.create(lessonForm.getTime(), lessonForm.getPrice(), trainer);
        }

        // 연락처 정보 저장
        for (ContactForm contactForm : trainerForm.getContactList()) {
            this.contactService.create(contactForm.getType(), contactForm.getContent(), trainer);
        }

        // 자격증 정보 저장
        for (CertificateForm certificateForm : trainerForm.getCertificateList()) {
            this.certificateService.create(certificateForm.getName(), certificateForm.getImgUrl(), trainer);
        }


        return "redirect:/trainer";
    }
}




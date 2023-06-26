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

import com.ogym.project.user.user.SiteUser;
import com.ogym.project.user.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
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
    private final CertificateService certificateService;
    private final UserService userService;

    @GetMapping("")
    public String main() {
        return "trainer_list";
    }

    @GetMapping("/detail")
    public String detail() {
        return "trainer_detail";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/register")
    public String register(Model model, TrainerForm trainerForm, Principal principal) {

        SiteUser userInfo = this.userService.getUserByLoginId(principal.getName());
        model.addAttribute("username", userInfo.getUsername());

        List<Field> fieldList = this.fieldService.getList();
        model.addAttribute("fieldList",fieldList);

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
                           BindingResult bindingResult, Principal principal) {

        int length;

        System.out.println("들어옴");
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

        SiteUser userInfo = this.userService.getUserByLoginId(principal.getName());

        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult);

            model.addAttribute("username", userInfo.getUsername());

            List<Field> fieldList = this.fieldService.getList();
            model.addAttribute("fieldList", fieldList);

            List<LessonForm> lessonList = new ArrayList<>();
            length = trainerForm.getLessonList().size() > 3 ? trainerForm.getLessonList().size() : 3;
            for (int i = 0; i < length; i++) {
                if (i < trainerForm.getLessonList().size()) {
                    lessonList.add(trainerForm.getLessonList().get(i));
                } else {
                    lessonList.add(new LessonForm());
                }
            }
            model.addAttribute("lessonList", lessonList);

            List<CertificateForm> certificateList = new ArrayList<>();
            length = trainerForm.getCertificateList().size() > 3 ? trainerForm.getCertificateList().size() : 3;
            for (int i = 0; i < length; i++) {
                if (i < trainerForm.getCertificateList().size()) {
                    certificateList.add(trainerForm.getCertificateList().get(i));
                } else {
                    certificateList.add(new CertificateForm());
                }
            }
            model.addAttribute("certificateList", certificateList);

            List<ContactForm> contactList = new ArrayList<>();
            length = trainerForm.getContactList().size() > 3 ? trainerForm.getContactList().size() : 3;
            for (int i = 0; i < length; i++) {
                if (i < trainerForm.getContactList().size()) {
                    contactList.add(trainerForm.getContactList().get(i));
                } else {
                    contactList.add(new ContactForm());
                }
            }
            model.addAttribute("contactList", contactList);

            System.out.println("error");
            return "trainer_form";
        }

        if (this.trainerService.isRegistered(userInfo)) {
            bindingResult.rejectValue("siteuser", "duplicated",
                    "이미 트레이너로 등록된 계정입니다.");

            model.addAttribute("username", userInfo.getUsername());

            List<Field> fieldList = this.fieldService.getList();
            model.addAttribute("fieldList", fieldList);

            List<LessonForm> lessonList = new ArrayList<>();
            length = trainerForm.getLessonList().size() > 3 ? trainerForm.getLessonList().size() : 3;
            for (int i = 0; i < length; i++) {
                if (i < trainerForm.getLessonList().size()) {
                    lessonList.add(trainerForm.getLessonList().get(i));
                } else {
                    lessonList.add(new LessonForm());
                }
            }
            model.addAttribute("lessonList", lessonList);

            List<CertificateForm> certificateList = new ArrayList<>();
            length = trainerForm.getCertificateList().size() > 3 ? trainerForm.getCertificateList().size() : 3;
            for (int i = 0; i < length; i++) {
                if (i < trainerForm.getCertificateList().size()) {
                    certificateList.add(trainerForm.getCertificateList().get(i));
                } else {
                    certificateList.add(new CertificateForm());
                }
            }
            model.addAttribute("certificateList", certificateList);

            List<ContactForm> contactList = new ArrayList<>();
            length = trainerForm.getContactList().size() > 3 ? trainerForm.getContactList().size() : 3;
            for (int i = 0; i < length; i++) {
                if (i < trainerForm.getContactList().size()) {
                    contactList.add(trainerForm.getContactList().get(i));
                } else {
                    contactList.add(new ContactForm());
                }
            }
            model.addAttribute("contactList", contactList);

            System.out.println("error");
            return "trainer_form";
        }

        // Form 데이터를 실제 객체로 변환
        List<Field> fieldList = new ArrayList<>();
        for (String field : trainerForm.getFieldList()) {
            fieldList.add(this.fieldService.getField(field));
        }

        // 트레이너 기본 정보 저장
        Trainer trainer = this.trainerService.create(userInfo, trainerForm.getCenter(),
                trainerForm.getGender(), trainerForm.getIntroAbstract(),
                trainerForm.getIntroDetail(), fieldList);

        // 레슨 정보 저장
        for (LessonForm lessonForm : trainerForm.getLessonList()) {
            if (lessonForm.getTime() != null && lessonForm.getPrice() != null) {
                this.lessonService.create(lessonForm.getTime(), lessonForm.getPrice(), trainer);
            }
        }

        // 연락처 정보 저장
        for(ContactForm contactForm : trainerForm.getContactList()){
            if (!contactForm.getType().equals("") && !contactForm.getContent().equals("")) {
                this.contactService.create(contactForm.getType(), contactForm.getContent(), trainer);
            }
        }

        // 자격증 정보 저장
        for(CertificateForm certificateForm : trainerForm.getCertificateList()){
            if (!certificateForm.getName().equals("") && !certificateForm.getImgUrl().equals("")) {
                this.certificateService.create(certificateForm.getName(), certificateForm.getImgUrl(), trainer);
            }
        }

        return "redirect:/trainer";
    }
}



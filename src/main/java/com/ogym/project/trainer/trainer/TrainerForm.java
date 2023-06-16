package com.ogym.project.trainer.trainer;

import com.ogym.project.trainer.address.AddressForm;
import com.ogym.project.trainer.certificate.CertificateForm;
import com.ogym.project.trainer.contact.ContactForm;
import com.ogym.project.trainer.lesson.LessonForm;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TrainerForm {
    //유저이름

    private String username;

    private String name;

    private String center;

    private String gender;

    private String introAbstract;

    private String introDetail;

    private List<String> fieldList;

    private List<LessonForm> lessonList;

    private List<ContactForm> contactList;

    private List<CertificateForm> certificateList;

    private AddressForm address;
}

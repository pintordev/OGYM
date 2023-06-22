package com.ogym.project.trainer.trainer;

import com.ogym.project.trainer.address.AddressForm;
import com.ogym.project.trainer.certificate.CertificateForm;
import com.ogym.project.trainer.contact.ContactForm;
import com.ogym.project.trainer.field.Field;
import com.ogym.project.trainer.lesson.LessonForm;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TrainerForm {
    //유저이름

    private String username;

    @NotEmpty(message = "트레이너이름을 입력해주세요.")
    private String name;

    @NotEmpty(message = "할동중인 센터를 입력해주세요.")
    private String center;

    @NotEmpty
    private String gender;

    @NotEmpty
    private String introAbstract;

    @NotEmpty
    private String introDetail;

    @NotEmpty
    private List<String> fieldList;

    @NotEmpty
    private List<LessonForm> lessonList;

    @NotEmpty
    private List<ContactForm> contactList;

    @NotEmpty
    private List<CertificateForm> certificateList;


    private AddressForm address;
}

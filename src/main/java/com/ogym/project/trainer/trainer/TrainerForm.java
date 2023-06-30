package com.ogym.project.trainer.trainer;

import com.ogym.project.trainer.address.AddressForm;
import com.ogym.project.trainer.certificate.CertificateForm;
import com.ogym.project.trainer.contact.ContactForm;
import com.ogym.project.trainer.field.Field;
import com.ogym.project.trainer.lesson.LessonForm;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
public class TrainerForm {
    @NotEmpty
    private String username;

    @NotEmpty
    private String center;

    @NotEmpty
    private String gender;

    private MultipartFile profileImage;

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

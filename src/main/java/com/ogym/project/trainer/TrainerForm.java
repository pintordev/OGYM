package com.ogym.project.trainer;

import com.ogym.project.trainerAddress.TrainerAddressForm;
import com.ogym.project.trainerCertificate.TrainerCertificateForm;
import com.ogym.project.trainerContact.TrainerContactForm;
import com.ogym.project.trainerLesson.TrainerLessonForm;
import lombok.Getter;
import lombok.Setter;

import java.security.cert.Certificate;
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

    private List<TrainerLessonForm> lessonList;

    private List<TrainerContactForm> contactList;

    private List<TrainerCertificateForm> certificateList;

    private TrainerAddressForm address;


}

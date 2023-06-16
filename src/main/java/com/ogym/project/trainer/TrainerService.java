package com.ogym.project.trainer;

import com.ogym.project.trainerAddress.TrainerAddress;
import com.ogym.project.trainerCertificate.TrainerCertificate;
import com.ogym.project.trainerContact.TrainerContact;
import com.ogym.project.trainerField.TrainerField;
import com.ogym.project.trainerField.TrainerFieldRepository;
import com.ogym.project.trainerLesson.TrainerLesson;
import com.ogym.project.trainerLesson.TrainerLessonRepository;
import com.ogym.project.user.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class TrainerService {
    private final TrainerRepository trainerRepository;


    public Trainer create(SiteUser userInfo, String name, String center, String gender,
                          String introAbstract, String introDetail, List<TrainerField> fieldList,
                          List<TrainerLesson> lessonList, List<TrainerContact> contactList, List<TrainerCertificate> certificateList,
                          TrainerAddress address )
    {
        Trainer trainer = new Trainer();
        trainer.setUserInfo(userInfo);
        trainer.setName(name);
        trainer.setCenter(center);
        trainer.setGender(gender);
        trainer.setIntroAbstract(introAbstract);
        trainer.setIntroDetail(introDetail);
        trainer.setFieldList(fieldList);
        trainer.setLessonList(lessonList);
        trainer.setContactList(contactList);
        trainer.setCertificateList(certificateList);
        trainer.setAddress(address);



        this.trainerRepository.save(trainer);
        return trainer;
    }
    public List<TrainerField> getTrainerItem() {

        return this.trainerRepository.findByOrderByIdAsc();
    }

    public TrainerField trainerRepository(String name) {
        return this.trainerRepository.findByName(name);
    }
}

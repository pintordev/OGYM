package com.ogym.project.trainer.trainer;

import com.ogym.project.trainer.address.Address;
import com.ogym.project.trainer.certificate.Certificate;
import com.ogym.project.trainer.contact.Contact;
import com.ogym.project.trainer.field.Field;
import com.ogym.project.trainer.field.FieldRepository;
import com.ogym.project.trainer.lesson.Lesson;
import com.ogym.project.user.user.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TrainerService {
    private final TrainerRepository trainerRepository;

    public Trainer create(SiteUser userInfo, String name, String center, String gender,
                          String introAbstract, String introDetail, List<Field> fieldList,
                          List<Lesson> lessonList, List<Contact> contactList, List<Certificate> certificateList,
                          Address address )
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
}

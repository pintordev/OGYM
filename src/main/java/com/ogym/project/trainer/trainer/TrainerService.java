package com.ogym.project.trainer.trainer;

import com.ogym.project.trainer.address.Address;
import com.ogym.project.trainer.address.AddressRepository;
import com.ogym.project.trainer.certificate.Certificate;
import com.ogym.project.trainer.contact.Contact;
import com.ogym.project.trainer.field.Field;
import com.ogym.project.trainer.field.FieldRepository;
import com.ogym.project.trainer.lesson.Lesson;
import com.ogym.project.trainer.lesson.LessonForm;
import com.ogym.project.user.user.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class TrainerService {
    private final TrainerRepository trainerRepository;


    public Trainer create(String name, String center, String gender,
                          String introAbstract, String introDetail, List<Field> fieldList,
                          List<Lesson> lessonList, List<Contact> contactList, List<Certificate> certificateList,
                          String zoneCode, String mainAddress, String subAddress, String latitude, String longitude)
    {
        Trainer trainer = new Trainer();
        trainer.setName(name);
        trainer.setCenter(center);
        trainer.setGender(gender);
        trainer.setIntroAbstract(introAbstract);
        trainer.setIntroDetail(introDetail);
        trainer.setFieldList(fieldList);
        trainer.setLessonList(lessonList);
        trainer.setContactList(contactList);
        trainer.setCertificateList(certificateList);

        Address address = new Address();
        address.setZoneCode(zoneCode);
        address.setMainAddress(mainAddress);
        address.setSubAddress(subAddress);
        address.setLatitude(latitude);
        address.setLongitude(longitude);
        trainer.setAddress(address);

        List<Lesson> lessonList = new ArrayList<>();
        for(LessonForm lessonForm : lessonFormList){
            Lesson lesson = new Lesson();
            lessonList.add(lesson);
        }
        trainer.setLessonList(lessonList);
        this.trainerRepository.save(trainer);
        return trainer;
    }
}

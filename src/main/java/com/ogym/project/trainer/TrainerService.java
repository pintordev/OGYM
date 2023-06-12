package com.ogym.project.trainer;

import com.ogym.project.trainerCertificate.TrainerCertificate;
import com.ogym.project.trainerItem.TrainerItem;
import com.ogym.project.trainerLesson.TrainerLesson;
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

    public Trainer create(SiteUser trainerInfo, String name, String center,
                          String address, String gender, String number,
                          String introAbstract, String introDetail,
                       LocalDateTime createDate)
    {
        List<TrainerItem> fieldList = new ArrayList<>();
        List<TrainerCertificate> certificate = new ArrayList<>();
        List<TrainerLesson> lesson = new ArrayList<>();
        Trainer trainer = new Trainer();
        trainer.setTrainerInfo(trainerInfo);
        trainer.setName(name);
        trainer.setCenter(center);
        trainer.setAddress(address);
        trainer.setGender(gender);
        trainer.setNumber(number);
        trainer.setIntroAbstract(introAbstract);
        trainer.setIntroDetail(introDetail);
        trainer.setCreateDate(createDate);
        trainer.setFieldList(fieldList);
        trainer.setCertificate(certificate);
        trainer.setLesson(lesson);

        this.trainerRepository.save(trainer);
        return trainer;
    }
}

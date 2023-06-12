package com.ogym.project.trainer;

import com.ogym.project.user.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TrainerService {
    private final TrainerRepository trainerRepository;

    public void create(Trainer trainer, SiteUser trainerInfo, String name, String center, String address, String gender){
        new Trainer();
        trainer.setTrainerInfo(trainerInfo);
        trainer.setName(name);
        trainer.setCenter(center);
        trainer.setAddress(address);
        trainer.setGender(gender);
    }
}

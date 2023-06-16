package com.ogym.project.trainerContact;

import com.ogym.project.trainer.Trainer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TrainerContactService {
    private final TrainerContactRepository trainerContactRepository;

    public TrainerContact create(String type, String content, Trainer trainer){
        TrainerContact trainerContact = new TrainerContact();
        trainerContact.setType(type);
        trainerContact.setContent(content);
        trainerContact.setTrainer(trainer);
        this.trainerContactRepository.save(trainerContact);
        return trainerContact;
    }
    public List<TrainerContact> getList(){
        return this.trainerContactRepository.findAll();
    }
}

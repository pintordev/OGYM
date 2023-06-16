package com.ogym.project.trainerField;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TrainerFieldService {

    private final TrainerFieldRepository trainerFieldRepository;

    public TrainerField create(String name) {
        TrainerField trainerField = new TrainerField();
        trainerField.setName(name);
        this.trainerFieldRepository.save(trainerField);
        return trainerField;
    }

    public List<TrainerField> getList() {
        return this.trainerFieldRepository.findByOrderByIdAsc();
    }

    public TrainerField getTrainerItem(String name) {
        return this.trainerFieldRepository.findByName(name);
    }
}
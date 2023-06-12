package com.ogym.project.trainer;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TrainerService {
    private final TrainerRepository trainerRepository;
    public List<Trainer> getList() {
        return this.trainerRepository.findAll();
    }
}

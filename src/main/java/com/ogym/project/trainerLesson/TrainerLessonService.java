package com.ogym.project.trainerLesson;

import com.ogym.project.trainer.Trainer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TrainerLessonService {
    private final TrainerLessonRepository trainerLessonRepository;

    public TrainerLesson create(Integer number, Integer price, Trainer trainer){
        TrainerLesson trainerLesson = new TrainerLesson();
        trainerLesson.setNumber(number);
        trainerLesson.setPrice(price);
        trainerLesson.setTrainer(trainer);
        this.trainerLessonRepository.save(trainerLesson);
        return trainerLesson;
    }
    public List<TrainerLesson> getList(){
        return this.trainerLessonRepository.findAll();

    }
}

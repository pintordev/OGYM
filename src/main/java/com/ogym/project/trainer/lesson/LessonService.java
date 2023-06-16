package com.ogym.project.trainer.lesson;

import com.ogym.project.trainer.trainer.Trainer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class LessonService {
    private final LessonRepository lessonRepository;

    public Lesson create(Integer number, Integer price, Trainer trainer){
        Lesson lesson = new Lesson();
        lesson.setNumber(number);
        lesson.setPrice(price);
        lesson.setTrainer(trainer);
        this.lessonRepository.save(lesson);
        return lesson;
    }
    public List<Lesson> getList(){
        return this.lessonRepository.findAll();

    }
}

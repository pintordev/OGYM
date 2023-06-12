package com.ogym.project.trainer;

import com.ogym.project.comment.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainerRepository extends JpaRepository<Trainer, Long> {
//    Trainer findByTrainerInfo(String trainerInfo);
}

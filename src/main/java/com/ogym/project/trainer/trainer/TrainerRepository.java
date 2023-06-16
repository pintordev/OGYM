package com.ogym.project.trainer.trainer;

import com.ogym.project.trainer.field.Field;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrainerRepository extends JpaRepository<Trainer, Long> {
}

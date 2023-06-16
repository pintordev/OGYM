package com.ogym.project.trainerField;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrainerFieldRepository extends JpaRepository<TrainerField, Integer> {
    List<TrainerField> findByOrderByIdAsc();
    TrainerField findByName(String name);
}

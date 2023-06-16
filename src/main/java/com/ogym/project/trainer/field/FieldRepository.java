package com.ogym.project.trainer.field;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FieldRepository extends JpaRepository<Field, Integer> {
    List<Field> findByOrderByIdAsc();
    Field findByName(String name);
}

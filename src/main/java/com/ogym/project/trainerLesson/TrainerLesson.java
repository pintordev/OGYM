package com.ogym.project.trainerLesson;

import com.ogym.project.trainer.Trainer;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class TrainerLesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Integer number;

    @Column
    private Integer price;

    @ManyToOne
    private Trainer trainer;
}

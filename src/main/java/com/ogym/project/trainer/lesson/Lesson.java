package com.ogym.project.trainer.lesson;

import com.ogym.project.trainer.trainer.Trainer;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer number;

    private Integer price;

    @ManyToOne
    private Trainer trainer;
}

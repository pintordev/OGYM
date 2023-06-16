package com.ogym.project.trainerContact;

import com.ogym.project.trainer.Trainer;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class TrainerContact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(length = 20)
    private String type;

    @Column(length = 50)
    private String content;

    @ManyToOne
    private Trainer trainer;
}

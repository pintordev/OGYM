package com.ogym.project.trainerCertificate;

import com.ogym.project.trainer.Trainer;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class TrainerCertificate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String imgUrl;

    @ManyToOne
    private Trainer trainer;
    }

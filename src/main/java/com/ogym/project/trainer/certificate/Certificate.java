package com.ogym.project.trainer.certificate;

import com.ogym.project.file.UploadedFile;
import com.ogym.project.trainer.trainer.Trainer;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Certificate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50)
    private String name;

    @OneToOne
    private UploadedFile image;

    @ManyToOne
    private Trainer trainer;

}

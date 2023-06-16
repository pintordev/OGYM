package com.ogym.project.trainer.address;

import com.ogym.project.trainer.trainer.Trainer;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer zoneCode; // 우편번호

    @Column(length = 50)
    private String mainAddress;  //  메인주소

    @Column(length = 50)
    private String subAddress;  // 서브주소

    private Double latitude; //위도

    private Double longitude;  //경도

    @OneToOne
    private Trainer trainer;
}

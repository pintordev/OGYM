package com.ogym.project.trainer;


import com.ogym.project.trainerAddress.TrainerAddress;
import com.ogym.project.trainerCertificate.TrainerCertificate;
import com.ogym.project.trainerContact.TrainerContact;
import com.ogym.project.trainerLesson.TrainerLesson;
import com.ogym.project.trainerField.TrainerField;
import com.ogym.project.user.SiteUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
public class Trainer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private SiteUser userInfo;

    //트레이너 이름 : 중복허용가능
    private String name;

    //소속(활동중인센터)
    private String center;

    //성별
    private String gender;

    //짧은소개
    @Column(length = 200)
    private String introAbstract;

    //긴소개
    @Column(columnDefinition = "TEXT")
    private String introDetail;

    //운동종류
    @ManyToMany
    private List<TrainerField> fieldList;

    //레슽가격 - 패키지 하나 더만들고 one to many
    @OneToMany(mappedBy = "trainer", cascade = CascadeType.REMOVE)
    private List<TrainerLesson> lessonList;


    @OneToMany(mappedBy = "trainer", cascade = CascadeType.REMOVE)
    private List<TrainerContact> contactList;

    //자격증 : certificate 패키지 만들고 list등록
    @OneToMany(mappedBy = "trainer", cascade = CascadeType.REMOVE)
    private List<TrainerCertificate> certificateList;

    //(활동중인센터)주소
    @OneToOne
    private TrainerAddress address;

    //트레이너 등록일
    @CreatedDate
    private LocalDateTime createDate;

    private LocalDateTime modifyDate;
}

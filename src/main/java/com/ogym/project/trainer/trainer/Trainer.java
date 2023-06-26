package com.ogym.project.trainer.trainer;


import com.ogym.project.trainer.address.Address;
import com.ogym.project.trainer.certificate.Certificate;
import com.ogym.project.trainer.contact.Contact;
import com.ogym.project.trainer.lesson.Lesson;
import com.ogym.project.trainer.field.Field;
import com.ogym.project.user.user.SiteUser;
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

//    //트레이너 이름 : 중복허용가능
//    private String name;

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
    private List<Field> fieldList;

    //레슽가격 - 패키지 하나 더만들고 one to many
    @OneToMany(mappedBy = "trainer", cascade = CascadeType.REMOVE)
    private List<Lesson> lessonList;


    @OneToMany(mappedBy = "trainer", cascade = CascadeType.REMOVE)
    private List<Contact> contactList;

    //자격증 : certificate 패키지 만들고 list등록
    @OneToMany(mappedBy = "trainer", cascade = CascadeType.REMOVE)
    private List<Certificate> certificateList;

    //(활동중인센터)주소
    @OneToOne
    private Address address;

    //트레이너 등록일
    @CreatedDate
    private LocalDateTime createDate;

    private LocalDateTime modifyDate;
}

package com.ogym.project.trainer;


import com.ogym.project.trainerCertificate.TrainerCertificate;
import com.ogym.project.trainerItem.TrainerItem;
import com.ogym.project.trainerLesson.TrainerLesson;
import com.ogym.project.user.SiteUser;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class TrainerCreateForm {
    @Size(min=3,max=12)
    @NotEmpty(message= "유저이름은 필수 항목입니다.")
    private SiteUser trainerInfo;

    @NotEmpty(message="트레이너 이름은 필수항목입니다.")
    //트레이너 이름 : 중복허용가능
    private String name;

    //소속(활동중인센터)
    private String center;

    //(활동중인센터)주소
    private String address;

    //운동종류

    private TrainerItem fieldList;

    //성별
    @NotEmpty(message = "성별은 필수항복입니다.")
    private String gender;

    //자격증 : certificate 패키지 만들고 list등록

    private TrainerCertificate certificate;

    //연락처
    @NotEmpty(message = "연락처는 필수 항목입니다.")
    private String number;

    //짧은소개
    private String introAbstract;

    //긴소개
    private String introDetail;

    //레슽가격 - 패키지 하나 더만들고 one to many
    @NotEmpty(message="레슨정보는 필수 항목입니다.")
    private TrainerLesson lesson;

    //트레이너 등록일
    @CreatedDate
    private LocalDateTime createDate;
}

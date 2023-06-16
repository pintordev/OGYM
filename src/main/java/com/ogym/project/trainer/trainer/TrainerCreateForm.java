package com.ogym.project.trainer.trainer;


import com.ogym.project.trainer.certificate.Certificate;
import com.ogym.project.trainer.lesson.LessonForm;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TrainerCreateForm {
//    @NotEmpty(message= "유저이름은 필수 항목입니다.")
//    private SiteUser trainerInfo;

    @NotEmpty(message="트레이너 이름은 필수항목입니다.")
    //트레이너 이름 : 중복허용가능
    private String name;

    //소속(활동중인센터)
    private String center;

    //(활동중인센터)주소
    private List<String> address;

    //운동종류

    private List<String> trainerItem;

    //성별

    private String gender;

    //자격증 : certificate 패키지 만들고 list등록

    private List<Certificate> certificate;

    //연락처

    private String phoneNumber;

    //짧은소개
    private String introAbstract;

    //긴소개
    private String introDetail;

    //레슽가격 - 패키지 하나 더만들고 one to many

    private List<LessonForm> lesson;

}

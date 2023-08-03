package com.ogym.project.trainer.address;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressForm {

    private Integer zoneCode; // 우편번호

    private String mainAddress;  //  메인주소

    private String subAddress;  // 서브주소

    private Double latitude; //위도

    private Double longitude;  //경도
}

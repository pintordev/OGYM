package com.ogym.project.trainer.certificate;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class CertificateForm {

    private String name;

    private MultipartFile image;
}

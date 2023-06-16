package com.ogym.project.trainer.certificate;

import com.ogym.project.trainer.trainer.Trainer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CertificateService {
    private final CertificateRepository certificateRepository;

    public Certificate create(String name, String imgUrl, Trainer trainer){
        Certificate certificate = new Certificate();

        certificate.setName(name);
        certificate.setImgUrl(imgUrl);
        certificate.setTrainer(trainer);

        this.certificateRepository.save(certificate);
        return certificate;
    }
    public List<Certificate> getList(){
        return this.certificateRepository.findAll();
    }
}

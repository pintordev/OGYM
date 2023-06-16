package com.ogym.project.trainerCertificate;

import com.ogym.project.trainer.Trainer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TrainerCertificateService {
    private final TrainerCertificateRepository trainerCertificateRepository;

    public TrainerCertificate create(String name, String imgUrl, Trainer trainer){
        TrainerCertificate trainerCertificate = new TrainerCertificate();

        trainerCertificate.setName(name);
        trainerCertificate.setImgUrl(imgUrl);
        trainerCertificate.setTrainer(trainer);

        this.trainerCertificateRepository.save(trainerCertificate);
        return trainerCertificate;
    }
    public List<TrainerCertificate> getList(){
        return this.trainerCertificateRepository.findAll();
    }
}

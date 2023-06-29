package com.ogym.project.trainer.certificate;

import com.ogym.project.file.FileService;
import com.ogym.project.file.UploadedFile;
import com.ogym.project.trainer.trainer.Trainer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CertificateService {
    private final CertificateRepository certificateRepository;
    private final FileService fileService;

    public Certificate create(String name, MultipartFile file, Trainer trainer) throws IOException {
        Certificate certificate = new Certificate();

        UploadedFile image = this.fileService.upload(file, "trainer", "certificate", trainer.getUserInfo().getLoginId());

        certificate.setName(name);
        certificate.setImage(image);
        certificate.setTrainer(trainer);

        this.certificateRepository.save(certificate);
        return certificate;
    }

    public List<Certificate> getList(){
        return this.certificateRepository.findAll();
    }
}

package com.ogym.project.trainer.trainer;

import com.ogym.project.DataNotFoundException;
import com.ogym.project.board.board.Board;
import com.ogym.project.file.FileService;
import com.ogym.project.file.UploadedFile;
import com.ogym.project.trainer.address.Address;
import com.ogym.project.trainer.address.AddressRepository;
import com.ogym.project.trainer.certificate.Certificate;
import com.ogym.project.trainer.contact.Contact;
import com.ogym.project.trainer.field.Field;
import com.ogym.project.trainer.field.FieldRepository;
import com.ogym.project.trainer.lesson.Lesson;
import com.ogym.project.trainer.lesson.LessonForm;
import com.ogym.project.user.user.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.zip.DataFormatException;

@RequiredArgsConstructor
@Service
public class TrainerService {

    private final TrainerRepository trainerRepository;
    private final FileService fileService;

    public Trainer create(SiteUser userInfo,
                          String center,
                          String gender,
                          MultipartFile file,
                          String introAbstract,
                          String introDetail,
                          List<Field> fieldList,
                          Integer avgLessonPrice,
                          Address address) throws IOException {

        UploadedFile profileImage = this.fileService.upload(file, "trainer", "profile", userInfo.getLoginId());

        Trainer trainer = new Trainer();
        trainer.setUserInfo(userInfo);
        trainer.setCenter(center);
        trainer.setGender(gender);
        trainer.setProfileImage(profileImage);
        trainer.setIntroAbstract(introAbstract);
        trainer.setIntroDetail(introDetail);
        trainer.setFieldList(fieldList);
        trainer.setAvgLessonPrice(avgLessonPrice);
        trainer.setAddress(address);
        trainer.setCreateDate(LocalDateTime.now());
        this.trainerRepository.save(trainer);
        return trainer;
    }

    public boolean isRegistered(SiteUser userInfo) {
        Optional<Trainer> ot = this.trainerRepository.findByUserInfo(userInfo);
        return ot.isPresent();
    }

    public Trainer getTrainer(Long id){
        Optional<Trainer> ot = this.trainerRepository.findById(id);
        if(ot.isPresent()){
            return ot.get();
        }else{
            throw new DataNotFoundException("trainer not found");
        }
    }

    public List<Trainer> getListWithRadius() {

        return null;

    }

    public Page<Trainer> getList(int tPage, String tKw, String tField) {
        Pageable pageable = PageRequest.of(tPage, 10);
        return this.trainerRepository.findAllByKeywordAndFieldOrderByCreateDate(tField, tKw, pageable);
    }
}

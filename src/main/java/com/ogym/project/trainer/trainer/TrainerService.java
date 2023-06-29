package com.ogym.project.trainer.trainer;

import com.ogym.project.DataNotFoundException;
import com.ogym.project.board.board.Board;
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
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.zip.DataFormatException;

@RequiredArgsConstructor
@Service
public class TrainerService {
    private final TrainerRepository trainerRepository;


    public Trainer create(SiteUser userInfo,
                          String center,
                          String gender,
                          String introAbstract,
                          String introDetail,
                          List<Field> fieldList) {

        Trainer trainer = new Trainer();
        trainer.setUserInfo(userInfo);
        trainer.setCenter(center);
        trainer.setGender(gender);
        trainer.setIntroAbstract(introAbstract);
        trainer.setIntroDetail(introDetail);
        trainer.setFieldList(fieldList);
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

    public void saveAddress(Address address, Trainer trainer) {
        trainer.setAddress(address);
        this.trainerRepository.save(trainer);
    }

    public List<Trainer> getListWithRadius() {

        return null;

    }

    public Page<Trainer> getList(int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        return this.trainerRepository.findAll(pageable);
    }
}

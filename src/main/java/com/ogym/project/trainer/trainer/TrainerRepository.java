package com.ogym.project.trainer.trainer;

import com.ogym.project.trainer.field.Field;
import com.ogym.project.user.user.SiteUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TrainerRepository extends JpaRepository<Trainer, Long> {
    Optional<Trainer> findByUserInfo(SiteUser userInfo);
    Page<Trainer> findAll(Pageable pageable);
}

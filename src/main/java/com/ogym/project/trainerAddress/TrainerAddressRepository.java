package com.ogym.project.trainerAddress;

import com.ogym.project.trainer.Trainer;
import jakarta.mail.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainerAddressRepository extends JpaRepository<TrainerAddress, Long> {
}

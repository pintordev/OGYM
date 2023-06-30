package com.ogym.project;

import com.ogym.project.trainer.trainer.Trainer;
import com.ogym.project.trainer.trainer.TrainerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
class ProjectApplicationTests {

	@Autowired
	private TrainerRepository trainerRepository;

	@Test
	void testJpa() {
		Optional<Trainer> optionalTrainer = this.trainerRepository.findById(12L);
		if (optionalTrainer.isPresent()) {
			this.trainerRepository.delete(optionalTrainer.get());
		}
	}
}

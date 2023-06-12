package com.ogym.project;

import com.ogym.project.trainer.Trainer;
import com.ogym.project.trainer.TrainerRepository;
import com.ogym.project.user.SiteUser;
import com.ogym.project.user.UserRepository;
import com.ogym.project.user.UserRole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
class ProjectApplicationTests {

	@Autowired
	private TrainerRepository trainerRepository;


	@Test
	void testJpa() {

	}
}

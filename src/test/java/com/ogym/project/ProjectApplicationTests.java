package com.ogym.project;

import com.ogym.project.trainer.trainer.Trainer;
import com.ogym.project.trainer.trainer.TrainerRepository;
import com.ogym.project.user.user.SiteUser;
import com.ogym.project.user.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
class ProjectApplicationTests {

	@Autowired
	private TrainerRepository trainerRepository;

	@Autowired
	private UserRepository userRepository;

	@Test
	void testJpa() {
//		Optional<SiteUser> optionalSiteUser = this.userRepository.findById(82L);
//		if (optionalSiteUser.isPresent()) {
//			this.userRepository.delete(optionalSiteUser.get());
//		}
	}
}

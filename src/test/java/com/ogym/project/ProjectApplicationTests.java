package com.ogym.project;

import com.ogym.project.trainer.trainer.TrainerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ProjectApplicationTests {

	@Autowired
	private TrainerRepository trainerRepository;


	@Test
	void testJpa() {

	}
}

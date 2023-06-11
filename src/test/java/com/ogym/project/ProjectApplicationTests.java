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
	Trainer trainer = new Trainer();
	trainer.setName("최기범");
		trainer.setCenter("대전헬스");
		trainer.setAddress("대전구암동");
		trainer.setGender("남자");
		trainer.setNumber("01098002794,choosencome@naver.com");
		trainer.setIntroAbstract("안녕하세요");
		trainer.setIntroDetail("아아아아아년년녀녀녀녕핫헤용");
	this.trainerRepository.save(trainer);
	}
}

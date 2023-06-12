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
//<<<<<<< HEAD
//		SiteUser user = new SiteUser();
//		user.setAuthority(UserRole.ADMIN);
//		user.setLoginId("ogym_admin");
//		user.setPassword("1234");
//		user.setNickname("오짐관리자");
//		user.setUsername("ogym_admin");
//		user.setBirthDate("20230608");
//		user.setEmail("administrator@ogym.one");
//		user.setCreateDate(LocalDateTime.now());
//		this.userRepository.save(user);
//=======
//	Trainer trainer = new Trainer();
//	trainer.setName("최기범");
//		trainer.setCenter("대전헬스");
//		trainer.setAddress("대전구암동");
//		trainer.setGender("남자");
//		trainer.setNumber("01098002794,choosencome@naver.com");
//		trainer.setIntroAbstract("안녕하세요");
//		trainer.setIntroDetail("아아아아아년년녀녀녀녕핫헤용");
//	this.trainerRepository.save(trainer);
//>>>>>>> 492a039180ca909d69ea3494a47adee4b11b99fd
	}
}

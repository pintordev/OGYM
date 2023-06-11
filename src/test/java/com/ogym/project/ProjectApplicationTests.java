package com.ogym.project;

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
	private UserRepository userRepository;

	@Test
	void testJpa() {
		SiteUser user = new SiteUser();
		user.setAuthority(UserRole.ADMIN);
		user.setLoginId("ogym_admin");
		user.setPassword("1234");
		user.setNickname("오짐관리자");
		user.setUsername("ogym_admin");
		user.setBirthDate("20230608");
		user.setEmail("administrator@ogym.one");
		user.setCreateDate(LocalDateTime.now());
		this.userRepository.save(user);
	}
}

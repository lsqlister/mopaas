package com.lansq.test.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lansq.test.domain.User;

public class UserServiceTest extends BaseServiceTest {
	@Autowired
	IUserService userService;

	@Test
	public void save() throws Exception {
		User user = new User();
		user.setEmail("xxxx");
		userService.save(user);
	}
}

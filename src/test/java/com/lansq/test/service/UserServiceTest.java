package com.lansq.test.service;

import java.util.List;

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
	
	@Test
	public void getAll() throws Exception {
		List<User> users = userService.getAll();
		for (User user : users) {
			System.out.println(user);
		}
	}
}

package com.lansq.test.service;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:development/applicationContext.xml",
		"classpath:applicationContext-test.xml" })
public abstract class BaseServiceTest {

}

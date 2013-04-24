package com.heki1224.spring.mail.send.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/resources/spring/app-config.xml" })
public class SendMailServiceTest {

	@Autowired
	private SendMailService sendService;

	@Test
	public void test() {
		sendService.send("xxx@gmail.com", "xxx@gmail.com", "テスト", "テストです");
	}
}

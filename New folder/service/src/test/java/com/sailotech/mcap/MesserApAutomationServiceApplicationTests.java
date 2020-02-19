package com.sailotech.mcap;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class MesserApAutomationServiceApplicationTests {

	@Autowired
	PasswordEncoder passwordEncode;

	@Test
	void contextLoads() {

		System.out.println(passwordEncode.encode("1234"));
	}

}

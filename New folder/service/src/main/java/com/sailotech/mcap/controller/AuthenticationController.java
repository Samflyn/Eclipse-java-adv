package com.sailotech.mcap.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sailotech.mcap.dto.JwtResponse;
import com.sailotech.mcap.dto.LoginRequestDto;
import com.sailotech.mcap.dto.UserDto;
import com.sailotech.mcap.entity.User;
import com.sailotech.mcap.security.jwt.JwtUtils;
import com.sailotech.mcap.util.MesserApAutomationUtil;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

	private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationController.class);

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	private JwtUtils jwtUtils;

	@Autowired
	private MesserApAutomationUtil mcApUtil;

	@PostMapping(value = "/signin")
	public ResponseEntity<String> signin(@RequestBody LoginRequestDto loginRequestDto) {

		LOGGER.info("Entered into signin {} ", loginRequestDto.getUsername());

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequestDto.getUsername(), loginRequestDto.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);

		User userDetails = (User) authentication.getPrincipal();

		UserDto userDto = mcApUtil.convertUserDaoToDto(userDetails);

		String response = mcApUtil.convertPojoToJson(new JwtResponse(jwt, userDto));

		return ResponseEntity.ok(response);
	}
}
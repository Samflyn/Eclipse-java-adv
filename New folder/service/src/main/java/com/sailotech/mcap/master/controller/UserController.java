package com.sailotech.mcap.master.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sailotech.mcap.dto.UserDto;
import com.sailotech.mcap.exception.DataValidationException;
import com.sailotech.mcap.master.service.UserService;
import com.sailotech.mcap.util.MesserApAutomationConstants;
import com.sailotech.mcap.util.MesserApAutomationUtil;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private MesserApAutomationUtil mcapUtil;

	@PostMapping(value = "/create")
	public String create(@RequestBody UserDto userDto) throws DataValidationException {
		log.info("create {} ");
		userService.saveUser(userDto);
		Map<String, String> dataMap = new HashMap<>();
		dataMap.put(MesserApAutomationConstants.MESSAGE, "User saved successfully");
		dataMap.put(MesserApAutomationConstants.STATUS, MesserApAutomationConstants.SUCCESS_STATUS);
		return mcapUtil.convertPojoToJson(dataMap);
	}

	@GetMapping("/getAll")
	public List<UserDto> getAll(@RequestParam(value = "userId", required = false) Integer userId) {
		return userService.getAllUsers(userId);
	}

	@DeleteMapping("/delete/{userId}")
	public String delete(@PathVariable("userId") Integer userId) {
		userService.deleteByUserId(userId);
		Map<String, String> dataMap = new HashMap<>();
		dataMap.put(MesserApAutomationConstants.MESSAGE, "User deleted successfully");
		dataMap.put(MesserApAutomationConstants.STATUS, MesserApAutomationConstants.SUCCESS_STATUS);
		return mcapUtil.convertPojoToJson(dataMap);
	}
}

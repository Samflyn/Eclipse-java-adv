package com.sailotech.mcap.master.service;

import java.util.List;

import com.sailotech.mcap.dto.UserDto;
import com.sailotech.mcap.exception.DataValidationException;

public interface UserService {

	public boolean validateUserLoginId(String userLoginId);

	public List<UserDto> getAllUsers(Integer userId);

	public void deleteByUserId(Integer userId);

	boolean validateEmailId(String emailId);

	Integer saveUser(UserDto userDto) throws DataValidationException;

	public UserDto getUserByUserId(Integer userLoginId);

	public List<UserDto> getUsersByCompanyId(Integer companyId);

}

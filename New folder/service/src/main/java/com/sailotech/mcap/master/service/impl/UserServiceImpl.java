package com.sailotech.mcap.master.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sailotech.mcap.dto.UserDto;
import com.sailotech.mcap.entity.ErrorMaster;
import com.sailotech.mcap.entity.User;
import com.sailotech.mcap.exception.DataValidationException;
import com.sailotech.mcap.master.service.UserService;
import com.sailotech.mcap.repository.UserRepository;
import com.sailotech.mcap.util.MesserApAutomationCacheManager;
import com.sailotech.mcap.util.MesserApAutomationUtil;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	MesserApAutomationUtil messerApAutomationUtil;

	@Autowired
	MesserApAutomationCacheManager mcapCacheManager;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Override
	public Integer saveUser(UserDto userDto) throws DataValidationException {
		Integer loggedInUser = messerApAutomationUtil.getUserId();
		if (userDto.getUserId() != null) {
			boolean validateUserLoginIdByUserId = validateUserLoginIdByUserId(userDto.getUsername(),
					userDto.getUserId());
			if (validateUserLoginIdByUserId) {
				ErrorMaster errorMaster = mcapCacheManager.findByErrorCode("");
				throw new DataValidationException(errorMaster.getErrorCode(), errorMaster.getErrorDesc());
			}

			userDto.setLastUpdatedBy(loggedInUser);
			userDto.setLastUpdatedOn(Calendar.getInstance().getTime());
		} else {
			boolean validateUserLoginId = validateUserLoginId(userDto.getUsername());
			boolean validateEmailId = validateEmailId(userDto.getEmailId());
			if (validateUserLoginId || validateEmailId) {
				ErrorMaster errorMaster = mcapCacheManager.findByErrorCode("");
				throw new DataValidationException(errorMaster.getErrorCode(), errorMaster.getErrorDesc());
			}
			userDto.setCreatedBy(loggedInUser);
			userDto.setCreatedOn(Calendar.getInstance().getTime());
			userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
			userDto.setEnabled(true);
		}

		User user = messerApAutomationUtil.convertUserDtoToDao(userDto);
		return userRepository.save(user).getUserId();
	}

	@Override
	public boolean validateEmailId(String emailId) {
		User user = userRepository.findByUserEmailId(emailId);
		return user != null ? Boolean.TRUE : Boolean.FALSE;
	}

	private Boolean validateUserLoginIdByUserId(String userLoginId, Integer userId) {
		User user = userRepository.finduserLoginIdNotInId(userLoginId, userId);
		return user != null ? Boolean.TRUE : Boolean.FALSE;
	}

	@Override
	public boolean validateUserLoginId(String username) {
		User user = userRepository.findByUsername(username.toLowerCase());
		return user != null ? Boolean.TRUE : Boolean.FALSE;
	}

	@Override
	public List<UserDto> getAllUsers(Integer userId) {
		List<User> users = userRepository.getAllUsersLoginIdNotIn(userId);
		List<UserDto> userDtos = new ArrayList<>();
		for (User user : users) {
			UserDto userDto = messerApAutomationUtil.convertUserDaoToDto(user);
			userDtos.add(userDto);
		}
		return userDtos;
	}

	@Override
	public void deleteByUserId(Integer userId) {
		userRepository.deleteById(userId);
	}

	@Override
	public UserDto getUserByUserId(Integer userId) {
		Optional<User> user = userRepository.findById(userId);
		if (user.isPresent()) {
			return messerApAutomationUtil.convertUserDaoToDto(user.get());
		}
		return null;
	}

	@Override
	public List<UserDto> getUsersByCompanyId(Integer companyId) {
		List<User> users = null;
		if (companyId != null) {
			users = userRepository.findByCompanyId(companyId);
		} else {
			users = (List<User>) userRepository.findAll();
		}
		List<UserDto> userDtos = new ArrayList<>();
		for (User user : users) {
			UserDto userDto = messerApAutomationUtil.convertUserDaoToDto(user);
			userDtos.add(userDto);
		}
		return userDtos;
	}

}

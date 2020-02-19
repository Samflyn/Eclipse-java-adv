package com.sailotech.mcap.dto;

import java.io.Serializable;
import java.util.Collection;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.sailotech.mcap.entity.Authority;
import com.sailotech.mcap.entity.Department;

import lombok.Getter;
import lombok.Setter;

/**
 * @author nagendra.babu
 *
 * @version 1.0
 *
 */

@Getter
@Setter
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class UserDto extends BaseDto implements Serializable {

	public UserDto() {
		super();
	}

	private static final long serialVersionUID = -7174791044981739916L;

	private Integer userId;

	private String fullName;

	private String username;

	private String password;

	private String emailId;

	private boolean enabled;

	private byte[] pic;

	private String addressLine1;

	private String addressLine2;

	private String city;

	private String pinCode;

	private String landLine;

	private String mobile;

	private CompanyDto companyDto;

	private CountryMasterDto countryMasterDto;

	private StateMasterDto stateDto;

	private RoleMasterDto roleMasterDto;

	private Department departmentDto;

	private Integer invalidLoginAttempts;

	private Integer managerId;

	private Collection<Authority> authorities;
}

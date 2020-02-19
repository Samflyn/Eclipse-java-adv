package com.sailotech.mcap.master.service;

import com.sailotech.mcap.dto.RoleMasterDto;

public interface RoleMasterService {
	public Integer saveUserRoles(RoleMasterDto roleMasterDto);

	public String getAllUserRoles();

	public void deleteByRoleId(Integer roleId);
}

package com.sailotech.mcap.master.service.impl;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sailotech.mcap.dto.RoleMasterDto;
import com.sailotech.mcap.entity.RoleMaster;
import com.sailotech.mcap.master.repository.RoleMasterRepository;
import com.sailotech.mcap.master.service.RoleMasterService;
import com.sailotech.mcap.util.MesserApAutomationUtil;

@Service
public class RoleMasterServiceImpl implements RoleMasterService {

	@Autowired
	private RoleMasterRepository roleMasterRepository;

	@Autowired
	MesserApAutomationUtil messerApAutomationUtil;

	@Override
	public Integer saveUserRoles(RoleMasterDto roleMasterDto) {
		Integer loggedInUser = messerApAutomationUtil.getUserId();
		if (roleMasterDto.getRoleId() != null) {
			roleMasterDto.setLastUpdatedBy(loggedInUser);
			roleMasterDto.setLastUpdatedOn(Calendar.getInstance().getTime());
		} else {
			roleMasterDto.setCreatedBy(loggedInUser);
		}

		RoleMaster roleMaster = messerApAutomationUtil.copyBeanProperties(roleMasterDto, RoleMaster.class);
		roleMaster = roleMasterRepository.save(roleMaster);
		return roleMaster.getRoleId();
	}

	@Override
	public String getAllUserRoles() {
		Iterable<RoleMaster> roleMaster = roleMasterRepository.findAll();
		return messerApAutomationUtil.convertPojoToJson(roleMaster);
	}

	@Override
	public void deleteByRoleId(Integer roleId) {
		roleMasterRepository.deleteById(roleId);
	}

}

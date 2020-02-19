package com.sailotech.mcap.master.service.impl;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sailotech.mcap.dto.StateMasterDto;
import com.sailotech.mcap.entity.StateMaster;
import com.sailotech.mcap.master.repository.StateMasterRepository;
import com.sailotech.mcap.master.service.StateMasterService;
import com.sailotech.mcap.util.MesserApAutomationUtil;

@Service
public class StateMasterServiceImpl implements StateMasterService {

	@Autowired
	StateMasterRepository stateMasterRepository;

	@Autowired
	MesserApAutomationUtil messerApAutomationUtil;

	@Override
	public Integer saveState(StateMasterDto stateMasterDto) {
		Integer loggedInUser = messerApAutomationUtil.getUserId();
		if (stateMasterDto.getStateId() != null) {
			stateMasterDto.setLastUpdatedBy(loggedInUser);
			stateMasterDto.setLastUpdatedOn(Calendar.getInstance().getTime());
		} else {
			stateMasterDto.setCreatedBy(loggedInUser);
			stateMasterDto.setCreatedOn(Calendar.getInstance().getTime());
		}

		StateMaster stateMaster = stateMasterRepository
				.save(messerApAutomationUtil.copyBeanProperties(stateMasterDto, StateMaster.class));
		return stateMaster.getStateId();

	}

	@Override
	public String getAllStates() {
		Iterable<StateMaster> stateMaster = stateMasterRepository.findAll();
		return messerApAutomationUtil.convertPojoToJson(stateMaster);
	}

	@Override
	public void deleteByStateId(Integer stateId) {
		stateMasterRepository.deleteById(stateId);
	}
}

package com.sailotech.mcap.master.service.impl;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sailotech.mcap.dto.ErrorMasterDto;
import com.sailotech.mcap.entity.ErrorMaster;
import com.sailotech.mcap.master.repository.ErrorMasterRepository;
import com.sailotech.mcap.master.service.ErrorMasterService;
import com.sailotech.mcap.util.MesserApAutomationUtil;

@Service
public class ErrorMasterServiceImpl implements ErrorMasterService {

	@Autowired
	ErrorMasterRepository errorMasterRepository;

	@Autowired
	MesserApAutomationUtil messerApAutomationUtil;

	@Override
	public Integer saveItemMapping(ErrorMasterDto errorMasterDto) {
		Integer loggedInUser = messerApAutomationUtil.getUserId();
		if (errorMasterDto.getErrorId() != null) {
			errorMasterDto.setLastUpdatedBy(loggedInUser);
			errorMasterDto.setLastUpdatedOn(Calendar.getInstance().getTime());
		} else {
			errorMasterDto.setCreatedBy(loggedInUser);
		}
		ErrorMaster errorMaster = errorMasterRepository
				.save(messerApAutomationUtil.copyBeanProperties(errorMasterDto, ErrorMaster.class));
		return errorMaster.getErrorId();
	}

	@Override
	public String getErrorMasters() {
		Iterable<ErrorMaster> errorMaster = errorMasterRepository.findAll();
		return messerApAutomationUtil.convertPojoToJson(errorMaster);
	}

	@Override
	public void deleteErrorMaster(Integer errorId) {
		errorMasterRepository.deleteById(errorId);
	}

}

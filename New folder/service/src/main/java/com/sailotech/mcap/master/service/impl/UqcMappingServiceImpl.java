package com.sailotech.mcap.master.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sailotech.mcap.dto.UQCMappingDto;
import com.sailotech.mcap.entity.ErrorMaster;
import com.sailotech.mcap.entity.UQCMapping;
import com.sailotech.mcap.exception.DataValidationException;
import com.sailotech.mcap.master.repository.UqcMappingRepository;
import com.sailotech.mcap.master.service.UqcMappingService;
import com.sailotech.mcap.util.MesserApAutomationCacheManager;
import com.sailotech.mcap.util.MesserApAutomationUtil;

@Service
public class UqcMappingServiceImpl implements UqcMappingService {

	@Autowired
	MesserApAutomationUtil messerApAutomationUtil;

	@Autowired
	UqcMappingRepository uqcMappingRepository;

	@Autowired
	MesserApAutomationCacheManager mcapCacheManager;

	@Override
	public Integer saveUqcMapping(UQCMappingDto uqcMappingDto) throws DataValidationException {
		Integer loggedInUser = messerApAutomationUtil.getUserId();
		if (uqcMappingDto.getUqcMappingId() != null) {
			checkUniqueUqcMasterOnupdate(uqcMappingDto);
			uqcMappingDto.setLastUpdatedBy(loggedInUser);
			uqcMappingDto.setLastUpdatedOn(Calendar.getInstance().getTime());
		} else {
			checkUniqueUqcMaster(uqcMappingDto);
			uqcMappingDto.setCreatedBy(loggedInUser);
			uqcMappingDto.setCreatedOn(Calendar.getInstance().getTime());
		}
		uqcMappingDto.setCompanyId(messerApAutomationUtil.getLoggedInUserCompanyId());
		UQCMapping uqcMapping = messerApAutomationUtil.copyBeanProperties(uqcMappingDto, UQCMapping.class);
		uqcMapping = uqcMappingRepository.save(uqcMapping);
		return uqcMapping.getUqcMappingId();
	}

	private void checkUniqueUqcMaster(UQCMappingDto uqcMappingDto) throws DataValidationException {
		boolean isUqcCodeExist = uqcMappingRepository
				.findByUqcMasterExists(uqcMappingDto.getUqcMasterDto().getUqcMasterId());
		if (isUqcCodeExist) {
			ErrorMaster errorMaster = mcapCacheManager.findByErrorCode("");
			throw new DataValidationException(errorMaster.getErrorCode(), errorMaster.getErrorDesc());
		}
	}

	private void checkUniqueUqcMasterOnupdate(UQCMappingDto uqcMappingDto) throws DataValidationException {
		boolean isUqcCodeExist = uqcMappingRepository
				.findByUqcMasterExistsOnUpdate(uqcMappingDto.getUqcMasterDto().getUqcMasterId());
		if (isUqcCodeExist) {
			ErrorMaster errorMaster = mcapCacheManager.findByErrorCode("");
			throw new DataValidationException(errorMaster.getErrorCode(), errorMaster.getErrorDesc());
		}
	}

	@Override
	public String getUqcMapping(Integer companyId) {
		List<UQCMapping> uQCMapping = uqcMappingRepository.findByCompanyId(companyId);
		List<UQCMappingDto> uqcMappingDto = new ArrayList<>();
		for (UQCMapping uqcrecord : uQCMapping) {
			UQCMappingDto uqcmappingDto = messerApAutomationUtil.convertuqcMappingDaoToDto(uqcrecord);
			uqcMappingDto.add(uqcmappingDto);
		}
		return messerApAutomationUtil.convertPojoToJson(uqcMappingDto);
	}

	@Override
	public void deleteUqcMapping(Integer uqcMappingId) {
		uqcMappingRepository.deleteById(uqcMappingId);
	}
}

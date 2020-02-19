package com.sailotech.mcap.master.service.impl;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sailotech.mcap.dto.UQCMasterDto;
import com.sailotech.mcap.entity.UQCMaster;
import com.sailotech.mcap.exception.DataValidationException;
import com.sailotech.mcap.master.repository.UqcMasterRepository;
import com.sailotech.mcap.master.service.UqcMasterService;
import com.sailotech.mcap.util.MesserApAutomationUtil;

@Service
public class UqcMasterServiceImpl implements UqcMasterService {

	@Autowired
	MesserApAutomationUtil messerApAutomationUtil;

	@Autowired
	UqcMasterRepository uqcMasterRepository;

	@Override
	public Integer saveUqc(UQCMasterDto uQCMasterDto) throws DataValidationException {
		Integer loggedInUser = messerApAutomationUtil.getUserId();
		if (uQCMasterDto.getUqcMasterId() != null) {
			checkUniqueUqcMasterOnUpdate(uQCMasterDto);
			uQCMasterDto.setLastUpdatedBy(loggedInUser);
			uQCMasterDto.setLastUpdatedOn(Calendar.getInstance().getTime());
		} else {
			checkUniqueUqcMaster(uQCMasterDto);
			uQCMasterDto.setCreatedBy(loggedInUser);
			uQCMasterDto.setCreatedOn(Calendar.getInstance().getTime());
		}
		uQCMasterDto.setCompanyId(messerApAutomationUtil.getLoggedInUserCompanyId());
		UQCMaster uQCMaster = uqcMasterRepository
				.save(messerApAutomationUtil.copyBeanProperties(uQCMasterDto, UQCMaster.class));
		return uQCMaster.getUqcMasterId();

	}

	@Override
	public String getUqcListByCompanyId(Integer companyId) {
		List<UQCMaster> uQCMaster =null;
		if(companyId==null) {
			uQCMaster = uqcMasterRepository.findByCompanyId(companyId);
		}else {
			uQCMaster = (List<UQCMaster>) uqcMasterRepository.findAll();
		}
		return messerApAutomationUtil.convertPojoToJson(uQCMaster);
	}

	@Override
	public String getUqcList() {
		Iterable<UQCMaster> uQCMaster = uqcMasterRepository.findAll();
		return messerApAutomationUtil.convertPojoToJson(uQCMaster);
	}

	@Override
	public void deleteUqcById(Integer uqcMasterId) {
		uqcMasterRepository.deleteById(uqcMasterId);

	}

	private void checkUniqueUqcMaster(UQCMasterDto uqcMasterDto) throws DataValidationException {
		boolean findByItemMasterExists = uqcMasterRepository.findByUqcMasterExists(uqcMasterDto.getUqcCode());
		if (findByItemMasterExists) {
			throw new DataValidationException("SVMP_DATA_VALIDATION_ERROR", "UQC Code  already exists");
		}
	}

	private void checkUniqueUqcMasterOnUpdate(UQCMasterDto uqcMasterDto) throws DataValidationException {
		boolean findByItemMasterExists = uqcMasterRepository.findByUqcMasterExistsOnUpdate(uqcMasterDto.getUqcCode());
		if (findByItemMasterExists) {
			throw new DataValidationException("SVMP_DATA_VALIDATION_ERROR", "UQC Code  already exists");
		}
	}

}

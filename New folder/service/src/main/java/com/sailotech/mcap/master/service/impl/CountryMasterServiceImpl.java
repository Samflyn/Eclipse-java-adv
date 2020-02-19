package com.sailotech.mcap.master.service.impl;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sailotech.mcap.dto.CountryMasterDto;
import com.sailotech.mcap.entity.CountryMaster;
import com.sailotech.mcap.master.repository.CountryMasterRepository;
import com.sailotech.mcap.master.service.CountryMasterService;
import com.sailotech.mcap.util.MesserApAutomationUtil;

@Service
public class CountryMasterServiceImpl implements CountryMasterService {

	@Autowired
	private CountryMasterRepository countryMasterRepository;

	@Autowired
	MesserApAutomationUtil messerApAutomationUtil;

	@Override
	public Integer saveCountryDetails(CountryMasterDto countryMasterDto) {
		Integer loggedInUser = messerApAutomationUtil.getUserId();
		if (countryMasterDto.getCountryId() != null) {
			countryMasterDto.setLastUpdatedBy(loggedInUser);
			countryMasterDto.setLastUpdatedOn(Calendar.getInstance().getTime());
		} else {
			countryMasterDto.setCreatedBy(loggedInUser);
		}
		CountryMaster countryMaster = countryMasterRepository
				.save(messerApAutomationUtil.copyBeanProperties(countryMasterDto, CountryMaster.class));
		return countryMaster.getCountryId();
	}

	@Override
	public String getAllCountryDetails() {
		Iterable<CountryMaster> countryMaster = countryMasterRepository.findAll();
		return messerApAutomationUtil.convertPojoToJson(countryMaster);
	}

	@Override
	public void deleteByCountryId(Integer countryId) {
		countryMasterRepository.deleteById(countryId);
	}
}

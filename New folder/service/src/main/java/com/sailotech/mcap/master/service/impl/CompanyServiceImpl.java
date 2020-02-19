package com.sailotech.mcap.master.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sailotech.mcap.dto.CompanyDto;
import com.sailotech.mcap.entity.Company;
import com.sailotech.mcap.master.repository.CompanyRepository;
import com.sailotech.mcap.master.service.CompanyService;
import com.sailotech.mcap.util.MesserApAutomationConstants;
import com.sailotech.mcap.util.MesserApAutomationUtil;

@Service
public class CompanyServiceImpl implements CompanyService {

	@Autowired
	private CompanyRepository companyRepository;

	@Autowired
	private MesserApAutomationUtil messerApAutomationUtil;

	@Override
	public String save(CompanyDto companyDto) {
		Date now = Calendar.getInstance().getTime();
		Integer loggedInUser = messerApAutomationUtil.getUserId();
		Company company = messerApAutomationUtil.copyBeanProperties(companyDto, Company.class);
		if (company.getCompanyId() != null) {
			company.setLastUpdatedOn(now);
			company.setLastUpdatedBy(loggedInUser);
		} else {
			company.setCreatedOn(now);
			company.setCreatedBy(loggedInUser);
		}
		companyRepository.save(company);
		Map<String, String> dataMap = new HashMap<>();
		dataMap.put(MesserApAutomationConstants.MESSAGE, "Company created successfully");
		dataMap.put(MesserApAutomationConstants.STATUS, MesserApAutomationConstants.SUCCESS_STATUS);
		return messerApAutomationUtil.convertPojoToJson(dataMap);
	}

	@Override
	public List<Company> getAllCompanyDetails() {
		return (List<Company>) companyRepository.findAll();
	}

	@Override
	public String deleteCompany(Integer companyId) {
		companyRepository.deleteById(companyId);
		Map<String, String> dataMap = new HashMap<>();
		dataMap.put(MesserApAutomationConstants.MESSAGE, "Company deleted successfully");
		dataMap.put(MesserApAutomationConstants.STATUS, MesserApAutomationConstants.SUCCESS_STATUS);
		return messerApAutomationUtil.convertPojoToJson(dataMap);
	}
}

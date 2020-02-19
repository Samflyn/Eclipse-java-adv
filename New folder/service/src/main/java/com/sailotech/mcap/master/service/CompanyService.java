package com.sailotech.mcap.master.service;

import java.util.List;

import com.sailotech.mcap.dto.CompanyDto;
import com.sailotech.mcap.entity.Company;

public interface CompanyService {

	public String save(CompanyDto companyDto);

	public List<Company> getAllCompanyDetails();
	
	public String deleteCompany(Integer companyId);

}

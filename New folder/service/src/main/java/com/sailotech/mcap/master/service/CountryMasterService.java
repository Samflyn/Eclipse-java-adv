package com.sailotech.mcap.master.service;

import com.sailotech.mcap.dto.CountryMasterDto;

public interface CountryMasterService {

	public Integer saveCountryDetails(CountryMasterDto countryMasterDto);

	public String getAllCountryDetails();

	public void deleteByCountryId(Integer countryId);

}

package com.sailotech.mcap.master.service;

import com.sailotech.mcap.dto.ErrorMasterDto;

public interface ErrorMasterService {

	Integer saveItemMapping(ErrorMasterDto errorMasterDto);

	String getErrorMasters();

	void deleteErrorMaster(Integer errorId);

}

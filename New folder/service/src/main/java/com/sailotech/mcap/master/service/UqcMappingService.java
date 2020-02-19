package com.sailotech.mcap.master.service;

import com.sailotech.mcap.dto.UQCMappingDto;
import com.sailotech.mcap.exception.DataValidationException;

public interface UqcMappingService {

	Integer saveUqcMapping(UQCMappingDto uqcMappingDto) throws DataValidationException;

	String getUqcMapping(Integer tenantId);

	void deleteUqcMapping(Integer uqcMappingId);

}

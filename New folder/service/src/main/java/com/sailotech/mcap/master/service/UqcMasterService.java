package com.sailotech.mcap.master.service;

import com.sailotech.mcap.dto.UQCMasterDto;
import com.sailotech.mcap.exception.DataValidationException;

public interface UqcMasterService {

	public Integer saveUqc(UQCMasterDto uQCMasterDto) throws DataValidationException;

	public String getUqcListByCompanyId(Integer tenantId);

	public String getUqcList();

	public void deleteUqcById(Integer uqcMasterId);

}
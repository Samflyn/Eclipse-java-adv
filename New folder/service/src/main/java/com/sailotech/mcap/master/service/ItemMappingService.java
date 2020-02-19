package com.sailotech.mcap.master.service;

import com.sailotech.mcap.dto.ItemMappingDto;
import com.sailotech.mcap.exception.DataValidationException;

public interface ItemMappingService {

	Integer saveItemMapping(ItemMappingDto itemMappingDto) throws DataValidationException;

	String getItemMapping(Integer tenantId);

	void deleteItemMapping(Integer itemMappingId);

}

package com.sailotech.mcap.master.service;

import java.util.List;

import com.sailotech.mcap.dto.ItemMasterDto;
import com.sailotech.mcap.exception.DataValidationException;

public interface ItemMasterService {

	public String getItemMaster();

	public String deletePartner(Integer itemId);

	List<ItemMasterDto> getAllItemsByCompanyId(Integer companyId);

	Integer saveItemMaster(ItemMasterDto itemMasterDto) throws DataValidationException;

}

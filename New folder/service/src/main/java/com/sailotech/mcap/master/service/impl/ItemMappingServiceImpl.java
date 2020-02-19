package com.sailotech.mcap.master.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sailotech.mcap.dto.ItemMappingDto;
import com.sailotech.mcap.entity.ItemMapping;
import com.sailotech.mcap.exception.DataValidationException;
import com.sailotech.mcap.master.repository.ItemMappingRepository;
import com.sailotech.mcap.master.service.ItemMappingService;
import com.sailotech.mcap.util.MesserApAutomationUtil;

@Service
public class ItemMappingServiceImpl implements ItemMappingService {

	@Autowired
	ItemMappingRepository itemMappingRepository;

	@Autowired
	MesserApAutomationUtil messerApAutomationUtil;

	@Override
	public Integer saveItemMapping(ItemMappingDto itemMappingDto) throws DataValidationException {
		Integer loggedInUser = messerApAutomationUtil.getUserId();
		if (itemMappingDto.getItemMappingId() != null) {
			checkUniqueItemMasterOnUpdate(itemMappingDto);
			itemMappingDto.setLastUpdatedBy(loggedInUser);
			itemMappingDto.setLastUpdatedOn(Calendar.getInstance().getTime());
		} else {
			checkUniqueItemMaster(itemMappingDto);
			itemMappingDto.setCreatedBy(loggedInUser);
		}
		itemMappingDto.setCompanyId(messerApAutomationUtil.getLoggedInUserCompanyId());
		ItemMapping itemMapping = itemMappingRepository
				.save(messerApAutomationUtil.convertItemMappingDtoToDao(itemMappingDto));
		return itemMapping.getItemMappingId();
	}

	@Override
	public String getItemMapping(Integer tenantId) {
		List<ItemMapping> itemMappingDao = itemMappingRepository.findByCompanyId(tenantId);
		List<ItemMappingDto> itemMappingDtos = new ArrayList<>();
		for (ItemMapping itemMapping : itemMappingDao) {
			ItemMappingDto itemMappingDto = messerApAutomationUtil.convertItemMappingDaoToDto(itemMapping);
			itemMappingDtos.add(itemMappingDto);
		}
		return messerApAutomationUtil.convertPojoToJson(itemMappingDtos);
	}

	private void checkUniqueItemMaster(ItemMappingDto itemMappingDto) throws DataValidationException {
		boolean findByItemMasterExists = itemMappingRepository
				.findByItemMasterExists(itemMappingDto.getItemMasterDto().getItemId());
		if (findByItemMasterExists) {
			throw new DataValidationException("SVMP_DATA_VALIDATION_ERROR",
					"Selected Customer Item Name already mapped");
		}
	}

	private void checkUniqueItemMasterOnUpdate(ItemMappingDto itemMappingDto) throws DataValidationException {
		boolean findByItemMasterExists = itemMappingRepository
				.findByItemMasterExistsOnUpdate(itemMappingDto.getItemMasterDto().getItemId());
		if (findByItemMasterExists) {
			throw new DataValidationException("SVMP_DATA_VALIDATION_ERROR",
					"Selected Customer Item Name already mapped");
		}
	}

	@Override
	public void deleteItemMapping(Integer itemMappingId) {
		itemMappingRepository.deleteById(itemMappingId);
	}

}

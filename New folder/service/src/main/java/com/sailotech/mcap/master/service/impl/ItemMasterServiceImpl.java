package com.sailotech.mcap.master.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sailotech.mcap.dto.ItemMasterDto;
import com.sailotech.mcap.entity.ItemMaster;
import com.sailotech.mcap.exception.DataValidationException;
import com.sailotech.mcap.master.repository.ItemMappingRepository;
import com.sailotech.mcap.master.repository.ItemMasterRepository;
import com.sailotech.mcap.master.service.ItemMasterService;
import com.sailotech.mcap.util.MesserApAutomationUtil;

@Service
public class ItemMasterServiceImpl implements ItemMasterService {

	@Autowired
	ItemMasterRepository itemMasterRepository;

	@Autowired
	MesserApAutomationUtil messerApAutomationUtil;

	@Autowired
	ItemMappingRepository itemmapping;

	@Override
	public Integer saveItemMaster(ItemMasterDto itemMasterDto) throws DataValidationException {
		Integer loggedInUser = messerApAutomationUtil.getUserId();
		if (itemMasterDto.getItemId() != null) {
			itemMasterDto.setLastUpdatedBy(loggedInUser);
			itemMasterDto.setLastUpdatedOn(Calendar.getInstance().getTime());
		} else {
			itemMasterDto.setCreatedBy(loggedInUser);
		}
		itemMasterDto.setCompanyId(messerApAutomationUtil.getLoggedInUserCompanyId());
		ItemMaster itemMaster = itemMasterRepository
				.save(messerApAutomationUtil.copyBeanProperties(itemMasterDto, ItemMaster.class));

		return itemMaster.getItemId();
	}

	@Override
	public String getItemMaster() {
		Iterable<ItemMaster> itemMasterList = itemMasterRepository.findAll();
		return messerApAutomationUtil.convertPojoToJson(itemMasterList);

	}

	@Override
	public String deletePartner(Integer itemId) {
		if (itemmapping.findByItemMasterExists(itemId)) {
			return "Item Mapping done for this item,Cannot delete";
		} else {
			itemMasterRepository.deleteById(itemId);
			return "Item Deleted Successfully";
		}
	}

	@Override
	public List<ItemMasterDto> getAllItemsByCompanyId(Integer companyId) {
		List<ItemMaster> itemsList = itemMasterRepository.findByCompanyId(companyId);
		List<ItemMasterDto> itemMasterDtos = new ArrayList<>();
		for (ItemMaster gstItemMaster : itemsList) {
			ItemMasterDto gstItemMasterDto = new ItemMasterDto();
			gstItemMasterDto.setItemId(gstItemMaster.getItemId());
			gstItemMasterDto.setItemDesc(gstItemMaster.getItemDesc());
			gstItemMasterDto.setGoodsServices(gstItemMaster.getGoodsServices());
			gstItemMasterDto.setItemType(gstItemMaster.getItemType());
			gstItemMasterDto.setItemName(gstItemMaster.getItemName());
			gstItemMasterDto.setHsnCode(gstItemMaster.getHsnCode());
			gstItemMasterDto.setRate(gstItemMaster.getRate());
			itemMasterDtos.add(gstItemMasterDto);
		}
		return itemMasterDtos;
	}
}

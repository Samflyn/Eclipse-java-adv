package com.sailotech.mcap.master.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sailotech.mcap.master.repository.DimensionTypeMasterRepository;
import com.sailotech.mcap.master.repository.LedgerAccountDimensionMasterRepository;
import com.sailotech.mcap.master.service.DimensionService;
import com.sailotech.mcap.util.MesserApAutomationUtil;

@Service
public class DimensionServiceImpl implements DimensionService {

	@Autowired
	LedgerAccountDimensionMasterRepository ledgerAccountDimensionMasterRepository;

	@Autowired
	DimensionTypeMasterRepository dimensionTypeMasterRepository;

	@Autowired
	MesserApAutomationUtil messerApAutomationUtil;

	@Override
	public String getDimensions(String ledgerAccount) {
		List<Integer> dimensions = ledgerAccountDimensionMasterRepository.getDimensions(ledgerAccount, "Y");
		if (!dimensions.isEmpty()) {
			return messerApAutomationUtil.convertPojoToJson(dimensionTypeMasterRepository.finByDimensionTypeIn(dimensions));
		} else {
			return messerApAutomationUtil.convertPojoToJson(dimensions);
		}
	}
}

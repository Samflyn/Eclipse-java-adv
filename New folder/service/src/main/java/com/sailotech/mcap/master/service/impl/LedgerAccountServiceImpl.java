package com.sailotech.mcap.master.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sailotech.mcap.entity.LedgerAccount;
import com.sailotech.mcap.master.repository.LedgerAccountRepository;
import com.sailotech.mcap.master.service.LedgerAccountService;
import com.sailotech.mcap.util.MesserApAutomationUtil;

@Service
public class LedgerAccountServiceImpl implements LedgerAccountService {

	@Autowired
	LedgerAccountRepository ledgerAccountRepository;

	@Autowired
	MesserApAutomationUtil messerApAutomationUtil;

	@Override
	public String getLedgerAccountByCompanyId(Integer companyId) {
		List<LedgerAccount> ledgerAccounts = ledgerAccountRepository.findByCompanyId(companyId);
		return messerApAutomationUtil.convertPojoToJson(ledgerAccounts);
	}

}

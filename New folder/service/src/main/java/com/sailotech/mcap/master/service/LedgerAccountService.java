package com.sailotech.mcap.master.service;

import org.springframework.stereotype.Service;

@Service
public interface LedgerAccountService {

	String getLedgerAccountByCompanyId(Integer companyId);

}

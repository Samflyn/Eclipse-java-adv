package com.sailotech.mcap.master.service;

import org.springframework.stereotype.Service;

@Service
public interface DimensionService {
	public String getDimensions(String ledgerAcount);
}

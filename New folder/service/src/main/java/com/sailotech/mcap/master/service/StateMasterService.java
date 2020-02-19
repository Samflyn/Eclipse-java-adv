package com.sailotech.mcap.master.service;

import com.sailotech.mcap.dto.StateMasterDto;

public interface StateMasterService {

	public Integer saveState(StateMasterDto stateMasterDto);

	public String getAllStates();

	public void deleteByStateId(Integer stateId);

}

package com.sailotech.mcap.util;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Component;

import com.sailotech.mcap.entity.ErrorMaster;
import com.sailotech.mcap.master.repository.ErrorMasterRepository;

@Component
@CacheConfig(cacheNames = "mcapCache")
public class MesserApAutomationCacheManager {

	private static final Logger LOGGER = LoggerFactory.getLogger(MesserApAutomationCacheManager.class);

	@Autowired
	ErrorMasterRepository errorMasterRepository;

	@Autowired
	CacheManager cacheManager;

	@CacheEvict(value = "mcapCache", allEntries = true)
	public void clearCache() {
		// This method will remove all 'errorMessages' from cache
	}

	@PreDestroy
	public void cleanUp() {
		LOGGER.info(": MCAP Cache Manager cleanUp :");
		clearCache();
	}

	@PostConstruct
	public void initSVMPCacheManager() {
		LOGGER.info(": MCAP initSVMPCacheManager :");
		putAllErrorCodes();
	}

	public void putAllErrorCodes() {
		List<ErrorMaster> errorMasters = (List<ErrorMaster>) errorMasterRepository.findAll();
		errorMasters.forEach(
				errorMaster -> cacheManager.getCache("mcapCache").put(errorMaster.getErrorCode(), errorMaster));
	}

	public ErrorMaster findByErrorCode(String errorCode) {
		return errorMasterRepository.findByErrorCode(errorCode);
	}

}

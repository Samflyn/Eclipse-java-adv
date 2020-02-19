package com.sailotech.mcap.master.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sailotech.mcap.entity.ErrorMaster;

@Repository
public interface ErrorMasterRepository extends CrudRepository<ErrorMaster, Integer> {

	ErrorMaster findByErrorCode(String errorCode);

}

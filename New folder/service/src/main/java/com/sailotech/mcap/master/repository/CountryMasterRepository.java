package com.sailotech.mcap.master.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sailotech.mcap.entity.CountryMaster;

@Repository
public interface CountryMasterRepository extends CrudRepository<CountryMaster, Integer> {

}

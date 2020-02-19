package com.sailotech.mcap.master.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sailotech.mcap.entity.StateMaster;

@Repository
public interface StateMasterRepository extends CrudRepository<StateMaster, Integer> {

}

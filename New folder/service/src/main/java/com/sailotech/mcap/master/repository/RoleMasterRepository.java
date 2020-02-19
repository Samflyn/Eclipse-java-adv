package com.sailotech.mcap.master.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sailotech.mcap.entity.RoleMaster;

@Repository
public interface RoleMasterRepository extends CrudRepository<RoleMaster, Integer> {

}

package com.sailotech.mcap.master.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.sailotech.mcap.entity.ItemMaster;

public interface ItemMasterRepository extends CrudRepository<ItemMaster, Integer> {

	List<ItemMaster> findByCompanyId(Integer companyId);

}
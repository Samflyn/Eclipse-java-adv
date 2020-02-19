package com.sailotech.mcap.master.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sailotech.mcap.entity.UQCMaster;

@Repository
public interface UqcMasterRepository extends CrudRepository<UQCMaster, Integer> {

	List<UQCMaster> findByCompanyId(Integer companyId);

	@Query(value = "select case when count(um) > 0 then true else false end from UQCMaster um where um.uqcCode=:uqcCode")
	public boolean findByUqcMasterExists(@Param("uqcCode") String uqcCode);

	@Query(value = "select case when count(um) > 1 then true else false end from UQCMaster um where um.uqcCode=:uqcCode")
	public boolean findByUqcMasterExistsOnUpdate(@Param("uqcCode") String uqcCode);

}

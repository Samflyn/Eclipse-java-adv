package com.sailotech.mcap.master.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sailotech.mcap.entity.UQCMapping;

@Repository
public interface UqcMappingRepository extends CrudRepository<UQCMapping, Integer> {

	List<UQCMapping> findByCompanyId(Integer companyId);

	@Query(value = "select case when count(um) > 0 then true else false end from uqc_mapping um where um.uqc_master_id in (:uqcMasterId)", nativeQuery = true)
	public boolean findByUqcMasterExists(@Param("uqcMasterId") Integer uqcMasterId);

	@Query(value = "select case when count(um) > 1 then true else false end from uqc_mapping um where um.uqc_master_id in (:uqcMasterId)", nativeQuery = true)
	public boolean findByUqcMasterExistsOnUpdate(@Param("uqcMasterId") Integer uqcMasterId);

	public UQCMapping findByuqcCode(String uqc);

}

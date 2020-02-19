package com.sailotech.mcap.master.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sailotech.mcap.entity.ItemMapping;

@Repository
public interface ItemMappingRepository extends CrudRepository<ItemMapping, Integer> {

	List<ItemMapping> findByCompanyId(Integer companyId);

	@Query(value = "select case when count(im) > 0 then true else false end from ItemMapping im where im.itemMaster.itemId in(:itemId)")
	public boolean findByItemMasterExists(@Param("itemId") Integer itemId);

	@Query(value = "select case when count(im) > 1 then true else false end from ItemMapping im where im.itemMaster.itemId in(:itemId)")
	public boolean findByItemMasterExistsOnUpdate(@Param("itemId") Integer itemId);

	ItemMapping findByItemMappingCode(String code);

}

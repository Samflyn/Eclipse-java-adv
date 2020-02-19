package com.sailotech.mcap.master.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sailotech.mcap.entity.DimensionTypeMaster;

@Repository
public interface DimensionTypeMasterRepository extends CrudRepository<DimensionTypeMaster, Integer> {

	@Query("from DimensionTypeMaster dm WHERE dm.dimensionType in(:dimensionTypes)")
	public List<DimensionTypeMaster> finByDimensionTypeIn(@Param("dimensionTypes") List<Integer> dimensionTypes);

}

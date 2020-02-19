package com.sailotech.mcap.master.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sailotech.mcap.entity.LedgerAccountDimensionMaster;

@Repository
public interface LedgerAccountDimensionMasterRepository extends CrudRepository<LedgerAccountDimensionMaster, Integer> {

	@Query("SELECT lad.dimensionType from LedgerAccountDimensionMaster lad WHERE lad.ledgerAccount=:ledgerAccount and lad.dimensionRequired=:dimensionRequired")
	public List<Integer> getDimensions(@Param("ledgerAccount") String ledgerAccount,
			@Param("dimensionRequired") String dimensionRequired);
}

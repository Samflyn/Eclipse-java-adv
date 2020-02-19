package com.sailotech.mcap.master.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sailotech.mcap.entity.LedgerAccount;

@Repository
public interface LedgerAccountRepository extends CrudRepository<LedgerAccount, Integer> {

	List<LedgerAccount> findByCompanyId(Integer companyId);

}

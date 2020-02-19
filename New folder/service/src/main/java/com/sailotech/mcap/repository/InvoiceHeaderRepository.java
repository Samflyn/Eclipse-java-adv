package com.sailotech.mcap.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sailotech.mcap.entity.InvoiceHeader;


@Repository
public interface InvoiceHeaderRepository
		extends CrudRepository<InvoiceHeader, Integer>, JpaSpecificationExecutor<InvoiceHeader> {

	Optional<InvoiceHeader> findByHeaderIdAndWorkFlowStatus(Integer headerId, int workflowStatus);

	Optional<List<InvoiceHeader>> findByTransactionTypeAndWorkFlowStatus(String transactionType, Integer workflowStatus);

	


}

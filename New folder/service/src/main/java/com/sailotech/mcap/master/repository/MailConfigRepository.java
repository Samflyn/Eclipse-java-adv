package com.sailotech.mcap.master.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sailotech.mcap.entity.EmailConfiguration;

@Repository
public interface MailConfigRepository extends CrudRepository<EmailConfiguration, Integer> {
	EmailConfiguration findByCompanyId(Integer companyId);
}

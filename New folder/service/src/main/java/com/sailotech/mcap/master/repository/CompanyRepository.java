package com.sailotech.mcap.master.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sailotech.mcap.entity.Company;

@Repository
public interface CompanyRepository extends CrudRepository<Company, Integer> {

}

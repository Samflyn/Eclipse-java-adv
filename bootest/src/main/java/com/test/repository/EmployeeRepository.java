package com.test.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.test.bean.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
	
	@Query("from Employee e where e.name=?1 and e.password=?2")
	Employee findByNameAndPassword(String name, String password);

	List<Employee> findByName(String name);
	

}

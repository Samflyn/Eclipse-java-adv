package com.example.demo.testing;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpRepository extends ReactiveCrudRepository<Emp, String> {
}

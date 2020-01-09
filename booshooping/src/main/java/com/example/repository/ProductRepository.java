package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.bean.Products;

@Repository
public interface ProductRepository extends JpaRepository<Products, Integer> {

}

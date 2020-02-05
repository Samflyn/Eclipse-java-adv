package com.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.bean.Roles;

public interface RolesRepository extends JpaRepository<Roles, Long> {

}

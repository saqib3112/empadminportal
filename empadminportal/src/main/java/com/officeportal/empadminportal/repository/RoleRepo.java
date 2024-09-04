package com.officeportal.empadminportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.officeportal.empadminportal.model.Role;


public interface RoleRepo extends JpaRepository<Role, Long>{

	@Query("select r from Role r where roleName = :roleName")
	public Role findByRoleName(@Param("roleName") String roleName ); 
}

package com.officeportal.empadminportal.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.officeportal.empadminportal.model.User;

public interface UserRepo extends JpaRepository<User, Long> {
		
		@Query("select u from User u where u.email = :email")
		public User findByEmail(@Param("email") String email); 
		
		//public User findById(@Param("id") String id); 
		
}

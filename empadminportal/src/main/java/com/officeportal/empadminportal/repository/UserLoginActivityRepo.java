package com.officeportal.empadminportal.repository;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.officeportal.empadminportal.model.User;
import com.officeportal.empadminportal.model.UserLoginActivity;

public interface UserLoginActivityRepo extends JpaRepository<UserLoginActivity, Long> {

	public UserLoginActivity findByUser(User user);
}

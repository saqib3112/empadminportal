package com.officeportal.empadminportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.officeportal.empadminportal.model.User;
import com.officeportal.empadminportal.model.UserToken;

public interface UserTokenRepo extends JpaRepository<UserToken, Long> {

	@Query("select ut from UserToken ut where ut.user = :user and ut.isExpired =:isExpired")
	public UserToken findByUserAndIsExpired(@Param("user") User user, @Param("isExpired") boolean isExpired);

	@Query("select ut from UserToken ut where ut.token = :token and ut.isExpired =:isExpired")
	public UserToken findByTokenAndIsExpired(@Param("token") String token, @Param("isExpired") boolean isExpired);

}

package com.officeportal.empadminportal.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.officeportal.empadminportal.model.User;
import com.officeportal.empadminportal.model.UserToken;
import com.officeportal.empadminportal.repository.UserTokenRepo;
import com.officeportal.empadminportal.service.UserTokenService;

@Service
public class UserTokenServiceImpl implements UserTokenService {

	@Autowired
	private UserTokenRepo userTokenRepo;

	@Override
	public User findByToken(String token) {

		UserToken ut = userTokenRepo.findByTokenAndIsExpired(token, false);

		if (ut != null) {
			return ut.getUser();
		} else
			return null;
	}

}

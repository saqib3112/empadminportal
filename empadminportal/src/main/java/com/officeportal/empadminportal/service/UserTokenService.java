package com.officeportal.empadminportal.service;

import com.officeportal.empadminportal.model.User;

public interface UserTokenService {

	User findByToken(String token);
}

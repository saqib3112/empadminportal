package com.officeportal.empadminportal.service;

import java.util.List;

import com.officeportal.empadminportal.model.User;
import com.officeportal.empadminportal.model.UserToken;
import com.officeportal.empadminportal.request.UserLoginRequest;
import com.officeportal.empadminportal.request.UserRegistrationRequest;
import com.officeportal.empadminportal.response.ApiResponseMessage;
import com.officeportal.empadminportal.response.LoginResponse;
import com.officeportal.empadminportal.response.RoleUpdateResponse;

public interface UserService {

	List<User> getAllUser();

	ApiResponseMessage addNewUser(UserRegistrationRequest userRegistrationRequest);

	LoginResponse loginUser(UserLoginRequest userLoginRequest);

	List<User> activeUsers();

	RoleUpdateResponse changeUserRole(User user, long id);

	User deActivateUser(long id, boolean isStatus);

	List<User> inActiveUsers();

	String generateUserReport();



}

package com.officeportal.empadminportal.serviceImpl;

import java.io.FileWriter;
import java.io.IOException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.hibernate.internal.build.AllowSysOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.officeportal.empadminportal.exception.BadRequestException;
import com.officeportal.empadminportal.model.Role;
import com.officeportal.empadminportal.model.User;
import com.officeportal.empadminportal.model.UserLoginActivity;
import com.officeportal.empadminportal.model.UserToken;
import com.officeportal.empadminportal.repository.RoleRepo;
import com.officeportal.empadminportal.repository.UserLoginActivityRepo;
import com.officeportal.empadminportal.repository.UserRepo;
import com.officeportal.empadminportal.repository.UserTokenRepo;
import com.officeportal.empadminportal.request.UserLoginRequest;
import com.officeportal.empadminportal.request.UserRegistrationRequest;
import com.officeportal.empadminportal.response.ApiResponseMessage;
import com.officeportal.empadminportal.response.LoginResponse;
import com.officeportal.empadminportal.response.RoleUpdateResponse;
import com.officeportal.empadminportal.service.UserService;

import jakarta.validation.Valid;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private RoleRepo roleRepo;

	@Autowired
	private UserLoginActivityRepo userLoginActivityRepo;
	
	@Autowired
	private UserTokenRepo userTokenRepo;

	public List<User> getAllUser() {
		return userRepo.findAll();

	}

	@Override
	public ApiResponseMessage addNewUser(UserRegistrationRequest userRegistrationRequest) {

		validateUserRegistrationRequest(userRegistrationRequest);

		Role role = roleRepo.findByRoleName("USER");

		User user = new User();

		user.setRole(role);
		user.setStatus(true);
		user.setPassword(passwordEncoder.encode(userRegistrationRequest.getPassword()));
		user.setUsername(userRegistrationRequest.getUsername());
		user.setEmail(userRegistrationRequest.getEmail());

		userRepo.save(user);
		return new ApiResponseMessage("Successfully registerd");
	}
////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private void validateUserRegistrationRequest(UserRegistrationRequest userRegistrationRequest) {

		if (!StringUtils.hasLength(userRegistrationRequest.getUsername())) {
			throw new BadRequestException("UserName is required");
		}

		if (!StringUtils.hasLength(userRegistrationRequest.getEmail())) {
			throw new BadRequestException("Email is required");
		}

		if (!StringUtils.hasLength(userRegistrationRequest.getPassword())) {
			throw new BadRequestException("Password is required");
		}

		User existingUser = userRepo.findByEmail(userRegistrationRequest.getEmail());

		if (existingUser != null) {
			throw new BadRequestException("User already existis");
		}

	}

/////////////////////////////////////////////

	@Override
	public LoginResponse loginUser(UserLoginRequest userLoginRequest) {

		User existingUser = userRepo.findByEmail(userLoginRequest.getEmail());

		if (existingUser == null) {
			throw new BadRequestException("User not found");
		}

		if (!existingUser.isStatus()) {
			throw new BadRequestException("Inactive User");
		}

		else if (passwordEncoder.matches(userLoginRequest.getPassword(), existingUser.getPassword())) {

			UserToken userToken = userTokenRepo.findByUserAndIsExpired(existingUser, false);

			if (userToken == null || userToken.getExpiresAt().isBefore(LocalDateTime.now())) {
				if (userToken != null) {
					userToken.setExpired(true);
					userTokenRepo.save(userToken);
				}

				userToken = new UserToken();

				userToken.setUser(existingUser);
				userToken.setCreatedAt(LocalDateTime.now());
				userToken.setExpiresAt(LocalDateTime.now().plusHours(4));
				userToken.setToken(generateToken());
				userToken.setExpired(false);

				userTokenRepo.save(userToken);

//////////////////////////////////////

//				UserLoginActivity existingLoginActivity = userLoginActivityRepo.findByUser(existingUser);
//
//				UserLoginActivity userLoginActivity;
//
//				if (existingLoginActivity != null) {
//					userLoginActivity = existingLoginActivity;
//					userLoginActivity.setLoginTime(LocalDateTime.now());
//				} else {
//					
//					//UserLoginActivity userLoginActivity = new UserLoginActivity();
//					userLoginActivity = new UserLoginActivity();
//					
//					userLoginActivity.setUser(existingUser);
//					userLoginActivity.setLoginTime(LocalDateTime.now());
//				}
//				userLoginActivityRepo.save(userLoginActivity);


				UserLoginActivity userLoginActivity = new UserLoginActivity();

				userLoginActivity.setUser(existingUser);
				userLoginActivity.setLoginTime(LocalDateTime.now());

				userLoginActivityRepo.save(userLoginActivity);

			}

			LoginResponse loginResponse = new LoginResponse();

			loginResponse.setUsername(existingUser.getUsername());
			loginResponse.setEmail(existingUser.getEmail());
			loginResponse.setRole(existingUser.getRole());
			loginResponse.setToken(userToken.getToken());
			loginResponse.setExpiresAt(userToken.getExpiresAt());

			return loginResponse;

		}

		else {
			throw new BadRequestException("Password is invalid");
		}

	}

	@Override
	public RoleUpdateResponse changeUserRole(User user, long id) {

		Role role = roleRepo.findByRoleName(user.getRole().getRoleName());

		User users = userRepo.findById(id).orElseThrow(() -> new BadRequestException("User not found"));

		users.setRole(role);
		userRepo.save(users);

		RoleUpdateResponse roleUpdateResponse = new RoleUpdateResponse();

		roleUpdateResponse.setId(users.getId());
		roleUpdateResponse.setUsername(users.getUsername());
		roleUpdateResponse.setEmail(users.getEmail());
		roleUpdateResponse.setRole(users.getRole());
		roleUpdateResponse.setMessage("Role Updated Successfully");

		return roleUpdateResponse;

	}

	@Override
	public User deActivateUser(long id, boolean isStatus) {

		User users = userRepo.findById(id).orElseThrow(() -> new BadRequestException("User not found"));

		users.setStatus(isStatus);

		return userRepo.save(users);

	}

	@Override
	public List<User> activeUsers() {

		List<User> allUser = userRepo.findAll();

		List<User> activeUsers = new ArrayList<User>();

		for (User user : allUser) {
			if (user.isStatus()) {
				activeUsers.add(user);
			}
		}

		return activeUsers;

	}

	@Override
	public List<User> inActiveUsers() {

		List<User> allUser = userRepo.findAll();

		List<User> inActiveUsers = new ArrayList<User>();

		for (User user : allUser) {
			if (!user.isStatus()) {
				inActiveUsers.add(user);
			}
		}

		return inActiveUsers;

	}
	
	 @Override
	    public String generateUserReport() {
	        List<User> activeUsers = activeUsers();
	        List<User> inactiveUsers = inActiveUsers();
	       
	        String filePath = "E:\\File/user_report.csv";
	        
	        try (FileWriter writer = new FileWriter(filePath)) {
	            writer.append("Active Users\n");
	            writer.append("ID,Username,Email,Role\n");
	            for (User user : activeUsers) {
	                writer.append(user.getId().toString()).append(",");
	                writer.append(user.getUsername()).append(",");
	                writer.append(user.getEmail()).append(",");
	                writer.append(user.getRole().getRoleName()).append("\n");
	            }

	            writer.append("\nInactive Users\n");
	            writer.append("ID,Username,Email,Role\n");
	            for (User user : inactiveUsers) {
	                writer.append(user.getId().toString()).append(",");
	                writer.append(user.getUsername()).append(",");
	                writer.append(user.getEmail()).append(",");
	                writer.append(user.getRole().getRoleName()).append("\n");
	              
	            }

	            writer.flush();
	            return "Report generated successfully!";
	        } catch (IOException e) {
	            e.printStackTrace();
	            return "Error while generating report!";
	        }
	 }
	
	

	private String generateToken() {

		String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		int TOKEN_LENGTH = 32;
		SecureRandom RANDOM = new SecureRandom();

		StringBuilder token = new StringBuilder(TOKEN_LENGTH);
		for (int i = 0; i < TOKEN_LENGTH; i++) {
			token.append(CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS.length())));
		}
		return token.toString();
	}

}

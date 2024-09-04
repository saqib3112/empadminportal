package com.officeportal.empadminportal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.officeportal.empadminportal.model.Role;
import com.officeportal.empadminportal.model.User;
import com.officeportal.empadminportal.model.UserToken;
import com.officeportal.empadminportal.repository.UserTokenRepo;
import com.officeportal.empadminportal.request.UserLoginRequest;
import com.officeportal.empadminportal.request.UserRegistrationRequest;
import com.officeportal.empadminportal.response.ApiResponseMessage;
import com.officeportal.empadminportal.response.LoginResponse;
import com.officeportal.empadminportal.response.RoleUpdateResponse;
import com.officeportal.empadminportal.service.UserService;
//import com.sun.tools.classfile.Opcode.Set;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("/admin/all-users")
	public List<User> showAllUser() {
		return userService.getAllUser();
	}

	@PostMapping("/registration")
	public ApiResponseMessage registrationNewUser(@RequestBody UserRegistrationRequest userRegistrationRequest) {
		return userService.addNewUser(userRegistrationRequest);
	}

	// Login
	@PostMapping("/login")
	public LoginResponse LoginUser(@RequestBody UserLoginRequest userLoginRequest) {

		return userService.loginUser(userLoginRequest);
	}

	@PutMapping("/admin/change-role/{id}")
	public RoleUpdateResponse updateRoleById(@RequestBody User user, @PathVariable("id") long id) {
		return userService.changeUserRole(user, id);
	}

	@PutMapping("/admin/update-activity/{id}")
	public User updateUserActivity(@PathVariable long id, @RequestParam boolean isStatus) {

		return userService.deActivateUser(id, isStatus);

	}

	@GetMapping("/admin/active-user")
	public List<User> showActiveUsers() {
		return userService.activeUsers();
	}

	@GetMapping("/admin/inactive-user")
	public List<User> showInActiveUsers() {
		return userService.inActiveUsers();
	}

	@GetMapping("/admin/report")
	public String generateUserReport() {
		return userService.generateUserReport();

	}

//	@GetMapping("/activeUser")
//	public List<User> getInActiveUsers() {
//		return userService.getActiveUsers();
//	}

//	@PostMapping("/userToken")
//	public UserToken addToken(UserToken userToken) {
//		
//		return userService.addToken(userToken);
//		
//	}

}

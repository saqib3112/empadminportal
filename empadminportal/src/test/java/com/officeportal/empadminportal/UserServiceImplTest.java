package com.officeportal.empadminportal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.officeportal.empadminportal.model.Role;
import com.officeportal.empadminportal.model.User;
import com.officeportal.empadminportal.repository.RoleRepo;
import com.officeportal.empadminportal.repository.UserRepo;
import com.officeportal.empadminportal.request.UserRegistrationRequest;
import com.officeportal.empadminportal.response.ApiResponseMessage;
import com.officeportal.empadminportal.serviceImpl.UserServiceImpl;

public class UserServiceImplTest {

	@Mock
	private BCryptPasswordEncoder passwordEncoder;

	@Mock
	private UserRepo userRepo;

	@Mock
	private RoleRepo roleRepo;

	@InjectMocks
	private UserServiceImpl userService;

	private User user;
	private Role role;
	private UserRegistrationRequest userRegistrationRequest;

	@BeforeEach
	void setUp() {
		role = new Role();
		role.setRoleName("USER");

		user = new User();
		user.setId(1L);
		user.setUsername("pp");
		user.setEmail("pp@gmail.com");
		user.setPassword("pp");
		user.setRole(role);
		user.setStatus(true);

		userRegistrationRequest = new UserRegistrationRequest();
		userRegistrationRequest.setUsername("pp");
		userRegistrationRequest.setEmail("pp@gmail.com");
		userRegistrationRequest.setPassword("pp");

	}

	@Test
	void testAddNewUser() {
//		when(roleRepo.findByRoleName("USER")).thenReturn(role);
//		when(passwordEncoder.encode(userRegistrationRequest.getPassword())).thenReturn("saqib");
//		when(userRepo.findByEmail(userRegistrationRequest.getEmail())).thenReturn(null);
//		when(userRepo.save(any(User.class))).thenReturn(user);
//
//		ApiResponseMessage response = userService.addNewUser(userRegistrationRequest);
//
//		assertEquals("Successfully registerd", response.getMessage());
//		verify(userRepo, times(1)).save(any(User.class));
	}

}

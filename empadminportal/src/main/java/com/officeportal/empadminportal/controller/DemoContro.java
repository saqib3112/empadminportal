package com.officeportal.empadminportal.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping()
public class DemoContro {

	@GetMapping("/user")
	public String Hi() {
		return "User logged in";
	}

	@GetMapping("/admin")
	public String He() {
		return "Admin now";
	}
}

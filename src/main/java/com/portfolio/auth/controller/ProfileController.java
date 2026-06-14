package com.portfolio.auth.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProfileController {
	
	@GetMapping("/api/profile")
	public String profile(Authentication authentication) {

		 System.out.println(
		            "Authentication = "
		            + authentication);
		 
		return authentication.getName();
	}
	
}

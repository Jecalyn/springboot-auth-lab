package com.portfolio.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.portfolio.auth.common.response.ApiResponse;
import com.portfolio.auth.dto.request.LoginRequest;
import com.portfolio.auth.dto.request.RegisterRequest;
import com.portfolio.auth.dto.response.LoginResponse;
import com.portfolio.auth.service.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
	
	private final AuthService authService;
	
	@PostMapping("/register")
	public ResponseEntity<ApiResponse<Void>> register(@Valid @RequestBody RegisterRequest request){
		
		authService.register(request);
		
		return ResponseEntity.ok(
				ApiResponse.<Void>builder()
					.success(true)
					.message("User registered successfully")
					.data(null)
					.build());
	}
	
	@PostMapping("/login")
	public ResponseEntity<ApiResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest request){
		
		LoginResponse response = authService.login(request);
		
		return ResponseEntity.ok(
				ApiResponse.<LoginResponse>builder()
				.success(true)
				.message("Login successful")
				.data(response)
				.build());
				
	}


}

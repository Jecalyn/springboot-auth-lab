package com.portfolio.auth.service;

import com.portfolio.auth.dto.request.LoginRequest;
import com.portfolio.auth.dto.request.RegisterRequest;
import com.portfolio.auth.dto.response.LoginResponse;

public interface AuthService {
	
	void register(RegisterRequest request);
	
	LoginResponse login(LoginRequest request);

}

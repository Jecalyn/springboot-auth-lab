package com.portfolio.auth.service;

import java.time.LocalDateTime;
import java.util.Set;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.portfolio.auth.dto.request.LoginRequest;
import com.portfolio.auth.dto.request.RegisterRequest;
import com.portfolio.auth.dto.response.LoginResponse;
import com.portfolio.auth.entity.Role;
import com.portfolio.auth.entity.User;
import com.portfolio.auth.exception.AuthenticationException;
import com.portfolio.auth.exception.ResourceAlreadyExistsException;
import com.portfolio.auth.repository.RoleRepository;
import com.portfolio.auth.repository.UserRepository;
import com.portfolio.auth.security.JwtService;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

	
	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	
	
	@Override
	public void register(RegisterRequest request) {
		
		if(userRepository.findByUsername(request.getUsername()).isPresent()) {
			throw new ResourceAlreadyExistsException("Username already exists");
		}
		
		if(userRepository.findByEmail(request.getEmail()).isPresent()) {
			throw new ResourceAlreadyExistsException("Email already exists");
		}
		
		Role userRole = roleRepository.findByRoleName("USER")
				.orElseThrow(() -> new ResourceAlreadyExistsException("Role USER not found"));
		
		User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .passwordHash(
                        passwordEncoder.encode(
                                request.getPassword()))
                .accountLocked(false)
                .failedLoginCount(0)
                .createdAt(LocalDateTime.now())
                .roles(Set.of(userRole))
                .build();
		
		System.out.print("New user registered");
		userRepository.save(user);
	}
	
	@Override
	public LoginResponse login(LoginRequest request) {
		
		User user = userRepository.findByUsername(request.getUsername())
				.orElseThrow(() -> new AuthenticationException("Invalid username or password"));
		
		boolean valid = passwordEncoder.matches(request.getPassword(), user.getPasswordHash());
		
		if(!valid) {
			throw new AuthenticationException("Invalid username or password");
		}
		
		return jwtService.generateToken(user.getUsername(), user.getRoles());
		
	}

}

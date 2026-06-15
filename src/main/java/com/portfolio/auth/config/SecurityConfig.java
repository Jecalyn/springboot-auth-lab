package com.portfolio.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.portfolio.auth.security.JwtAuthenticationFilter;

import lombok.RequiredArgsConstructor;


@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	
	private final JwtAuthenticationFilter jwtAuthenticationFilter;
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http)
	        throws Exception {

	    http
	        .csrf(csrf -> csrf.disable())
	        
	        .sessionManagement(session -> 
	        		session.sessionCreationPolicy(
	        				SessionCreationPolicy.STATELESS))
	        
	        .authorizeHttpRequests(auth -> auth
        		.requestMatchers(
        			    "/api/auth/**",
        			    "/health"
        			)
	            .permitAll()
	            .anyRequest()
	            .authenticated())
	        
	        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
	        
	        

	    return http.build();
	}

}

package com.portfolio.auth.security;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.portfolio.auth.dto.response.LoginResponse;
import com.portfolio.auth.entity.Role;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
	
	@Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;
    
    public LoginResponse generateToken(String username, Set<Role> roles) {
    	
    	SecretKey key = Keys.hmacShaKeyFor(
    			secret.getBytes(StandardCharsets.UTF_8)
    			);
    	
    	List<String> roleNames = roles.stream()
    						.map(Role::getRoleName)
    						.toList();
    			
    	
    	String token = Jwts.builder()
    			.subject(username)
    			.claim("roles", roleNames)
    			.issuedAt(new Date())
    			.expiration(new Date(
    						System.currentTimeMillis()
    							+ expiration))
    			.signWith(key)
    			.compact();
    	
    	return LoginResponse.builder()
				.token(token)
				.tokenType("Bearer")
				.username(username)
				.build();
    }
    
    public String extractUsername(String token) {
    	
    	String username = extractAllClaims(token).getSubject();
    	System.out.println("Username: " + username);
    	
    	return username;
    }
    
    public List<String> extractRole(String token) {
    	
    	List<String> roleNames = extractAllClaims(token).get("roles", List.class);
    	System.out.println("List of Roles: " + roleNames.toString());
    	
    	return roleNames;
    	
    }
    
    public boolean isTokenValid(String token) {
    	try {
    		
    		extractAllClaims(token);
    		System.out.println("Valid token.");
    		return true;
    		
    	}catch(Exception e){
    		System.out.println("Invalid token.");
    		return false;
    	}
    }
    
    private Claims extractAllClaims(String token) {
    	
    	System.out.println(Jwts.parser()
    			.verifyWith(getSigningKey())
    			.build()
    			.parseSignedClaims(token)
    			.getPayload());
    	return Jwts.parser()
    			.verifyWith(getSigningKey())
    			.build()
    			.parseSignedClaims(token)
    			.getPayload();
    }
    
    private SecretKey getSigningKey() {
    	
    	return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }
    
    

}

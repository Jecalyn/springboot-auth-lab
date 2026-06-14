package com.portfolio.auth.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.portfolio.auth.common.response.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(ResourceAlreadyExistsException.class)
	public ResponseEntity<ApiResponse<Void>> handleResourceAlreadyExists(ResourceAlreadyExistsException ex) {

	    return ResponseEntity
	            .status(HttpStatus.CONFLICT)
	            .body(
	                    ApiResponse.<Void>builder()
	                            .success(false)
	                            .message(ex.getMessage())
	                            .build());
	}
	
	@ExceptionHandler(AuthenticationException.class)
	public ResponseEntity<ApiResponse<Void>> handleAuthentication(AuthenticationException ex) {

	    return ResponseEntity
	            .status(HttpStatus.UNAUTHORIZED)
	            .body(
	                    ApiResponse.<Void>builder()
	                            .success(false)
	                            .message(ex.getMessage())
	                            .build());
	}
	
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiResponse<Void>> handleException(Exception ex) {

	    return ResponseEntity
	            .status(
	                    HttpStatus.INTERNAL_SERVER_ERROR)
	            .body(
	                    ApiResponse.<Void>builder()
	                            .success(false)
	                            .message(ex.getMessage())
	                            .build());
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiResponse<Map<String,String>>> handleValidationException(MethodArgumentNotValidException ex){
		
		Map<String, String> errors = new HashMap<>();
		
		ex.getBindingResult()
			.getFieldErrors()
			.forEach(error -> 
				errors.put(error.getField(), error.getDefaultMessage()));
		
		return ResponseEntity
				.badRequest()
				.body(
						ApiResponse
							.<Map<String,String>> builder()
							.success(false)
							.message("Validation failed")
							.data(errors)
							.build());
	}
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponse<Void>>
	handleResourceNotFound(
	        ResourceNotFoundException ex) {

	    return ResponseEntity
	            .status(HttpStatus.NOT_FOUND)
	            .body(
	                    ApiResponse.<Void>builder()
	                            .success(false)
	                            .message(ex.getMessage())
	                            .build());
	}
	

}

package com.cognixia.jump.exception;

import java.util.Date;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

// advice the controller about what to do when an exception is thrown
@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<?> resourceNotFoundException( ResourceNotFoundException ex, WebRequest request ) {
		
		// request.getDescription(false) => details on the request (usually includes the uri path where request was made)
		ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));
		
		// when the exception gets thrown, instead of returning the exception as json in the response,
		// return instead this response entity
		return ResponseEntity.status(404).body(errorDetails);
		
	}
	@ExceptionHandler(DuplicateUserException.class)
	public ResponseEntity<?> duplicateUserException( DuplicateUserException ex, WebRequest request ) {
		
		// request.getDescription(false) => details on the request (usually includes the uri path where request was made)
		ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));
		
		// when the exception gets thrown, instead of returning the exception as json in the response,
		// return instead this response entity
		return ResponseEntity.status(404).body(errorDetails);
		
	}
	
	@ExceptionHandler(DuplicateCypherException.class)
	public ResponseEntity<?> duplicateCypherException( DuplicateCypherException ex, WebRequest request ) {
		
		// request.getDescription(false) => details on the request (usually includes the uri path where request was made)
		ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));
		
		// when the exception gets thrown, instead of returning the exception as json in the response,
		// return instead this response entity
		return ResponseEntity.status(404).body(errorDetails);
		
	}
}
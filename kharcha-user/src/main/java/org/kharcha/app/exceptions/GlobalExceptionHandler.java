package org.kharcha.app.exceptions;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<Object> handleUserAlreadyExists(UserAlreadyExistsException ex) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.CONFLICT);
    }
	
	@ExceptionHandler(UserNotExistsException.class)
	public ResponseEntity<Object> handleUserNotExists(UserNotExistsException ex){
        return buildErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGenericException(Exception ex) {
        return buildErrorResponse("Something went wrong"+ex, HttpStatus.INTERNAL_SERVER_ERROR);
    }
	
	 private ResponseEntity<Object> buildErrorResponse(String message, HttpStatus status) {
	        Map<String, Object> error = new HashMap<>();
	        error.put("timestamp", LocalDateTime.now());
	        error.put("status", status.value());
	        error.put("error", status.getReasonPhrase());
	        error.put("message", message);

	        return new ResponseEntity<>(error, status);
	  }
}

package com.officeportal.empadminportal.exception;

import java.time.LocalDateTime;	

import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.officeportal.empadminportal.response.ErrorResponse;

@RestControllerAdvice
public class GloballyExpHandller {
	
	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<ErrorResponse> badRequestExceptionHandler(BadRequestException ex){
		
		String message = ex.getMessage();
		ErrorResponse apiMessage = new ErrorResponse(LocalDateTime.now().toString(), 400, "Bad Request", message);
		return new ResponseEntity<ErrorResponse>(apiMessage, HttpStatus.NOT_FOUND);

	}

}

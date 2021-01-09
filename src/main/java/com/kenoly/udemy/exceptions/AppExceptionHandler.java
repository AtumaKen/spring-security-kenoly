package com.kenoly.udemy.exceptions;

import java.util.Date;

import javax.jws.WebService;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.kenoly.udemy.model.response.ErrorMessage;

@ControllerAdvice
public class AppExceptionHandler {

	@ExceptionHandler(UserServiceException.class)
	public ResponseEntity<Object> handleUserServiceException(UserServiceException ex, WebService wb) {
		ErrorMessage errorMessage = new ErrorMessage(new Date(), ex.getMessage());
//		return new ResponseEntity<>(ex, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
		return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
}

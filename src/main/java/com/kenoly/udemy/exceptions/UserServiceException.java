package com.kenoly.udemy.exceptions;

public class UserServiceException extends RuntimeException{

	private static final long serialVersionUID = -2125081699995009362L;
	
	public UserServiceException(String message) {
		super(message);
	}
	
}

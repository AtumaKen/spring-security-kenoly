package com.kenoly.udemy.model.response;

public enum ErrorMessages {
	MISSING_REQUIRED_FIELD("Missing required field. Please check documentation for required fields"),
	RECORD_ALREADY_EXISTS("Record already exists"),
	INTERNAL_SERVER_ERROR("Internal server Error"),
	NO_RECORD_FOUND("Record with provided Id not found"),
	AUTHENTICATION_FAILED("Authentication Faliled"),
	COULD_NOT_UPDATE_RECORD("Could not update record"),
	COULD_NOT_DELETE_RECORD("Could not delete record"),
	EMAIL_ADDREDD_NOT_VERIFIED("Email address not verified");
	
	private String errorMessage;
	
	 ErrorMessages(String errorMesage) {
		this.setErrorMessage(errorMesage);
	}
	 
	 /** 
	  * @return the errorMessage
	  */
	public String getErrorMessage() {
		return errorMessage;
	}
	
	 /** 
	  * @param errorMessage the errorMessage to be set
	  */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	 
	
	
}

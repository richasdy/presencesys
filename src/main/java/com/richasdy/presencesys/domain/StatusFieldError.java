package com.richasdy.presencesys.domain;

public class StatusFieldError extends Status {
	
	private String field;
	private String message;
	
	public StatusFieldError(String status, String field, String message) {
		super(status);
		this.field = field;
		this.message = message;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


}

package com.bigbang.api.models;

import com.bigbang.api.models.ErrorMessage;;

public class ErrorResponse {

	ErrorMessage errors ;
	String trace_id;
	public ErrorMessage getErrors() {
		return errors;
	}
	public void setErrors(ErrorMessage errors) {
		this.errors = errors;
	}
	public String getTrace_id() {
		return trace_id;
	}
	public void setTrace_id(String trace_id) {
		this.trace_id = trace_id;
	}
	
}


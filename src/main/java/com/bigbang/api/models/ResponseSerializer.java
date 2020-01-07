package com.bigbang.api.models;

public class ResponseSerializer<T> {
	private boolean success;
	private T data;
	private String text;
	private ErrorMessage errorMessage;
	private long runtime;
	
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public ErrorMessage getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(ErrorMessage errorMessage) {
		this.errorMessage = errorMessage;
	}
	public long getRuntime() {
		return runtime;
	}
	public void setRuntime(long runtime) {
		this.runtime = runtime;
	}
	@Override
	public String toString() {
		return "ResponseSerializer [success=" + success + ", data=" + data + ", text=" + text + ", errorMessage="
				+ errorMessage + ", runtime=" + runtime + "]";
	}
	
	
}

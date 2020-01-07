package com.bigbang.api.models;

public class ForgotPassword {
	private String newPassword;
	private String confirmPassword;
	private String email;
	private String forgotPasswordId;
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getForgotPasswordId() {
		return forgotPasswordId;
	}
	public void setForgotPasswordId(String forgotPasswordId) {
		this.forgotPasswordId = forgotPasswordId;
	}
}

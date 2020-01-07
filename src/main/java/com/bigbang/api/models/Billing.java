package com.bigbang.api.models;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class Billing {
	
	private String uuid;
	private String paymentUserId;
	private String cardNumber;
	private String year;
	private String cvv;
	private Date createdAt;
	private String userId;
	private String projectId;
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getPaymentUserId() {
		return paymentUserId;
	}
	public void setPaymentUserId(String paymentUserId) {
		this.paymentUserId = paymentUserId;
	}
	public String getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getCvv() {
		return cvv;
	}
	public void setCvv(String cvv) {
		this.cvv = cvv;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	@JsonSerialize(using = CustomDateSerializer.class)
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	@Override
	public String toString() {
		return "Billing [uuid=" + uuid + ", paymentUserId=" + paymentUserId + ", cardNumber=" + cardNumber + ", year="
				+ year + ", cvv=" + cvv + ", createdAt=" + createdAt + ", userId=" + userId + ", projectId=" + projectId
				+ "]";
	}
	
}

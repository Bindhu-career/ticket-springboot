package com.bigbang.api.models;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class Transaction {
	private String uuid;
	private String projectId;
	private double amount;
	private Long date;
	private String status;
	private String email;
	private String currency;
	private String description;
	private String receiptEmail;
	private String receiptNumber;
	private String receiptURL;
	private String type;
	private String brand;
	private String customerId;
	private String cardId;
	private String balanceTransaction;
	private String customerPhoneNumber;
	private String customerCountry;
	private String cardType;
	private String cardHolderName;
	private String cardLast4Digits;
	private String cardExpMonth;
	private String cardExpYear;
	private Date createdAt;
	private String userId;

	@JsonSerialize(using = CustomDateSerializer.class)
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	

	public String getUuid() {
		return uuid;
	}



	public void setUuid(String uuid) {
		this.uuid = uuid;
	}



	public String getBalanceTransaction() {
		return balanceTransaction;
	}



	public String getCurrency() {
		return currency;
	}



	public void setCurrency(String currency) {
		this.currency = currency;
	}



	public void setBalanceTransaction(String balanceTransaction) {
		this.balanceTransaction = balanceTransaction;
	}



	public String getProjectId() {
		return projectId;
	}



	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}



	public double getAmount() {
		return amount;
	}



	public void setAmount(double amount) {
		this.amount = amount;
	}



	public Long getDate() {
		return date;
	}



	public void setDate(Long date) {
		this.date = date;
	}



	public String getStatus() {
		return status;
	}



	public void setStatus(String status) {
		this.status = status;
	}



	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}



	public String getDescription() {
		return description;
	}



	public void setDescription(String description) {
		this.description = description;
	}



	public String getReceiptEmail() {
		return receiptEmail;
	}



	public void setReceiptEmail(String receiptEmail) {
		this.receiptEmail = receiptEmail;
	}



	public String getReceiptNumber() {
		return receiptNumber;
	}



	public void setReceiptNumber(String receiptNumber) {
		this.receiptNumber = receiptNumber;
	}



	public String getReceiptURL() {
		return receiptURL;
	}



	public void setReceiptURL(String receiptURL) {
		this.receiptURL = receiptURL;
	}



	public String getType() {
		return type;
	}



	public void setType(String type) {
		this.type = type;
	}



	public String getBrand() {
		return brand;
	}



	public void setBrand(String brand) {
		this.brand = brand;
	}



	public String getCustomerId() {
		return customerId;
	}



	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}



	public String getCardId() {
		return cardId;
	}



	public void setCardId(String cardId) {
		this.cardId = cardId;
	}



	public String getCustomerPhoneNumber() {
		return customerPhoneNumber;
	}



	public void setCustomerPhoneNumber(String customerPhoneNumber) {
		this.customerPhoneNumber = customerPhoneNumber;
	}



	public String getCustomerCountry() {
		return customerCountry;
	}



	public void setCustomerCountry(String customerCountry) {
		this.customerCountry = customerCountry;
	}



	public String getCardType() {
		return cardType;
	}



	public void setCardType(String cardType) {
		this.cardType = cardType;
	}



	public String getCardHolderName() {
		return cardHolderName;
	}



	public void setCardHolderName(String cardHolderName) {
		this.cardHolderName = cardHolderName;
	}



	public String getCardLast4Digits() {
		return cardLast4Digits;
	}



	public void setCardLast4Digits(String cardLast4Digits) {
		this.cardLast4Digits = cardLast4Digits;
	}



	public String getCardExpMonth() {
		return cardExpMonth;
	}



	public void setCardExpMonth(String cardExpMonth) {
		this.cardExpMonth = cardExpMonth;
	}



	public String getCardExpYear() {
		return cardExpYear;
	}



	public void setCardExpYear(String cardExpYear) {
		this.cardExpYear = cardExpYear;
	}



	public String getUserId() {
		return userId;
	}



	public void setUserId(String userId) {
		this.userId = userId;
	}



	public Date getCreatedAt() {
		return createdAt;
	}



	@Override
	public String toString() {
		return "Transaction [uuid=" + uuid + ", projectId=" + projectId + ", amount=" + amount + ", date=" + date
				+ ", status=" + status + ", email=" + email + ", description=" + description + ", receiptEmail="
				+ receiptEmail + ", receiptNumber=" + receiptNumber + ", receiptURL=" + receiptURL + ", type=" + type
				+ ", brand=" + brand + ", customerId=" + customerId + ", createdAt=" + createdAt + ", userId=" + userId
				+ "]";
	}

}

package com.bigbang.api.models;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class Milestone {
	private String uuid;

	private Status status;

	public enum Status {
		completed, pending, inprogress
	};

	private long payment;
	private String title;
	private String description;
	private String startDate;
	private String deliveryDate;
	private String deliverables;

	private Date createdAt;
	private String projectId;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public long getPayment() {
		return payment;
	}

	public void setPayment(long payment) {
		this.payment = payment;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(String deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public String getDeliverables() {
		return deliverables;
	}

	public void setDeliverables(String deliverables) {
		this.deliverables = deliverables;
	}

	public Date getCreatedAt() {
		return createdAt;
	}
	
	@JsonSerialize(using = CustomDateSerializer.class)
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
	
}

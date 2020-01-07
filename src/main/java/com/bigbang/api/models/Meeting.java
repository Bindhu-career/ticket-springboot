package com.bigbang.api.models;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class Meeting {
	private String uuid;
	private MeetingStatus status;

	public enum MeetingStatus {
		completed, inprogress, upcoming
	};

	private List<Object> connectors;
	private Date createdAt;
	private String userId;
	private String projectId;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public List<Object> getConnectors() {
		return connectors;
	}

	public void setConnectors(List<Object> connectors) {
		this.connectors = connectors;
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

	public MeetingStatus getStatus() {
		return status;
	}

	public void setStatus(MeetingStatus status) {
		this.status = status;
	}

}

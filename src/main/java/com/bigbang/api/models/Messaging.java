package com.bigbang.api.models;

public class Messaging {
	private String uuid;
	private String userId;
	private String fcmId;
	private String userType;
	private String projectId;
	private String createdAt;
	private String updatedAt;
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getFcmId() {
		return fcmId;
	}
	public void setFcmId(String fcmId) {
		this.fcmId = fcmId;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public String getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
	
	public String getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}
	@Override
	public String toString() {
		return "Messaging [uuid=" + uuid + ", userId=" + userId + ", fcmId=" + fcmId + ", userType=" + userType
				+ ", projectId=" + projectId + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + "]";
	}
	
	
}

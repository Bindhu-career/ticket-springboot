package com.bigbang.api.models;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class Preference {
	private String uuid;
	private String profilePicture;
	private String timeZone;
	private String location;
	private boolean availability;
	private Date createdAt;
	private String notificationId;
	private String userId;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getProfilePicture() {
		return profilePicture;
	}

	public void setProfilePicture(String profilePicture) {
		this.profilePicture = profilePicture;
	}

	public String getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public boolean isAvailability() {
		return availability;
	}

	public void setAvailability(boolean availability) {
		this.availability = availability;
	}

	public Date getCreatedAt() {
		return createdAt;
	}
	
	@JsonSerialize(using = CustomDateSerializer.class)
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public String getNotificationId() {
		return notificationId;
	}

	public void setNotificationId(String notificationId) {
		this.notificationId = notificationId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}

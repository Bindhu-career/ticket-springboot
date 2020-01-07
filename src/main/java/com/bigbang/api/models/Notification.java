package com.bigbang.api.models;

import java.util.ArrayList;
import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class Notification {
	public static enum NotificationType {
		mobile, email
	};
	private String uuid;
	private ArrayList<NotificationType> inboxMessages=new ArrayList<NotificationType>();
	private ArrayList<NotificationType> projectRelatedMessages=new ArrayList<NotificationType>();
	private ArrayList<NotificationType> projectRelatedUpdates=new ArrayList<NotificationType>();
	private ArrayList<NotificationType> relatedToMyAccount=new ArrayList<NotificationType>();
	private Date createdAt;
	private String preferenceId;
	private String userId;
	private boolean sound;
	public boolean isSound() {
		return sound;
	}
	public void setSound(boolean sound) {
		this.sound = sound;
	}
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public ArrayList<NotificationType> getInboxMessages() {
		return inboxMessages;
	}
	public void setInboxMessages(ArrayList<NotificationType> inboxMessages) {
		this.inboxMessages = inboxMessages;
	}
	
	public ArrayList<NotificationType> getProjectRelatedMessages() {
		return projectRelatedMessages;
	}
	public void setProjectRelatedMessages(ArrayList<NotificationType> projectRelatedMessages) {
		this.projectRelatedMessages = projectRelatedMessages;
	}
	public ArrayList<NotificationType> getProjectRelatedUpdates() {
		return projectRelatedUpdates;
	}
	public void setProjectRelatedUpdates(ArrayList<NotificationType> projectRelatedUpdates) {
		this.projectRelatedUpdates = projectRelatedUpdates;
	}
	public ArrayList<NotificationType> getRelatedToMyAccount() {
		return relatedToMyAccount;
	}
	public void setRelatedToMyAccount(ArrayList<NotificationType> relatedToMyAccount) {
		this.relatedToMyAccount = relatedToMyAccount;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public String getPreferenceId() {
		return preferenceId;
	}
	public void setPreferenceId(String preferenceId) {
		this.preferenceId = preferenceId;
	}
	@Override
	public String toString() {
		return "Notification [uuid=" + uuid + ", inboxMessages=" + inboxMessages + ", projectRelatedMessages="
				+ projectRelatedMessages + ", projectRelatedUpdates=" + projectRelatedUpdates + ", relatedToMyAccount="
				+ relatedToMyAccount + ", createdAt=" + createdAt + ", preferenceId=" + preferenceId + ", userId="
				+ userId + ", sound=" + sound + "]";
	}
	
	

}

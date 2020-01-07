package com.bigbang.api.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.bigbang.api.models.Notification;

public interface NotificationService {
	
	public Notification saveNotification(Notification notificationData) throws Exception;
	
	public List<Notification> listAllNotifications() throws Exception;
	
	public Notification getNotificationByUuid(String uuid) throws Exception;
	
	public boolean deleteNotificationById(String uuid) throws Exception;
	
	public List<Notification> getNotificationByUserId(String userId) throws Exception;

	Notification updateNotificationData(Notification notificationObj) throws Exception;
	
	public ResponseEntity<?> save(Notification notificationData) throws Exception;
	
	public ResponseEntity<?> getNotificationById(String uuid) throws Exception;
	
	public ResponseEntity<?> deleteNotification(String uuid) throws Exception;
	
	public ResponseEntity<?> updateNotification(Notification notificationlist, String uuid) throws Exception;
	
	public ResponseEntity<?> getNotificationByUser(String userId) throws Exception;
}


package com.bigbang.api.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.bigbang.api.models.Messaging;

public interface MessagingService {

	public Messaging updateMessagingData(Messaging message) throws Exception;
	
//	public List<Messaging> listAllCompletedMessaging(String projectId) throws Exception;
//	
//	public List<Messaging> listAllUpcomingMessaging(String projectId) throws Exception;
//
//	public List<Messaging> listAllMessagingByUseridProjectid(String userid, String projectid) throws Exception;

	Messaging saveMessaging(Messaging meetingObj) throws Exception;

	List<Messaging> listAllMessaging() throws Exception;
	
	public Messaging getMessagingById(String id) throws Exception;
	
	public List<Messaging> getMessagingByUserId(String userId) throws Exception;
	
	public boolean deleteMessagingById(String id) throws Exception;
	
	public ResponseEntity<?> save(Messaging messagingData) throws Exception;
	
	public ResponseEntity<?> getMessagingByUuid(String uuid) throws Exception;
	
	public ResponseEntity<?> getMessagingByUser(String userId) throws Exception;
	
	public ResponseEntity<?> updateMessaging(Messaging messagingList) throws Exception;
	
}

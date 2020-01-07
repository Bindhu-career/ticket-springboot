package com.bigbang.api.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.bigbang.api.models.Meeting;


public interface MeetingService {
	
	public Meeting updateMeetingData(Meeting meetinglist) throws Exception;
	
	public List<Meeting> listAllCompletedMeeting(String projectId) throws Exception;
	
	public List<Meeting> listAllUpcomingMeeting(String projectId) throws Exception;

	public List<Meeting> listAllMeetingByUseridProjectid(String userid, String projectid) throws Exception;

	Meeting saveMeeting(Meeting meetingObj) throws Exception;

	List<Meeting> listAllMeeting() throws Exception;
	
	public Meeting getMeetingByUuid(String id) throws Exception;
	
	public boolean deleteMeetingById(String id) throws Exception;
	
	public ResponseEntity<?> save(Meeting meetingData) throws Exception;
	
	public ResponseEntity<?> getMeetingById(String uuid) throws Exception;
	
	public ResponseEntity<?> getMeetingByUseridProjectid(String userid, String projectid) throws Exception;
	
	public ResponseEntity<?> deleteMeeting(String uuid) throws Exception;
	
	public ResponseEntity<?> updateMeeting(Meeting meetinglist) throws Exception;
	
	public ResponseEntity<?> getAllCompletedMeeting(String projectId) throws Exception;
	
	public ResponseEntity<?> getAllUpcomingMeeting(String projectId) throws Exception;
	
}


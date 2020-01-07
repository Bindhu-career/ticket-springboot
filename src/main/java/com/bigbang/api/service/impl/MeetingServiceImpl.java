package com.bigbang.api.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.bigbang.api.models.ErrorMessage;
import com.bigbang.api.models.ErrorResponse;
import com.bigbang.api.models.Meeting;
import com.bigbang.api.models.ResponseSerializer;
import com.bigbang.api.repository.MeetingRepository;
import com.bigbang.api.service.MeetingService;
import com.bigbang.api.util.Constants;
import com.google.api.server.spi.Constant;

@Service
@SuppressWarnings("unchecked")
public class MeetingServiceImpl implements MeetingService {

	@Autowired
	private MeetingRepository meetingRepository;

	private String MEETING_COLLECTION = "Meeting";

	// Save meeting
	@Override
	public ResponseEntity<?> save(Meeting meetingData) throws Exception {
		HttpStatus status = null;
		Meeting meeting = null;
		ResponseSerializer<Meeting> responseSerializer = new ResponseSerializer<Meeting>();
		if (meetingData.getStatus() == null || meetingData.getProjectId() == null || meetingData.getUserId() == null) {
			return sendResponse(Constants.MEETING_REQUIRED_FEILD, "No Content", responseSerializer,
					HttpStatus.BAD_REQUEST, 400);
		} else {
			meetingData.setUuid(UUID.randomUUID().toString());
			meeting = saveMeeting(meetingData);
			responseSerializer.setSuccess(true);
			responseSerializer.setData(meeting);
			responseSerializer.setErrorMessage(null);
			status = HttpStatus.CREATED;
		}
		return new ResponseEntity<ResponseSerializer<Meeting>>(responseSerializer, status);
	}

	// Get meeting by uuid
	@Override
	public ResponseEntity<?> getMeetingById(String uuid) throws Exception {
		ResponseSerializer<Meeting> responseSerializer = new ResponseSerializer<Meeting>();
		responseSerializer.setSuccess(false);
		HttpStatus status = null;
		Meeting res = getMeetingByUuid(uuid);
		if (res != null) {
			responseSerializer.setSuccess(true);
			responseSerializer.setData(res);
			responseSerializer.setErrorMessage(null);
			status = HttpStatus.OK;
		} else {
			return sendResponse(Constants.UUID_NOT_EXIST, "Validation", responseSerializer, HttpStatus.NOT_FOUND,
					404);
		}
		return new ResponseEntity<ResponseSerializer<Meeting>>(responseSerializer, status);
	}

	// Get meeting by user id and project id
	@Override
	public ResponseEntity<?> getMeetingByUseridProjectid(String userid, String projectid) throws Exception {
		ResponseSerializer<List<Meeting>> responseSerializer = new ResponseSerializer<>();
		responseSerializer.setSuccess(false);
		HttpStatus status = null;
		HttpHeaders responseHeaders = new HttpHeaders();
		List<Meeting> res = listAllMeetingByUseridProjectid(userid, projectid);
		if (res.isEmpty()) {
			return sendResponseList(Constants.UUID_NOT_EXIST, "Validation", responseSerializer,
					HttpStatus.NOT_FOUND, 404);

		} else {
			responseSerializer.setSuccess(true);
			responseSerializer.setData(res);
			responseSerializer.setErrorMessage(null);
			status = HttpStatus.OK;
		}
		return new ResponseEntity<ResponseSerializer<List<Meeting>>>(responseSerializer, responseHeaders, status);
	}

	// Delete meeting
	@Override
	public ResponseEntity<?> deleteMeeting(String uuid) throws Exception {
		ResponseSerializer<String> responseSerializer = new ResponseSerializer<String>();
		responseSerializer.setSuccess(false);
		HttpStatus status = null;
		Meeting res = getMeetingByUuid(uuid);
		if (res != null) {
			deleteMeetingById(uuid);
			responseSerializer.setSuccess(true);
			status = HttpStatus.NO_CONTENT;
		} else {
			return sendResponseString(Constants.UUID_NOT_EXIST, "Validation", responseSerializer,
					HttpStatus.NOT_FOUND, 404);
		}
		return new ResponseEntity<Object>(responseSerializer, status);
	}

	// Update Meeting
	@Override
	public ResponseEntity<?> updateMeeting(Meeting meetinglist) throws Exception {
		HttpStatus status = null;
		Meeting meeting = null;
		ResponseSerializer<Meeting> responseSerializer = new ResponseSerializer<Meeting>();
		responseSerializer.setSuccess(false);
		Meeting resFind = getMeetingByUuid(meetinglist.getUuid());
		if (resFind != null) {
			meeting = updateMeetingData(meetinglist);
			responseSerializer.setSuccess(true);
			responseSerializer.setData(meeting);
			responseSerializer.setErrorMessage(null);
			status = HttpStatus.OK;
		} else {
			return sendResponse(Constants.UUID_NOT_EXIST, "Validation", responseSerializer, HttpStatus.NOT_FOUND,
					404);
		}
		return new ResponseEntity<ResponseSerializer<Meeting>>(responseSerializer, status);
	}

	// Get all completed meeting
	@Override
	public ResponseEntity<?> getAllCompletedMeeting(String projectId) throws Exception {
		ResponseSerializer<List<Meeting>> responseSerializer = new ResponseSerializer<List<Meeting>>();
		List<Meeting> meetings = null;
		HttpStatus status = null;
		responseSerializer.setSuccess(false);

			meetings = (List<Meeting>) listAllCompletedMeeting(projectId);
			if (!meetings.isEmpty()) {
				responseSerializer.setSuccess(true);
				responseSerializer.setData(meetings);
				responseSerializer.setErrorMessage(null);
				status = HttpStatus.OK;
			} else {
				responseSerializer.setSuccess(true);
				responseSerializer.setData(null);
				responseSerializer.setErrorMessage(null);
				status = HttpStatus.OK;
			}
		return new ResponseEntity<ResponseSerializer<List<Meeting>>>(responseSerializer, status);
	}

	// Get all completed meeting
	@Override
	public ResponseEntity<?> getAllUpcomingMeeting(String projectId) throws Exception {
		List<Meeting> meetings = null;
		ResponseSerializer<List<Meeting>> responseSerializer = new ResponseSerializer<List<Meeting>>();
		HttpStatus status = null;
			meetings = (List<Meeting>) listAllUpcomingMeeting(projectId);
			if (!meetings.isEmpty()) {
				responseSerializer.setSuccess(true);
				responseSerializer.setData(meetings);
				responseSerializer.setErrorMessage(null);
				status = HttpStatus.OK;
			} else {
				responseSerializer.setSuccess(true);
				responseSerializer.setData(null);
				responseSerializer.setErrorMessage(null);
				status = HttpStatus.OK;
			}
		return new ResponseEntity<ResponseSerializer<List<Meeting>>>(responseSerializer, status);
	}

	private ResponseEntity<?> sendResponse(String errormessage, String type,
			ResponseSerializer<Meeting> responseSerializer, HttpStatus status, int errorCode) {
		ErrorMessage error = new ErrorMessage();
		error.setCode(errorCode);
		error.setMessage(errormessage);
		error.setType(type);
		responseSerializer.setSuccess(false);
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setErrors(error);
		errorResponse.setTrace_id(UUID.randomUUID().toString());
		return new ResponseEntity<Object>(errorResponse, status);
	}

	private ResponseEntity<?> sendResponseList(String errormessage, String type,
			ResponseSerializer<List<Meeting>> responseSerializer, HttpStatus status, int errorCode) {
		ErrorMessage error = new ErrorMessage();
		error.setCode(errorCode);
		error.setMessage(errormessage);
		error.setType(type);
		responseSerializer.setSuccess(false);
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setErrors(error);
		errorResponse.setTrace_id(UUID.randomUUID().toString());
		return new ResponseEntity<Object>(errorResponse, status);
	}

	private ResponseEntity<?> sendResponseString(String errormessage, String type,
			ResponseSerializer<String> responseSerializer, HttpStatus status, int errorCode) {
		ErrorMessage error = new ErrorMessage();
		error.setCode(errorCode);
		error.setMessage(errormessage);
		error.setType(type);
		responseSerializer.setSuccess(false);
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setErrors(error);
		errorResponse.setTrace_id(UUID.randomUUID().toString());
		return new ResponseEntity<Object>(errorResponse, status);
	}

	@Override
	public Meeting saveMeeting(Meeting meetingObj) throws Exception {
		Meeting meeting = null;
		meeting = (Meeting) meetingRepository.save(meetingObj, meetingObj.getUuid(), MEETING_COLLECTION);
		return meeting;
	}

	@Override
	public List<Meeting> listAllMeeting() throws Exception {
		List<Meeting> meeting = null;
		meeting = (List<Meeting>) meetingRepository.findAll(MEETING_COLLECTION);
		return meeting;
	}

	@Override
	public Meeting getMeetingByUuid(String id) throws Exception {
		Meeting meeting = (Meeting) meetingRepository.findOne(id, MEETING_COLLECTION);
		return meeting;
	}

	@Override
	public boolean deleteMeetingById(String id) throws Exception {
		boolean isDeleted = meetingRepository.delete(id, MEETING_COLLECTION);
		if (isDeleted) {
			return true;
		}
		return false;
	}

	@Override
	public Meeting updateMeetingData(Meeting meetingObj) throws Exception {
		Meeting meeting = null;
		meeting = (Meeting) meetingRepository.save(meetingObj, meetingObj.getUuid(), MEETING_COLLECTION);
		return meeting;
	}

	@Override
	public List<Meeting> listAllCompletedMeeting(String projectId) throws Exception {
		List<Meeting> meeting = null;
		meeting = (List<Meeting>) meetingRepository.findByMultipleCondition("projectId", projectId, "status",
				"completed", MEETING_COLLECTION);
		return meeting;
	}

	@Override
	public List<Meeting> listAllUpcomingMeeting(String projectId) throws Exception {
		List<Meeting> meeting = null;
		meeting = (List<Meeting>) meetingRepository.findByMultipleCondition("projectId", projectId, "status",
				"upcoming", MEETING_COLLECTION);
		return meeting;
	}

	@Override
	public List<Meeting> listAllMeetingByUseridProjectid(String userId, String projectId) throws Exception {
		List<Meeting> meeting = null;
		meeting = (List<Meeting>) meetingRepository.findByMultipleCondition("userId", userId, "projectId", projectId,
				MEETING_COLLECTION);
		return meeting;
	}
}

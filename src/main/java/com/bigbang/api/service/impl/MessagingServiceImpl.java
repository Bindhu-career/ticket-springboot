package com.bigbang.api.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.bigbang.api.models.ErrorMessage;
import com.bigbang.api.models.ErrorResponse;
import com.bigbang.api.models.Messaging;
import com.bigbang.api.models.ResponseSerializer;
import com.bigbang.api.repository.MessagingRepository;
import com.bigbang.api.service.MessagingService;
import com.bigbang.api.util.Constants;

@Service
@SuppressWarnings("unchecked")
public class MessagingServiceImpl implements MessagingService {

	private String MESSAGING_COLLECTION = "Messaging";

	@Autowired
	MessagingRepository messagingRepository;

	/*
	 * Save messaging
	 */
	@Override
	public ResponseEntity<?> save(Messaging messagingData) throws Exception {
		HttpStatus status = null;
		ResponseSerializer<Messaging> responseSerializer = new ResponseSerializer<Messaging>();
		responseSerializer.setSuccess(false);
		messagingData.setUuid(UUID.randomUUID().toString());
		Messaging messaging = saveMessaging(messagingData);
		if (messaging == null) {
			return sendResponse(Constants.SOMTHING_WRONG, "No Content", responseSerializer, HttpStatus.BAD_REQUEST, 400);
		} else {
			responseSerializer.setSuccess(true);
			responseSerializer.setData(messagingData);
			responseSerializer.setErrorMessage(null);
			status = HttpStatus.CREATED;
		}
		return new ResponseEntity<ResponseSerializer<Messaging>>(responseSerializer, status);
	}

	/*
	 * Get messaging by uuid
	 */
	@Override
	public ResponseEntity<?> getMessagingByUuid(String uuid) throws Exception {
		ResponseSerializer<Messaging> responseSerializer = new ResponseSerializer<Messaging>();
		responseSerializer.setSuccess(false);
		HttpStatus status = null;
		Messaging res = getMessagingById(uuid);
		if (res != null) {
			responseSerializer.setSuccess(true);
			responseSerializer.setData(res);
			responseSerializer.setErrorMessage(null);
			status = HttpStatus.OK;
		} else {
			ErrorMessage error = new ErrorMessage();
			return sendResponse(Constants.UUID_NOT_EXIST, "Validation", responseSerializer, HttpStatus.NOT_FOUND,
					404);
		}
		return new ResponseEntity<ResponseSerializer<Messaging>>(responseSerializer, status);
	}

	/*
	 * Get messaging by user id
	 */
	@Override
	public ResponseEntity<?> getMessagingByUser(String userId) throws Exception {
		ResponseSerializer<List<Messaging>> responseSerializer = new ResponseSerializer<List<Messaging>>();
		responseSerializer.setSuccess(false);
		HttpStatus status = null;
		List<Messaging> res = getMessagingByUserId(userId);
		if (res != null) {
			responseSerializer.setSuccess(true);
			responseSerializer.setData(res);
			responseSerializer.setErrorMessage(null);
			status = HttpStatus.OK;
		} else {
			return sendResponseList(Constants.USERID_NOT_EXIST, "Validation", responseSerializer, HttpStatus.NOT_FOUND,
					404);
		}
		return new ResponseEntity<ResponseSerializer<List<Messaging>>>(responseSerializer, status);
	}
	/*
	 * Update messaging
	 */
	@Override
	public ResponseEntity<?> updateMessaging(Messaging messagingList) throws Exception{
		HttpStatus status = null;
		ResponseSerializer<Messaging> responseSerializer = new ResponseSerializer<Messaging>();
		responseSerializer.setSuccess(false);
			Messaging messaging = updateMessagingData(messagingList);
			if (messaging != null) {
				responseSerializer.setSuccess(true);
				responseSerializer.setData(messaging);
				responseSerializer.setErrorMessage(null);
				status = HttpStatus.OK;
			} else {
				return sendResponse(Constants.UUID_NOT_EXIST, "Validation", responseSerializer, HttpStatus.NOT_FOUND,
						404);
			}
			return new ResponseEntity<ResponseSerializer<Messaging>>(responseSerializer, status);

		}

	private ResponseEntity<?> sendResponse(String errormessage, String type,
			ResponseSerializer<Messaging> responseSerializer, HttpStatus status, int errorCode) {
		ErrorMessage error = new ErrorMessage();
		error.setCode(errorCode);
		error.setMessage(errormessage);
		error.setType(type);
		responseSerializer.setSuccess(false);
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setErrors(error);
		errorResponse.setTrace_id(UUID.randomUUID().toString());
		return new ResponseEntity(errorResponse, status);
	}
	private ResponseEntity<?> sendResponseList(String errormessage, String type,
			ResponseSerializer<List<Messaging>> responseSerializer, HttpStatus status, int errorCode) {
		ErrorMessage error = new ErrorMessage();
		error.setCode(errorCode);
		error.setMessage(errormessage);
		error.setType(type);
		responseSerializer.setSuccess(false);
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setErrors(error);
		errorResponse.setTrace_id(UUID.randomUUID().toString());
		return new ResponseEntity(errorResponse, status);
	}

	@Override
	public Messaging updateMessagingData(Messaging messagingObj) throws Exception {
		Messaging messaging = null;
		messaging = (Messaging) messagingRepository.save(messagingObj, messagingObj.getUuid(), MESSAGING_COLLECTION);
		return messaging;
	}

	@Override
	public Messaging saveMessaging(Messaging messagingObj) throws Exception {
		Messaging messaging = null;
		messaging = (Messaging) messagingRepository.save(messagingObj, messagingObj.getUuid(), MESSAGING_COLLECTION);
		return messaging;
	}

	@Override
	public List<Messaging> listAllMessaging() throws Exception {
		List<Messaging> messaging = null;
		messaging = (List<Messaging>) messagingRepository.findAll(MESSAGING_COLLECTION);
		return messaging;
	}

	@Override
	public Messaging getMessagingById(String id) throws Exception {
		Messaging messaging = (Messaging) messagingRepository.findOne(id, MESSAGING_COLLECTION);
		return messaging;
	}

	@Override
	public boolean deleteMessagingById(String id) throws Exception {
		boolean isDeleted = messagingRepository.delete(id, MESSAGING_COLLECTION);
		if (isDeleted) {
			return true;
		}
		return false;
	}

	@Override
	public List<Messaging> getMessagingByUserId(String userId) throws Exception {
		List<Messaging> messaging = null;
		messaging = (List<Messaging>) messagingRepository.findBySingleCondition("userId", userId, MESSAGING_COLLECTION);
		return messaging;
	}

}

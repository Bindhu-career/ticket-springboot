package com.bigbang.api.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.bigbang.api.models.ErrorMessage;
import com.bigbang.api.models.ErrorResponse;
import com.bigbang.api.models.Notification;
import com.bigbang.api.models.ResponseSerializer;
import com.bigbang.api.repository.NotificationRepository;
import com.bigbang.api.service.NotificationService;
import com.bigbang.api.util.Constants;

@Service
@SuppressWarnings("unchecked")
public class NotificationServiceImpl implements NotificationService {
	@Autowired
	private NotificationRepository notificationRepository;

	private String NOTIFICATION_COLLECTION = "Notification";

	// Save notification
	@Override
	public ResponseEntity<?> save(Notification notificationData) throws Exception {
		HttpStatus status = null;
		ResponseSerializer<Notification> responseSerializer = new ResponseSerializer<Notification>();
		responseSerializer.setSuccess(false);
		ErrorMessage error = new ErrorMessage();
		if (notificationData.getUserId() == null) {
			return sendResponse(Constants.NOTIFICATION_REQUIRED_FEILD, "No Content", responseSerializer,
					status = HttpStatus.BAD_REQUEST, 400);
		} else {
			notificationData.setUuid(UUID.randomUUID().toString());
			saveNotification(notificationData);
			responseSerializer.setSuccess(true);
			responseSerializer.setData(notificationData);
			responseSerializer.setErrorMessage(null);
			status = HttpStatus.CREATED;
		}
		return new ResponseEntity<ResponseSerializer<Notification>>(responseSerializer, status);
	}

	// Get notification by uuid
	@Override
	public ResponseEntity<?> getNotificationById(String uuid) throws Exception {
		ResponseSerializer<Notification> responseSerializer = new ResponseSerializer<Notification>();
		responseSerializer.setSuccess(false);
		HttpStatus status = null;
		Notification res = getNotificationByUuid(uuid);
		if (res != null) {
			responseSerializer.setSuccess(true);
			responseSerializer.setData(res);
			responseSerializer.setErrorMessage(null);
			status = HttpStatus.OK;
		} else {
			return sendResponse(Constants.UUID_NOT_EXIST, "No Content", responseSerializer,
					status = HttpStatus.NOT_FOUND, 404);
		}
		return new ResponseEntity<ResponseSerializer<Notification>>(responseSerializer, status);
	}

	// Get notification by uuid
	@Override
	public ResponseEntity<?> deleteNotification(String uuid) throws Exception {
		ResponseSerializer<String> responseSerializer = new ResponseSerializer<String>();
		responseSerializer.setSuccess(false);
		HttpStatus status = null;
		Notification res = getNotificationByUuid(uuid);
		if (res != null) {
			deleteNotificationById(uuid);
			responseSerializer.setSuccess(true);
			status = HttpStatus.NO_CONTENT;
		} else {
			return sendResponseString(Constants.UUID_NOT_EXIST, "Validation", responseSerializer,
					status = HttpStatus.NOT_FOUND, 404);
		}
		return new ResponseEntity(responseSerializer, status);
	}

	// Update notification
	@Override
	public ResponseEntity<?> updateNotification(Notification notificationlist, String uuid) throws Exception {
		HttpStatus status = null;
		ResponseSerializer<Notification> responseSerializer = new ResponseSerializer<Notification>();
		responseSerializer.setSuccess(false);
		Notification resFind = getNotificationByUuid(uuid);
		if (resFind != null) {
			resFind.setInboxMessages(notificationlist.getInboxMessages());
			resFind.setProjectRelatedMessages(notificationlist.getProjectRelatedMessages());
			resFind.setProjectRelatedUpdates(notificationlist.getProjectRelatedUpdates());
			resFind.setRelatedToMyAccount(notificationlist.getRelatedToMyAccount());
			resFind.setSound(notificationlist.isSound());
			updateNotificationData(resFind);
			responseSerializer.setSuccess(true);
			responseSerializer.setData(resFind);
			responseSerializer.setErrorMessage(null);
			status = HttpStatus.OK;
		} else {
			return sendResponse(Constants.UUID_NOT_EXIST, "Validation", responseSerializer,
					status = HttpStatus.NOT_FOUND, 404);
		}
		return new ResponseEntity<ResponseSerializer<Notification>>(responseSerializer, status);
	}

	// Get notification by user id
	@Override
	public ResponseEntity<?> getNotificationByUser(String userId) throws Exception {
		ResponseSerializer<List<Notification>> responseSerializer = new ResponseSerializer<List<Notification>>();
		responseSerializer.setSuccess(false);
		HttpStatus status = null;
		List<Notification> notification = getNotificationByUserId(userId);
		if (notification.isEmpty()) {
			return sendResponseList(Constants.USERID_NOT_EXIST, "Validation", responseSerializer,
					status = HttpStatus.NOT_FOUND, 404);
		} else {
			responseSerializer.setData(notification);
			responseSerializer.setSuccess(true);
			responseSerializer.setErrorMessage(null);
			status = HttpStatus.OK;
		}
		return new  ResponseEntity(responseSerializer,status);
	}

	private ResponseEntity<?> sendResponse(String errormessage, String type,
			ResponseSerializer<Notification> responseSerializer, HttpStatus status, int errorCode) {
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
			ResponseSerializer<List<Notification>> responseSerializer, HttpStatus status, int errorCode) {
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
	public Notification saveNotification(Notification notificationObj) throws Exception {
		Notification notification = null;
		notification = (Notification) notificationRepository.save(notificationObj, notificationObj.getUuid(),
				NOTIFICATION_COLLECTION);
		return notification;
	}

	@Override
	public List<Notification> listAllNotifications() throws Exception {
		List<Notification> notification = null;
		notification = (List<Notification>) notificationRepository.findAll(NOTIFICATION_COLLECTION);
		return notification;
	}

	@Override
	public Notification getNotificationByUuid(String id) throws Exception {
		Notification notification = (Notification) notificationRepository.findOne(id, NOTIFICATION_COLLECTION);
		return notification;
	}

	@Override
	public boolean deleteNotificationById(String id) throws Exception {
		boolean isDeleted = notificationRepository.delete(id, NOTIFICATION_COLLECTION);
		if (isDeleted) {
			return true;
		}
		return false;
	}

	@Override
	public Notification updateNotificationData(Notification notificationObj) throws Exception {
		Notification notification = null;
		notification = (Notification) notificationRepository.save(notificationObj, notificationObj.getUuid(),
				NOTIFICATION_COLLECTION);
		return notification;
	}

	@Override
	public List<Notification> getNotificationByUserId(String userId) throws Exception {
		List<Notification> notification = null;
		notification = (List<Notification>) notificationRepository.findBySingleCondition("userId", userId,
				NOTIFICATION_COLLECTION);
		return notification;
	}

}

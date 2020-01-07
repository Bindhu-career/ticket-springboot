package com.bigbang.api.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bigbang.api.models.ErrorMessage;
import com.bigbang.api.models.ErrorResponse;
import com.bigbang.api.models.Notification;
import com.bigbang.api.models.ResponseSerializer;
import com.bigbang.api.service.NotificationService;

@RestController
@CrossOrigin
@RequestMapping("/v1")
public class NotificationController {
	@Autowired
	NotificationService notificationService;

	/*
	 * Save Notification
	 */
	@PostMapping(value = "/notification/save")
	public Object saveNotification(@RequestBody Notification notificationData) throws Exception {
		return notificationService.save(notificationData);
	}

	/*
	 * Get all notification
	 */
	@GetMapping(value = "/notification")
	public ResponseEntity<ResponseSerializer<List<Notification>>> listAllNotifications(
			@RequestHeader HttpHeaders headers) {
		List<Notification> notifications = null;
		ResponseSerializer<List<Notification>> responseSerializer = new ResponseSerializer<List<Notification>>();

		HttpStatus status = null;
		try {
			notifications = (List<Notification>) notificationService.listAllNotifications();
			responseSerializer.setSuccess(true);
			responseSerializer.setData(notifications);
			responseSerializer.setErrorMessage(null);
			status = HttpStatus.OK;

		} catch (Exception e) {

		}
		return new ResponseEntity<ResponseSerializer<List<Notification>>>(responseSerializer, status);
	}

	/*
	 * Get notification by uuid
	 */
	@GetMapping(value = "/notification/{uuid}")
	public Object getNotificationById(@PathVariable("uuid") String uuid) throws Exception {
		return notificationService.getNotificationById(uuid);
	}

	/*
	 * Delete Notification
	 */
	@DeleteMapping(path = "/notification/{uuid}")
	public Object deleteNotificationById(@PathVariable("uuid") String uuid) throws Exception {
		return notificationService.deleteNotification(uuid);
	}

	/*
	 * Update notification
	 */
	@PutMapping(path = "/notification/{uuid}")
	public Object updateNotificationRecord(@RequestBody Notification notificationlist,
			@PathVariable("uuid") String uuid) throws Exception {
		return notificationService.updateNotification(notificationlist, uuid);
	}

	@GetMapping(value = "notification/get/{userId}")
	public Object getNotificationByUserId(@PathVariable("userId") String userId) throws Exception {
		return notificationService.getNotificationByUser(userId);
	}
}

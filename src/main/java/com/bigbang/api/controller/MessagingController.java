package com.bigbang.api.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.bigbang.api.models.Messaging;
import com.bigbang.api.models.ResponseSerializer;
import com.bigbang.api.service.MessagingService;

@RestController
@CrossOrigin
@RequestMapping("/v1")
public class MessagingController {
	
	@Autowired
	MessagingService messagingService;
	
	/*
	 * Save Messaging
	 */
	@PostMapping(value = "/messaging")
	public Object saveMessaging(@RequestBody Messaging messagingData) throws Exception {
		return messagingService.save(messagingData);
	} 
	
	@GetMapping(value = "/messaging")
	public ResponseEntity<ResponseSerializer<List<Messaging>>> listAllMessaging(@RequestHeader HttpHeaders headers) {
		List<Messaging> meetings = null;
		ResponseSerializer<List<Messaging>> responseSerializer = new ResponseSerializer<List<Messaging>>();
		HttpStatus status = null;
		try {
			meetings = (List<Messaging>) messagingService.listAllMessaging();
			responseSerializer.setSuccess(true);
			responseSerializer.setData(meetings);
			responseSerializer.setErrorMessage(null);
			status = HttpStatus.OK;

		} catch (Exception e) {

		}
		return new ResponseEntity<ResponseSerializer<List<Messaging>>>(responseSerializer, status);
	}
	
	@GetMapping(value = "/messaging/{uuid}")
	public Object getMessagingById(@PathVariable("uuid") String uuid) throws Exception {
		return messagingService.getMessagingByUuid(uuid);
	}
	
	@GetMapping(value = "/messaging/user")
	public Object getMessagingByUserId(@RequestParam("userId") String userId) throws Exception {
		return messagingService.getMessagingByUser(userId);
	}
	
	
	@PutMapping(path = "/messaging")
	public Object updateMessagingRecord(@RequestBody Messaging messagingList) throws Exception {
		return messagingService.updateMessaging(messagingList);
	}

}

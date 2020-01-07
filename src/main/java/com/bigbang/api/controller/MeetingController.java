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
import com.bigbang.api.models.Meeting;
import com.bigbang.api.models.ResponseSerializer;
import com.bigbang.api.service.MeetingService;
import com.bigbang.api.service.UserService;

@RestController
@CrossOrigin
@RequestMapping("/v1")
public class MeetingController {
	@Autowired
	MeetingService meetingService;

	@Autowired
	UserService userService;

	/*
	 * Save meeting
	 */
	@PostMapping(value = "/meeting")
	public Object saveMeeting(@RequestBody Meeting meetingData) throws Exception {
		return meetingService.save(meetingData);
	}

	/*
	 * Get all meetings
	 */
	@GetMapping(value = "/meeting")
	public ResponseEntity<ResponseSerializer<List<Meeting>>> listAllMeetings(@RequestHeader HttpHeaders headers) {
		List<Meeting> meetings = null;
		ResponseSerializer<List<Meeting>> responseSerializer = new ResponseSerializer<List<Meeting>>();
		HttpStatus status = null;
		try {
			meetings = (List<Meeting>) meetingService.listAllMeeting();
			responseSerializer.setSuccess(true);
			responseSerializer.setData(meetings);
			responseSerializer.setErrorMessage(null);
			status = HttpStatus.OK;

		} catch (Exception e) {

		}
		return new ResponseEntity<ResponseSerializer<List<Meeting>>>(responseSerializer, status);
	}

	/*
	 * Get meeting by uuid
	 */
	@GetMapping(value = "/meeting/{uuid}")
	public Object getMeetingById(@PathVariable("uuid") String uuid) throws Exception {
		return meetingService.getMeetingById(uuid);
	}

	/*
	 * Get meeting by User id and project id
	 */
	@GetMapping("/meeting/{userid}/{projectid}")
	public Object getMeetingByUseridProjectid(@PathVariable("userid") String userid,
			@PathVariable("projectid") String projectid) throws Exception {
		return meetingService.getMeetingByUseridProjectid(userid, projectid);
	}

	/*
	 * Delete meeting
	 */
	@DeleteMapping(path = "/meeting/{uuid}")
	public Object deleteMeetingById(@PathVariable("uuid") String uuid) throws Exception {
		return meetingService.deleteMeeting(uuid);
	}

	/*
	 * Update meeting
	 */
	@PutMapping(path = "/meeting")
	public Object updateMeetingRecord(@RequestBody Meeting meetinglist) throws Exception {
		return meetingService.updateMeeting(meetinglist);
	}

	/*
	 * Get all completed meeting
	 */
	@GetMapping(value = "/meeting/completed/{projectId}")
	public ResponseEntity<ResponseSerializer<List<Meeting>>> listAllCompletedMeetings(
			@PathVariable("projectId") String projectId) throws Exception {
		return (ResponseEntity<ResponseSerializer<List<Meeting>>>) meetingService.getAllCompletedMeeting(projectId);
	}

	/*
	 * Get all upcoming meetings
	 */
	@GetMapping(value = "/meeting/upcoming/{projectId}")
	public ResponseEntity<ResponseSerializer<List<Meeting>>> listAllUpcomingMeetings(
			@PathVariable("projectId") String projectId, @RequestHeader HttpHeaders headers) throws Exception {
		return (ResponseEntity<ResponseSerializer<List<Meeting>>>) meetingService.getAllUpcomingMeeting(projectId);
	}
}

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

import com.bigbang.api.models.Milestone;
import com.bigbang.api.models.ResponseSerializer;
import com.bigbang.api.service.MilestoneService;

@RestController
@CrossOrigin
@RequestMapping("/v1")
public class MilestoneController {
	@Autowired
	MilestoneService milestoneService;

	/*
	 * Save milestone
	 */
	@PostMapping(value = "/milestone")
	public Object saveMilestone(@RequestBody Milestone milestoneData) throws Exception {
		return milestoneService.save(milestoneData);
	}

	/*
	 * Get all milestone
	 */
	@GetMapping(value = "/milestone")
	public ResponseEntity<ResponseSerializer<List<Milestone>>> listAllMilestones(@RequestHeader HttpHeaders headers) {
		List<Milestone> milestones = null;
		ResponseSerializer<List<Milestone>> responseSerializer = new ResponseSerializer<List<Milestone>>();
		HttpStatus status = null;
		try {
			milestones = (List<Milestone>) milestoneService.listAllMilestones();
			responseSerializer.setSuccess(true);
			responseSerializer.setData(milestones);
			responseSerializer.setErrorMessage(null);
			status = HttpStatus.OK;
		} catch (Exception e) {
			
		}
		return new ResponseEntity<ResponseSerializer<List<Milestone>>>(responseSerializer, status);
	}

	/*
	 * Get milestone by uuid
	 */
	@GetMapping(value = "/milestone/{uuid}")
	public Object getMilestoneById(@PathVariable("uuid") String uuid) throws Exception {
		return milestoneService.getMilestoneById(uuid);
	}

	/*
	 * Delete milestone
	 */
	@DeleteMapping(path = "/milestone/{uuid}")
	public Object deleteMilestoneById(@PathVariable("uuid") String uuid) throws Exception {
		return milestoneService.deleteMilestone(uuid);
	}

	/*
	 * Update milestone
	 */
	@PutMapping(path = "/milestone")
	public Object updateMilestoneRecord(@RequestBody Milestone milestonelist) throws Exception {
		return milestoneService.updateMilestone(milestonelist);
	}
	/*
	 * Get All Milestone by project id
	 */
	@GetMapping(value = "/milestone/project/{projectId}")
	public Object getAllMilestoneByUser(@PathVariable("projectId") String projectId) throws Exception {
		return milestoneService.getMilestoneByProject(projectId);
	}
}

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bigbang.api.models.ErrorMessage;
import com.bigbang.api.models.ErrorResponse;
import com.bigbang.api.models.Milestone;
import com.bigbang.api.models.Project;
import com.bigbang.api.models.ResponseSerializer;
import com.bigbang.api.service.MilestoneService;
import com.bigbang.api.service.ProjectService;
import com.bigbang.api.service.UserService;

@RestController
@CrossOrigin
@RequestMapping("/v1")
public class ProjectController {
	@Autowired
	ProjectService projectService;

	@Autowired
	UserService userService;
	
	@Autowired
	MilestoneService milestoneService;

	/*
	 * This method will create the user input data's into the Project table uuid and
	 * createdAt fields are auto generated
	 */
	@PostMapping(value = "/project")
	public Object saveProject(@RequestBody Project projectData) throws Exception {
		return projectService.saveProject(projectData);
	}

	/*
	 * This method will display the all records from the Project table
	 */
	@GetMapping(value = "/project")
	public ResponseEntity<ResponseSerializer<List<Project>>> getAllProject(@RequestHeader HttpHeaders headers) {
		List<Project> project = null;
		ResponseSerializer<List<Project>> responseSerializer = new ResponseSerializer<List<Project>>();

		HttpStatus status = null;
		try {
			project = (List<Project>) projectService.getAllProject();
			responseSerializer.setSuccess(true);
			responseSerializer.setData(project);
			responseSerializer.setErrorMessage(null);
			status = HttpStatus.OK;

		} catch (Exception e) {
			status = HttpStatus.BAD_REQUEST;
		}
		return new ResponseEntity<ResponseSerializer<List<Project>>>(responseSerializer, status);
	}

	/*
	 * This method will display the one record from the Project table where uuid is
	 * equal
	 */
	@GetMapping(value = "/project/{uuid}")
	public Object getProjectById(@PathVariable("uuid") String uuid, @RequestHeader HttpHeaders headers) throws Exception {
		return projectService.getProjectByUuid(uuid);
	}

	/*
	 * This method will delete one record from the Project table where uuid is equal
	 */
	@DeleteMapping(path = "/project/{uuid}")
	public Object deleteProjectById(@PathVariable("uuid") String uuid) throws Exception {
		return projectService.deleteProjectById(uuid);
	}

	/*
	 * This method will update the one record from the Project table where uuid is
	 * equal
	 */
	@PutMapping(path = "/project")
	public Object updateProjectRecord(@RequestBody Project projectlist) throws Exception {
		return projectService.updateProjectRecord(projectlist);
		
	}
	/*
	 * Get All projects for a single user
	 */
	@GetMapping(value = "project/user/{userId}")
	public Object getAllProjectByUser(@PathVariable("userId") String userId, 
			@RequestParam(value = "page", defaultValue = "1", required = false) String page,
			@RequestParam(value = "limit", defaultValue = "10", required = false) String limit) throws Exception {
		return projectService.listAllProjectByUserId(userId, page, limit);
		
	}
	/*
	 * Update Project Status is Completed where all Milestones are Completed
	 */
	@GetMapping(value = "/project/milestonestatus/{projectId}")
	public Object updateProjectByMilestoneStatus(@PathVariable("projectId") String projectId) throws Exception {
		return milestoneService.updateProjectByMilestone(projectId);
		
	}
	
	/*
	 * save user channel
	 */
	@GetMapping(path = "/project/channel/{channel}/{projectId}")
	public Object saveUserChannel(@PathVariable("channel") String channel,@PathVariable("projectId") String projectId) {
		return projectService.updateProjectRecord(channel, projectId);
	}

}

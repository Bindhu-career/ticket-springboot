package com.bigbang.api.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.bigbang.api.models.Project;

public interface ProjectService {

	public ResponseEntity<?> saveProject(Project projectData) throws Exception;

	public List<Project> getAllProject() throws Exception;

	public ResponseEntity<?> getProjectByUuid(String uuid) throws Exception;

	public ResponseEntity<?> deleteProjectById(String uuid) throws Exception;

	public ResponseEntity<?> updateProjectRecord(Project projectData) throws Exception;

	public ResponseEntity<?> listAllProjectByUserId(String userId, String page,String limit) throws Exception;

	public Project getProjectById(String uuid) throws Exception;

	Project _updateProjectRecord(Project projectObj) throws Exception;

	public ResponseEntity<?> updateProjectRecord(String channel, String projectId);

}

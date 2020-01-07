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
import com.bigbang.api.models.Project;
import com.bigbang.api.models.ResponseSerializer;
import com.bigbang.api.repository.ProjectRepository;
import com.bigbang.api.service.ProjectService;
import com.bigbang.api.util.Constants;

@Service
@SuppressWarnings("unchecked")
public class ProjectServiceimpl implements ProjectService {

	@Autowired
	private ProjectRepository projectRepository;

	private String PROJECT_COLLECTION = "Project";

	@Override
	public ResponseEntity<?> saveProject(Project projectData) throws Exception {
		HttpStatus status = null;
		ResponseSerializer<Project> responseSerializer = new ResponseSerializer<Project>();
		try {
			ErrorMessage error = new ErrorMessage();
			if (projectData.getProjectName() == null) {
				error.setCode(400);
				error.setMessage(Constants.PROJECT_REQUIRED_FEILD);
				error.setType("No Content");
				responseSerializer.setSuccess(false);
				responseSerializer.setData(null);
				responseSerializer.setErrorMessage(error);
				status = HttpStatus.BAD_REQUEST;
				return new ResponseEntity<ResponseSerializer<Project>>(responseSerializer, status);
			} else {
				projectData.setUuid(UUID.randomUUID().toString());
				Project project = null;
				project = (Project) projectRepository.save(projectData, projectData.getUuid(), PROJECT_COLLECTION);
				responseSerializer.setSuccess(true);
				responseSerializer.setData(project);
				responseSerializer.setErrorMessage(null);
				status = HttpStatus.CREATED;
				return new ResponseEntity<ResponseSerializer<Project>>(responseSerializer, status);
			}
		} catch (Exception e) {
			status = HttpStatus.BAD_REQUEST;
			return new ResponseEntity<Project>(status);
		}

	}

	@Override
	public List<Project> getAllProject() throws Exception {
		List<Project> project = null;
		project = (List<Project>) projectRepository.findAll(PROJECT_COLLECTION);
		return project;
	}

	@Override
	public ResponseEntity<?> getProjectByUuid(String id) throws Exception {
		ResponseSerializer<Project> responseSerializer = new ResponseSerializer<Project>();
		HttpStatus status = null;
		HttpHeaders responseHeaders = new HttpHeaders();
		try {
			Project project = (Project) projectRepository.findOne(id, PROJECT_COLLECTION);

			if (project != null) {
				responseSerializer.setSuccess(true);
				responseSerializer.setData(project);
				responseSerializer.setErrorMessage(null);
				status = HttpStatus.OK;
			} else {
				ErrorMessage error = new ErrorMessage();
				error.setCode(404);
				error.setMessage(Constants.UUID_NOT_EXIST);
				error.setType("validation");
				responseSerializer.setSuccess(false);
				ErrorResponse errorResponse = new ErrorResponse();
				errorResponse.setErrors(error);
				errorResponse.setTrace_id(UUID.randomUUID().toString());
				status = HttpStatus.NOT_FOUND;
				return new ResponseEntity<Object>(errorResponse, status);
			}
		} catch (Exception e) {
			ErrorMessage error = new ErrorMessage();
			error.setCode(400);
			error.setMessage(Constants.INVALID_FEILDS);
			error.setType("validation");
			ErrorResponse errorResponse = new ErrorResponse();
			errorResponse.setErrors(error);
			errorResponse.setTrace_id(UUID.randomUUID().toString());
			status = HttpStatus.BAD_REQUEST;
			return new ResponseEntity<Object>(errorResponse, status);
		}
		return new ResponseEntity<ResponseSerializer<Project>>(responseSerializer, responseHeaders, status);

	}

	public Project getProjectById(String uuid) throws Exception {
		Project project = (Project) projectRepository.findOne(uuid, PROJECT_COLLECTION);
		return project;
	}

	@Override
	public ResponseEntity<?> deleteProjectById(String uuid) throws Exception {

		ResponseSerializer<String> responseSerializer = new ResponseSerializer<String>();
		HttpStatus status = null;
		try {
			Project res = getProjectById(uuid);
			if (res != null) {
				boolean isDeleted = projectRepository.delete(uuid, PROJECT_COLLECTION);
				if (isDeleted) {
					responseSerializer.setSuccess(true);
					status = HttpStatus.NO_CONTENT;
					return new ResponseEntity<Object>(responseSerializer, status);
				}
				ErrorMessage error = new ErrorMessage();
				error.setCode(404);
				error.setMessage(Constants.UUID_NOT_EXIST);
				error.setType("validation");
				responseSerializer.setSuccess(false);
				ErrorResponse errorResponse = new ErrorResponse();
				errorResponse.setErrors(error);
				errorResponse.setTrace_id(UUID.randomUUID().toString());
				status = HttpStatus.NOT_FOUND;
				return new ResponseEntity<Object>(errorResponse, status);

			}
		} catch (Exception e) {
			ErrorMessage error = new ErrorMessage();
			error.setCode(400);
			error.setMessage(Constants.INVALID_FEILDS);
			error.setType("validation");
			ErrorResponse errorResponse = new ErrorResponse();
			errorResponse.setErrors(error);
			errorResponse.setTrace_id(UUID.randomUUID().toString());
			status = HttpStatus.BAD_REQUEST;
			return new ResponseEntity<Object>(errorResponse, status);
		}
		return null;

	}

	@Override
	public ResponseEntity<?> updateProjectRecord(Project projectObj) throws Exception {
		HttpStatus status = null;
		HttpHeaders responseHeaders = new HttpHeaders();
		ResponseSerializer<Project> responseSerializer = new ResponseSerializer<Project>();
		try {
			Project resFind = getProjectById(projectObj.getUuid());
			if (resFind != null) {
				Project project = null;
				project = (Project) projectRepository.save(projectObj, projectObj.getUuid(), PROJECT_COLLECTION);
				responseSerializer.setSuccess(true);
				responseSerializer.setData(project);
				responseSerializer.setErrorMessage(null);
				status = HttpStatus.OK;
			} else {
				ErrorMessage error = new ErrorMessage();
				error.setCode(404);
				error.setMessage(Constants.UUID_NOT_EXIST);
				error.setType("validation");
				responseSerializer.setSuccess(false);
				ErrorResponse errorResponse = new ErrorResponse();
				errorResponse.setErrors(error);
				errorResponse.setTrace_id(UUID.randomUUID().toString());
				status = HttpStatus.NOT_FOUND;
				return new ResponseEntity<Object>(errorResponse, status);
			}

		} catch (Exception e) {
			ErrorMessage error = new ErrorMessage();
			error.setCode(400);
			error.setMessage(Constants.UUID_NOT_EXIST);
			error.setType("Invalid Parameter");
			ErrorResponse errorResponse = new ErrorResponse();
			errorResponse.setErrors(error);
			errorResponse.setTrace_id(UUID.randomUUID().toString());
			status = HttpStatus.BAD_REQUEST;
			return new ResponseEntity<Object>(errorResponse, status);
		}
		return new ResponseEntity<ResponseSerializer<Project>>(responseSerializer, responseHeaders, status);

	}
	
	@Override
	public Project _updateProjectRecord(Project projectObj) throws Exception {
		Project project = null;
		project = (Project) projectRepository.save(projectObj, projectObj.getUuid(), PROJECT_COLLECTION);
		return project;
	}

	@Override
	public ResponseEntity<?> listAllProjectByUserId(String userId, String page, String limit) throws Exception {
		ResponseSerializer<List<Project>> responseSerializer = new ResponseSerializer<>();
		HttpHeaders responseHeaders = new HttpHeaders();
		try {
			List<Project> project = null;
			project = (List<Project>) projectRepository.listAllProjectByUserId("userId", userId, page, limit,
					PROJECT_COLLECTION);
			if (project.isEmpty()) {
				ErrorMessage error = new ErrorMessage();
				error.setCode(404);
				error.setMessage(Constants.USERID_NOT_EXIST);
				error.setType("validation");
				responseSerializer.setSuccess(false);
				ErrorResponse errorResponse = new ErrorResponse();
				errorResponse.setErrors(error);
				errorResponse.setTrace_id(UUID.randomUUID().toString());
				return new ResponseEntity<Object>(errorResponse, HttpStatus.NOT_FOUND);
			} else {
				responseSerializer.setSuccess(true);
				responseSerializer.setData(project);
				responseSerializer.setErrorMessage(null);
			}
		} catch (Exception e) {
			ErrorMessage error = new ErrorMessage();
			error.setCode(400);
			error.setMessage(Constants.INVALID_FEILDS);
			error.setType("validation");
			ErrorResponse errorResponse = new ErrorResponse();
			errorResponse.setErrors(error);
			errorResponse.setTrace_id(UUID.randomUUID().toString());
			return new ResponseEntity<Object>(errorResponse, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<ResponseSerializer<List<Project>>>(responseSerializer, responseHeaders,
				HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> updateProjectRecord(String channel, String projectId) {
		HttpStatus status = null;
		HttpHeaders responseHeaders = new HttpHeaders();
		ResponseSerializer<Project> responseSerializer = new ResponseSerializer<Project>();
		try {
			Project resFind = getProjectById(projectId);
			if (resFind != null) {
				resFind.setUserChannel(channel);
				
				_updateProjectRecord(resFind);
				responseSerializer.setSuccess(true);
				responseSerializer.setData(null);
				responseSerializer.setErrorMessage(null);
				status = HttpStatus.OK;
			} else {
				ErrorMessage error = new ErrorMessage();
				error.setCode(404);
				error.setMessage(Constants.PROJECT_NOT_EXIST);
				error.setType("validation");
				responseSerializer.setSuccess(false);
				ErrorResponse errorResponse = new ErrorResponse();
				errorResponse.setErrors(error);
				errorResponse.setTrace_id(UUID.randomUUID().toString());
				status = HttpStatus.NOT_FOUND;
				return new ResponseEntity<Object>(errorResponse, status);
			}

		} catch (Exception e) {
			ErrorMessage error = new ErrorMessage();
			error.setCode(400);
			error.setMessage(Constants.PROJECT_NOT_EXIST);
			error.setType("Invalid Parameter");
			ErrorResponse errorResponse = new ErrorResponse();
			errorResponse.setErrors(error);
			errorResponse.setTrace_id(UUID.randomUUID().toString());
			status = HttpStatus.BAD_REQUEST;
			return new ResponseEntity<Object>(errorResponse, status);
		}
		return new ResponseEntity<ResponseSerializer<Project>>(responseSerializer, responseHeaders, status);
	}

}

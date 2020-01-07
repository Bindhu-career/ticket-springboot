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
import com.bigbang.api.models.Milestone;
import com.bigbang.api.models.Project;
import com.bigbang.api.models.ResponseSerializer;
import com.bigbang.api.repository.MilestoneRepository;
import com.bigbang.api.service.MilestoneService;
import com.bigbang.api.util.Constants;

@Service
@SuppressWarnings("unchecked")
public class MilestoneServiceImpl implements MilestoneService {

	@Autowired
	private MilestoneRepository milestoneRepository;

	private String MILESTONE_COLLECTION = "Milestone";

	// Save meeting
	@Override
	public ResponseEntity<?> save(Milestone milestoneData) throws Exception {
		HttpStatus status = null;
		ResponseSerializer<Milestone> responseSerializer = new ResponseSerializer<Milestone>();
		responseSerializer.setSuccess(false);
		ErrorMessage error = new ErrorMessage();
		if (milestoneData.getTitle() == null) {
			return sendResponse(Constants.MILESTONE_REQUIRED_FEILD, "No Content", responseSerializer, HttpStatus.BAD_REQUEST, 400);
		} else {
			milestoneData.setUuid(UUID.randomUUID().toString());
			saveMilestone(milestoneData);
			responseSerializer.setSuccess(true);
			responseSerializer.setData(milestoneData);
			responseSerializer.setErrorMessage(null);
			status = HttpStatus.CREATED;
		}
		return new ResponseEntity<ResponseSerializer<Milestone>>(responseSerializer, status);
	}

	// Get milestone by uuid
	@Override
	public ResponseEntity<?> getMilestoneById(String uuid) throws Exception {
		ResponseSerializer<Milestone> responseSerializer = new ResponseSerializer<Milestone>();
		responseSerializer.setSuccess(false);
		HttpStatus status = null;
		Milestone res = getMilestoneByUuid(uuid);
		if (res != null) {
			responseSerializer.setSuccess(true);
			responseSerializer.setData(res);
			responseSerializer.setErrorMessage(null);
			status = HttpStatus.OK;
		} else {
			return sendResponse(Constants.UUID_NOT_EXIST, "Validation", responseSerializer, HttpStatus.NOT_FOUND,
					404);
		}
		return new ResponseEntity<ResponseSerializer<Milestone>>(responseSerializer, status);
	}

	// Delete Milestone
	@Override
	public ResponseEntity<?> deleteMilestone(String uuid) throws Exception {
		ResponseSerializer<String> responseSerializer = new ResponseSerializer<String>();
		responseSerializer.setSuccess(false);
		HttpStatus status = null;
		Milestone res = getMilestoneByUuid(uuid);
		if (res != null) {
			deleteMilestoneById(uuid);
			responseSerializer.setSuccess(true);
			status = HttpStatus.NO_CONTENT;
		} else {
			return sendResponseString(Constants.UUID_NOT_EXIST, "Validation", responseSerializer,
					HttpStatus.NOT_FOUND, 404);
		}
		return new ResponseEntity(responseSerializer, status);
	}
	
	// Delete Milestone
		@Override
		public ResponseEntity<?> updateMilestone(Milestone milestonelist) throws Exception {
			HttpStatus status = null;
			ResponseSerializer<Milestone> responseSerializer = new ResponseSerializer<Milestone>();
			responseSerializer.setSuccess(false);
				Milestone resFind = getMilestoneByUuid(milestonelist.getUuid());
				if (resFind != null) {
					updateMilestoneData(milestonelist);
					responseSerializer.setSuccess(true);
					responseSerializer.setData(milestonelist);
					responseSerializer.setErrorMessage(null);
					status = HttpStatus.OK;
				} else {
					return sendResponse(Constants.UUID_NOT_EXIST, "Validation", responseSerializer,
							HttpStatus.NOT_FOUND, 404);
				}
				return new ResponseEntity<ResponseSerializer<Milestone>>(responseSerializer, status);
			}
		
		// Get milestone by project id
		public ResponseEntity<?> getMilestoneByProject(String projectId) throws Exception {
			HttpStatus status = null;
			ResponseSerializer<List<Milestone>> responseSerializer = new ResponseSerializer<>();
			responseSerializer.setSuccess(false);
			HttpHeaders responseHeaders = new HttpHeaders();
				List<Milestone> res = listAllMilestonesByProject(projectId);
				if (res.isEmpty()) {
					return sendResponseList(Constants.PROJECT_NOT_EXIST, "Validation", responseSerializer, status, 404);
				} else {
					responseSerializer.setSuccess(true);
					responseSerializer.setData(res);
					responseSerializer.setErrorMessage(null);
					status = HttpStatus.OK;
				}
				return new ResponseEntity<ResponseSerializer<List<Milestone>>>(responseSerializer, responseHeaders,status);
			}
	private ResponseEntity<?> sendResponse(String errormessage, String type,
			ResponseSerializer<Milestone> responseSerializer, HttpStatus status, int errorCode) {
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
	
	private ResponseEntity<?> sendResponseList(String errormessage, String type,
			ResponseSerializer<List<Milestone>> responseSerializer, HttpStatus status, int errorCode) {
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
	public Milestone saveMilestone(Milestone milestoneData) throws Exception {
		Milestone milestone = null;
		milestone = (Milestone) milestoneRepository.save(milestoneData, milestoneData.getUuid(), MILESTONE_COLLECTION);
		return milestone;
	}

	@Override
	public List<Milestone> listAllMilestones() throws Exception {
		List<Milestone> milestone = null;
		milestone = (List<Milestone>) milestoneRepository.findAll(MILESTONE_COLLECTION);
		return milestone;
	}

	@Override
	public Milestone getMilestoneByUuid(String id) throws Exception {
		Milestone milestone = (Milestone) milestoneRepository.findOne(id, MILESTONE_COLLECTION);
		return milestone;
	}

	@Override
	public boolean deleteMilestoneById(String id) throws Exception {
		boolean isDeleted = milestoneRepository.delete(id, MILESTONE_COLLECTION);
		if (isDeleted) {
			return true;
		}
		return false;
	}

	@Override
	public Milestone updateMilestoneData(Milestone milestoneObj) throws Exception {
		Milestone milestone = null;
		milestone = (Milestone) milestoneRepository.save(milestoneObj, milestoneObj.getUuid(), MILESTONE_COLLECTION);
		return milestone;
	}

	@Override
	public List<Milestone> listAllMilestonesByProject(String projectId) throws Exception {
		List<Milestone> milestone = null;
		milestone = (List<Milestone>) milestoneRepository.findBySingleCondition("projectId", projectId,
				MILESTONE_COLLECTION);
		return milestone;
	}

	@Override
	public ResponseEntity<?> updateProjectByMilestone(String projectId) throws Exception {
		ResponseSerializer<List<Milestone>> responseSerializer = new ResponseSerializer<>();
		HttpHeaders responseHeaders = new HttpHeaders();
		String res = null;
		try {
			List<Milestone> milestones = null;
			milestones = (List<Milestone>) milestoneRepository.findBySingleCondition("projectId", projectId, MILESTONE_COLLECTION);
			int noOfMile = 0;
			for (Milestone milestone : milestones) {
				if (!milestone.getStatus().equals("completed")) {
					break;
				} else {
					noOfMile++;
				}
			}
			if (noOfMile == milestones.size()) {
				try {
					ProjectServiceimpl projectServiceimpl = new ProjectServiceimpl();
					Project resp = projectServiceimpl.getProjectById(projectId);
					resp.setProjectStatus(Project.ProjectStatus.completed);
					projectServiceimpl._updateProjectRecord(resp);
				} catch (Exception e) {
					e.printStackTrace();
				}
				res =  "Updated project to completed";

			} else {
				res =  "All milestones not yet completed";
			}
			
			if (res.equals(null)) {
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
				responseSerializer.setText(res);
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
		return new ResponseEntity<ResponseSerializer<List<Milestone>>>(responseSerializer, responseHeaders,
				HttpStatus.OK);
	}

}

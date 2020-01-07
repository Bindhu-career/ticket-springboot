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
import com.bigbang.api.models.Preference;
import com.bigbang.api.models.ResponseSerializer;
import com.bigbang.api.repository.PreferenceRepository;
import com.bigbang.api.service.PreferenceService;
import com.bigbang.api.util.Constants;

@Service
@SuppressWarnings("unchecked")
public class PreferenceServiceImpl implements PreferenceService {

	@Autowired
	private PreferenceRepository preferenceRepository;

	private String PREFERENCE_COLLECTION = "Preference";

	// Save meeting
	@Override
	public ResponseEntity<?> save(Preference preferenceData) throws Exception {
		HttpStatus status = null;
		ResponseSerializer<Preference> responseSerializer = new ResponseSerializer<Preference>();
		responseSerializer.setSuccess(false);
		if (preferenceData.getUserId() == null) {
			return sendResponse(Constants.PREFERENCE_REQUIRED_FEILD, "No Content",
					responseSerializer, HttpStatus.BAD_REQUEST, 400);
		} else {
			preferenceData.setUuid(UUID.randomUUID().toString());
			savePreference(preferenceData);
			responseSerializer.setSuccess(true);
			responseSerializer.setData(preferenceData);
			responseSerializer.setErrorMessage(null);
			status = HttpStatus.CREATED;
			return new ResponseEntity<ResponseSerializer<Preference>>(responseSerializer, status);
		}
	}

	// Get preference by uuid
	@Override
	public ResponseEntity<?> getPreferenceById(String uuid) throws Exception {
		ResponseSerializer<Preference> responseSerializer = new ResponseSerializer<Preference>();
		responseSerializer.setSuccess(false);
		HttpStatus status = null;
		Preference res = getPreferenceByUuid(uuid);
		if (res != null) {
			responseSerializer.setSuccess(true);
			responseSerializer.setData(res);
			responseSerializer.setErrorMessage(null);
			status = HttpStatus.OK;
		} else {
			return sendResponse(Constants.UUID_NOT_EXIST, "Validation", responseSerializer, HttpStatus.NOT_FOUND,
					404);
		}
		return new ResponseEntity<ResponseSerializer<Preference>>(responseSerializer, status);
	}

	// Delete preference
	@Override
	public ResponseEntity<?> deletePreference(String uuid) throws Exception {
		ResponseSerializer<String> responseSerializer = new ResponseSerializer<String>();
		responseSerializer.setSuccess(false);
		HttpStatus status = null;
		Preference res = getPreferenceByUuid(uuid);
		if (res != null) {
			deletePreferenceById(uuid);
			responseSerializer.setSuccess(true);
			status = HttpStatus.NO_CONTENT;
		} else {
			return sendResponseString(Constants.UUID_NOT_EXIST, "Validation", responseSerializer,
					HttpStatus.NOT_FOUND, 404);
		}
		return new ResponseEntity(responseSerializer, status);
	}

	// Update preference
	@Override
	public ResponseEntity<?> updatePreference(Preference preferencelist) throws Exception {
		HttpStatus status = null;
		ResponseSerializer<Preference> responseSerializer = new ResponseSerializer<Preference>();
		responseSerializer.setSuccess(false);
		Preference resFind = getPreferenceByUuid(preferencelist.getUuid());
		if (resFind != null) {
			updatePreferenceRecord(preferencelist);
			responseSerializer.setSuccess(true);
			responseSerializer.setData(preferencelist);
			responseSerializer.setErrorMessage(null);
			status = HttpStatus.OK;
		} else {
			return sendResponse(Constants.UUID_NOT_EXIST, "Validation", responseSerializer, HttpStatus.NOT_FOUND,
					404);
		}
		return new ResponseEntity<ResponseSerializer<Preference>>(responseSerializer, status);
	}
	// Get preference by user id
		@Override
		public ResponseEntity<?> getPreferenceByUser(String userId) throws Exception {
			HttpStatus status = null;
			ResponseSerializer<List<Preference>> responseSerializer = new ResponseSerializer<>();
			HttpHeaders responseHeaders = new HttpHeaders();
				List<Preference> res = getAllPreferenceByUser(userId);
				if (res.isEmpty()) {
					return sendResponseList(Constants.UUID_NOT_EXIST, "Validation", responseSerializer, HttpStatus.NOT_FOUND,
							404);
				} else {
					responseSerializer.setSuccess(true);
					responseSerializer.setData(res);
					responseSerializer.setErrorMessage(null);
					status = HttpStatus.OK;
				}
			return new ResponseEntity<ResponseSerializer<List<Preference>>>(responseSerializer, responseHeaders,
					status);
		}

	private ResponseEntity<?> sendResponse(String errormessage, String type,
			ResponseSerializer<Preference> responseSerializer, HttpStatus status, int errorCode) {
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
			ResponseSerializer<List<Preference>> responseSerializer, HttpStatus status, int errorCode) {
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
	public Preference savePreference(Preference preferenceObj) throws Exception {
		Preference preference = null;
		preference = (Preference) preferenceRepository.save(preferenceObj, preferenceObj.getUuid(),
				PREFERENCE_COLLECTION);
		return preference;
	}

	@Override
	public List<Preference> getAllPreference() throws Exception {
		List<Preference> preference = null;
		preference = (List<Preference>) preferenceRepository.findAll(PREFERENCE_COLLECTION);
		return preference;
	}

	@Override
	public Preference getPreferenceByUuid(String id) throws Exception {
		Preference preference = (Preference) preferenceRepository.findOne(id, PREFERENCE_COLLECTION);
		return preference;
	}

	@Override
	public boolean deletePreferenceById(String id) throws Exception {
		boolean isDeleted = preferenceRepository.delete(id, PREFERENCE_COLLECTION);
		if (isDeleted) {
			return true;
		}
		return false;
	}

	@Override
	public Preference updatePreferenceRecord(Preference preferenceObj) throws Exception {
		Preference preference = null;
		preference = (Preference) preferenceRepository.save(preferenceObj, preferenceObj.getUuid(),
				PREFERENCE_COLLECTION);
		return preference;
	}

	@Override
	public List<Preference> getAllPreferenceByUser(String userId) throws Exception {
		List<Preference> preference = null;
		preference = (List<Preference>) preferenceRepository.findBySingleCondition("userId", userId,
				PREFERENCE_COLLECTION);
		return preference;
	}

}

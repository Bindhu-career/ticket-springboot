package com.bigbang.api.controller;

import java.util.List;
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
import com.bigbang.api.models.Preference;
import com.bigbang.api.models.ResponseSerializer;
import com.bigbang.api.service.PreferenceService;

@RestController
@CrossOrigin
@RequestMapping("/v1")
public class PreferenceController {
	@Autowired
	PreferenceService preferenceService;

	/*
	 * This method will create the user input data's into the Preference table uuid
	 * and createdAt fields are auto generated
	 */
	@PostMapping(value = "/preference")
	public Object savePreference(@RequestBody Preference preferenceData) throws Exception {
		return preferenceService.save(preferenceData);
	}

	/*
	 * Get all preference
	 */
	@GetMapping(value = "/preference")
	public ResponseEntity<ResponseSerializer<List<Preference>>> getAllPreference(@RequestHeader HttpHeaders headers) {
		List<Preference> preference = null;
		ResponseSerializer<List<Preference>> responseSerializer = new ResponseSerializer<List<Preference>>();
		HttpStatus status = null;
		try {
			preference = (List<Preference>) preferenceService.getAllPreference();
			responseSerializer.setSuccess(true);
			responseSerializer.setData(preference);
			responseSerializer.setErrorMessage(null);
			status = HttpStatus.OK;

		} catch (Exception e) {
			
		}
		return new ResponseEntity<ResponseSerializer<List<Preference>>>(responseSerializer, status);
	}

	/*
	 * Get preference by uuid
	 */
	@GetMapping(value = "/preference/{uuid}")
	public Object getPreferenceById(@PathVariable("uuid") String uuid) throws Exception {
		return preferenceService.getPreferenceById(uuid);
	}

	/*
	 * Delete preference
	 */
	@DeleteMapping(path = "/preference/{uuid}")
	public Object deletePreferenceById(@PathVariable("uuid") String uuid) throws Exception {
		return preferenceService.deletePreference(uuid);
	}

	/*
	 * This method will update the one record from the Preference table where uuid is
	 * equal
	 */
	@PutMapping(path = "/preference")
	public Object updatePreferenceRecord(@RequestBody Preference preferencelist) throws Exception {
		return preferenceService.updatePreference(preferencelist);
	}
	/*
	 * Get preference by user id
	 */
	@GetMapping(value = "/preference/user/{userId}")
	public Object getAllPreferenceByUser(@PathVariable("userId") String userId) throws Exception {
		return preferenceService.getPreferenceByUser(userId);
	}
	
}

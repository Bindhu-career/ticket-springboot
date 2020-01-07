package com.bigbang.api.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.bigbang.api.models.Preference;

public interface PreferenceService {
	
	public Preference savePreference(Preference preferenceData) throws Exception;

	public List<Preference> getAllPreference() throws Exception;

	public Preference getPreferenceByUuid(String uuid) throws Exception;

	public boolean deletePreferenceById(String uuid) throws Exception;

	public Preference updatePreferenceRecord(Preference preferencelist) throws Exception;
	
	public List<Preference> getAllPreferenceByUser(String user) throws Exception;
	
	public ResponseEntity<?> save(Preference preferenceData) throws Exception;
	
	public ResponseEntity<?> getPreferenceById(String uuid) throws Exception;
	
	public ResponseEntity<?> deletePreference(String uuid) throws Exception;
	
	public ResponseEntity<?> updatePreference(Preference preferencelist) throws Exception;
	
	public ResponseEntity<?> getPreferenceByUser(String userId) throws Exception;
	
}

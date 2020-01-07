package com.bigbang.api.service;
import java.util.List;

import org.springframework.http.ResponseEntity;

import com.bigbang.api.models.Milestone;
public interface MilestoneService {
	
	public Milestone saveMilestone(Milestone milestoneData) throws Exception;
	
	public List<Milestone> listAllMilestones() throws Exception;
	
	public Milestone getMilestoneByUuid(String uuid) throws Exception;
	
	public boolean deleteMilestoneById(String uuid) throws Exception;
	
	public Milestone updateMilestoneData(Milestone milestonelist) throws Exception;
	
	public List<Milestone> listAllMilestonesByProject(String projectId) throws Exception;
	
	public ResponseEntity<?> updateProjectByMilestone(String projectId) throws Exception;
	
	public ResponseEntity<?> save(Milestone milestoneData) throws Exception;
	
	public ResponseEntity<?> getMilestoneById(String uuid) throws Exception;
	
	public ResponseEntity<?> deleteMilestone(String uuid) throws Exception;
	
	public ResponseEntity<?> updateMilestone(Milestone milestonelist) throws Exception;
	
	public ResponseEntity<?> getMilestoneByProject(String projectId) throws Exception;
	
}


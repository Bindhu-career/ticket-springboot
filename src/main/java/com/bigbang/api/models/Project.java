package com.bigbang.api.models;

import java.util.ArrayList;
import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class Project {
	private String uuid;
	private String projectName;
	private String jobSubCategory;
	private String description;
	private String typeOfProject;
	private Long projectBudget;
	private ProjectStatus projectStatus;

	public static enum ProjectStatus {
		completed, pending, inprogress, waitingforapproval, rejected
	};

	private Date projectStartDate;
	private Date projectEndDate;
	private String projectDuration;
	private MinExperienceRequired minExperienceRequired;

	private enum MinExperienceRequired {
		basic, intermediate, experienced
	};

	private String paymentType;
	private ArrayList<Object> freeLancerList = new ArrayList<Object>();
	private ArrayList<Object> overview = new ArrayList<Object>();
	private ArrayList<Object> objective = new ArrayList<Object>();
	private ArrayList<Object> technicalFeatures = new ArrayList<Object>();
	private String projectManager;
	private long finalizedBudget;
	private boolean hoursApproved;
	private Date expextedEndDate;
	private Date actualEndDate;
	private Date createdAt;
	private String userId;
	private String userChannel;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getJobSubCategory() {
		return jobSubCategory;
	}

	public void setJobSubCategory(String jobSubCategory) {
		this.jobSubCategory = jobSubCategory;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTypeOfProject() {
		return typeOfProject;
	}

	public void setTypeOfProject(String typeOfProject) {
		this.typeOfProject = typeOfProject;
	}

	public Long getProjectBudget() {
		return projectBudget;
	}

	public void setProjectBudget(Long projectBudget) {
		this.projectBudget = projectBudget;
	}

	public ProjectStatus getProjectStatus() {
		return projectStatus;
	}

	public void setProjectStatus(ProjectStatus projectStatus) {
		this.projectStatus = projectStatus;
	}

	public Date getProjectStartDate() {
		return projectStartDate;
	}

	@JsonSerialize(using = CustomDateSerializer.class)
	public void setProjectStartDate(Date projectStartDate) {
		this.projectStartDate = projectStartDate;
	}

	public Date getProjectEndDate() {
		return projectEndDate;
	}

	@JsonSerialize(using = CustomDateSerializer.class)
	public void setProjectEndDate(Date projectEndDate) {
		this.projectEndDate = projectEndDate;
	}

	public String getProjectDuration() {
		return projectDuration;
	}

	public void setProjectDuration(String projectDuration) {
		this.projectDuration = projectDuration;
	}

	public MinExperienceRequired getMinExperienceRequired() {
		return minExperienceRequired;
	}

	public void setMinExperienceRequired(MinExperienceRequired minExperienceRequired) {
		this.minExperienceRequired = minExperienceRequired;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public ArrayList<Object> getFreeLancerList() {
		return freeLancerList;
	}

	public void setFreeLancerList(ArrayList<Object> freeLancerList) {
		this.freeLancerList = freeLancerList;
	}

	public long getFinalizedBudget() {
		return finalizedBudget;
	}

	public void setFinalizedBudget(long finalizedBudget) {
		this.finalizedBudget = finalizedBudget;
	}

	public boolean isHoursApproved() {
		return hoursApproved;
	}

	public void setHoursApproved(boolean hoursApproved) {
		this.hoursApproved = hoursApproved;
	}

	public Date getCreatedAt() {
		return createdAt;
	}
	
	@JsonSerialize(using = CustomDateSerializer.class)
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Date getExpextedEndDate() {
		return expextedEndDate;
	}
	
	@JsonSerialize(using = CustomDateSerializer.class)
	public void setExpextedEndDate(Date expextedEndDate) {
		this.expextedEndDate = expextedEndDate;
	}

	public Date getActualEndDate() {
		return actualEndDate;
	}
	
	@JsonSerialize(using = CustomDateSerializer.class)
	public void setActualEndDate(Date actualEndDate) {
		this.actualEndDate = actualEndDate;
	}

	public ArrayList<Object> getOverview() {
		return overview;
	}

	public void setOverview(ArrayList<Object> overview) {
		this.overview = overview;
	}

	public ArrayList<Object> getObjective() {
		return objective;
	}

	public void setObjective(ArrayList<Object> objective) {
		this.objective = objective;
	}

	public ArrayList<Object> getTechnicalFeatures() {
		return technicalFeatures;
	}

	public void setTechnicalFeatures(ArrayList<Object> technicalFeatures) {
		this.technicalFeatures = technicalFeatures;
	}

	public String getProjectManager() {
		return projectManager;
	}

	public void setProjectManager(String projectManager) {
		this.projectManager = projectManager;
	}

	public String getUserChannel() {
		return userChannel;
	}

	public void setUserChannel(String userChannel) {
		this.userChannel = userChannel;
	}

}

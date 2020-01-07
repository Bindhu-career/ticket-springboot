package com.bigbang.api.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class User {

	private String uuid;
	private String firstName;
	private String lastName;
	private String projectName;
	private String email;
	private boolean isValid;
	private String password;
	private String userType;
	private String companyName;
	private String companyDescription;
	private String companyVatId;
	private String taxAddress;
	private Date createdAt;
	private String website;
	private String skills;
	private String certificationName;
	private List<Object> projectFilesList = new ArrayList<Object>();
	private String category;
	private boolean active;
	private String verifyCode;
	private String role;
	private String currentJob;
	private String workingHours;
	private String randomPin;
	private String closureAccountDetails;
	private String industry;
	private String department;
	private String jobTitle;
	private String noOfEmployees;
	private String contactNumber;
	private String skypeId;
	private String leaveReason;
	private String question;
	private String answer;
	private String forgotPasswordId;
	private String imageUrl;
	private boolean slackAccount;

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public boolean isValid() {
		return isValid;
	}

	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}

	public String getVerifyCode() {
		return verifyCode;
	}

	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}

	

	public String getClosureAccountDetails() {
		return closureAccountDetails;
	}

	public void setClosureAccountDetails(String closureAccountDetails) {
		this.closureAccountDetails = closureAccountDetails;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyDescription() {
		return companyDescription;
	}

	public void setCompanyDescription(String companyDescription) {
		this.companyDescription = companyDescription;
	}

	public String getCompanyVatId() {
		return companyVatId;
	}

	public void setCompanyVatId(String companyVatId) {
		this.companyVatId = companyVatId;
	}

	public String getTaxAddress() {
		return taxAddress;
	}

	public void setTaxAddress(String taxAddress) {
		this.taxAddress = taxAddress;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	@JsonSerialize(using = CustomDateSerializer.class)
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getSkills() {
		return skills;
	}

	public void setSkills(String skills) {
		this.skills = skills;
	}

	public String getCertificationName() {
		return certificationName;
	}

	public void setCertificationName(String certificationName) {
		this.certificationName = certificationName;
	}

	public List<Object> getProjectFilesList() {
		return projectFilesList;
	}

	public void setProjectFilesList(List<Object> projectFilesList) {
		this.projectFilesList = projectFilesList;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getCurrentJob() {
		return currentJob;
	}

	public void setCurrentJob(String currentJob) {
		this.currentJob = currentJob;
	}

	public String getWorkingHours() {
		return workingHours;
	}

	public void setWorkingHours(String workingHours) {
		this.workingHours = workingHours;
	}

	public String getRandomPin() {
		return randomPin;
	}

	public void setRandomPin(String randomPin) {
		this.randomPin = randomPin;
	}

	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public String getNoOfEmployees() {
		return noOfEmployees;
	}

	public void setNoOfEmployees(String noOfEmployees) {
		this.noOfEmployees = noOfEmployees;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getSkypeId() {
		return skypeId;
	}

	public void setSkypeId(String skypeId) {
		this.skypeId = skypeId;
	}

	public String getLeaveReason() {
		return leaveReason;
	}

	public void setLeaveReason(String leaveReason) {
		this.leaveReason = leaveReason;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getForgotPasswordId() {
		return forgotPasswordId;
	}

	public void setForgotPasswordId(String forgotPasswordId) {
		this.forgotPasswordId = forgotPasswordId;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean isSlackAccount() {
		return slackAccount;
	}

	public void setSlackAccount(boolean slackAccount) {
		this.slackAccount = slackAccount;
	}

	@Override
	public String toString() {
		return "User [uuid=" + uuid + ", firstName=" + firstName + ", lastName=" + lastName + ", projectName="
				+ projectName + ", email=" + email + ", isValid=" + isValid + ", password=" + password + ", userType="
				+ userType + ", companyName=" + companyName + ", companyDescription=" + companyDescription
				+ ", companyVatId=" + companyVatId + ", taxAddress=" + taxAddress + ", createdAt=" + createdAt
				+ ", website=" + website + ", skills=" + skills + ", certificationName=" + certificationName
				+ ", projectFilesList=" + projectFilesList + ", category=" + category + ", active=" + active
				+ ", verifyCode=" + verifyCode + ", role=" + role + ", currentJob=" + currentJob + ", workingHours="
				+ workingHours + ", randomPin=" + randomPin + ", closureAccountDetails=" + closureAccountDetails
				+ ", industry=" + industry + ", department=" + department + ", jobTitle=" + jobTitle
				+ ", noOfEmployees=" + noOfEmployees + ", contactNumber=" + contactNumber + ", skypeId=" + skypeId
				+ ", leaveReason=" + leaveReason + ", question=" + question + ", answer=" + answer
				+ ", forgotPasswordId=" + forgotPasswordId + ", imageUrl=" + imageUrl + ", slackAccount=" + slackAccount
				+ "]";
	}
	

}

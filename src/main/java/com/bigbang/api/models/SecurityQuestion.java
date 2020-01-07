package com.bigbang.api.models;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class SecurityQuestion {
	private String question;
	private String answer;
	private String uuid;
	private Date createdAt;
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
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	@JsonSerialize(using = CustomDateSerializer.class)
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	
}

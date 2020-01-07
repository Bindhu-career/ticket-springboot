package com.bigbang.api.service.impl;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.bigbang.api.models.TokenRequest;
import com.bigbang.api.models.TokenResponse;

@Service
public class TokenServiceImpl {

	@Autowired
	RestTemplate restTemplate;

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	public TokenResponse generateToken() {
		HttpEntity<TokenRequest> entity = null;
		InputStream inputStream;
		try {

			Properties prop = new Properties();
			String propFileName = "auth.properties";

			inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);

			if (inputStream != null) {
				prop.load(inputStream);
			} else {
				throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
			}

			HttpHeaders headers = new HttpHeaders();
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
			TokenRequest tokenRequest = new TokenRequest();
			tokenRequest.setAudience(prop.getProperty("audience"));
			tokenRequest.setClient_id(prop.getProperty("client_id"));
			tokenRequest.setClient_secret(prop.getProperty("client_secret"));
			tokenRequest.setGrant_type(prop.getProperty("grant_type"));
			entity = new HttpEntity<TokenRequest>(tokenRequest, headers);

		} catch (Exception e) {
			System.out.println("Exception: " + e);
		}
		return restTemplate
				.exchange("https://ashwinachu.auth0.com/oauth/token", HttpMethod.POST, entity, TokenResponse.class)
				.getBody();
	}
}

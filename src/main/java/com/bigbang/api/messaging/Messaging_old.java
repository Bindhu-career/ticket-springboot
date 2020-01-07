package com.bigbang.api.messaging;

import java.util.ArrayList;

import javax.jdo.annotations.Value;

import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.bigbang.api.interceptor.HeaderRequestInterceptor;

public class Messaging_old {
	
	private final static String FIREBASE_API_URL = "https://fcm.googleapis.com/fcm/send";
    private final static String FIREBASE_SERVER_KEY = "AAAAvLAZhxQ:APA91bHlYaKaFDCxsS7t4h2piU2d5PAlqBiZLhEKTrJ_Yr3463OIs7RqU-uG-S6UvZxwwQJXOjn4iAEPE5hqHPjky1cHyZhYciZf-fYeuYIt5-cgOucarRqxiGZ5jjw558RLb9KK0Gpq";
    private final static String CLICK_ACTION = "http://localhost:4200";
    private final static String IMAGE_URL = "https://www.gstatic.com/webp/gallery/2.webp";
	
	public static String sendNotification(String title, String message, String token) {
		   RestTemplate restTemplate = new RestTemplate();
		   String firebaseResponse = null;
		   
		    /**
		    https://fcm.googleapis.com/fcm/send
		    Content-Type:application/json
		    Authorization:key=FIREBASE_SERVER_KEY*/
		 
		    try {
				ArrayList<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
				interceptors.add(new HeaderRequestInterceptor("Authorization", "key=" + FIREBASE_SERVER_KEY));
				interceptors.add(new HeaderRequestInterceptor("Content-Type", "application/json"));
				restTemplate.setInterceptors(interceptors);
				
				JSONObject json = new JSONObject();
				
//				Map<String, String> data = new HashMap<>();
//				data.put("title", "Angular News");
//				data.put("body", "Newsletter Available!");
//				data.put("icon", "https://www.gstatic.com/webp/gallery/2.webp");
//				data.put("click_action", "http://localhost:4200");
				

			    JSONObject body = new JSONObject();
			    body.put("to", token);
//			    body.put("priority", "high");
			 
			    JSONObject notification = new JSONObject();
			    notification.put("title", title);
			    notification.put("body", message);
			    notification.put("icon", IMAGE_URL);
			    notification.put("click_action", CLICK_ACTION);
			    
			    JSONObject data = new JSONObject();
			    data.put("Key-1", "JSA Data 1");
			    data.put("Key-2", "JSA Data 2");
			 
			    body.put("notification", notification);
			    body.put("data", data);
				
				HttpEntity<String> httpEntity = new HttpEntity<>(body.toString());
				firebaseResponse = restTemplate.postForObject(FIREBASE_API_URL, httpEntity, String.class);
				System.out.println("firebase response are ------  "+ firebaseResponse);
			} catch (RestClientException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return firebaseResponse;
	}
	
}
package com.bigbang.api.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import org.json.JSONObject;

public class EliteUtil {

	public static String getClientIpAddress() {
		String country = null;
		try {
			URL findIp = new URL("http://checkip.amazonaws.com/");
			BufferedReader br = new BufferedReader(new InputStreamReader(findIp.openStream()));
			String publicIp = br.readLine();
			String API_KEY = "at_Vg9L4fUUvi0CDkml8LtJRfh5x3PGD";
			String API_URL = "https://geo.ipify.org/api/v1?";
			String url = API_URL + "&apiKey=" + API_KEY + "&ipAddress=" + publicIp;
			try (java.util.Scanner s = new java.util.Scanner(new java.net.URL(url).openStream())) {
				String ipifyResponse = s.useDelimiter("\\A").next();
				JSONObject ipifyObject = new JSONObject(ipifyResponse);
				JSONObject location = ipifyObject.getJSONObject("location");
				country = location.getString("country");
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return country;
	}
	
	public static String getFirebaseProperty(String propertyName) {
		Properties prop = new Properties();
		try {
		    //load a properties file from class path, inside static method
		    prop.load(EliteUtil.class.getClassLoader().getResourceAsStream("firebase.properties"));
		    return prop.getProperty(propertyName);
		} 
		catch (IOException ex) {
		    ex.printStackTrace();
		}
		return null;
	}
}

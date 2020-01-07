package com.bigbang.api.database;

import java.io.IOException;
import java.util.logging.Logger;

import org.apache.commons.lang3.time.StopWatch;
import org.springframework.core.io.ClassPathResource;

import com.bigbang.api.util.Constants;
//import com.google.api.services.storage.model.Bucket;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
//import com.google.cloud.storage.Storage;
//import com.google.cloud.storage.StorageOptions;

public class FireStoreDBConn {
	private static final Logger log = Logger.getLogger(FireStoreDBConn.class.getName());
	private static FireStoreDBConn instance = new FireStoreDBConn();
	private static Firestore db;

	/*
	 * FireStore Database Connection
	 */
	private FireStoreDBConn() {
		StopWatch watch = new StopWatch();
		watch.start();

		try {

			FirestoreOptions options = FirestoreOptions.newBuilder().setTimestampsInSnapshotsEnabled(true)
					.setCredentials(GoogleCredentials.fromStream(new ClassPathResource("/bigbang-creds.json")
					.getInputStream())).setProjectId(Constants.PROJECT_ID)
				    .build();
			
//			FirestoreOptions.newBuilder().setCredentials(GoogleCredentials.fromStream(
//			new ClassPathResource(Constants.CREDENTIALS).getInputStream()))
//			.setTimestampsInSnapshotsEnabled(true)
//			.setProjectId(Constants.PROJECT_ID).build();
			db = options.getService();

		} catch (IOException e1) {
			System.out.println("firebase connection error>>>>>" + e1);
			return;
		}

		watch.stop();

	}

	/*
	 * This method will return the FireStore Database Connection instance
	 */
	public static FireStoreDBConn getInstance() {
		return instance;
	}

	/*
	 * This method will return the FireStone Database
	 */
	public static Firestore getFireStoreDB() {
		return db;
	}
}

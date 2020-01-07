package com.bigbang.api.repository;

import org.springframework.stereotype.Repository;

import com.bigbang.api.database.FireStoreDBConn;
import com.bigbang.api.models.Messaging;
import com.google.cloud.firestore.Firestore;

@Repository
public class MessagingRepository extends BaseRepository {
	
	public MessagingRepository() {
		super();
		setCls(Messaging.class);
	}
	
	public Firestore getFirestoreInstance() {
		FireStoreDBConn.getInstance();
		return FireStoreDBConn.getFireStoreDB();
	}

}

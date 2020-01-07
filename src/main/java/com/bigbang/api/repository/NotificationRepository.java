package com.bigbang.api.repository;

import org.springframework.stereotype.Repository;

import com.bigbang.api.database.FireStoreDBConn;
import com.bigbang.api.models.Notification;
import com.google.cloud.firestore.Firestore;

@Repository
public class NotificationRepository extends BaseRepository {
	public NotificationRepository() {
		super();
		setCls(Notification.class);
	}
	public Firestore getFirestoreInstance() {
		FireStoreDBConn.getInstance();
		return FireStoreDBConn.getFireStoreDB();
	}

}

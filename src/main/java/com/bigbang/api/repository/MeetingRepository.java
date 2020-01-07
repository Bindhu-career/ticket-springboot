package com.bigbang.api.repository;

import org.springframework.stereotype.Repository;
import com.bigbang.api.database.FireStoreDBConn;
import com.bigbang.api.models.Meeting;
import com.google.cloud.firestore.Firestore;

@Repository
public class MeetingRepository extends BaseRepository {

	public MeetingRepository() {
		super();
		setCls(Meeting.class);
	}

	public Firestore getFirestoreInstance() {
		FireStoreDBConn.getInstance();
		return FireStoreDBConn.getFireStoreDB();
	}

}

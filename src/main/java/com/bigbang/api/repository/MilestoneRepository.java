package com.bigbang.api.repository;

import org.springframework.stereotype.Repository;
import com.bigbang.api.database.FireStoreDBConn;
import com.bigbang.api.models.Milestone;
import com.google.cloud.firestore.Firestore;

@Repository
public class MilestoneRepository extends BaseRepository {
	
	public MilestoneRepository() {
		super();
		setCls(Milestone.class);
	}
	
	public Firestore getFirestoreInstance() {
		FireStoreDBConn.getInstance();
		return FireStoreDBConn.getFireStoreDB();
	}
	
}


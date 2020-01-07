package com.bigbang.api.repository;

import org.springframework.stereotype.Repository;

import com.bigbang.api.database.FireStoreDBConn;
import com.bigbang.api.models.Preference;
import com.google.cloud.firestore.Firestore;

@Repository
public class PreferenceRepository extends BaseRepository {
	
	public PreferenceRepository() {
		super();
		setCls(Preference.class);
	}
	public Firestore getFirestoreInstance() {
		FireStoreDBConn.getInstance();
		return FireStoreDBConn.getFireStoreDB();
	}

	
}



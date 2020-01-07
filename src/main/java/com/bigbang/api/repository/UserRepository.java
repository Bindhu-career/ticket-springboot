package com.bigbang.api.repository;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.bigbang.api.database.FireStoreDBConn;
import com.bigbang.api.models.User;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.SetOptions;
import com.google.cloud.firestore.WriteResult;


@Repository
public class UserRepository extends BaseRepository {
	
	public UserRepository() {
		super();
		setCls(User.class);
	}

	public Firestore getFirestoreInstance() {
		FireStoreDBConn.getInstance();
		return FireStoreDBConn.getFireStoreDB();
	}

}

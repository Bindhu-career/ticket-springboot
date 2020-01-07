package com.bigbang.api.repository;

import org.springframework.stereotype.Repository;

import com.bigbang.api.database.FireStoreDBConn;
import com.bigbang.api.models.BillingInfo;
import com.bigbang.api.models.Meeting;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.SetOptions;
import com.google.cloud.firestore.WriteResult;

@Repository
public class BillingInfoRepositary extends BaseRepository {

	public BillingInfoRepositary() {
		super();
		setCls(BillingInfo.class);
	}
	
	public static final Firestore getFireStoreInstance() {
		FireStoreDBConn.getInstance();
		return FireStoreDBConn.getFireStoreDB();
	}
	
}

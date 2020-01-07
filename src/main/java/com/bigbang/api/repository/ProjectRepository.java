package com.bigbang.api.repository;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.springframework.stereotype.Repository;
import com.bigbang.api.database.FireStoreDBConn;
import com.bigbang.api.models.Project;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.Query.Direction;


@Repository
public class ProjectRepository extends BaseRepository {
	
	public ProjectRepository() {
		super();
		setCls(Project.class);
	}
	
	public Firestore getFirestoreInstance() {
		FireStoreDBConn.getInstance();
		return FireStoreDBConn.getFireStoreDB();
	}

	public int startsFrom(int page, int limit) {
		int actual = page - 1;
		return actual * limit;
	}

	public int endsAt(int page, int limit) {
		return page * limit;
	}
	
	public List<Project> listAllProjectByUserId(String string, String userId, String page, String limitNumber,
			String PROJECT_COLLECTION) throws InterruptedException, ExecutionException {
		Firestore firestore_db = getFirestoreInstance();
		List<QueryDocumentSnapshot> docs = null;
		CollectionReference collectionRef = firestore_db.collection(PROJECT_COLLECTION);
		Query query = null;
		Direction direction = Query.Direction.DESCENDING;
		int limit = startsFrom(Integer.parseInt(page), Integer.parseInt(limitNumber));
		if (limit == 0) {
			limit = 1;
		}
		collectionRef = firestore_db.collection(PROJECT_COLLECTION);
		query = collectionRef.whereEqualTo("userId", userId);
		query = query.limit(limit);
		ApiFuture<QuerySnapshot> future = query.get();
		try {
			docs = future.get(30, TimeUnit.SECONDS).getDocuments();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (TimeoutException e) {
			e.printStackTrace();
		}
		if (docs.size() > 0) {
			DocumentSnapshot lastDoc = docs.get(docs.size() - 1);
			ApiFuture<QuerySnapshot> x = firestore_db.collection(PROJECT_COLLECTION).whereEqualTo("userId", userId)
					.orderBy("createdAt", direction).startAt(lastDoc).limit(Integer.parseInt(limitNumber)).get();
			
			ApiFuture<QuerySnapshot> query2 = null;
			query2 = x;
			List<Project> p = new ArrayList<>();
			QuerySnapshot querySnapshot = null;
			try {
				if (query != null) {
					querySnapshot = query2.get();
					List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
					for (QueryDocumentSnapshot document : documents) {
						p.add(document.toObject(Project.class));
					}
				}
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
			return p;
		}
		return null;
	}

}














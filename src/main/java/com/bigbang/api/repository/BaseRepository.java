package com.bigbang.api.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Repository;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import com.bigbang.api.database.FireStoreDBConn;

@Repository
@SuppressWarnings("unchecked")
public abstract class BaseRepository<T> {

	private Class<T> cls;

	public void setCls(Class<T> cls) {
		this.cls = cls;
	}

	public Firestore getFirestoreInstance() {
		FireStoreDBConn.getInstance();
		return FireStoreDBConn.getFireStoreDB();
	}

	public T findOne(String id, String schema) throws Exception {
		T entity = null;
		DocumentReference docRef = getFirestoreInstance().collection(schema).document(id);
		ApiFuture<DocumentSnapshot> future = docRef.get();
		DocumentSnapshot document = future.get();
		if (document.exists()) {
			Class<?> c = Class.forName(cls.getName());
			entity = (T) document.toObject(c);
			return entity;
		}
		return entity;
	}

	public List<T> findBySingleCondition(String key, String value, String schema) throws Exception {
		ApiFuture<QuerySnapshot> query = getFirestoreInstance().collection(schema).whereEqualTo(key, value).get();
		QuerySnapshot querySnapshot = null;
		querySnapshot = query.get();
		List<T> entities = new ArrayList<>();
		List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
		for (QueryDocumentSnapshot document : documents) {
			Class<?> c = Class.forName(cls.getName());
			entities.add((T) document.toObject(c));
		}
		return entities;
	}
	
	public List<T> findByMultipleCondition(String key, String value, String key2, String value2, String schema) throws Exception {
		ApiFuture<QuerySnapshot> query = getFirestoreInstance().collection(schema).whereEqualTo(key, value).whereEqualTo(key2, value2).get();
		QuerySnapshot querySnapshot = null;
		querySnapshot = query.get();
		List<T> entities = new ArrayList<>();
		List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
		for (QueryDocumentSnapshot document : documents) {
			Class<?> c = Class.forName(cls.getName());
			entities.add((T) document.toObject(c));
		}
		return entities;
	}
	public List<T> findAll(String schema) throws Exception {
		ApiFuture<QuerySnapshot> query = getFirestoreInstance().collection(schema).get();
		QuerySnapshot querySnapshot = null;
		querySnapshot = query.get();
		List<T> entities = new ArrayList<>();
		List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
		for (QueryDocumentSnapshot document : documents) {
			Class<?> c = Class.forName(cls.getName());
			entities.add((T) document.toObject(c));
		}
		return entities;
	}

	public T save(T entity, String id, String schema) throws Exception {
		DocumentReference docRef = getFirestoreInstance().collection(schema).document(id);
		docRef.set(entity);
		ApiFuture<DocumentSnapshot> future = docRef.get();
		DocumentSnapshot document = future.get();
		if (document.exists()) {
			Class<?> c = Class.forName(cls.getName());
			entity = (T) document.toObject(c);
		}
		return entity;
	}
	public T updateSpecificFeild(T entity, String id, String schema) throws Exception {
		DocumentReference docRef = getFirestoreInstance().collection(schema).document(id);
		docRef.update((Map<String, Object>) entity);
		ApiFuture<DocumentSnapshot> future = docRef.get();
		DocumentSnapshot document = future.get();
		if (document.exists()) {
			Class<?> c = Class.forName(cls.getName());
			entity = (T) document.toObject(c);
		}
		return entity;
	}

	public boolean delete(String id, String schema) throws InterruptedException, ExecutionException {
		getFirestoreInstance().collection(schema).document(id).delete();
		return true;
	}
	
}
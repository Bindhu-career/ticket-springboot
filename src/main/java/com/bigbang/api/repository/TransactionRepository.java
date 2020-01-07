package com.bigbang.api.repository;

import org.springframework.stereotype.Repository;

import com.bigbang.api.database.FireStoreDBConn;
import com.bigbang.api.models.Transaction;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.SetOptions;
import com.google.cloud.firestore.WriteResult;

@Repository
public class TransactionRepository extends BaseRepository {
	
	public TransactionRepository() {
		super();
		setCls(Transaction.class);
	}

	public Firestore getFirestoreInstance() {
		FireStoreDBConn.getInstance();
		return FireStoreDBConn.getFireStoreDB();
	}
	private final static String TRANSACTION_COLLECTION = "Transaction";

	public ApiFuture<WriteResult> saveTransaction(Transaction transactionData) {
		DocumentReference docRef = getFirestoreInstance().collection(TRANSACTION_COLLECTION)
				.document(transactionData.getUuid());
		return docRef.set(transactionData);
	}

	public ApiFuture<QuerySnapshot> getAllTransaction() {
		return getFirestoreInstance().collection(TRANSACTION_COLLECTION).get();
	}

	public ApiFuture<DocumentSnapshot> getTransactionByUuid(String uuid) {
		return getFirestoreInstance().collection(TRANSACTION_COLLECTION).document(uuid).get();
	}

	public void deleteTransactionById(String uuid) {
		getFirestoreInstance().collection(TRANSACTION_COLLECTION).document(uuid).delete();
	}

	public ApiFuture<WriteResult> updateTransactionRecord(Transaction transaction) {
		Firestore firestoreDb = getFirestoreInstance();
		return firestoreDb.collection(TRANSACTION_COLLECTION).document(transaction.getUuid()).set(transaction,
				SetOptions.merge());
	}
	
	public ApiFuture<QuerySnapshot> getAllTransactionByProject(String projectId) {
		return getFirestoreInstance().collection(TRANSACTION_COLLECTION).whereEqualTo("projectId", projectId).get();
	}
	public ApiFuture<QuerySnapshot> getAllTransactionByUser(String userId) {
		return getFirestoreInstance().collection(TRANSACTION_COLLECTION).whereEqualTo("userId", userId).get();
	}
	
	public ApiFuture<QuerySnapshot> getTransactionByBillingAndProject(String billingId, String projectId) {
		return getFirestoreInstance().collection(TRANSACTION_COLLECTION).whereEqualTo("billingId", billingId).whereEqualTo("projectId", projectId).get();
	}
}

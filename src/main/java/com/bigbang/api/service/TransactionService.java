package com.bigbang.api.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.bigbang.api.models.Transaction;

public interface TransactionService {
	
	public Transaction saveTransaction(Transaction transactionData) throws Exception;

	public List<Transaction> listAllTransaction() throws Exception;

	public Transaction getTransactionByUuid(String uuid) throws Exception;

	public boolean deleteTransactionById(String uuid) throws Exception;

	public Transaction updateTransactionData(Transaction transactionList) throws Exception;

	public List<Transaction> getAllTransactionByProject(String projectId) throws Exception;

	public List<Transaction> getAllTransactionByUser(String projectId) throws Exception;

	public List<Transaction> getTransactionByBillingAndProject(String billingId, String projectId) throws Exception;
	
	public ResponseEntity<?> save(Transaction transactionData) throws Exception;
	
	public ResponseEntity<?> getTransactionById(String uuid) throws Exception;
	
	public ResponseEntity<?> deleteTransaction(String uuid) throws Exception;
	
	public ResponseEntity<?> updateTransaction(Transaction transactionlist) throws Exception;
	
	public ResponseEntity<?> getAllTransactionByProjectId(String projectId) throws Exception;
	
	public ResponseEntity<?> getAllTransactionByUserId(String userId) throws Exception;
	
	public ResponseEntity<?> getAllTransactionByBillAndProject(String billingId, String projectId) throws Exception;
}

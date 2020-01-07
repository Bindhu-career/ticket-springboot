package com.bigbang.api.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.bigbang.api.models.ErrorMessage;
import com.bigbang.api.models.ErrorResponse;
import com.bigbang.api.models.Transaction;
import com.bigbang.api.models.ResponseSerializer;
import com.bigbang.api.repository.TransactionRepository;
import com.bigbang.api.service.TransactionService;
import com.bigbang.api.util.Constants;

@Service
@SuppressWarnings("unchecked")
public class TransactionServiceImpl implements TransactionService {

	@Autowired
	private TransactionRepository transactionRepository;

	private String TRANSACTION_COLLECTION = "Transaction";

	// Save transaction
	@Override
	public ResponseEntity<?> save(Transaction transactionData) throws Exception {
		HttpStatus status = null;
		ResponseSerializer<Transaction> responseSerializer = new ResponseSerializer<Transaction>();
		responseSerializer.setSuccess(false);
		Transaction transaction = null;

		transactionData.setUuid(UUID.randomUUID().toString());
		transaction = saveTransaction(transactionData);
		if (transaction == null) {
			return sendResponse(Constants.INVALID_PARAMETER, "Validation", responseSerializer, HttpStatus.BAD_REQUEST, 400);
		} else {
			responseSerializer.setSuccess(true);
			responseSerializer.setData(transaction);
			responseSerializer.setErrorMessage(null);
			status = HttpStatus.CREATED;
		}
		return new ResponseEntity<ResponseSerializer<Transaction>>(responseSerializer, status);
	}

	// Get transaction by uuid
	@Override
	public ResponseEntity<?> getTransactionById(String uuid) throws Exception {
		ResponseSerializer<Transaction> responseSerializer = new ResponseSerializer<Transaction>();
		responseSerializer.setSuccess(false);
		HttpStatus status = null;
		Transaction res = getTransactionByUuid(uuid);
		if (res != null) {
			responseSerializer.setSuccess(true);
			responseSerializer.setData(res);
			responseSerializer.setErrorMessage(null);
			status = HttpStatus.OK;
		} else {
			return sendResponse(Constants.UUID_NOT_EXIST, "Validation", responseSerializer, HttpStatus.NOT_FOUND,
					404);
		}
		return new ResponseEntity<ResponseSerializer<Transaction>>(responseSerializer, status);
	}

	// Delete transaction
	@Override
	public ResponseEntity<?> deleteTransaction(String uuid) throws Exception {
		ResponseSerializer<String> responseSerializer = new ResponseSerializer<String>();
		responseSerializer.setSuccess(false);
		HttpStatus status = null;
		Transaction res = getTransactionByUuid(uuid);
		if (res != null) {
			deleteTransactionById(uuid);
			responseSerializer.setSuccess(true);
			status = HttpStatus.NO_CONTENT;
		} else {
			return sendResponseString(Constants.UUID_NOT_EXIST, "Validation", responseSerializer,
					HttpStatus.NOT_FOUND, 404);
		}
		return new ResponseEntity(responseSerializer, status);
	}

	// Update transaction
	@Override
	public ResponseEntity<?> updateTransaction(Transaction transactionlist) throws Exception {
		HttpStatus status = null;
		ResponseSerializer<Transaction> responseSerializer = new ResponseSerializer<Transaction>();
		responseSerializer.setSuccess(false);
		Transaction resFind = getTransactionByUuid(transactionlist.getUuid());
		if (resFind != null) {
			updateTransactionData(transactionlist);
			responseSerializer.setSuccess(true);
			responseSerializer.setData(transactionlist);
			responseSerializer.setErrorMessage(null);
			status = HttpStatus.OK;
		} else {
			return sendResponse(Constants.UUID_NOT_EXIST, "Validation", responseSerializer, HttpStatus.NOT_FOUND,
					404);
		}

		return new ResponseEntity<ResponseSerializer<Transaction>>(responseSerializer, status);
	}

	// Get transaction by project id
	@Override
	public ResponseEntity<?> getAllTransactionByProjectId(String projectId) throws Exception {
		ResponseSerializer<List<Transaction>> responseSerializer = new ResponseSerializer<>();
		responseSerializer.setSuccess(false);
		HttpStatus status = null;
		List<Transaction> res = getAllTransactionByProject(projectId);
		if (res.isEmpty()) {
			return sendResponseList(Constants.PROJECT_NOT_EXIST, "Validation", responseSerializer,
					HttpStatus.NOT_FOUND, 404);
		} else {
			responseSerializer.setSuccess(true);
			responseSerializer.setData(res);
			responseSerializer.setErrorMessage(null);
			status = HttpStatus.OK;
		}
		return new ResponseEntity<ResponseSerializer<List<Transaction>>>(responseSerializer, status);
	}

	// Get transaction by project id
	@Override
	public ResponseEntity<?> getAllTransactionByUserId(String userId) throws Exception {
		ResponseSerializer<List<Transaction>> responseSerializer = new ResponseSerializer<>();
		HttpHeaders responseHeaders = new HttpHeaders();
		HttpStatus status = null;
		List<Transaction> res = getAllTransactionByUser(userId);
		if (res.isEmpty()) {
			return sendResponseList(Constants.USERID_NOT_EXIST, "Validation", responseSerializer,
					HttpStatus.NOT_FOUND, 404);
		} else {
			responseSerializer.setSuccess(true);
			responseSerializer.setData(res);
			responseSerializer.setErrorMessage(null);
			status = HttpStatus.OK;
		}
		return new ResponseEntity<ResponseSerializer<List<Transaction>>>(responseSerializer, responseHeaders, status);
	}

	// Get transaction by billing and project
	@Override
	public ResponseEntity<?> getAllTransactionByBillAndProject(String billingId, String projectId) throws Exception {
		ResponseSerializer<List<Transaction>> responseSerializer = new ResponseSerializer<>();
		HttpStatus status = null;
		List<Transaction> res = getTransactionByBillingAndProject(billingId, projectId);
		if (res.isEmpty()) {
			return sendResponseList(Constants.INVALID_BILLING_PROJECT, "Validation", responseSerializer,
					HttpStatus.NOT_FOUND, 404);
		} else {
			responseSerializer.setSuccess(true);
			responseSerializer.setData(res);
			responseSerializer.setErrorMessage(null);
			status = HttpStatus.OK;
		}
		return new ResponseEntity<ResponseSerializer<List<Transaction>>>(responseSerializer, status);
	}

	private ResponseEntity<?> sendResponse(String errormessage, String type,
			ResponseSerializer<Transaction> responseSerializer, HttpStatus status, int errorCode) {
		ErrorMessage error = new ErrorMessage();
		error.setCode(errorCode);
		error.setMessage(errormessage);
		error.setType(type);
		responseSerializer.setSuccess(false);
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setErrors(error);
		errorResponse.setTrace_id(UUID.randomUUID().toString());
		return new ResponseEntity<Object>(errorResponse, status);
	}

	private ResponseEntity<?> sendResponseList(String errormessage, String type,
			ResponseSerializer<List<Transaction>> responseSerializer, HttpStatus status, int errorCode) {
		ErrorMessage error = new ErrorMessage();
		error.setCode(errorCode);
		error.setMessage(errormessage);
		error.setType(type);
		responseSerializer.setSuccess(false);
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setErrors(error);
		errorResponse.setTrace_id(UUID.randomUUID().toString());
		return new ResponseEntity<Object>(errorResponse, status);
	}

	private ResponseEntity<?> sendResponseString(String errormessage, String type,
			ResponseSerializer<String> responseSerializer, HttpStatus status, int errorCode) {
		ErrorMessage error = new ErrorMessage();
		error.setCode(errorCode);
		error.setMessage(errormessage);
		error.setType(type);
		responseSerializer.setSuccess(false);
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setErrors(error);
		errorResponse.setTrace_id(UUID.randomUUID().toString());
		return new ResponseEntity<Object>(errorResponse, status);
	}

	@Override
	public Transaction saveTransaction(Transaction transactionObj) throws Exception {
		Transaction transaction = null;
		transaction = (Transaction) transactionRepository.save(transactionObj, transactionObj.getUuid(),
				TRANSACTION_COLLECTION);
		return transaction;
	}

	@Override
	public List<Transaction> listAllTransaction() throws Exception {
		List<Transaction> transaction = null;
		transaction = (List<Transaction>) transactionRepository.findAll(TRANSACTION_COLLECTION);
		return transaction;
	}

	@Override
	public Transaction getTransactionByUuid(String id) throws Exception {
		Transaction transaction = (Transaction) transactionRepository.findOne(id, TRANSACTION_COLLECTION);
		return transaction;
	}

	@Override
	public boolean deleteTransactionById(String id) throws Exception {
		boolean isDeleted = transactionRepository.delete(id, TRANSACTION_COLLECTION);
		if (isDeleted) {
			return true;
		}
		return false;
	}

	@Override
	public Transaction updateTransactionData(Transaction transactionObj) throws Exception {
		Transaction transaction = null;
		transaction = (Transaction) transactionRepository.save(transactionObj, transactionObj.getUuid(),
				TRANSACTION_COLLECTION);
		return transaction;
	}

	@Override
	public List<Transaction> getAllTransactionByProject(String projectId) throws Exception {
		List<Transaction> transaction = null;
		transaction = (List<Transaction>) transactionRepository.findBySingleCondition("projectId", projectId,
				TRANSACTION_COLLECTION);
		return transaction;
	}

	@Override
	public List<Transaction> getAllTransactionByUser(String userId) throws Exception {
		List<Transaction> transaction = null;
		transaction = (List<Transaction>) transactionRepository.findBySingleCondition("userId", userId,
				TRANSACTION_COLLECTION);
		return transaction;
	}

	@Override
	public List<Transaction> getTransactionByBillingAndProject(String billingId, String projectId) throws Exception {
		List<Transaction> transaction = null;
		transaction = (List<Transaction>) transactionRepository.findByMultipleCondition("billingId", billingId,
				"projectId", projectId, TRANSACTION_COLLECTION);
		return transaction;
	}

}

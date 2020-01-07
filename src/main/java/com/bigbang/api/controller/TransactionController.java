package com.bigbang.api.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.bigbang.api.models.ResponseSerializer;
import com.bigbang.api.models.Transaction;
import com.bigbang.api.service.TransactionService;


@RestController
@CrossOrigin
@RequestMapping("/v1")
public class TransactionController {
	@Autowired
	TransactionService transactionService;

	/*
	 * Save transaction
	 */

	@PostMapping(value = "/transaction")
	public Object saveTransaction(@RequestBody Transaction transactionData) throws Exception {
		return transactionService.save(transactionData);
	}

	/*
	 * Get all transaction
	 */
	@GetMapping(value = "/transaction")
	public ResponseEntity<ResponseSerializer<List<Transaction>>> listAllTrasaction(@RequestHeader HttpHeaders headers) {
		List<Transaction> transaction = null;
		ResponseSerializer<List<Transaction>> responseSerializer = new ResponseSerializer<List<Transaction>>();

		HttpStatus status = null;
		try {
			transaction = (List<Transaction>) transactionService.listAllTransaction();
			responseSerializer.setSuccess(true);
			responseSerializer.setData(transaction);
			responseSerializer.setErrorMessage(null);
			status = HttpStatus.OK;

		} catch (Exception e) {
			status = HttpStatus.BAD_REQUEST;
		}
		return new ResponseEntity<ResponseSerializer<List<Transaction>>>(responseSerializer, status);
	}

	/*
	 * Get transaction by uuid
	 */
	@GetMapping(value = "/transaction/{uuid}")
	public Object getTransactionById(@PathVariable("uuid") String uuid) throws Exception {
		return transactionService.getTransactionById(uuid);
	}

	/*
	 * Delete transaction
	 */
	@DeleteMapping(path = "/transaction/{uuid}")
	public Object deleteTransactionById(@PathVariable("uuid") String uuid) throws Exception {
		return transactionService.deleteTransaction(uuid);
	}

	/*
	 * Update transaction
	 */
	@PutMapping(path = "/transaction")
	public Object updateTransactionRecord(@RequestBody Transaction transactionlist) throws Exception {
		return transactionService.updateTransaction(transactionlist);
	}

	/*
	 * Get Transaction by project id
	 */
	@GetMapping(value = "/transaction/project/{projectId}")
	public Object getAllTransactionByProject(@PathVariable("projectId") String projectId) throws Exception {
		return transactionService.getAllTransactionByProjectId(projectId);
	}

	/*
	 * Get Transaction by user id
	 */
	@GetMapping(value = "/transaction/user/{userId}")
	public Object getAllTransactionByUser(@PathVariable("userId") String userId) throws Exception {
		return transactionService.getAllTransactionByUserId(userId);
	}

	/*
	 * Get Transaction by billing and project
	 */
	@GetMapping(value = "/transaction/billing/project/{billingId}/{projectId}")
	public Object getTransactionByBillingAndProject(@PathVariable("billingId") String billingId,
			@PathVariable("projectId") String projectId) throws Exception {
		return transactionService.getAllTransactionByBillAndProject(billingId, projectId);
	}
}

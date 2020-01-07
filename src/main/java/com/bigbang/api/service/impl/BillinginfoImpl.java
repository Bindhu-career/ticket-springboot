package com.bigbang.api.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.bigbang.api.models.BillingInfo;
import com.bigbang.api.models.ErrorMessage;
import com.bigbang.api.models.ErrorResponse;
import com.bigbang.api.models.ResponseSerializer;
import com.bigbang.api.models.Transaction;
import com.bigbang.api.repository.BillingInfoRepositary;
import com.bigbang.api.service.BillingInfoService;
import com.bigbang.api.service.StripeService;
import com.bigbang.api.service.TransactionService;
import com.bigbang.api.util.Constants;
import com.bigbang.api.util.Hash;
import com.stripe.model.Charge;
import com.stripe.model.Customer;

@Service
@SuppressWarnings("unchecked")
public class BillinginfoImpl implements BillingInfoService {

	@Autowired
	StripeService stripeService;

	@Autowired
	TransactionService transactionService;

	@Autowired
	private BillingInfoRepositary billingInfoRepository;

	private String BILLINGINFO_COLLECTION = "BillingInfo";

	// Save billinginfo
	@Override
	public ResponseEntity<?> save(BillingInfo billingData) throws Exception {
		HttpStatus status = null;
		ResponseSerializer<BillingInfo> responseSerializer = new ResponseSerializer<BillingInfo>();
		responseSerializer.setSuccess(false);
		if (billingData.getProjectId() == null || billingData.getUserId() == null) {
			return sendResponse(Constants.BILLINGINFO_REQUIRED_FEILD, "No Content", responseSerializer,
					HttpStatus.BAD_REQUEST, 400);
		} else {
			billingData.setUuid(UUID.randomUUID().toString());
			saveBillingInfo(billingData);
			responseSerializer.setSuccess(true);
			responseSerializer.setData(billingData);
			responseSerializer.setErrorMessage(null);
			status = HttpStatus.CREATED;
		}
		return new ResponseEntity<ResponseSerializer<BillingInfo>>(responseSerializer, status);
	}

	// Update billinginfo
	@Override
	public ResponseEntity<?> updateBillingInfo(BillingInfo billinglistinfo, String uuid) throws Exception {
		HttpStatus status = null;
		ResponseSerializer<BillingInfo> responseSerializer = new ResponseSerializer<BillingInfo>();
		responseSerializer.setSuccess(false);
		BillingInfo billingInfo = getBillingInfoByUuid(uuid);
		if (billingInfo != null) {
			billingInfo.setFullName(billinglistinfo.getFullName());
			billingInfo.setCompanyName(billinglistinfo.getCompanyName());
			billingInfo.setCity(billinglistinfo.getCity());
			billingInfo.setZipCode(billinglistinfo.getZipCode());
			billingInfo.setAddress(billinglistinfo.getAddress());
			billingInfo.setVat(billinglistinfo.getVat());
			billingInfo.setInvoices(billinglistinfo.isInvoices());
			billingInfo.setCustomerId(billinglistinfo.getCustomerId());
			billingInfo.setCardBrand(billinglistinfo.getCardBrand());
			billingInfo.setCardType(billinglistinfo.getCardType());
			billingInfo.setCardLast4Digit(billinglistinfo.getCardLast4Digit());
			updateBillingInfoData(billingInfo, uuid);
			responseSerializer.setSuccess(true);
			responseSerializer.setData(billingInfo);
			responseSerializer.setErrorMessage(null);
			status = HttpStatus.OK;
		} else {
			return sendResponse(Constants.UUID_NOT_EXIST, "Validation", responseSerializer, HttpStatus.NOT_FOUND,
					404);
		}
		return new ResponseEntity<ResponseSerializer<BillingInfo>>(responseSerializer, status);
	}

	// Get billinginfo by uuid
	@Override
	public ResponseEntity<?> getBillingInfoByUser(String userid) throws Exception {
		ResponseSerializer<List<BillingInfo>> responseSerializer = new ResponseSerializer<>();
		HttpStatus status = null;
		List<BillingInfo> res = getBillingInfoByUserid(userid);
		if (res.isEmpty()) {
			return sendResponseList(Constants.UUID_NOT_EXIST, "Validation", responseSerializer,
					HttpStatus.NOT_FOUND, 404);
		} else {
			responseSerializer.setSuccess(true);
			responseSerializer.setData(res);
			responseSerializer.setErrorMessage(null);
			status = HttpStatus.OK;
		}
		return new ResponseEntity<ResponseSerializer<List<BillingInfo>>>(responseSerializer, status);
	}

	// Add billing payment
	@Override
	public ResponseEntity<?> addBillingPayment(String userId, Map<String, Object> paymentObj) throws Exception {
		ResponseSerializer<String> responseSerializer = new ResponseSerializer<>();
		responseSerializer.setSuccess(false);
		HttpStatus status = null;
		JSONObject object = addBillingCard(userId, paymentObj);
		if (object == null) {
			return sendResponseString(Constants.PAYMENT_UNDONE, "Payment undone", responseSerializer,
					HttpStatus.BAD_REQUEST, 400);
		} else {
			responseSerializer.setSuccess(true);
			responseSerializer.setData(object.toString());
			responseSerializer.setErrorMessage(null);
			status = HttpStatus.OK;
		}
		return new ResponseEntity<ResponseSerializer<String>>(responseSerializer, status);
	}


	@Override
	public ResponseEntity<?> payment(String userId, Map<String, Object> paymentObj) throws Exception {
		ResponseSerializer<String> responseSerializer = new ResponseSerializer<>();
		responseSerializer.setSuccess(false);
		HttpHeaders responseHeaders = new HttpHeaders();
		HttpStatus status = null;
			Boolean object = billingPayment(userId, paymentObj);
			if (!object) {
				return sendResponseString(Constants.PAYMENT_UNDONE, "Payment undone", responseSerializer,
						HttpStatus.BAD_REQUEST, 400);
			} else {
				responseSerializer.setSuccess(true);
				responseSerializer.setData("Payment Done");
				responseSerializer.setErrorMessage(null);
				status = HttpStatus.OK;
			}
		return new ResponseEntity<ResponseSerializer<String>>(responseSerializer, responseHeaders, status);
	}

	private ResponseEntity<?> sendResponse(String errormessage, String type,
			ResponseSerializer<BillingInfo> responseSerializer, HttpStatus status, int errorCode) {
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
			ResponseSerializer<List<BillingInfo>> responseSerializer, HttpStatus status, int errorCode) {
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
	public BillingInfo saveBillingInfo(BillingInfo billingInfoObj) throws Exception {
		BillingInfo billingInfo = null;
		billingInfo = (BillingInfo) billingInfoRepository.save(billingInfoObj, billingInfoObj.getUuid(),
				BILLINGINFO_COLLECTION);
		return billingInfo;
	}

	@Override
	public List<BillingInfo> listAllBillingsInfo() throws Exception {
		return null;
	}

	@Override
	public BillingInfo getBillingInfoByUuid(String id) throws Exception {
		BillingInfo billingInfo = (BillingInfo) billingInfoRepository.findOne(id, BILLINGINFO_COLLECTION);
		return billingInfo;
	}

	@Override
	public Object deleteBillingInfoById(String uuid) throws Exception {
		return null;
	}

	@Override
	public BillingInfo updateBillingInfoData(BillingInfo billingInfoObj, String uuid) throws Exception {
		BillingInfo billingInfo = null;
		billingInfo = (BillingInfo) billingInfoRepository.save(billingInfoObj, uuid, BILLINGINFO_COLLECTION);
		return billingInfo;
	}

	@Override
	public List<BillingInfo> getBillingInfoByUserid(String userId) throws Exception {
		List<BillingInfo> billingInfo = null;
		billingInfo = (List<BillingInfo>) billingInfoRepository.findBySingleCondition("userId", userId,
				BILLINGINFO_COLLECTION);
		return billingInfo;
	}

	@Override
	public JSONObject addBillingCard(String userId, Map<String, Object> paymentObj) throws Exception {
		Map<String, Object> customerObj = stripeService.createCustomer(paymentObj);
		Customer customerDetails = null;
		JSONObject cardDetails = null;
		if (customerObj.get("error") != null) {
			return cardDetails;
		} else {
			try {
				JSONObject chargeObj = new JSONObject(customerObj);
				JSONObject customer = chargeObj.getJSONObject("customer");
				JSONObject sources = customer.getJSONObject("sources");
				JSONArray sourcesArray = sources.getJSONArray("data");
				Hash hash = new Hash();
				cardDetails = new JSONObject();
				cardDetails.put("customerId", Hash.encrypt(sourcesArray.getJSONObject(0).getString("customer")));
				cardDetails.put("cardBrand", sourcesArray.getJSONObject(0).getString("brand"));
				cardDetails.put("cardType", sourcesArray.getJSONObject(0).getString("funding"));
				cardDetails.put("cardLast4Digit", sourcesArray.getJSONObject(0).getString("last4"));
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		return cardDetails;
	}

	@Override
	public Boolean billingPayment(String userId, Map<String, Object> paymentObj) throws Exception {
		List<BillingInfo> billing = getBillingInfoByUserid(userId);
		Transaction isSaved = null;
		Boolean saved = false;
		String customerId = Hash.decrypt(billing.get(0).getCustomerId());
		Charge charge = stripeService.createCharge(customerId, paymentObj);
		if (charge != null) {
			JSONObject chargeObj = new JSONObject(charge);
			JSONObject source = chargeObj.getJSONObject("source");
			try {
				Transaction transaction = new Transaction();
				transaction.setCreatedAt(new Date());
				transaction.setAmount(charge.getAmount());
				transaction.setBrand(source.getString("brand"));
				transaction.setCardId(source.getString("id"));
				transaction.setCardHolderName(source.getString("name"));
				transaction.setCardType(source.getString("funding"));
				transaction.setCardLast4Digits(source.getString("last4"));
				transaction.setCurrency(paymentObj.get("currency").toString());
				transaction.setCardExpMonth(String.valueOf(source.getInt("expMonth")));
				transaction.setCardExpYear(String.valueOf(source.getInt("expYear")));
				transaction.setCustomerCountry(source.getString("country"));
				transaction.setBalanceTransaction(charge.getBalanceTransaction());
				transaction.setDate(charge.getCreated());
				transaction.setCustomerId(charge.getCustomer());
				transaction.setDescription(charge.getDescription());
				transaction.setEmail(paymentObj.get("email").toString());
				transaction.setReceiptEmail(charge.getReceiptEmail());
				transaction.setReceiptNumber(charge.getReceiptNumber());
				transaction.setReceiptURL(charge.getReceiptUrl());
				transaction.setStatus(charge.getStatus());
				transaction.setUserId(userId);

				isSaved = transactionService.saveTransaction(transaction);
				if (isSaved == null) {
					saved = false;
				} else {
					saved = true;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return saved;
	}

}

package com.bigbang.api.service;

import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.http.ResponseEntity;

import com.bigbang.api.models.BillingInfo;

public interface BillingInfoService {
	
public BillingInfo saveBillingInfo(BillingInfo billingData) throws Exception;

public List<BillingInfo> listAllBillingsInfo() throws Exception;

public BillingInfo getBillingInfoByUuid(String uuid) throws Exception;

public Object deleteBillingInfoById(String uuid) throws Exception;

public BillingInfo updateBillingInfoData(BillingInfo billinglist,String uuid) throws Exception;

public List<BillingInfo> getBillingInfoByUserid(String userId) throws Exception;

public JSONObject addBillingCard(String userId, Map<String, Object> paymentObj) throws Exception;

public Boolean billingPayment(String userId, Map<String, Object> paymentObj) throws Exception;

public ResponseEntity<?> save(BillingInfo billingData) throws Exception;

public ResponseEntity<?> updateBillingInfo(BillingInfo billinglistinfo, String uuid) throws Exception;

public ResponseEntity<?> getBillingInfoByUser(String userid) throws Exception;

public ResponseEntity<?> addBillingPayment(String userId, Map<String, Object> paymentObj) throws Exception;

public ResponseEntity<?> payment(String userId, Map<String, Object> paymentObj) throws Exception;

}

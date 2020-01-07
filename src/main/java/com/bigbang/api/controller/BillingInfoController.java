package com.bigbang.api.controller;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.bigbang.api.models.BillingInfo;
import com.bigbang.api.service.BillingInfoService;

@RestController
@CrossOrigin
@RequestMapping("/v1")
public class BillingInfoController {

	@Autowired
	BillingInfoService billingInfoService;

	/*
	 * Save billinginfo
	 */
	@PostMapping(value = "/billinginfo/save")
	public Object saveBilling(@RequestBody BillingInfo billingData) throws Exception {
		return billingInfoService.save(billingData);
	}

	/*
	 * Update billinginfo
	 */
	@PutMapping(path = "/billinginfo/{uuid}")
	public Object updateBillingInfoRecord(@RequestBody BillingInfo billinglistinfo, @PathVariable("uuid") String uuid)
			throws Exception {
		return billingInfoService.updateBillingInfo(billinglistinfo, uuid);
	}

	/*
	 * Get billinginfo by uuid
	 */
	@GetMapping(value = "/billinginfo/get/{userid}")
	public Object getBillingInfoByUserId(@PathVariable("userid") String userid) throws Exception {
		return billingInfoService.getBillingInfoByUser(userid);
	}

	/*
	 * Add billing payment
	 */
	@PutMapping(value = "/billinginfo/card/{userid}")
	public Object addBillingPayment(@PathVariable("userid") String userId, @RequestBody Map<String, Object> paymentObj)
			throws Exception {
		return billingInfoService.addBillingPayment(userId, paymentObj);
	}
	
	@PutMapping(value = "/billinginfo/pay/{userid}")
	public Object billingPayment(@PathVariable("userid") String userId, @RequestBody Map<String, Object> paymentObj) throws Exception {
		return billingInfoService.payment(userId, paymentObj);
	}

}

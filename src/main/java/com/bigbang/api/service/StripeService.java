package com.bigbang.api.service;


import com.bigbang.api.util.Currency;
import com.stripe.Stripe;
import com.stripe.model.Charge;
import com.stripe.model.Coupon;
import com.stripe.model.Customer;
import com.stripe.model.Subscription;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class StripeService {

    @Value("${stripe.keys.secret}")
    private String API_SECRET_KEY;

    public StripeService() {
    }

    public Map<String, Object> createCustomer(Map<String, Object> paymentObj) {
        Map<String, Object> response = new HashMap<>();
        try {
            Stripe.apiKey = API_SECRET_KEY;
            System.out.println("create customer --------- "+ API_SECRET_KEY);
            Map<String, Object> customerParams = new HashMap<>();
            // add customer unique id here to track them in your web application
            customerParams.put("description", paymentObj.get("description"));
            customerParams.put("email", paymentObj.get("email"));

            customerParams.put("source", paymentObj.get("stripeToken")); // ^ obtained with Stripe.js
            //create a new customer
            Customer customer = Customer.create(customerParams);
//            id = customer.getId();
            System.out.println("customer id are -@##----- "+ customer.getId());
//            return customer;
            response.put("customer", customer);
            
            
        } catch (Exception ex) {
            ex.printStackTrace();
          response.put("error", "error");
          response.put("errorMessage", ex.getMessage());
        }
        return response;
    }

    public String createSubscription(String customerId, String plan, String coupon) {
        String id = null;
        try {
            Stripe.apiKey = API_SECRET_KEY;
            Map<String, Object> item = new HashMap<>();
            item.put("plan", plan);

            Map<String, Object> items = new HashMap<>();
            items.put("0", item);

            Map<String, Object> params = new HashMap<>();
            params.put("customer", customerId);
            params.put("items", items);

            //add coupon if available
            if (!coupon.isEmpty()) {
                params.put("coupon", coupon);
            }

            Subscription sub = Subscription.create(params);
            id = sub.getId();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return id;
    }

//    public boolean cancelSubscription(String subscriptionId) {
//        boolean status;
//        try {
//            Stripe.apiKey = API_SECRET_KEY;
//            Subscription sub = Subscription.retrieve(subscriptionId);
//            sub.cancel(null);
//            status = true;
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            status = false;
//        }
//        return status;
//    }

    public Coupon retrieveCoupon(String code) {
        try {
            Stripe.apiKey = API_SECRET_KEY;
            return Coupon.retrieve(code);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public Charge createCharge(String customerId, Map<String, Object> paymentObj) {
        String id = null;
        Charge charge = null;
        Map<String, Object> chargeParams = new HashMap<>();
        String convertToDollar = Currency.convertCentToDollar(paymentObj.get("amount").toString());
        System.out.println("after convert to dollar eare ---- "+ Integer.parseInt(paymentObj.get("amount").toString()) * 100);
        
        try {
            Stripe.apiKey = API_SECRET_KEY;
           
            chargeParams.put("amount", (Integer.parseInt(paymentObj.get("amount").toString()) * 100));
            chargeParams.put("customer", customerId);
            chargeParams.put("currency", paymentObj.get("currency"));
            chargeParams.put("description", paymentObj.get("description"));
//            chargeParams.put("source", token); // ^ obtained with Stripe.js

            //create a charge
             charge = Charge.create(chargeParams);
           
//            id = charge.getId();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return charge;
    }
}
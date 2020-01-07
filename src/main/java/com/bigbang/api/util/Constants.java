package com.bigbang.api.util;

public class Constants {
	
	public static final String CREDENTIALS = "/bigbang-creds.json";
	public static final String PROJECT_ID = "bigbang-b2ebf";
	public static final String PROJECT_API = "/v1/project/**";
	public static final String TRANSACTION_API = "/v1/transaction/**";
	public static final String PERFERENCE_API = "/v1/preference/**";
	public static final String NOTIFICATION_API = "/v1/notification/**";
	public static final String MILESTONE_API = "/v1/milestone/**";
	public static final String MEETING_API = "/v1/meeting/**";
	public static final String MESSAGING_API = "/v1/messaging/**";
	public static final String BILLING_API = "/v1/billinginfo/**";
	
	// Hardcoded messages
	
	// User service
	public static final String USER_REQUIRED_FEILD = "First name, email, password, usertype can't be empty";
	public static final String INVALID_DOMAIN = "This domain not allowed";
	public static final String SAME_COMPANY = "More than one company with same name is not allowed";
	public static final String REGISTERED_EMAIL = "This Email id is Already Registered";
	public static final String UUID_NOT_EXIST = "The given uuid does not exist";
	public static final String EMAIL_NOT_EXIST = "Email not exist";
	public static final String PROJECT_NOT_EXIST = "The given project does not exist";
	public static final String USERID_NOT_EXIST = "The given user id does not exist";
	public static final String PASSWORD_NOT_EXIST = "The given password does not exist";
	public static final String INVALID_CODE = "Invalid Verification Code";
	public static final String SOMTHING_WRONG = "Something went wrong";
	public static final String EMAIL_NOT_VERIFIED = "Email not verified";
	public static final String ACCOUNT_DEACTIVATED = "This Account is deactivated";
	public static final String INVALID_EMAIL_PASSWORD = "Email or password is invalid";
	public static final String INVALID_ACCOUNT = "This account doesnt exist";
	
	// Transaction service
	public static final String INVALID_PARAMETER = "Invalid Parameter";
	public static final String INVALID_BILLING_PROJECT = "The given billing id, project id does not exist";
	
	// Project service
	public static final String PROJECT_REQUIRED_FEILD = "Invalid Parameter";
	public static final String INVALID_FEILDS = "The given feilds are not valid";
	
	// Preference service
	public static final String PREFERENCE_REQUIRED_FEILD = "First name, email, password, usertype can't be empty";
	
	// Notification service
	public static final String NOTIFICATION_REQUIRED_FEILD = "UserId can't be empty";
	
	// Milestone service
	public static final String MILESTONE_REQUIRED_FEILD = "Title can't be empty";
	
	// Meeting service
	public static final String MEETING_REQUIRED_FEILD = "Meeting status, project id, user id can't be empty";
	
	// BillingInfo service
	public static final String BILLINGINFO_REQUIRED_FEILD = "Project id, User id can't be empty";
	public static final String PAYMENT_UNDONE = "Your payment is not done";
}

package com.bigbang.api.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.bigbang.api.models.User;
import com.bigbang.api.mail.SendGridMail;
import com.bigbang.api.messaging.Messaging_old;
import com.bigbang.api.models.ChangePassword;
import com.bigbang.api.models.ErrorMessage;
import com.bigbang.api.models.ErrorResponse;
import com.bigbang.api.models.ForgotPassword;
import com.bigbang.api.models.Login;
import com.bigbang.api.models.ResponseSerializer;
import com.bigbang.api.models.ResponseSerializerLogin;
import com.bigbang.api.models.TokenResponse;
import com.bigbang.api.repository.UserRepository;
import com.bigbang.api.service.UserService;
import com.bigbang.api.util.CloudStorageHelper;
import com.bigbang.api.util.Constants;
import com.bigbang.api.util.EliteUtil;
import com.bigbang.api.util.Hash;
import com.bigbang.api.validator.PasswordConstraintValidator;
import com.google.api.server.spi.Constant;

@Service
@SuppressWarnings("unchecked")
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	private String CLIENT_COLLECTION = "User";

	@Autowired
	TokenServiceImpl tokenService;

	// Save User
	@Override
	public ResponseEntity<?> saveUser(User userData) throws Exception {
		HttpStatus status = null;
		String verifyCode;
		ResponseSerializer<User> responseSerializer = new ResponseSerializer<User>();
		responseSerializer.setSuccess(false);
		userData.setCreatedAt(new Date());
		ErrorMessage error = new ErrorMessage();
		if (userData.getUserType() == null || userData.getFirstName() == null || userData.getEmail() == null
				|| userData.getPassword() == null) {
			return sendResponse(Constants.USER_REQUIRED_FEILD, responseSerializer,
					HttpStatus.FORBIDDEN, 403);
		} else {
			String country = EliteUtil.getClientIpAddress();
			String msg = null;
			if (isRestrictedCountry(country)) {
				responseSerializer.setSuccess(false);
				responseSerializer.setErrorMessage(error);
				return sendResponse(msg, responseSerializer, HttpStatus.FORBIDDEN, 403);
			} else {

				if (userData.getUserType().equalsIgnoreCase("client")) {
					String[] emails = { "gmail", "yahoo", "hotmail", "live", "aol", "msn", "rediffmail", "ymail",
							"outlook" };
					String companyName = userData.getCompanyName().trim().toLowerCase();
					String userMail = userData.getEmail();
					String emailValidation = userMail.substring(userMail.indexOf('@') + 1, userMail.lastIndexOf('.'))
							.toLowerCase();
					userData.setCompanyName(companyName);
					if (Arrays.asList(emails).contains(emailValidation)) {
						return sendResponse(Constants.INVALID_DOMAIN, responseSerializer, HttpStatus.BAD_REQUEST, 400);
					}
					if (!findUserByCompanyName(companyName).isEmpty()) {
						return sendResponse(Constants.SAME_COMPANY, responseSerializer,
								HttpStatus.BAD_REQUEST, 400);
					}
				}
				String email = userData.getEmail();
				if (isValidPassword(userData.getPassword())) {
					String emailHash = Hash.sha256(email);
					String passWordHash = Hash.sha256(userData.getPassword());
					userData.setUuid(emailHash);
					int randint = getRandomPin();
					userData.setVerifyCode(String.valueOf(randint));
					verifyCode = userData.getVerifyCode();
					userData.setPassword(passWordHash);
					User res = getUserByUuid(userData.getUuid());
					if (res == null) {
						userRepository.save(userData, userData.getUuid(), CLIENT_COLLECTION);
						SendGridMail.sendMail(userData.getEmail(), verifyCode);
						userData.setVerifyCode(null);
						responseSerializer.setSuccess(true);
						responseSerializer.setData(userData);
						responseSerializer.setErrorMessage(null);
						status = HttpStatus.CREATED;
						return new ResponseEntity<ResponseSerializer<User>>(responseSerializer, status);
					} else {
						return sendResponse(Constants.REGISTERED_EMAIL, responseSerializer,
								HttpStatus.BAD_REQUEST, 400);
					}
				} else {
					return sendResponse("Invalid Password", responseSerializer, HttpStatus.BAD_REQUEST, 400);
				}

			}
		}

	}

	// Get single user by uuid
	@Override
	public ResponseEntity<?> getSingleUser(String uuid) throws Exception {
		ResponseSerializer<User> responseSerializer = new ResponseSerializer<User>();
		responseSerializer.setSuccess(false);
		HttpStatus status = null;
		User res = getUserByUuid(uuid);
		if (res != null) {
			responseSerializer.setSuccess(true);
			responseSerializer.setData(res);
			responseSerializer.setErrorMessage(null);
			status = HttpStatus.OK;
			return new ResponseEntity<ResponseSerializer<User>>(responseSerializer, status);
		} else {
			return sendResponse(Constants.UUID_NOT_EXIST, responseSerializer, HttpStatus.NOT_FOUND, 404);
		}
	}

	// Delete user
	@Override
	public ResponseEntity<?> deleteUser(String uuid) throws Exception {
		ResponseSerializer<User> responseSerializer = new ResponseSerializer<User>();
		responseSerializer.setSuccess(false);
		HttpStatus status = null;
		User res = getUserByUuid(uuid);
		if (res != null) {
			deleteUserById(uuid);
			responseSerializer.setSuccess(true);
			responseSerializer.setData(null);
			responseSerializer.setErrorMessage(null);
			status = HttpStatus.NO_CONTENT;
			return new ResponseEntity<Object>(responseSerializer, status);
		} else {
			return sendResponse(Constants.UUID_NOT_EXIST, responseSerializer, HttpStatus.NOT_FOUND, 404);
		}
	}

	@Override
	public ResponseEntity<?> updateUser(User userlist) throws Exception {
		HttpStatus status = null;
		HttpHeaders responseHeaders = new HttpHeaders();
		ResponseSerializer<User> responseSerializer = new ResponseSerializer<User>();
		responseSerializer.setSuccess(false);
		User userData = getUserByUuid(userlist.getUuid());
		if (userData != null) {
			updateuserData(updateUserFeild(userData, userlist));
			responseSerializer.setSuccess(true);
			responseSerializer.setData(userData);
			responseSerializer.setErrorMessage(null);
			status = HttpStatus.OK;
		} else {
			return sendResponse(Constants.UUID_NOT_EXIST, responseSerializer, HttpStatus.NOT_FOUND, 404);
		}
		return new ResponseEntity<ResponseSerializer<User>>(responseSerializer, responseHeaders, status);
	}

	// Update user profile
	@Override
	public ResponseEntity<?> userProfileUpdation(User userlist, String userId) throws Exception {
		HttpStatus status = null;
		HttpHeaders responseHeaders = new HttpHeaders();
		ResponseSerializer<User> responseSerializer = new ResponseSerializer<User>();
		responseSerializer.setSuccess(false);
		User resFind = getUserByUuid(userId);
		if (resFind != null) {
			updateuserData(updateUserProfile(resFind, userlist));
			responseSerializer.setSuccess(true);
			responseSerializer.setData(resFind);
			responseSerializer.setErrorMessage(null);
			status = HttpStatus.OK;
		} else {
			return sendResponse(Constants.UUID_NOT_EXIST, responseSerializer, HttpStatus.NOT_FOUND, 404);
		}
		return new ResponseEntity<ResponseSerializer<User>>(responseSerializer, responseHeaders, status);
	}

	// Email verification
	@Override
	public ResponseEntity<?> verifyEmail(String email, String code) throws Exception {
		ResponseSerializerLogin<Login> responseSerializer = new ResponseSerializerLogin<>();
		responseSerializer.setSuccess(false);
		Login loginRes = new Login();
		HttpHeaders responseHeaders = new HttpHeaders();
		TokenResponse tokenResponse = null;
		String emailHash = Hash.sha256(email);
		User res = getUserByUuid(emailHash);
		if (res == null) {
			return sendResponse(Constants.EMAIL_NOT_EXIST, responseSerializer, HttpStatus.NOT_FOUND, 404);
		} else if (res.getEmail().equals(email) && res.getVerifyCode().equals(code)) {
			res.setValid(true);
			User setUserValid = updateuserData(res);
			SendGridMail.welcomeTemplate(email);
			tokenResponse = tokenService.generateToken();
			loginRes.setUuid(res.getUuid());
			loginRes.setEmail(email);
			loginRes.setPassword(res.getPassword());
			loginRes.setUserType(res.getUserType());
			responseSerializer.setToken(tokenResponse.getAccess_token());
			responseSerializer.setSuccess(true);
			responseSerializer.setData(loginRes);
			responseSerializer.setErrorMessage(null);
		} else {
			return sendResponse(Constants.INVALID_CODE, responseSerializer, HttpStatus.NOT_FOUND, 404);
		}
		return new ResponseEntity<ResponseSerializerLogin<Login>>(responseSerializer, responseHeaders, HttpStatus.OK);
	}

	// Save slack user channel
	@Override
	public ResponseEntity<?> saveUserChannel(String userId, boolean flag) throws Exception {
		HttpStatus status = null;
		HttpHeaders responseHeaders = new HttpHeaders();
		ResponseSerializer<User> responseSerializer = new ResponseSerializer<User>();
		User resFind = getUserByUuid(userId);
		if (resFind != null) {
			resFind.setSlackAccount(flag);
			updateuserData(resFind);
			responseSerializer.setSuccess(true);
			responseSerializer.setData(null);
			responseSerializer.setErrorMessage(null);
			status = HttpStatus.OK;
		} else {
			return sendResponse(Constants.PROJECT_NOT_EXIST, responseSerializer, HttpStatus.NOT_FOUND, 404);
		}
		return new ResponseEntity<ResponseSerializer<User>>(responseSerializer, responseHeaders, status);
	}
	
	private ResponseEntity<?> sendResponse(String errormessage, ResponseSerializer<User> responseSerializer,
			HttpStatus status, int errorCode) {
		ErrorMessage error = new ErrorMessage();
		error.setCode(errorCode);
		error.setMessage(errormessage);
		error.setType("Validation");
		responseSerializer.setSuccess(false);
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setErrors(error);
		errorResponse.setTrace_id(UUID.randomUUID().toString());
		return new ResponseEntity<Object>(errorResponse, status);
	}
	
	// Change password
	@Override
	public ResponseEntity<?> changePassword(ChangePassword userData, String userId) throws Exception{
		HttpStatus status = null;
		HttpHeaders responseHeaders = new HttpHeaders();
		ResponseSerializer<ChangePassword> responseSerializer = new ResponseSerializer<ChangePassword>();
		responseSerializer.setSuccess(false);
			String oldPasswordHash = Hash.sha256(userData.getOldPassword());
			List<User> resFind = getUserByPassword(oldPasswordHash);
			if (!resFind.isEmpty()) {
				String newPasswordHash = Hash.sha256(userData.getNewPassword());
				userData.setNewPassword(newPasswordHash);
				userData.setOldPassword(oldPasswordHash);
				userData.setQuestion(userData.getQuestion());
				userData.setAnswer(userData.getAnswer());
				User user = getUserByUuid(userId);
				if (user != null) {
					updatePassword(userId, userData);
					responseSerializer.setSuccess(true);
					responseSerializer.setData(userData);
					responseSerializer.setErrorMessage(null);
					status = HttpStatus.OK;
				} else {
					return sendResponseChangePassword(Constants.USERID_NOT_EXIST, responseSerializer, HttpStatus.NOT_FOUND, 404);
				}

			} else {
				return sendResponseChangePassword(Constants.PASSWORD_NOT_EXIST, responseSerializer, HttpStatus.NOT_FOUND, 404);
			}
			return new ResponseEntity<ResponseSerializer<ChangePassword>>(responseSerializer, responseHeaders, status);
	}
	public ResponseEntity<?> sendResponseChangePassword(String errormessage, ResponseSerializer<ChangePassword> responseSerializer,
			HttpStatus status, int errorCode) {
		ErrorMessage error = new ErrorMessage();
		error.setCode(errorCode);
		error.setMessage(errormessage);
		error.setType("Validation");
		responseSerializer.setSuccess(false);
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setErrors(error);
		errorResponse.setTrace_id(UUID.randomUUID().toString());
		status = HttpStatus.NOT_FOUND;
		return new ResponseEntity<Object>(errorResponse, status);
	}
	
	public ResponseEntity<?> sendResponseForgotPassword(String errormessage, ResponseSerializer<ForgotPassword> responseSerializer,
			HttpStatus status, int errorCode) {
		ErrorMessage error = new ErrorMessage();
		error.setCode(errorCode);
		error.setMessage(errormessage);
		error.setType("Validation");
		responseSerializer.setSuccess(false);
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setErrors(error);
		errorResponse.setTrace_id(UUID.randomUUID().toString());
		status = HttpStatus.NOT_FOUND;
		return new ResponseEntity<Object>(errorResponse, status);
	}
	
	// Resend code
	@Override
	public ResponseEntity<?> resend(String userId,String email) throws Exception{
		HttpStatus status = null;
		HttpHeaders responseHeaders = new HttpHeaders();
		ResponseSerializer<ChangePassword> responseSerializer = new ResponseSerializer<ChangePassword>();
			int randint = getRandomPin();
			SendGridMail.sendMail(email, String.valueOf(randint));
			resendCode(userId, String.valueOf(randint));
			responseSerializer.setSuccess(true);
			responseSerializer.setData(null);
			responseSerializer.setErrorMessage(null);
			status = HttpStatus.OK;
			return new ResponseEntity<ResponseSerializer<ChangePassword>>(responseSerializer, responseHeaders, status);
	}
	
	// Deactivate account
	@Override
	public ResponseEntity<?> deactivateAccount(User userlist, String userId) throws Exception{
		HttpStatus status = null;
		HttpHeaders responseHeaders = new HttpHeaders();
		ResponseSerializer<User> responseSerializer = new ResponseSerializer<User>();
		responseSerializer.setSuccess(false);
			User resFind = getUserByUuid(userId);
			if (resFind != null) {
				resFind.setActive(userlist.isActive());
				resFind.setLeaveReason(userlist.getLeaveReason());
				updateuserData(resFind);
				responseSerializer.setSuccess(true);
				responseSerializer.setData(resFind);
				responseSerializer.setErrorMessage(null);
				status = HttpStatus.OK;
				SendGridMail.deactivateTemplate(userlist.getEmail());
			} else {
				return sendResponse(Constants.UUID_NOT_EXIST, responseSerializer, HttpStatus.NOT_FOUND, 404);
			}
			return new ResponseEntity<ResponseSerializer<User>>(responseSerializer, responseHeaders, status);
	}
	
	// Forgot password
	@Override
	public ResponseEntity<?> forgotPassword(String userId, String email) throws Exception{
		HttpStatus status = null;
		HttpHeaders responseHeaders = new HttpHeaders();
		ResponseSerializer<ChangePassword> responseSerializer = new ResponseSerializer<ChangePassword>();
		responseSerializer.setSuccess(false);
			User res = getUserByUuid(userId);
			if (res == null) {
				return sendResponseChangePassword(Constants.UUID_NOT_EXIST, responseSerializer, HttpStatus.NOT_FOUND, 404);
			} else {
				SendGridMail.forgotPasswordMail(email);
				res.setForgotPasswordId(UUID.randomUUID().toString());
				updateuserData(res);
				responseSerializer.setSuccess(true);
				responseSerializer.setData(null);
				responseSerializer.setErrorMessage(null);
				status = HttpStatus.OK;
			}
			
			return new ResponseEntity<ResponseSerializer<ChangePassword>>(responseSerializer, responseHeaders, status);

		} 
	// Save forgot password
	@Override
	public ResponseEntity<?> saveForgotPassword(ForgotPassword userData, String userId) throws Exception{
		HttpStatus status = null;
		HttpHeaders responseHeaders = new HttpHeaders();
		ResponseSerializer<ForgotPassword> responseSerializer = new ResponseSerializer<ForgotPassword>();
		responseSerializer.setSuccess(false);
			List<User> res = getUserByEmailAndId(userData.getEmail(), userData.getForgotPasswordId());
			String newPassword = Hash.sha256(userData.getConfirmPassword());
			if (res.isEmpty()) {
				return sendResponseForgotPassword(Constants.EMAIL_NOT_EXIST, responseSerializer, HttpStatus.NOT_FOUND, 404);
			} else {
				saveForgotPassword(userId, newPassword);
				responseSerializer.setSuccess(true);
				responseSerializer.setData(null);
				responseSerializer.setErrorMessage(null);
				status = HttpStatus.OK;
			}
			return new ResponseEntity<ResponseSerializer<ForgotPassword>>(responseSerializer, responseHeaders, status);
	}
	
	// Send notification
	@Override
	public ResponseEntity<?> notification(Map<String, Object> obj) throws Exception{
		HttpStatus status = null;
		HttpHeaders responseHeaders = new HttpHeaders();
		ResponseSerializer<String> responseSerializer = new ResponseSerializer<String>();
		responseSerializer.setSuccess(false);
			String res = sendNotification(obj);
//			String newPassword = Hash.sha256(userData.getConfirmPassword());
			if (res == null) {
				return sendResponseString(Constants.SOMTHING_WRONG, responseSerializer, HttpStatus.NOT_FOUND, 404);
			} else {
				responseSerializer.setSuccess(true);
				responseSerializer.setData(res);
				responseSerializer.setErrorMessage(null);
				status = HttpStatus.OK;
			}
			return new ResponseEntity<ResponseSerializer<String>>(responseSerializer, responseHeaders, status);
	}
	
	@Override
	public List<User> listAllUser() throws Exception {
		List<User> user = null;
		user = (List<User>) userRepository.findAll(CLIENT_COLLECTION);
		return user;
	}

	@Override
	public User getUserByUuid(String id) throws Exception {
		User user = (User) userRepository.findOne(id, CLIENT_COLLECTION);
		return user;
	}

	public List<User> findUserByCompanyName(String companyName) throws Exception {
		List<User> users = (List<User>) userRepository.findBySingleCondition("companyName", companyName,
				CLIENT_COLLECTION);
		return users;
	}

	@Override
	public boolean deleteUserById(String id) throws Exception {
		boolean isDeleted = userRepository.delete(id, CLIENT_COLLECTION);
		if (isDeleted) {
			return true;
		}
		return false;
	}

	@Override
	public User updateuserData(User userObj) throws Exception {
		User user = null;
		user = (User) userRepository.save(userObj, userObj.getUuid(), CLIENT_COLLECTION);
		return user;
	}

	@Override
	public ResponseEntity<?> login(Login loginData, ResponseSerializerLogin<Login> responseSerializer) throws Exception {
		String emailhash = Hash.sha256(loginData.getEmail());
		responseSerializer.setSuccess(false);
		User user = null;
		TokenResponse tokenResponse = null;
		user = getUserByUuid(emailhash);
		String passwordHash = Hash.sha256(loginData.getPassword());
		if (user != null) {
			List<User> users = new ArrayList<>();
			users = (List<User>) userRepository.findByMultipleCondition("email", loginData.getEmail(), "password",
					passwordHash, CLIENT_COLLECTION);
			if (!users.isEmpty()) {
				user = users.get(0);
				loginData.setUuid(user.getUuid());
				String passWordHash = Hash.sha256(loginData.getPassword());
				loginData.setPassword(passWordHash);
				loginData.setUserType(user.getUserType());
				if (!user.isActive()) {
					if (user.isValid()) {
						tokenResponse = tokenService.generateToken();
						responseSerializer.setData(loginData);
						responseSerializer.setToken(tokenResponse.getAccess_token());
						responseSerializer.setErrorMessage(null);
						responseSerializer.setSuccess(true);
						return new ResponseEntity<Object>(responseSerializer, HttpStatus.OK);
					} else {
						return sendResponse(Constants.EMAIL_NOT_VERIFIED, responseSerializer, HttpStatus.BAD_REQUEST, 400);
					}
				} else {
					return sendResponse(Constants.ACCOUNT_DEACTIVATED, responseSerializer, HttpStatus.BAD_REQUEST, 400);
				}
			} else {
				return sendResponse(Constants.INVALID_EMAIL_PASSWORD, responseSerializer, HttpStatus.BAD_REQUEST, 400);
			}
		} else {
			return sendResponse(Constants.INVALID_ACCOUNT, responseSerializer, HttpStatus.BAD_REQUEST, 400);
		}

	}

	public ResponseEntity<?> sendResponse(String errormessage, ResponseSerializerLogin<Login> responseSerializer,
			HttpStatus status, int errorCode) {
		ErrorMessage error = new ErrorMessage();
		error.setCode(errorCode);
		error.setMessage(errormessage);
		error.setType("Validation");
		responseSerializer.setSuccess(false);
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setErrors(error);
		errorResponse.setTrace_id(UUID.randomUUID().toString());
		status = HttpStatus.NOT_FOUND;
		return new ResponseEntity<Object>(errorResponse, status);
	}
	
	public ResponseEntity<?> sendResponseString(String errormessage, ResponseSerializer<String> responseSerializer,
			HttpStatus status, int errorCode) {
		ErrorMessage error = new ErrorMessage();
		error.setCode(errorCode);
		error.setMessage(errormessage);
		error.setType("Validation");
		responseSerializer.setSuccess(false);
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setErrors(error);
		errorResponse.setTrace_id(UUID.randomUUID().toString());
		status = HttpStatus.NOT_FOUND;
		return new ResponseEntity<Object>(errorResponse, status);
	}

	@Override
	public List<User> getUserByEmail(String email, String code) throws Exception {
		List<User> user = null;
		user = (List<User>) userRepository.findByMultipleCondition("email", email, "verifyCode", code,
				CLIENT_COLLECTION);
		return user;
	}

	@Override
	public List<User> getUserByPassword(String password) throws Exception {
		List<User> user = null;
		user = (List<User>) userRepository.findBySingleCondition("password", password, CLIENT_COLLECTION);
		return user;
	}

	@Override
	public boolean updatePassword(String userId, ChangePassword userlist) throws Exception {
		Map<String, Object> update = new HashMap<>();
		update.put("password", userlist.getNewPassword());
		update.put("question", userlist.getQuestion());
		update.put("answer", userlist.getAnswer());
		User user = (User) userRepository.updateSpecificFeild(update, userId, CLIENT_COLLECTION);
		if (user == null) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public boolean resendCode(String userId, String code) throws Exception {
		Map<String, Object> update = new HashMap<>();
		update.put("verifyCode", code);
		User user = (User) userRepository.updateSpecificFeild(update, userId, CLIENT_COLLECTION);
		if (user == null) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public int getRandomPin() throws Exception {
		Random generator = new Random();
		generator.setSeed(System.currentTimeMillis());

		int num = generator.nextInt(99999) + 99999;
		if (num < 100000 || num > 999999) {
			num = generator.nextInt(99999) + 99999;
			if (num < 100000 || num > 999999) {
				throw new Exception("Unable to generate PIN at this time..");
			}
		}
		return num;
	}

	@Override
	public boolean saveForgotPassword(String userId, String password) throws Exception {
		Map<String, Object> update = new HashMap<>();
		update.put("password", password);
		User user = (User) userRepository.updateSpecificFeild(update, userId, CLIENT_COLLECTION);
		if (user == null) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public List<User> getUserByEmailAndId(String email, String id) throws Exception {
		List<User> user = null;
		user = (List<User>) userRepository.findByMultipleCondition("email", email, "forgotPasswordId", id,
				CLIENT_COLLECTION);
		return user;
	}

	@Override
	public String uploadFile(HttpServletRequest req, HttpServletResponse res, String userId) {
		User user = new User();
		try {

			CloudStorageHelper cloudStorageHelper = new CloudStorageHelper();
			String fileUrl = cloudStorageHelper.getImageUrl(req, res, EliteUtil.getFirebaseProperty("bucket_name"));
			user.setImageUrl(fileUrl);
			updateuserData(user);
			System.out.println(fileUrl);
			return fileUrl;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean isValidPassword(String password) {
		if (PasswordConstraintValidator.isValid(password)) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean isRestrictedCountry(String country) {
		if (/* country.equals("IN") || country.equals("IND") || */ country.equals("PK") || country.equals("PAK")
				|| country.equals("UA") || country.equals("UKR") || country.equals("PH") || country.equals("PHL")
				|| country.equals("LK") || country.equals("LKA")) {
			return true;
		}
		return false;
	}

	@Override
	public String setCountryErrorMsg(String country) {
		String msg = null;
		if (country.equals("IN") || country.equals("IND")) {
			msg = "Can't Register Indian Users";
		} else if (country.equals("PK") || country.equals("PAK")) {
			msg = "Can't Register Pakistan Users";
		} else if (country.equals("UA") || country.equals("UKR")) {
			msg = "Can't Register Ukraine Users";
		} else if (country.equals("PH") || country.equals("PHL")) {
			msg = "Can't Register Philippines Users";
		} else if (country.equals("LK") || country.equals("LKA")) {
			msg = "Can't Register Sri Lankan Users";
		}
		return msg;
	}

	@Override
	public User updateUserFeild(User userData, User userlist) {
		String passwordHash = null;
		userData.setFirstName(userlist.getFirstName());
		userData.setLastName(userlist.getLastName());
		userData.setProjectName(userlist.getProjectName());
		passwordHash = Hash.sha256(userlist.getPassword());
		userData.setPassword(passwordHash);
		userData.setUserType(userlist.getUserType());
		userData.setCompanyName(userlist.getCompanyName());
		userData.setCompanyDescription(userlist.getCompanyDescription());
		userData.setCompanyVatId(userlist.getCompanyVatId());
		userData.setTaxAddress(userlist.getTaxAddress());
		userData.setWebsite(userlist.getWebsite());
		userData.setSkills(userlist.getSkills());
		userData.setCertificationName(userlist.getCertificationName());
		userData.setProjectFilesList(userlist.getProjectFilesList());
		userData.setCategory(userlist.getCategory());
		userData.setRole(userlist.getRole());
		userData.setCurrentJob(userlist.getCurrentJob());
		userData.setWorkingHours(userlist.getWorkingHours());
		userData.setActive(userlist.isActive());
		userData.setClosureAccountDetails(userlist.getClosureAccountDetails());
		return userData;
	}

	@Override
	public User updateUserProfile(User resFind, User userlist) {
		resFind.setCompanyName(userlist.getCompanyName());
		resFind.setIndustry(userlist.getIndustry());
		resFind.setDepartment(userlist.getDepartment());
		resFind.setJobTitle(userlist.getJobTitle());
		resFind.setNoOfEmployees(userlist.getNoOfEmployees());
		resFind.setContactNumber(userlist.getContactNumber());
		resFind.setImageUrl(userlist.getImageUrl());
		resFind.setSkypeId(userlist.getSkypeId());
		resFind.setWebsite(userlist.getWebsite());
		return resFind;
	}

	@Override
	public String sendNotification(Map<String, Object> tempObj) {
		return Messaging_old.sendNotification(tempObj.get("title").toString(), tempObj.get("message").toString(),
				tempObj.get("to").toString());
	}

}

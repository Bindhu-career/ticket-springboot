package com.bigbang.api.controller;


import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.bigbang.api.models.ChangePassword;
import com.bigbang.api.models.ForgotPassword;
import com.bigbang.api.models.Login;
import com.bigbang.api.models.ResponseSerializer;
import com.bigbang.api.models.ResponseSerializerLogin;
import com.bigbang.api.models.User;
import com.bigbang.api.service.UserService;
import com.bigbang.api.service.impl.TokenServiceImpl;

@RestController
@CrossOrigin
@SuppressWarnings("rawtypes")
@RequestMapping("/v1")
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	UserService userService;

	@Autowired
	TokenServiceImpl tokenService;

	@GetMapping("/hello")
	public String hello() {

		return "redirect:swagger-ui.html";
	}

	@GetMapping("/welcome")
	public String hello_welcome() {

		return "Welcome to Bigbang";
	}

	/*
	 * Signup API
	 */
	@PostMapping(value = "/user/register")
	public Object saveUser(@RequestBody User userData, HttpServletRequest request) throws Exception {
		logger.info("Entered saveUser method, User email: {}", userData.getEmail());
		ResponseEntity response;
		response = userService.saveUser(userData);
		return response;
	}
	
	/*
	 * Login API
	 */
	@PostMapping(value = "/user/login")
	public ResponseEntity<ResponseSerializerLogin<Login>> login(@RequestBody Login userData) throws Exception {
		ResponseSerializerLogin<Login> responseSerializer = new ResponseSerializerLogin<Login>();
		ResponseEntity response;
		response = userService.login(userData, responseSerializer);
		return response;
	}
	/*
	 * Get all records
	 */
	@GetMapping(value = "/user")
	public ResponseEntity<ResponseSerializer<List<User>>> listAllUser(@RequestHeader HttpHeaders headers) {
		List<User> customers = null;
		ResponseSerializer<List<User>> responseSerializer = new ResponseSerializer<List<User>>();
		HttpStatus status = null;
		try {
			customers = (List<User>) userService.listAllUser();
			responseSerializer.setSuccess(true);
			responseSerializer.setData(customers);
			responseSerializer.setErrorMessage(null);
			status = HttpStatus.OK;

		} catch (Exception e) {
			
		}
		return new ResponseEntity<ResponseSerializer<List<User>>>(responseSerializer, status);
	}

	/*
	 * Get user by uuid
	 */
	
	@GetMapping(value = "/user/{uuid}")
	public Object getUserById(@PathVariable("uuid") String uuid, @RequestHeader HttpHeaders headers) throws Exception {
		return userService.getSingleUser(uuid);
	}
	/*
	 * Delete record by uuid
	 */
	@DeleteMapping(path = "/user/{uuid}")
	public Object deleteUserById(@PathVariable("uuid") String uuid) throws Exception {
		return userService.deleteUser(uuid);
	}
	/*
	 * Update user data
	 * 
	 */
	@PutMapping(path = "/user")
	public Object updateUserRecord(@RequestBody User userlist) throws Exception {
		return userService.updateUser(userlist);
	}
	/*
	 * Update user profile
	 */
	@PutMapping(path = "/user/profile/{userId}")
	public Object updateUserProfile(@RequestBody User userlist, @PathVariable("userId") String userId) throws Exception {
		return userService.userProfileUpdation(userlist, userId);
	}

	/*
	 * Email Verification
	 */
	@GetMapping(value = "/user/verify/{email}/{code}")
	public Object getUserByEmail(@PathVariable("email") String email, @PathVariable("code") String code) throws Exception {
		return userService.verifyEmail(email, code);
	}

	/*
	 * Change Password
	 */
	@PutMapping(path = "/user/changepassword/{userId}")
	public Object changePassword(@RequestBody ChangePassword userData, @PathVariable("userId") String userId) throws Exception {
		return userService.changePassword(userData, userId);
	}

	/*
	 * Resend code to email
	 */
	@GetMapping(path = "/user/resend/{email}/{userid}")
	public Object resendCode(@PathVariable("userid") String userId, @PathVariable("email") String email) throws Exception {
		return userService.resend(userId, email);
	}

	/*
	 * Deactivate account
	 */
	@PutMapping(path = "/user/deleteuser/{userId}")
	public Object deleteAccount(@RequestBody User userlist, @PathVariable("userId") String userId) throws Exception {
		return userService.deactivateAccount(userlist, userId);
	}
	/*
	 * Forgot Password
	 */
	@GetMapping(path = "/user/forget/{email}/{userid}")
	public Object forgetPassword(@PathVariable("userid") String userId, @PathVariable("email") String email) throws Exception {

		return userService.forgotPassword(userId, email);
	}

	// Save Forgot Password

	@GetMapping(path = "/user/savepass/{userId}")
	public Object saveForgotPassword(@RequestBody ForgotPassword userData, @PathVariable("userId") String userId) throws Exception {
		return userService.saveForgotPassword(userData, userId);
	}

	/*
	 * Profile Picture Upload
	 */
	@PostMapping(path = "/user/upload/{userId}")
	public String upload(@PathVariable("userId") String userId, HttpServletRequest req, HttpServletResponse res)
			throws Exception {
		try {
			JSONObject imageUrl = new JSONObject();
			imageUrl.put("imageUrl", userService.uploadFile(req, res, userId));
			return imageUrl.toString();
		} catch (Exception e) {
			logger.error("Exception occured at upload: {}", e.getMessage());
		}
		return null;
	}
	
	/*
	 *  Send Notification
	 */
	@PostMapping(path = "/user/notification")
	public Object sendNotification(@RequestBody Map<String, Object> obj) throws Exception {
		return userService.notification(obj);
	}

	/*
	 * save Slack Account
	 */
	@GetMapping(path = "/user/slack/{userId}/{flag}")
	public Object saveUserChannel(@PathVariable("userId") String userId, @PathVariable("flag") boolean flag) throws Exception {
		return userService.saveUserChannel(userId, flag);
	}
	
}

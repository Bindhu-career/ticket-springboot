package com.bigbang.api.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import com.bigbang.api.models.ChangePassword;
import com.bigbang.api.models.ForgotPassword;
import com.bigbang.api.models.Login;
import com.bigbang.api.models.ResponseSerializer;
import com.bigbang.api.models.ResponseSerializerLogin;
import com.bigbang.api.models.User;


public interface UserService {
	public ResponseEntity<?> saveUser(User userData) throws Exception;
	
	public List<User> listAllUser() throws Exception;
	
	public User getUserByUuid(String uuid) throws Exception;
	
	public ResponseEntity<?> getSingleUser(String uuid) throws Exception;
	
	public boolean deleteUserById(String uuid) throws Exception;
	
	public ResponseEntity<?> deleteUser(String uuid) throws Exception;
	
	public ResponseEntity<?> updateUser(User userlist) throws Exception;
	
	public User updateuserData(User userlist) throws Exception;
	
	public ResponseEntity<?> login(Login user, ResponseSerializerLogin<Login> login) throws Exception;
		
	public List<User> getUserByEmail(String email, String code) throws Exception;
	
	public List<User> getUserByPassword(String password) throws Exception;
	
	public boolean updatePassword(String userId, ChangePassword userlist) throws Exception;
	
	public boolean resendCode(String userId, String code) throws Exception;
	
	public int getRandomPin() throws Exception;
	
	public boolean saveForgotPassword(String userId, String code) throws Exception;
	
	public List<User> getUserByEmailAndId(String email, String id) throws Exception;
	
	public ResponseEntity<?> verifyEmail(String email, String code) throws Exception;
	
	public boolean isValidPassword(String password) throws Exception;
	
	public String sendNotification(Map<String, Object> tempObj) throws Exception;

	public String uploadFile(HttpServletRequest req, HttpServletResponse res, String userId) throws Exception;
	
	public boolean isRestrictedCountry(String country) throws Exception;
	
	public String setCountryErrorMsg(String country) throws Exception;
	
	public User updateUserFeild(User userData, User userlist) throws Exception;
	
	public User updateUserProfile(User resFind, User userlists) throws Exception;
	
	public ResponseEntity<?> userProfileUpdation(User userlist, String userId) throws Exception;
	
	public ResponseEntity<?> saveUserChannel(String userId, boolean flag) throws Exception;
	
	public ResponseEntity<?> changePassword(ChangePassword userData, String userId) throws Exception;
	
	public ResponseEntity<?> resend(String userId,String email) throws Exception;
	
	public ResponseEntity<?> deactivateAccount(User userlist, String userId) throws Exception;
	
	public ResponseEntity<?> forgotPassword(String userId, String email) throws Exception;
	
	public ResponseEntity<?> saveForgotPassword(ForgotPassword userData, String userId) throws Exception;
	
	public ResponseEntity<?> notification(Map<String, Object> obj) throws Exception;
}

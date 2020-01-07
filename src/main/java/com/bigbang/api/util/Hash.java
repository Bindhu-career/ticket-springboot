package com.bigbang.api.util;

import java.io.IOException;
import static org.apache.commons.codec.binary.Hex.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.beans.factory.annotation.Value;
import com.sun.mail.util.BASE64DecoderStream;
import com.sun.mail.util.BASE64EncoderStream;

public class Hash {

	private static Cipher ecipher;
	private static Cipher dcipher;

	@Value("${crypto.secretkey}")
	private static final String CRYPTO_SECRET_KEY = "a2100d2a4a1fb97c";
	
//	@Value("${crypto.secretkey}")
//	private static String CRYPTO_SECRET_KEY;

	private static SecretKey key;

	private static final Logger log = Logger.getLogger(Hash.class.getName());

	public static String sha256(final String originalString) {
		MessageDigest digest = null;
		try {
			digest = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			log.log(Level.SEVERE, "Exception: " + e);
		}
		byte[] encodedhash = digest.digest(originalString.getBytes(StandardCharsets.UTF_8));

		StringBuilder hexString = new StringBuilder();
		for (int i = 0; i < encodedhash.length; i++) {
			String hex = Integer.toHexString(0xff & encodedhash[i]);
			if (hex.length() == 1)
				hexString.append('0');
			hexString.append(hex);
		}
		return hexString.toString();
	}

	public static String encrypt(String str) {

		try {
			System.out.println("crypto secret keys are ---- " + CRYPTO_SECRET_KEY);
			SecretKey key = loadKey(CRYPTO_SECRET_KEY);
			System.out.println("secret keys are ------  " + key+"   "+str);
			ecipher = Cipher.getInstance("DES");
			ecipher.init(Cipher.ENCRYPT_MODE, key);
			
			// encode the string into a sequence of bytes using the named charset

			// storing the result into a new byte array.

			byte[] utf8 = str.getBytes("UTF8");
			byte[] enc = ecipher.doFinal(utf8);

			// encode to base64
			enc = BASE64EncoderStream.encode(enc);
			return new String(enc);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String decrypt(String str) {

		try {
			SecretKey key = loadKey(CRYPTO_SECRET_KEY);
			dcipher = Cipher.getInstance("DES");
			dcipher.init(Cipher.DECRYPT_MODE, key);
			

			// decode with base64 to get bytes

			byte[] dec = BASE64DecoderStream.decode(str.getBytes());
			byte[] utf8 = dcipher.doFinal(dec);

			// create new string based on the specified charset
			return new String(utf8, "UTF8");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}

	public SecretKey getKey() {
		try {
			// generate secret key using DES algorithm
			key = KeyGenerator.getInstance("DES").generateKey();
			return key;
		} catch (NoSuchAlgorithmException e) {
			System.out.println("No Such Algorithm:" + e.getMessage());
			return null;
		}

	}

	public static String saveKey(SecretKey key) throws IOException {
		char[] hex = encodeHex(key.getEncoded());
		return String.valueOf(hex);
	}

	public static SecretKey loadKey(String data) throws IOException, org.apache.commons.codec.DecoderException {
		byte[] encoded;
		encoded = decodeHex(data.toCharArray());
		return new SecretKeySpec(encoded, "DES");
	}

}

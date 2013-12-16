package edu.uwm.cs361.fantastic_five.training_tracker.app.services;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import javax.xml.bind.DatatypeConverter;

public class PasswordService {
	// The higher the number of iterations the more
	// expensive computing the hash is for us
	// and also for a brute force attack.
	private static final int iterations = 10*1024;
	private static final int desiredKeyLen = 256;

	public static String generateSalt(int saltLen) {
		return encodeBase64(getSecureRandom().generateSeed(saltLen));
	}
	public static String generateSalt() {
		return generateSalt(32);
	}

	/** Checks whether given plain text password corresponds
		to a stored salted hash of the password. */
	public static boolean check(String password, String salt, String stored) {
		if (password == null || password.length() == 0) return false;
		String hashOfInput = hash(password, salt);
		return hashOfInput.equals(stored);
	}

	public static String hash(String password, String salt) {
		byte[] saltBytes = decodeBase64(salt);
		if (password == null || password.length() == 0) {
			throw new IllegalArgumentException("Empty passwords are not supported.");
		}
		SecretKeyFactory f = getSecretKeyFactory();
		SecretKey key;
		try {
			key = f.generateSecret(
				new PBEKeySpec(
					password.toCharArray(),
					saltBytes,
					iterations,
					desiredKeyLen
				)
			);
		} catch (InvalidKeySpecException e) {
			// Not sure how to deal with this properly right now.
			throw new RuntimeException("InvalidKeySpecException: " + e.getMessage());
		}
		return encodeBase64(key.getEncoded());
	}

	private static SecureRandom getSecureRandom() {
		SecureRandom secureRandom;
		try {
			secureRandom = SecureRandom.getInstance("SHA1PRNG");
		} catch (NoSuchAlgorithmException e) {
			// Not sure how to deal with this properly right now.
			throw new RuntimeException("NoSuchAlgorithmException: " + e.getMessage());
		}
		return secureRandom;
	}

	private static SecretKeyFactory getSecretKeyFactory() {
		SecretKeyFactory secretKeyFactory;
		try {
			secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
		} catch (NoSuchAlgorithmException e) {
			// Not sure how to deal with this properly right now.
			throw new RuntimeException("NoSuchAlgorithmException: " + e.getMessage());
		}
		return secretKeyFactory;
	}

	private static String encodeBase64(byte[] bytes) {
		return DatatypeConverter.printBase64Binary(bytes);
	}

	private static byte[] decodeBase64(String string) {
		return DatatypeConverter.parseBase64Binary(string);
	}
}
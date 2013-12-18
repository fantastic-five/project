package edu.uwm.cs361.fantastic_five.training_tracker.tests.services;

import static org.junit.Assert.*;

import javax.xml.bind.DatatypeConverter;

import org.junit.Test;

import edu.uwm.cs361.fantastic_five.training_tracker.app.services.PasswordService;

public class PasswordServiceTest {
	private final String salt1 = "vojuGW3XnKR/EroGISz1Se9tNEsPJlUcOWiy6/6nbpc1AMGZ+gSqblPvKFWSexa3QAjRloW45zfp9E0OUtyKKg==";
	private final String salt2 = "7cQn32klFSFpzyRrb8VSKAZIcUluE3ErkLM9Y230kafGh/5UDdgEZDcX0WaSx6oO0l3MUPrK6W7BW+vYneVpPQ==";

	@Test
	public void testGenerate32Salt() {
		String salt = PasswordService.generateSalt(32);
		byte[] saltBytes = decodeBase64(salt);
		assertEquals(32, saltBytes.length);
	}
	@Test
	public void testGenerate64Salt() {
		String salt = PasswordService.generateSalt(64);
		byte[] saltBytes = decodeBase64(salt);
		assertEquals(64, saltBytes.length);
	}
	@Test
	public void testSaltsUnique() {
		String salt1 = PasswordService.generateSalt(64);
		String salt2 = PasswordService.generateSalt(64);

		assertNotEquals(salt1, salt2);
	}

	@Test
	public void testHashEquals() {
		String password = "12345";

		String hash1 = PasswordService.hash(password, salt1);
		String hash2 = PasswordService.hash(password, salt1);

		assertEquals(hash1, hash2);
	}

	@Test
	public void testHashPasswordsNotEqual() {
		String hash1 = PasswordService.hash("12345", salt1);
		String hash2 = PasswordService.hash("asdfg", salt1);

		assertNotEquals(hash1, hash2);
	}

	@Test
	public void testHashSaltsNotEqual() {
		String password = "12345";

		String hash1 = PasswordService.hash(password, salt1);
		String hash2 = PasswordService.hash(password, salt2);

		assertNotEquals(hash1, hash2);
	}

	@Test
	public void testCheckTrue() {
		String password = "12345";

		String hash = PasswordService.hash(password, salt1);

		assertTrue(PasswordService.check(password, salt1, hash));
	}

	@Test
	public void testCheckPasswordsNotEqual() {
		String password = "12345";

		String hash = PasswordService.hash(password, salt1);

		assertFalse(PasswordService.check("asdf", salt1, hash));
	}
	@Test
	public void testCheckSaltsNotEqual() {
		String password = "12345";

		String hash = PasswordService.hash(password, salt1);

		assertFalse(PasswordService.check(password, salt2, hash));
	}

//	private String encodeBase64(byte[] bytes) {
//		return DatatypeConverter.printBase64Binary(bytes);
//	}
	private byte[] decodeBase64(String string) {
		return DatatypeConverter.parseBase64Binary(string);
	}
}

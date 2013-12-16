package edu.uwm.cs361.fantastic_five.training_tracker.tests.entities;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import edu.uwm.cs361.fantastic_five.training_tracker.app.entities.User;

public class UserTest {
	private User user;

	@Before
	public void setUp() {
		user = new User("skroob", "12345");
	}

	@Test
	public void testGetUsername() {
		assertEquals("skroob", user.getUsername());
	}
	@Test
	public void testSetUsername() {
		user.setUsername("joe");

		assertEquals("joe", user.getUsername());
	}

	@Test
	public void testPasswordMatches() {
		assertTrue(user.passwordMatches("12345"));
	}
	@Test
	public void testPasswordDoesNotMatch() {
		assertFalse(user.passwordMatches("asdf"));
	}
	@Test
	public void testPasswordDoesNotMatchBlank() {
		assertFalse(user.passwordMatches(""));
	}

	@Test
	public void testSetPassword() {
		user.setPassword("asdf");

		assertTrue(user.passwordMatches("asdf"));
	}

}

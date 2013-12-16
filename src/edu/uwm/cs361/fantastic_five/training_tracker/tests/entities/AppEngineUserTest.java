package edu.uwm.cs361.fantastic_five.training_tracker.tests.entities;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

import javax.jdo.PersistenceManager;

import org.junit.Before;
import org.junit.Test;

import edu.uwm.cs361.fantastic_five.training_tracker.app.entities.User;
import edu.uwm.cs361.fantastic_five.training_tracker.tests.AppEngineTest;

public class AppEngineUserTest extends AppEngineTest {
	User user;

	@Before
	public void setUp() {
		user = new User("skroob", "12345");
	}

	@Test
	public void testKey() {
		User user2 = new User("joe", "12345");

		PersistenceManager pm = getPersistenceManager();

		pm.makePersistent(user);
		pm.makePersistent(user2);

		assertThat(user.getKey(), is(not(user2.getKey())));
	}
}

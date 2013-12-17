package edu.uwm.cs361.fantastic_five.training_tracker.tests;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

import javax.jdo.PersistenceManager;

import org.junit.Before;
import org.junit.Test;

import edu.uwm.cs361.fantastic_five.training_tracker.app.entities.account;
import edu.uwm.cs361.fantastic_five.training_tracker.app.entities.Student;

public class AppEngineAccountTest extends AppEngineTest {
	account account;
	Student student;
	
	@Before
	public void setUpTests() {
		this.student = new Student("Andrew", "Meyer", "11/11/2011", "andrew", "password", true);
		this.account = new account(student, "123 Main St", "414-123-1234");
	}

	@Test
	public void testKey() {
		account account2 = new account(student, "123 Main St", "414-123-1234");

		PersistenceManager pm = getPersistenceManager();

		pm.makePersistent(account);
		pm.makePersistent(account2);

		assertThat(account.getKey(), is(not(account2.getKey())));
	}
}

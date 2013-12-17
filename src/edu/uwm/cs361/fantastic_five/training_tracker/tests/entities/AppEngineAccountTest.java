package edu.uwm.cs361.fantastic_five.training_tracker.tests.entities;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

import javax.jdo.PersistenceManager;

import org.junit.Before;
import org.junit.Test;

import edu.uwm.cs361.fantastic_five.training_tracker.app.entities.Account;
import edu.uwm.cs361.fantastic_five.training_tracker.app.entities.Student;
import edu.uwm.cs361.fantastic_five.training_tracker.tests.AppEngineTest;

public class AppEngineAccountTest extends AppEngineTest {
	Account account;
	Student student;

	@Before
	public void setUpTests() {
		this.student = new Student("Andrew", "Meyer", "11/11/2011", "andrew", "password", true);
		this.account = new Account(student, "123 Main St", "414-123-1234");
	}

	@Test
	public void testKey() {
		Account account2 = new Account(student, "123 Main St", "414-123-1234");

		PersistenceManager pm = getPersistenceManager();

		pm.makePersistent(account);
		pm.makePersistent(account2);

		assertThat(account.getKey(), is(not(account2.getKey())));
	}
}

package edu.uwm.cs361.fantastic_five.training_tracker.tests.use_cases;

import static org.junit.Assert.*;

import java.util.Iterator;

import javax.jdo.PersistenceManager;

import org.junit.Before;
import org.junit.Test;

import edu.uwm.cs361.fantastic_five.training_tracker.app.entities.Student;
import edu.uwm.cs361.fantastic_five.training_tracker.app.entities.Account;
import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.AccountCreator;
import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.AccountFinder;
import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.requests.CreateAccountRequest;
import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.requests.ListAccountsRequest;
import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.responses.ListAccountsResponse;
import edu.uwm.cs361.fantastic_five.training_tracker.tests.AppEngineTest;

public class AccountFinderListAccountsTest extends AppEngineTest {
	AccountFinder programFinder;
	ListAccountsRequest req;
	ListAccountsResponse resp;
	Student student;
	Student student2;

	@Before
	public void setUp() {
		PersistenceManager pm = getPersistenceManager();
		programFinder = new AccountFinder();
		req = new ListAccountsRequest();

		student = new Student("Cassie","Dowling","11/23/1992","dowling@uwm.edu","password",true);
		pm.makePersistent(student);
		student2 = new Student("FirstName","LastName","12/12/2012","email@gmail.com","password",true);
		pm.makePersistent(student2);
	}

	private void doRequest() {
		resp = programFinder.listAccounts(req);
	}

	private void createAccount(Student primary, String phone, String address) {
		CreateAccountRequest req = new CreateAccountRequest();
		req.primary = Long.toString(primary.getKey().getId());
		req.phone = phone;
		req.address = address;

		new AccountCreator().createAccount(req);
	}

	private void createAccounts() {
		createAccount(student, "414-123-4567", "123 Main St");
		createAccount(student2, "262-123-4567", "1234 N 1st St");
	}

	@Test
	public void testListAccountsNotEmpty() {
		createAccounts();
		doRequest();

		assertFalse(resp.accounts.isEmpty());
	}

	@Test
	public void testListAccountsCount() {
		createAccounts();
		doRequest();

		Iterator<Account> iterator = resp.accounts.iterator();
		for (int i = 0; i < 2; ++i) {
			assertTrue(iterator.next() != null);
		}
	}

	@Test
	public void testListEntriesCorrect() {
		createAccounts();
		doRequest();

		Iterator<Account> iterator = resp.accounts.iterator();
		for (int i = 0; i < 2; ++i) {
			Account account = iterator.next();

			assertTrue(account.getPhone().equals("414-123-4567") || account.getPhone().equals("262-123-4567"));

			if (account.getPhone().equals("414-123-4567")) {
				assertEquals("Cassie Dowling", account.getPrimary().getFullName());
				assertEquals("123 Main St", account.getAddress());
			} else {
				assertEquals("FirstName LastName", account.getPrimary().getFullName());
				assertEquals("1234 N 1st St", account.getAddress());
			}
		}
	}

	@Test
	public void testListAccountsWithNoAccounts() {
		doRequest();

		assertTrue(resp.accounts.isEmpty());
	}

}

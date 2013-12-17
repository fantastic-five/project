package edu.uwm.cs361.fantastic_five.training_tracker.tests.use_cases;

import static org.junit.Assert.*;

import java.util.List;

import javax.jdo.PersistenceManager;

import org.junit.Before;
import org.junit.Test;

import edu.uwm.cs361.fantastic_five.training_tracker.app.entities.Student;
import edu.uwm.cs361.fantastic_five.training_tracker.app.entities.Account;
import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.AccountCreator;
import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.requests.CreateAccountRequest;
import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.responses.CreateAccountResponse;
import edu.uwm.cs361.fantastic_five.training_tracker.tests.AppEngineTest;

public class AccountCreatorCreateAccountTest extends AppEngineTest {
	private AccountCreator accountCreator;
	private CreateAccountRequest req;
	private CreateAccountResponse resp;
	private Student student;

	@Before
	public void setUp() {
		accountCreator = new AccountCreator();
		req = new CreateAccountRequest();
	}

	private void doRequest() {
		resp = accountCreator.createAccount(req);
	}

	private void generateValidRequest() {
		PersistenceManager pm = getPersistenceManager();
		student = new Student("Cassie", "Dowling", "11/23/1992", "dowling@uwm.edu","password",true);
		pm.makePersistent(student);
		req.primary = Long.toString(student.getKey().getId());
		req.phone = "414-123-1234";
		req.address = "123 Main St";
	}

	@Test
	public void testCreateValidAccountSuccess() {
		generateValidRequest();

		doRequest();

		assertTrue(resp.success);
		assertTrue(resp.errors.isEmpty());
	}

	@SuppressWarnings("unchecked")
	private List<Account> getAllAccounts() {
		PersistenceManager pm = getPersistenceManager();
		return (List<Account>) pm.newQuery(Account.class).execute();
	}

	private Account getFirstAccount() {
		List<Account> accounts = getAllAccounts();
		return accounts.iterator().next();
	}

	@Test
	public void testAccountCreated() {
		generateValidRequest();
		doRequest();

		assertTrue(getAllAccounts().iterator().hasNext());
	}

	@Test
	public void testAccountCreatedCorrectPrimary() {
		generateValidRequest();
		req.primary = Long.toString(student.getKey().getId());
		doRequest();

		assertEquals("Cassie Dowling", getFirstAccount().getPrimary().getFullName());
	}

	@Test
	public void testAccountCreatedCorrectPhone() {
		generateValidRequest();
		req.phone = "414-123-1234";
		doRequest();

		assertEquals("414-123-1234", getFirstAccount().getPhone());
	}

	@Test
	public void testAccountCreatedCorrectAddress() {
		generateValidRequest();
		req.address = "123 Main St";
		doRequest();

		assertEquals("123 Main St", getFirstAccount().getAddress());
	}

	@Test
	public void testCreateAccountWithBlankPrimary() {
		generateValidRequest();
		req.primary = "";

		doRequest();

		assertFalse(resp.success);
		assertFalse(resp.errors.isEmpty());
		assertNotNull(resp.errors.get("primary"));
		assertFalse(resp.errors.get("primary").isEmpty());
	}

	@Test
	public void testCreateAccountWithInvalidPrimary() {
		generateValidRequest();
		req.primary = "12345";

		doRequest();

		assertFalse(resp.success);
		assertFalse(resp.errors.isEmpty());
		assertNotNull(resp.errors.get("primary"));
		assertFalse(resp.errors.get("primary").isEmpty());
	}

	@Test
	public void testCreateAccountWithBlankPhone() {
		generateValidRequest();
		req.phone = "";

		doRequest();

		assertFalse(resp.success);
		assertFalse(resp.errors.isEmpty());
		assertNotNull(resp.errors.get("phone"));
		assertFalse(resp.errors.get("phone").isEmpty());
	}

	@Test
	public void testInvalidAccountNotCreated() {
		generateValidRequest();
		req.primary = "";

		doRequest();

		assertFalse(getAllAccounts().iterator().hasNext());
	}

	@Test
	public void testCreateAccountWithBlankAddress() {
		generateValidRequest();
		req.address = "";

		doRequest();

		assertFalse(resp.success);
		assertFalse(resp.errors.isEmpty());
		assertNotNull(resp.errors.get("address"));
		assertFalse(resp.errors.get("address").isEmpty());
	}

	@Test
	public void testCreateAccountWithInvalidPhone() {
		generateValidRequest();
		req.phone = "123-1234";

		doRequest();

		assertFalse(resp.success);
		assertFalse(resp.errors.isEmpty());
		assertNotNull(resp.errors.get("phone"));
		assertFalse(resp.errors.get("phone").isEmpty());
	}

}

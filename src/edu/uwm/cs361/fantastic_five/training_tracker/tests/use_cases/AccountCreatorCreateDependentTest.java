package edu.uwm.cs361.fantastic_five.training_tracker.tests.use_cases;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.jdo.PersistenceManager;

import org.junit.Before;
import org.junit.Test;

import edu.uwm.cs361.fantastic_five.training_tracker.app.entities.Student;
import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.AccountFinder;
import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.StudentCreator;
import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.AccountCreator;
import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.requests.CreateAccountRequest;
import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.requests.CreateStudentRequest;
import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.responses.CreateAccountResponse;
import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.responses.ListAccountsResponse;
import edu.uwm.cs361.fantastic_five.training_tracker.tests.AppEngineTest;

public class AccountCreatorCreateDependentTest extends AppEngineTest {
	private AccountCreator accountCreator;
	private CreateAccountRequest req;
	private CreateAccountResponse resp;

	@Before
	public void setUp() {
		accountCreator = new AccountCreator();
		req = new CreateAccountRequest();
		
		createStudent("Cassie","Dowling","11/23/1992","dowling@uwm.edu",true);
		createAccount(getPrimary(),"414-123-4567","123 Main St");
		createStudent("DependentA","LastNameA","01/01/2012","A@email.com",false);
		createStudent("DependentB","LastNameB","02/02/2012","B@email.com",false);
	}

	private void generateValidRequest() {
		req.primary = Long.toString(getPrimary().getKey().getId());
		req.student = Long.toString(getFirstStudent().getKey().getId());
	}

	private void doRequest() {
		resp = accountCreator.createDependent(req);
	}

	private void createStudent(String firstName, String lastName, String DOB, String email, boolean primary) {
		CreateStudentRequest req = new CreateStudentRequest();
		req.firstName = firstName;
		req.lastName = lastName;
		req.DOB = DOB;
		req.email = email;
		req.primary = primary;

		new StudentCreator().createStudent(req);
	}
	
	private void createAccount(Student primary, String phone, String address) {
		CreateAccountRequest req = new CreateAccountRequest();
		req.primary = Long.toString(primary.getKey().getId());
		req.phone = phone;
		req.address = address;

		new AccountCreator().createAccount(req);
	}

	@SuppressWarnings("unchecked")
	private List<Student> getAllStudents() {
		PersistenceManager pm = getPersistenceManager();
		return (List<Student>) pm.newQuery(Student.class).execute();
	}
	
	private Student getFirstStudent() {
		List<Student> students = getAllStudents();
		Iterator<Student> it = students.iterator();
		Student student;
		while (it.hasNext()) {
			student = it.next();
			if (!student.isPrimary()) 
				return student;
		}
		return null;
	}

	private Student getPrimary() {
		List<Student> students = getAllStudents();
		Iterator<Student> it = students.iterator();
		Student student;
		while(it.hasNext()) {
			student = it.next();
			if (student.isPrimary())
				return student;
		}
		return null;
	}

	@Test
	public void testCreateSuccess() {
		generateValidRequest();
		doRequest();

		assertTrue(resp.success);
	}

	@Test
	public void testCreateNoError() {
		generateValidRequest();
		doRequest();

		assertTrue(resp.errors.isEmpty());
	}

	@Test
	public void testCreateDependent() {
		Student primary = getPrimary();
		Student student = getFirstStudent();

		req.primary = Long.toString(primary.getKey().getId());
		req.student = Long.toString(student.getKey().getId());

		doRequest();
		ListAccountsResponse resp = new AccountFinder().listAccounts();
		assertTrue(this.resp.errors.isEmpty());
		assertTrue(this.resp.success);
		Student dependent = resp.accounts.iterator().next().getDependents().iterator().next();
		
		assertEquals(student.getKey().getId(), dependent.getKey().getId());
	}

	@Test
	public void testCreateMultipleDependents() {
		Student primary = getPrimary();
		List<Student> students = getAllStudents();

		for (Student student : students) {
			req.primary = Long.toString(primary.getKey().getId());
			req.student = Long.toString(student.getKey().getId());
			doRequest();
		}

		ListAccountsResponse accountResp = new AccountFinder().listAccounts();
		Set<Student> dependents = accountResp.accounts.iterator().next().getDependents();
		
		for (Student dependent : students) {
			assertThat(dependents, hasItem(dependent));
		}
	}

	@Test
	public void testCreateBlankPrimaryFail() {
		generateValidRequest();
		req.primary = "";
		doRequest();

		assertFalse(resp.success);
	}

	@Test
	public void testCreateBlankPrimaryError() {
		generateValidRequest();
		req.primary = "";
		doRequest();

		assertNotNull(resp.errors);
		assertFalse(resp.errors.isEmpty());
	}

	@Test
	public void testCreateNonexistantPrimaryFail() {
		generateValidRequest();
		req.primary = "12345";
		doRequest();

		assertFalse(resp.success);
	}

	@Test
	public void testCreateNonexistantPrimaryError() {
		generateValidRequest();
		req.primary = "12345";
		doRequest();

		assertNotNull(resp.errors);
		assertFalse(resp.errors.isEmpty());
	}

	@Test
	public void testCreateBlankStudentFail() {
		generateValidRequest();
		req.student = "";
		doRequest();

		assertFalse(resp.success);
	}

	@Test
	public void testCreateBlankStudentError() {
		generateValidRequest();
		req.student = "";
		doRequest();

		assertNotNull(resp.errors);
		assertFalse(resp.errors.isEmpty());
	}

	@Test
	public void testCreateNonexistantStudentFail() {
		generateValidRequest();
		req.student = "12345";
		doRequest();

		assertFalse(resp.success);
	}

	@Test
	public void testCreateNonexistantStudentError() {
		generateValidRequest();
		req.student = "12345";
		doRequest();

		assertNotNull(resp.errors);
		assertFalse(resp.errors.isEmpty());
	}
}

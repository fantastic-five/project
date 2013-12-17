package edu.uwm.cs361.fantastic_five.training_tracker.tests.services;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;

import javax.jdo.PersistenceManager;

import org.junit.Before;
import org.junit.Test;

import edu.uwm.cs361.fantastic_five.training_tracker.app.entities.Student;
import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.StudentCreator;
import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.requests.CreateStudentRequest;
import edu.uwm.cs361.fantastic_five.training_tracker.app.services.AccountValidator;
import edu.uwm.cs361.fantastic_five.training_tracker.tests.AppEngineTest;

public class AccountValidatorTest extends AppEngineTest {
	private AccountValidator accountValidator;
	private Map<String, List<String>> errors;

	private String primary;
	private String address;
	private String phone;

	@Before
	public void setUp() {
		accountValidator = new AccountValidator();
		createStudent("Cassie","Dowling","11/23/1992","dowling@uwm.edu");
	}

	private void validate() {
		errors = accountValidator.validate(primary, address, phone);
	}

	private void generateValidParams() {
		primary = Long.toString(getFirstStudent().getKey().getId());
		address = "123 Main St";
		phone = "414-123-1234";
	}

	private void createStudent(String firstName, String lastName, String DOB, String email) {
		CreateStudentRequest req = new CreateStudentRequest();
		req.firstName = firstName;
		req.lastName = lastName;
		req.DOB = DOB;
		req.email = email;
		req.primary = true;

		new StudentCreator().createStudent(req);
	}

	@Test
	public void testValidAccount() {
		generateValidParams();
		validate();

		assertTrue(errors.isEmpty());
	}

	@Test
	public void testBlankPrimary() {
		generateValidParams();
		primary = "";

		validate();

		assertFalse(errors.isEmpty());
		assertNotNull(errors.get("primary"));
		assertFalse(errors.get("primary").isEmpty());
	}

	@Test
	public void testInvalidPrimary() {
		generateValidParams();
		primary = "12345";

		validate();

		assertFalse(errors.isEmpty());
		assertNotNull(errors.get("primary"));
		assertFalse(errors.get("primary").isEmpty());
	}

	@Test
	public void testBlankAddress() {
		generateValidParams();
		address = "";

		validate();

		assertFalse(errors.isEmpty());
		assertNotNull(errors.get("address"));
		assertFalse(errors.get("address").isEmpty());
	}

	@Test
	public void testBlankPhone() {
		generateValidParams();
		phone = "";

		validate();

		assertFalse(errors.isEmpty());
		assertNotNull(errors.get("phone"));
		assertFalse(errors.get("phone").isEmpty());
	}

	@Test
	public void testInvalidPhone() {
		generateValidParams();
		phone = "123-1234";

		validate();

		assertFalse(errors.isEmpty());
		assertNotNull(errors.get("phone"));
		assertFalse(errors.get("phone").isEmpty());
	}

	@SuppressWarnings("unchecked")
	private List<Student> getAllStudents() {
		PersistenceManager pm = getPersistenceManager();
		return (List<Student>) pm.newQuery(Student.class).execute();
	}
	private Student getFirstStudent() {
		List<Student> students = getAllStudents();
		return students.iterator().next();
	}

}

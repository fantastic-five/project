package edu.uwm.cs361.fantastic_five.training_tracker.tests.services;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;

import javax.jdo.PersistenceManager;

import org.junit.Before;
import org.junit.Test;

import edu.uwm.cs361.fantastic_five.training_tracker.app.entities.Student;
import edu.uwm.cs361.fantastic_five.training_tracker.app.services.DependentValidator;
import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.AccountCreator;
import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.StudentCreator;
import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.requests.CreateAccountRequest;
import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.requests.CreateStudentRequest;
import edu.uwm.cs361.fantastic_five.training_tracker.tests.AppEngineTest;

public class DependentValidatorTest extends AppEngineTest {
	private DependentValidator dependentValidator;
	private Map<String, List<String>> errors;

	private String primary;
	private String student;

	@Before
	public void setUp() {
		dependentValidator = new DependentValidator();
		createStudent("Cassie","Dowling","11/23/1992","dowling@uwm.edu",true);
		createAccount(Long.toString(getPrimary().getKey().getId()),"414-123-1234","123 Main St");
		createStudent("Dependent","LastName","11/11/2011","student@uwm.edu",false);
	}

	private void validate() {
		errors = dependentValidator.validate(primary, student);
	}

	private void generateValidParams() {
		primary = Long.toString(getFirstStudent().getKey().getId());
		student = Long.toString(getPrimary().getKey().getId());
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
	
	private void createAccount(String primary, String phone, String address) {
		CreateAccountRequest req = new CreateAccountRequest();
		req.primary = primary;
		req.phone = phone;
		req.address = address;

		new AccountCreator().createAccount(req);
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
	public void testBlankStudent() {
		generateValidParams();
		student = "";

		validate();

		assertFalse(errors.isEmpty());
		assertNotNull(errors.get("dependent"));
		assertFalse(errors.get("dependent").isEmpty());
	}

	@Test
	public void testInvalidStudent() {
		generateValidParams();
		student = "12345";

		validate();

		assertFalse(errors.isEmpty());
		assertNotNull(errors.get("dependent"));
		assertFalse(errors.get("dependent").isEmpty());
	}
	
	@SuppressWarnings("unchecked")
	private List<Student> getAllStudents() {
		PersistenceManager pm = getPersistenceManager();
		return (List<Student>) pm.newQuery(Student.class).execute();
	}
	private Student getPrimary() {
		List<Student> students = getAllStudents();
		for (Student s : students) {
			if (s.isPrimary())
				return s;
		}
		return null;
	}
	private Student getFirstStudent() {
		List<Student> students = getAllStudents();
		for (Student s : students) {
			if (!s.isPrimary())
				return s;
		}
		return null;
	}

}

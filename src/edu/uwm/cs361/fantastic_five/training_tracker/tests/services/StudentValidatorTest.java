package edu.uwm.cs361.fantastic_five.training_tracker.tests.services;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import edu.uwm.cs361.fantastic_five.training_tracker.app.services.StudentValidator;

public class StudentValidatorTest {
	private StudentValidator studentValidator;
	private Map<String, List<String>> errors;

	private String firstName;
	private String lastName;
	private String DOB;
	private String email;
	private boolean primary;

	@Before
	public void setUp() {
		studentValidator = new StudentValidator();
	}

	private void validate() {
		errors = studentValidator.validate(firstName, lastName, DOB, email, primary);
	}

	private void generateValidParams() {
		firstName = "Andrew";
		lastName = "Meyer";
		DOB = "12/12/1990";
		email = "test@example.com";
		primary = true;
	}

	@Test
	public void testValidStudent() {
		generateValidParams();
		validate();

		assertTrue(errors.isEmpty());
	}

	@Test
	public void testBlankFirstName() {
		generateValidParams();
		firstName = "";

		validate();

		assertFalse(errors.isEmpty());
		assertNotNull(errors.get("firstName"));
		assertFalse(errors.get("firstName").isEmpty());
	}

	@Test
	public void testBlankLastName() {
		generateValidParams();
		lastName = "";

		validate();

		assertFalse(errors.isEmpty());
		assertNotNull(errors.get("lastName"));
		assertFalse(errors.get("lastName").isEmpty());
	}

	@Test
	public void testBlankEmail() {
		generateValidParams();
		email = "";

		validate();

		assertFalse(errors.isEmpty());
		assertNotNull(errors.get("email"));
		assertFalse(errors.get("email").isEmpty());
	}

	@Test
	public void testInvalidEmail() {
		generateValidParams();
		email = "asdf";

		validate();

		assertFalse(errors.isEmpty());
		assertNotNull(errors.get("email"));
		assertFalse(errors.get("email").isEmpty());
	}
	
	@Test
	public void testBlankDOB() {
		generateValidParams();
		DOB = "";

		validate();

		assertFalse(errors.isEmpty());
		assertNotNull(errors.get("dob"));
		assertFalse(errors.get("dob").isEmpty());
	}
	
	@Test
	public void testInvalidDOB() {
		generateValidParams();
		DOB = "05/2012";

		validate();

		assertFalse(errors.isEmpty());
		assertNotNull(errors.get("dob"));
		assertFalse(errors.get("dob").isEmpty());
	}
	
}

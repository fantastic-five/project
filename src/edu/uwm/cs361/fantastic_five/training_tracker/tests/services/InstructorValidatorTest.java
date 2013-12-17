package edu.uwm.cs361.fantastic_five.training_tracker.tests.services;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;

import org.junit.*;

import edu.uwm.cs361.fantastic_five.training_tracker.app.services.InstructorValidator;

public class InstructorValidatorTest {

	private InstructorValidator iValidator;
	private Map<String, List<String>> errors;
	
	private String firstName;
	private String lastName;
	private String _username;
	private String _password;
	
	@Before
	public void setUp() {
		iValidator = new InstructorValidator();
	}
	
	//****************************************************
	
	@Test
	public void validInstructorTest() {
		generateValidParameters();
		validate();
		
		assertTrue(errors.isEmpty() );
	} //end validInstructorTest
	
	@Test
	public void blankFirstNameTest() {
		generateValidParameters();
		firstName = "";
		validate();
		
		assertFalse(errors.isEmpty() );
		assertNotNull(errors.get("firstName") );
		assertFalse(errors.get("firstName").isEmpty() );
	} //end blankFirstNameTest
	
	@Test
	public void blankLastNameTest() {
		generateValidParameters();
		lastName = "";
		validate();
		
		assertFalse(errors.isEmpty() );
		assertNotNull(errors.get("lastName") );
		assertFalse(errors.get("lastName").isEmpty() );
	} //end blankLastNameTest
	
	@Test
	public void blank_usernameTest() {
		generateValidParameters();
		_username = "";
		validate();
		
		assertFalse(errors.isEmpty() );
		assertNotNull(errors.get("_username") );
		assertFalse(errors.get("_username").isEmpty() );
	} //end blank_usernameTest
	
	@Test
	public void blank_passwordTest() {
		generateValidParameters();
		_password = "";
		validate();
		
		assertFalse(errors.isEmpty() );
		assertNotNull(errors.get("_password") );
		assertFalse(errors.get("_password").isEmpty() );
	} //end blank_passwordTest
	
	//****************************************************
	
	private void generateValidParameters() {
		firstName = "George";
		lastName = "Washington";
		_username = "george13";
		_password = "whitehouse";
	} //end generateValidParameters
	
	private void validate() {
		errors = iValidator.validate(firstName, lastName, _username, _password);
	} //end validate
	
} //end class

package edu.uwm.cs361.fantastic_five.training_tracker.tests.services;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import edu.uwm.cs361.fantastic_five.training_tracker.app.entities.Instructor;
import edu.uwm.cs361.fantastic_five.training_tracker.app.services.ProgramValidator;

public class ProgramValidatorTest {
	private ProgramValidator programValidator;
	private Map<String, List<String>> errors;

	private String name;
	private Instructor instructor;
	private String price;
	private String start;
	private String end;
	
	@Before
	public void setUp() {
		programValidator = new ProgramValidator();
	}

	private void validate() {
		errors = programValidator.validate(name, instructor, price, start, end);
	}

	private void generateValidParams() {
		name = "Example Program";
		instructor = new Instructor("Cassie","Dowling","cassie","password");
		price = "2.50";
		start = "11/11/1911";
		end = "12/12/1911";
	}

	@Test
	public void testValidProgram() {
		generateValidParams();
		validate();

		assertTrue(errors.isEmpty());
	}

	@Test
	public void testNullInstructor() {
		generateValidParams();
		instructor = null;

		validate();

		assertFalse(errors.isEmpty());
		assertNotNull(errors.get("instructor"));
		assertFalse(errors.get("instructor").isEmpty());
	}

	@Test
	public void testBlankName() {
		generateValidParams();
		name = "";

		validate();

		assertFalse(errors.isEmpty());
		assertNotNull(errors.get("name"));
		assertFalse(errors.get("name").isEmpty());
	}

	@Test
	public void testBlankPrice() {
		generateValidParams();
		price = "";

		validate();

		assertFalse(errors.isEmpty());
		assertNotNull(errors.get("price"));
		assertFalse(errors.get("price").isEmpty());
	}

	@Test
	public void testInvalidPrice() {
		generateValidParams();
		price = "asdf";

		validate();

		assertFalse(errors.isEmpty());
		assertNotNull(errors.get("price"));
		assertFalse(errors.get("price").isEmpty());
	}
	
	@Test
	public void testBlankStart() {
		generateValidParams();
		start = "";

		validate();

		assertFalse(errors.isEmpty());
		assertNotNull(errors.get("date"));
		assertFalse(errors.get("date").isEmpty());
	}

	@Test
	public void testInvalidStart() {
		generateValidParams();
		start = "12/12";

		validate();

		assertFalse(errors.isEmpty());
		assertNotNull(errors.get("date"));
		assertFalse(errors.get("date").isEmpty());
	}
	
	@Test
	public void testBlankEnd() {
		generateValidParams();
		end = "";

		validate();

		assertFalse(errors.isEmpty());
		assertNotNull(errors.get("date"));
		assertFalse(errors.get("date").isEmpty());
	}

	@Test
	public void testInvalidEnd() {
		generateValidParams();
		end = "05/05/1911";

		validate();

		assertFalse(errors.isEmpty());
		assertNotNull(errors.get("date"));
		assertFalse(errors.get("date").isEmpty());
	}
}

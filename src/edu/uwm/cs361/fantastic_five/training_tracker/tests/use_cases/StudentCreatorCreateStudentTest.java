package edu.uwm.cs361.fantastic_five.training_tracker.tests.use_cases;

import static org.junit.Assert.*;

import java.util.List;

import javax.jdo.PersistenceManager;

import org.junit.Before;
import org.junit.Test;

import edu.uwm.cs361.fantastic_five.training_tracker.app.entities.Student;
import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.StudentCreator;
import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.requests.CreateStudentRequest;
import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.responses.CreateStudentResponse;

import edu.uwm.cs361.fantastic_five.training_tracker.tests.AppEngineTest;

public class StudentCreatorCreateStudentTest extends AppEngineTest {
	private StudentCreator studentCreator;
	private CreateStudentRequest req;
	private CreateStudentResponse resp;

	@Before
	public void setUp() {
		studentCreator = new StudentCreator();
		req = new CreateStudentRequest();
	}

	private void doRequest() {
		resp = studentCreator.createStudent(req);
	}

	private void generateValidRequest() {
		req.firstName = "Andrew";
		req.lastName = "Meyer";
		req.primary = true;
		req.DOB = "11/11/1911";
		req.email = "andrew@example.com";
	}

	@Test
	public void testCreateValidStudentSuccess() {
		generateValidRequest();

		doRequest();

		assertTrue(resp.success);
		assertTrue(resp.errors.isEmpty());
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

	@Test
	public void testStudentCreated() {
		generateValidRequest();
		doRequest();

		assertTrue(getAllStudents().iterator().hasNext());
	}

	@Test
	public void testStudentCreatedCorrectName() {
		generateValidRequest();
		req.firstName = "Andrew";
		doRequest();

		assertEquals("Andrew", getFirstStudent().getFirstName());
	}

	@Test
	public void testStudentCreatedCorrectLastName() {
		generateValidRequest();
		req.lastName = "Meyer";
		doRequest();

		assertEquals("Meyer", getFirstStudent().getLastName());
	}

	@Test
	public void testStudentCreatedCorrectEmail() {
		generateValidRequest();
		req.email = "andrew@example.com";
		doRequest();

		assertEquals("andrew@example.com", getFirstStudent().get_email());
	}
	
	@Test
	public void testStudentCreatedCorrectDOB() {
		generateValidRequest();
		req.email = "andrew@example.com";
		doRequest();

		assertEquals("11/11/1911", getFirstStudent().getDOB());
	}
	
	@Test
	public void testCreateStudentWithBlankFirstName() {
		generateValidRequest();
		req.firstName = "";

		doRequest();

		assertFalse(resp.success);
		assertFalse(resp.errors.isEmpty());
		assertNotNull(resp.errors.get("firstName"));
		assertFalse(resp.errors.get("firstName").isEmpty());
	}

	@Test
	public void testCreateStudentWithBlankLastName() {
		generateValidRequest();
		req.lastName = "";

		doRequest();

		assertFalse(resp.success);
		assertFalse(resp.errors.isEmpty());
		assertNotNull(resp.errors.get("lastName"));
		assertFalse(resp.errors.get("lastName").isEmpty());
	}

	@Test
	public void testCreateStudentWithBlankEmail() {
		generateValidRequest();
		req.email = "";

		doRequest();

		assertFalse(resp.success);
		assertFalse(resp.errors.isEmpty());
		assertNotNull(resp.errors.get("email"));
		assertFalse(resp.errors.get("email").isEmpty());
	}

	@Test
	public void testCreateStudentWithInvalidEmail() {
		generateValidRequest();
		req.email = "asdf";

		doRequest();

		assertFalse(resp.success);
		assertFalse(resp.errors.isEmpty());
		assertNotNull(resp.errors.get("email"));
		assertFalse(resp.errors.get("email").isEmpty());
	}
	
	@Test
	public void testCreateStudentWithBlankDOB() {
		generateValidRequest();
		req.DOB = "";

		doRequest();

		assertFalse(resp.success);
		assertFalse(resp.errors.isEmpty());
		assertNotNull(resp.errors.get("dob"));
		assertFalse(resp.errors.get("dob").isEmpty());
	}
	
	@Test
	public void testCreateStudentWithInvalidDOB() {
		generateValidRequest();
		req.DOB = "1/2/1234";

		doRequest();

		assertFalse(resp.success);
		assertFalse(resp.errors.isEmpty());
		assertNotNull(resp.errors.get("dob"));
		assertFalse(resp.errors.get("dob").isEmpty());
	}
	
	@Test
	public void testInvalidStudentNotCreated() {
		generateValidRequest();
		req.firstName = "";

		doRequest();

		assertFalse(getAllStudents().iterator().hasNext());
	}
}

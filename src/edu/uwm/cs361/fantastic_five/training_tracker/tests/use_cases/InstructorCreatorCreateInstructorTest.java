package edu.uwm.cs361.fantastic_five.training_tracker.tests.use_cases;

import static org.junit.Assert.*;

import java.util.List;

import javax.jdo.PersistenceManager;

import edu.uwm.cs361.fantastic_five.training_tracker.app.entities.Instructor;
import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.InstructorCreator;
import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.requests.CreateInstructorRequest;
import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.responses.CreateInstructorResponse;
import edu.uwm.cs361.fantastic_five.training_tracker.tests.AppEngineTest;

import org.junit.*;

public class InstructorCreatorCreateInstructorTest extends AppEngineTest{

	private InstructorCreator iCreator;
	private CreateInstructorRequest req;
	private CreateInstructorResponse resp;

	//****************************************************
	@Before
	public void setUp() {
		iCreator = new InstructorCreator();
		req = new CreateInstructorRequest();
	} //end setUp

	//****************************************************
	@Test
	public void createValidInstructorTest() {
		generateValidRequest();
		doRequest();

		assertTrue(resp.success);
		assertTrue(resp.errors.isEmpty() );
	} //end createValidInstructorTest

	@Test
	public void instructorCreatedTest() {
		generateValidRequest();
		doRequest();

		assertTrue(getInstructorList().iterator().hasNext() );
	} //end createStudentTest

	@Test
	public void instructorCreatedFirstNameTest() {
		generateValidRequest();
		req.firstName = "Abraham";
		doRequest();

		assertEquals("Abraham", getFirstInstructor().getFirstName() );
	} //end instructorCreatedFirstNameTest

	@Test
	public void instructorCreatedLastNameTest() {
		generateValidRequest();
		req.lastName = "Lincoln";
		doRequest();

		assertEquals("Lincoln", getFirstInstructor().getLastName() );
	} //end instructorCreatedLastNameTest

	@Test
	public void instructorCreatedUsernameTest() {
		generateValidRequest();
		req.username = "lincoln14";
		doRequest();

		assertEquals("lincoln14", getFirstInstructor().getUsername() );
	} //end instructorCreatedUsernameTest

	@Test
	public void instructorCreatedPasswordTest() {
		generateValidRequest();
		req.password = "cabin3";
		doRequest();

		assertEquals("cabin3", getFirstInstructor().getPassword() );
	} //end instructorCreatedPasswordTest

	@Test
	public void instructorCreatedWithBlankFirstNameTest() {
		generateValidRequest();
		req.firstName = "";
		doRequest();

		assertFalse(resp.success);
		assertFalse(resp.errors.isEmpty() );
		assertNotNull(resp.errors.get("firstName") );
		assertFalse(resp.errors.get("firstName").isEmpty() );
	} //end instructorCreatedWithBlankFirstNameTest

	@Test
	public void instructorCreatedWithBlankLastNameTest() {
		generateValidRequest();
		req.lastName = "";
		doRequest();

		assertFalse(resp.success);
		assertFalse(resp.errors.isEmpty() );
		assertNotNull(resp.errors.get("lastName") );
		assertFalse(resp.errors.get("lastName").isEmpty() );
	} //end instructorCreatedWithBlankLastNameTest

	@Test
	public void instructorCreatedWithBlankUsernameTest() {
		generateValidRequest();
		req.username = "";
		doRequest();

		assertFalse(resp.success);
		assertFalse(resp.errors.isEmpty() );
		assertNotNull(resp.errors.get("_username") );
		assertFalse(resp.errors.get("_username").isEmpty() );
	} //end instructorCreatedWithBlankUsernameTest

	@Test
	public void instructorCreatedWithBlankPasswordTest() {
		generateValidRequest();
		req.password = "";
		doRequest();

		assertFalse(resp.success);
		assertFalse(resp.errors.isEmpty() );
		assertNotNull(resp.errors.get("_password") );
		assertFalse(resp.errors.get("_password").isEmpty() );
	} //end instructorCreatedWithBlankPasswordTest

	@Test
	public void invalidStudentNotCreatedTest() {
		generateValidRequest();
		req.firstName = "";
		doRequest();

		assertFalse(getInstructorList().iterator().hasNext() );
	} //end invalidStudentNotCreatedTest

	//****************************************************
	private void doRequest() {
		resp = iCreator.createInstructor(req);
	} //end doRequest

	private void generateValidRequest() {
		req.firstName = "George";
		req.lastName = "Washington";
		req.username = "George13";
		req.password = "whitehouse";
	} //end generateValidRequest

	@SuppressWarnings("unchecked")
	private List<Instructor> getInstructorList() {
		PersistenceManager pm = getPersistenceManager();

		return (List<Instructor>) pm.newQuery(Instructor.class).execute();
	} //end getInstructorList

	private Instructor getFirstInstructor() {
		List<Instructor> instructors = getInstructorList();
		return instructors.iterator().next();
	} //end getFirstInstructor

} //end class

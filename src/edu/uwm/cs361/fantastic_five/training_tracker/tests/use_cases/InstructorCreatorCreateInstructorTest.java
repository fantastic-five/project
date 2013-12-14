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
	}
	
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
	}
	
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

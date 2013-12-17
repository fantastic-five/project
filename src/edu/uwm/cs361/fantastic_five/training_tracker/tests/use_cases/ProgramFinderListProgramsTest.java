package edu.uwm.cs361.fantastic_five.training_tracker.tests.use_cases;

import static org.junit.Assert.*;

import java.util.Iterator;

import javax.jdo.PersistenceManager;

import org.junit.Before;
import org.junit.Test;

import edu.uwm.cs361.fantastic_five.training_tracker.app.entities.Instructor;
import edu.uwm.cs361.fantastic_five.training_tracker.app.entities.Program;
import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.ProgramCreator;
import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.ProgramFinder;
import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.requests.CreateProgramRequest;
import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.requests.ListProgramsRequest;
import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.responses.ListProgramsResponse;
import edu.uwm.cs361.fantastic_five.training_tracker.tests.AppEngineTest;

public class ProgramFinderListProgramsTest extends AppEngineTest {
	ProgramFinder programFinder;
	ListProgramsRequest req;
	ListProgramsResponse resp;
	Instructor instructor;
	Instructor instructor2;

	@Before
	public void setUp() {
		PersistenceManager pm = getPersistenceManager();
		programFinder = new ProgramFinder();
		req = new ListProgramsRequest();
		
		instructor = new Instructor("Cassie","Dowling","cassie","password");
		pm.makePersistent(instructor);
		instructor2 = new Instructor("FirstName","LastName","user","password");
		pm.makePersistent(instructor2);
	}

	private void doRequest() {
		resp = programFinder.listPrograms(req);
	}

	private void createProgram(String name, Instructor instructor, String price, String start, String end) {
		CreateProgramRequest req = new CreateProgramRequest();
		req.name = name;
		req.instructor = Long.toString(instructor.getKey().getId());
		req.price = price;
		req.startDate = start;
		req.endDate = end;
		
		new ProgramCreator().createProgram(req);
	}

	private void createPrograms() {
		createProgram("Example Program", instructor, "2.60","11/11/2013","11/12/2013");
		createProgram("Example Program 2", instructor2, "7.20","12/12/2013","12/21/2013");
	}

	@Test
	public void testListProgramsNotEmpty() {
		createPrograms();
		doRequest();

		assertFalse(resp.programs.isEmpty());
	}

	@Test
	public void testListProgramsCount() {
		createPrograms();
		doRequest();

		Iterator<Program> iterator = resp.programs.iterator();
		for (int i = 0; i < 2; ++i) {
			assertTrue(iterator.next() != null);
		}
	}

	@Test
	public void testListEntriesCorrect() {
		createPrograms();
		doRequest();

		Iterator<Program> iterator = resp.programs.iterator();
		for (int i = 0; i < 2; ++i) {
			Program program = iterator.next();

			assertTrue(program.getName().equals("Example Program") || program.getName().equals("Example Program 2"));

			if (program.getName().equals("Example Program")) {
				assertEquals("Cassie Dowling", program.getInstructor().getFullName());
				assertEquals(2.60, program.getPrice(), 0.001);
			} else { // program.getName().equals("Example Program 2")
				assertEquals("FirstName LastName", program.getInstructor().getFullName());
				assertEquals(7.20, program.getPrice(), 0.001);
			}
		}
	}

	@Test
	public void testListProgramsWithNoPrograms() {
		doRequest();

		assertTrue(resp.programs.isEmpty());
	}

}

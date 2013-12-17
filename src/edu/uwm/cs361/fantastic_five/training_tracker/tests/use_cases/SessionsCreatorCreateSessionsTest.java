package edu.uwm.cs361.fantastic_five.training_tracker.tests.use_cases;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.jdo.PersistenceManager;

import org.junit.Before;
import org.junit.Test;

import edu.uwm.cs361.fantastic_five.training_tracker.app.entities.Instructor;
import edu.uwm.cs361.fantastic_five.training_tracker.app.entities.Program;
import edu.uwm.cs361.fantastic_five.training_tracker.app.entities.Session;
import edu.uwm.cs361.fantastic_five.training_tracker.app.entities.Time;
import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.SessionsCreator;
import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.requests.CreateSessionsRequest;
import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.responses.CreateSessionsResponse;
import edu.uwm.cs361.fantastic_five.training_tracker.tests.AppEngineTest;

public class SessionsCreatorCreateSessionsTest extends AppEngineTest {
	private SessionsCreator sessionsCreator;
	private CreateSessionsRequest req;
	private CreateSessionsResponse resp;

	@Before
	public void setUp() {
		sessionsCreator = new SessionsCreator();
		req = new CreateSessionsRequest();
	}

	private void doRequest() {
		resp = sessionsCreator.createSessions(req);
	}

	private void generateValidRequest() {
		PersistenceManager pm = getPersistenceManager();
		List<Time> times = new ArrayList<Time>();
		times.add(new Time("Monday",1,"1pm","2pm"));
		Program program = new Program("Program",new Instructor("Cassie", "Dowling", "cassie", "password"),35,times,"12/02/2012","12/22/2012");

		pm.makePersistent(program);
		req.programId = Long.toString(program.getKey().getId());
	}

	@Test
	public void testCreateValidSessionsSuccess() {
		generateValidRequest();

		doRequest();

		assertTrue(resp.success);
		assertNull(resp.errors);
	}

	@SuppressWarnings("unchecked")
	private List<Program> getAllPrograms() {
		PersistenceManager pm = getPersistenceManager();
		return (List<Program>) pm.newQuery(Program.class).execute();
	}

	private Program getFirstProgram() {
		List<Program> programs = getAllPrograms();
		return programs.iterator().next();
	}

	@SuppressWarnings("unchecked")
	private List<Session> getAllSessions() {
		PersistenceManager pm = getPersistenceManager();
		return (List<Session>) pm.newQuery(Session.class).execute();
	}

	@Test
	public void testSessionsCreated() {
		generateValidRequest();
		doRequest();

		assertTrue(getAllPrograms().iterator().hasNext());
	}

	@Test
	public void testSessionsCreatedCorrectDates() {
		generateValidRequest();
		doRequest();

		Iterator<Session> it = getFirstProgram().getSessions().iterator();
		assertEquals("12/2/2012", it.next().getDate());
		assertEquals("12/9/2012", it.next().getDate());
		assertEquals("12/16/2012", it.next().getDate());
		assertFalse(it.hasNext());
	}

	@Test
	public void testInvalidSessionNotCreated() {
		generateValidRequest();
		req.programId = "123";

		doRequest();

		assertFalse(getAllSessions().iterator().hasNext());
	}

	@Test
	public void testCreateSessionsWithBlankProgramId() {
		generateValidRequest();
		req.programId = "";

		doRequest();

		assertFalse(resp.success);
		assertNotNull(resp.errors);
	}

	@Test
	public void testCreateSessionsWithInvalidProgramId() {
		generateValidRequest();
		req.programId = "123";

		doRequest();

		assertFalse(resp.success);
		assertNotNull(resp.errors);
	}
}
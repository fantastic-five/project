package edu.uwm.cs361.fantastic_five.training_tracker.tests.use_cases;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.util.Iterator;
import java.util.List;

import javax.jdo.PersistenceManager;

import org.junit.Before;
import org.junit.Test;

import edu.uwm.cs361.fantastic_five.training_tracker.app.entities.Session;
import edu.uwm.cs361.fantastic_five.training_tracker.app.entities.Student;
import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.AttendanceTaker;
import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.StudentCreator;
import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.requests.AttendanceRequest;
import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.requests.CreateStudentRequest;
import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.responses.AttendanceResponse;
import edu.uwm.cs361.fantastic_five.training_tracker.tests.AppEngineTest;

public class AttendanceTakerTakeAttendanceTest extends AppEngineTest {
	private AttendanceTaker attendanceTaker;
	private AttendanceRequest req;
	private AttendanceResponse resp;
	private Session session;
	
	@Before
	public void setUp() {
		PersistenceManager pm = getPersistenceManager();
		attendanceTaker = new AttendanceTaker();
		req = new AttendanceRequest();
	
		session = new Session("Monday",2013,12,16);
		pm.makePersistent(session);
	
		createStudent("Andrew", "Meyer", "01/01/2001","andrew@example.com");
		createStudent("Charlie", "Liberski", "02/02/2002", "charlie@example.com");
	}

	private void generateValidRequest() {
		req.ids = new String[1];
		req.ids[0] = Long.toString(getFirstStudent().getKey().getId());
		req.sessionId = Long.toString(session.getKey().getId());
	}

	private void doRequest() {
		resp = attendanceTaker.takeAttendance(req);
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
	public void testAttendanceSuccess() {
		generateValidRequest();
		doRequest();

		assertTrue(resp.success);
	}

	@Test
	public void testAttendanceNoError() {
		generateValidRequest();
		doRequest();

		assertNull(resp.error);
	}

	@Test
	public void testAttendance() {
		PersistenceManager pm = getPersistenceManager();
		Student student = getFirstStudent();
		
		req.ids = new String[1];
		req.sessionId = Long.toString(session.getKey().getId());
		req.ids[0] = Long.toString(student.getKey().getId());

		doRequest();
		
		Session session2 = pm.getObjectById(Session.class,session.getKey().getId());
		assertEquals(1,session2.getStudents().size());
		Student s = session2.getStudents().iterator().next();
		assertEquals(student.getKey().getId(), s.getKey().getId());
	}

	@Test
	public void testMultipleAttendance() {
		PersistenceManager pm = getPersistenceManager();
		List<Student> students = getAllStudents();
		Iterator<Student> it = students.iterator();
		
		req.ids = new String[2];
		req.sessionId = Long.toString(session.getKey().getId());
		req.ids[0] = Long.toString(it.next().getKey().getId());
		req.ids[1] = Long.toString(it.next().getKey().getId());
		doRequest();
		
		Session session2 = pm.getObjectById(Session.class,session.getKey().getId());
		assertEquals(2,session2.getStudents().size());
		
		for (Student s : students) {
			assertThat(session2.getStudents(), hasItem(s));
		}
	}
	
	@Test
	public void testAttendanceBlankSessionIdFail() {
		generateValidRequest();
		req.sessionId = "";
		doRequest();

		assertFalse(resp.success);
	}

	@Test
	public void testAddBlankSessionIdError() {
		generateValidRequest();
		req.sessionId = "";
		doRequest();

		assertNotNull(resp.error);
		assertFalse(resp.error.isEmpty());
	}

	@Test
	public void testAttendanceNonexistantSessionIdFail() {
		generateValidRequest();
		req.sessionId = "12345";
		doRequest();

		assertFalse(resp.success);
	}

	@Test
	public void testAttendanceNonexistantSessionIdError() {
		generateValidRequest();
		req.sessionId = "12345";
		doRequest();

		assertNotNull(resp.error);
		assertFalse(resp.error.isEmpty());
	}

	@Test
	public void testAttendanceBlankStudentIdFail() {
		generateValidRequest();
		req.ids[0] = "";
		doRequest();

		assertFalse(resp.success);
	}

	@Test
	public void testAttendanceBlankStudentIdError() {
		generateValidRequest();
		req.ids[0] = "";
		doRequest();

		assertNotNull(resp.error);
		assertFalse(resp.error.isEmpty());
	}
	
	@Test
	public void testAttendanceInvalidStudentIdFail() {
		generateValidRequest();
		req.ids[0] = "12345";
		doRequest();

		assertFalse(resp.success);
	}

	@Test
	public void testAttendanceInvalidStudentIdError() {
		generateValidRequest();
		req.ids[0] = "12345";
		doRequest();

		assertNotNull(resp.error);
		assertFalse(resp.error.isEmpty());
	}
	
	@Test
	public void testAttendanceNoStudentIdSuccess() {
		generateValidRequest();
		req.ids = null;
		doRequest();

		assertTrue(resp.success);
	}

	@Test
	public void testAttendanceNoStudentIdNoError() {
		generateValidRequest();
		req.ids = null;
		doRequest();

		assertNull(resp.error);
	}
}

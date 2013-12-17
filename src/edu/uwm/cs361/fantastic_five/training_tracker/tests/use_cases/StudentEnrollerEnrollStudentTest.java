package edu.uwm.cs361.fantastic_five.training_tracker.tests.use_cases;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.util.List;

import javax.jdo.PersistenceManager;

import org.junit.Before;
import org.junit.Test;

import edu.uwm.cs361.fantastic_five.training_tracker.app.entities.Instructor;
import edu.uwm.cs361.fantastic_five.training_tracker.app.entities.Program;
import edu.uwm.cs361.fantastic_five.training_tracker.app.entities.Student;
import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.ProgramCreator;
import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.ProgramViewer;
import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.StudentCreator;
import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.StudentEnroller;
import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.requests.CreateProgramRequest;
import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.requests.CreateStudentRequest;
import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.requests.EnrollStudentRequest;
import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.requests.ViewProgramRequest;
import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.responses.EnrollStudentResponse;
import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.responses.ViewProgramResponse;
import edu.uwm.cs361.fantastic_five.training_tracker.tests.AppEngineTest;

public class StudentEnrollerEnrollStudentTest extends AppEngineTest {
	private StudentEnroller studentEnroller;
	private EnrollStudentRequest req;
	private EnrollStudentResponse resp;

	@Before
	public void setUp() {
		PersistenceManager pm = getPersistenceManager();
		studentEnroller = new StudentEnroller();
		req = new EnrollStudentRequest();
		Instructor instructor = new Instructor("Cassie","Dowling","cassie","password");
		pm.makePersistent(instructor);
		
		createProgram("Example Program", instructor, "2.60","11/11/2013","11/18/2013");
		createStudent("Andrew", "Meyer", "01/01/2001","andrew@example.com");
		createStudent("Charlie", "Liberski", "02/02/2002", "charlie@example.com");
	}

	private void generateValidRequest() {
		req.programId = Long.toString(getFirstProgram().getKey().getId());
		req.studentId = Long.toString(getFirstStudent().getKey().getId());
	}

	private void doRequest() {
		resp = studentEnroller.enrollStudent(req);
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
	private List<Program> getAllPrograms() {
		PersistenceManager pm = getPersistenceManager();
		return (List<Program>) pm.newQuery(Program.class).execute();
	}
	private Program getFirstProgram() {
		List<Program> programs = getAllPrograms();
		return programs.iterator().next();
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
	public void testEnrollSuccess() {
		generateValidRequest();
		doRequest();

		assertTrue(resp.success);
	}

	@Test
	public void testEnrollNoError() {
		generateValidRequest();
		doRequest();

		assertNull(resp.error);
	}

	@Test
	public void testStudentEnrolled() {
		Program program = getFirstProgram();
		Student student = getFirstStudent();

		req.programId = Long.toString(program.getKey().getId());
		req.studentId = Long.toString(student.getKey().getId());

		doRequest();

		ViewProgramRequest programReq = new ViewProgramRequest();
		programReq.id = Long.toString(program.getKey().getId());
		ViewProgramResponse programResp = new ProgramViewer().viewProgram(programReq);

		Student enrolledStudent = programResp.students.iterator().next();

		assertEquals(student.getKey().getId(), enrolledStudent.getKey().getId());
	}

	@Test
	public void testEnrollMultipleStudents() {
		Program program = getFirstProgram();
		List<Student> students = getAllStudents();

		for (Student student : students) {
			req.programId = Long.toString(program.getKey().getId());
			req.studentId = Long.toString(student.getKey().getId());
			doRequest();
		}

		ViewProgramRequest programReq = new ViewProgramRequest();
		programReq.id = Long.toString(program.getKey().getId());
		ViewProgramResponse programResp = new ProgramViewer().viewProgram(programReq);

		for (Student enrolledStudent : students) {
			assertThat(programResp.students, hasItem(enrolledStudent));
		}
	}

	@Test
	public void testEnrollBlankProgramIdFail() {
		generateValidRequest();
		req.programId = "";
		doRequest();

		assertFalse(resp.success);
	}

	@Test
	public void testEnrollBlankProgramIdError() {
		generateValidRequest();
		req.programId = "";
		doRequest();

		assertNotNull(resp.error);
		assertFalse(resp.error.isEmpty());
	}

	@Test
	public void testEnrollNonexistantProgramIdFail() {
		generateValidRequest();
		req.programId = "12345";
		doRequest();

		assertFalse(resp.success);
	}

	@Test
	public void testEnrollNonexistantProgramIdError() {
		generateValidRequest();
		req.programId = "12345";
		doRequest();

		assertNotNull(resp.error);
		assertFalse(resp.error.isEmpty());
	}

	@Test
	public void testEnrollBlankStudentIdFail() {
		generateValidRequest();
		req.studentId = "";
		doRequest();

		assertFalse(resp.success);
	}

	@Test
	public void testEnrollBlankStudentIdError() {
		generateValidRequest();
		req.studentId = "";
		doRequest();

		assertNotNull(resp.error);
		assertFalse(resp.error.isEmpty());
	}

	@Test
	public void testEnrollNonexistantStudentIdFail() {
		generateValidRequest();
		req.studentId = "12345";
		doRequest();

		assertFalse(resp.success);
	}

	@Test
	public void testEnrollNonexistantStudentIdError() {
		generateValidRequest();
		req.studentId = "12345";
		doRequest();

		assertNotNull(resp.error);
		assertFalse(resp.error.isEmpty());
	}
}

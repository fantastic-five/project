package edu.uwm.cs361.fantastic_five.training_tracker.tests.use_cases;

import static org.junit.Assert.*;

import java.util.Iterator;
import java.util.List;

import javax.jdo.PersistenceManager;

import org.junit.Before;
import org.junit.Test;

import edu.uwm.cs361.fantastic_five.training_tracker.app.entities.Instructor;
import edu.uwm.cs361.fantastic_five.training_tracker.app.entities.Program;
import edu.uwm.cs361.fantastic_five.training_tracker.app.entities.Student;
import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.InstructorCreator;
import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.ProgramCreator;
import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.ProgramIncomeViewer;
import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.StudentCreator;
import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.StudentEnroller;
import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.requests.CreateInstructorRequest;
import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.requests.CreateProgramRequest;
import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.requests.CreateStudentRequest;
import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.requests.EnrollStudentRequest;
import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.requests.ViewProgramIncomeRequest;
import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.responses.ViewProgramIncomeResponse;
import edu.uwm.cs361.fantastic_five.training_tracker.tests.AppEngineTest;

public class ProgramIncomeViewerTest extends AppEngineTest {
	ProgramIncomeViewer programIncomeViewer;
	ViewProgramIncomeRequest req;
	ViewProgramIncomeResponse resp;

	@Before
	public void setUp() {
		programIncomeViewer = new ProgramIncomeViewer();
		req = new ViewProgramIncomeRequest();
	}

	private void doRequest() {
		resp = programIncomeViewer.viewProgramIncome(req);
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
	
	private void createInstructor(String firstName, String lastName, String username, String password) {
		CreateInstructorRequest req = new CreateInstructorRequest();
		req.firstName = firstName;
		req.lastName = lastName;
		req.username = username;
		req.password = password;
		
		new InstructorCreator().createInstructor(req);
	}
	
	private void enrollStudent(Program program, Student student) {
		String programId = Long.toString(program.getKey().getId());
		String studentId = Long.toString(student.getKey().getId());

		EnrollStudentRequest req = new EnrollStudentRequest();
		req.programId = programId;
		req.studentId = studentId;

		new StudentEnroller().enrollStudent(req);
	}

	private void createPrograms() {
		createInstructor("Andrew","Meyer","andrew","password");
		createProgram("Example Program", getFirstInstructor() , "2.60","11/11/2013","12/12/2013");
		createProgram("Example Program 2", getFirstInstructor(), "7.20","12/12/2013","01/01/2014");
	}
	private void createStudents() {
		createStudent("Andrew", "Meyer", "01/01/2001", "andrew@example.com");
		createStudent("Charlie", "Liberski", "02/02/2002", "charlie@example.com");
	}
	private void enrollStudentsInPrograms() {
		for (Program program : getAllPrograms()) {
			if (program.getName().equals("Example Program")) {
				Student student = getFirstStudent();
				enrollStudent(program, student);
			} else if (program.getName().equals("Example Program 2")) {
				for (Student student : getAllStudents()) {
					enrollStudent(program, student);
				}
			}
		}
	}


	@SuppressWarnings("unchecked")
	private List<Program> getAllPrograms() {
		PersistenceManager pm = getPersistenceManager();
		return (List<Program>) pm.newQuery(Program.class).execute();
	}
//	private Program getFirstProgram() {
//		List<Program> programs = getAllPrograms();
//		return programs.iterator().next();
//	}

	@SuppressWarnings("unchecked")
	private List<Student> getAllStudents() {
		PersistenceManager pm = getPersistenceManager();
		return (List<Student>) pm.newQuery(Student.class).execute();
	}
	private Student getFirstStudent() {
		List<Student> students = getAllStudents();
		return students.iterator().next();
	}

	@SuppressWarnings("unchecked")
	private List<Instructor> getAllInstructors() {
		PersistenceManager pm = getPersistenceManager();
		return (List<Instructor>) pm.newQuery(Instructor.class).execute();
	}
	private Instructor getFirstInstructor() {
		List<Instructor> instructors = getAllInstructors();
		return instructors.iterator().next();
	}
	
	@Test
	public void testViewProgramIncomeProgramsNotEmpty() {
		createPrograms();
		doRequest();

		assertFalse(resp.programs.isEmpty());
	}

	@Test
	public void testViewProgramIncomeProgramCount() {
		createPrograms();
		doRequest();

		Iterator<Program> iterator = resp.programs.iterator();
		for (int i = 0; i < 2; ++i) {
			assertTrue(iterator.next() != null);
		}
	}

	@Test
	public void testViewProgramIncomeEntriesCorrect() {
		createPrograms();
		createStudents();
		enrollStudentsInPrograms();
		doRequest();

		Iterator<Program> iterator = resp.programs.iterator();
		for (int i = 0; i < 2; ++i) {
			Program program = iterator.next();

			assertTrue(program.getName().equals("Example Program") || program.getName().equals("Example Program 2"));

			if (program.getName().equals("Example Program")) {
				assertEquals("Andrew Meyer", program.getInstructor().getFullName());
				assertEquals(2.60, program.getPrice(), 0.001);
				assertEquals(2.60, program.getRevenue(), 0.001);
			} else { // program.getName().equals("Example Program 2")
				assertEquals("Andrew Meyer", program.getInstructor().getFullName());
				assertEquals(7.20, program.getPrice(), 0.001);
				assertEquals(14.40, program.getRevenue(), 0.001);
			}
		}
	}

	@Test
	public void testViewProgramIncomeWithNoPrograms() {
		doRequest();

		assertTrue(resp.programs.isEmpty());
	}

}

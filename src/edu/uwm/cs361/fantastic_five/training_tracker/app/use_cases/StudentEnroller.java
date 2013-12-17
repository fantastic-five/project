package edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import edu.uwm.cs361.fantastic_five.training_tracker.app.entities.Program;
import edu.uwm.cs361.fantastic_five.training_tracker.app.entities.Student;
import edu.uwm.cs361.fantastic_five.training_tracker.app.services.PersistenceService;
import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.requests.EnrollStudentRequest;
import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.requests.ListUnenrolledStudentsRequest;
import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.responses.EnrollStudentResponse;
import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.responses.ListUnenrolledStudentsResponse;

public class StudentEnroller {
	@SuppressWarnings("unchecked")
	public ListUnenrolledStudentsResponse listUnenrolledStudents(ListUnenrolledStudentsRequest req) {
		ListUnenrolledStudentsResponse resp = new ListUnenrolledStudentsResponse();

		PersistenceManager pm = getPersistenceManager();
		try {
			Query q = pm.newQuery(Student.class);
			List<Student> allStudents = (List<Student>) q.execute();

			long programId;
			try {
				programId = Long.parseLong(req.programId);
			} catch (NumberFormatException ex) {
				return resp;
			}

			Program program;
			try {
				program = pm.getObjectById(Program.class, programId);
			} catch (JDOObjectNotFoundException ex) {
				return resp;
			}
			
			// Quick hack: Pre-load times so that they're there when the view
			// tries to access them.
			program.getTimes();

			ArrayList<Student> unenrolledStudents = new ArrayList<Student>(allStudents);
			unenrolledStudents.removeAll(program.getStudents());

			resp.program = program;
			resp.unenrolledStudents = unenrolledStudents;
		} finally {
			pm.close();
		}

		return resp;
	}

	public EnrollStudentResponse enrollStudent(EnrollStudentRequest req) {
		EnrollStudentResponse resp = new EnrollStudentResponse();

		PersistenceManager pm = getPersistenceManager();

		try {
			if (req.studentId == null) {
				resp.success = false;
				resp.error = "No student selected";
			} else {
				long studentId;
				long programId;

				try {
					studentId = Long.parseLong(req.studentId);
				} catch (NumberFormatException ex) {
					resp.success = false;
					resp.error = "Invalid student id.";

					return resp;
				}
				try {
					programId = Long.parseLong(req.programId);
				} catch (NumberFormatException ex) {
					resp.success = false;
					resp.error = "Invalid program id.";

					return resp;
				}

				Program program;
				Student student;

				try {
					program = pm.getObjectById(Program.class, programId);
				} catch (JDOObjectNotFoundException ex) {
					resp.success = false;
					resp.error = "The specified program does not exist.";

					return resp;
				}
				try {
					student = pm.getObjectById(Student.class, studentId);
				} catch (JDOObjectNotFoundException ex) {
					resp.success = false;
					resp.error = "The specified student does not exist.";

					return resp;
				}

				program.addStudent(student);
				student.addProgram(program);
				resp.success = true;
			}
		} finally {
			pm.close();
		}

		return resp;
	}

	private PersistenceManager getPersistenceManager()
	{
		return PersistenceService.getPersistenceManager();
	}
}

package edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases;

import java.util.List;

import javax.jdo.PersistenceManager;

import edu.uwm.cs361.fantastic_five.training_tracker.app.entities.Student;
import edu.uwm.cs361.fantastic_five.training_tracker.app.services.PersistenceService;
import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.requests.ListStudentsRequest;
import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.responses.ListStudentsResponse;

public class StudentFinder {
	@SuppressWarnings("unchecked")
	public ListStudentsResponse listStudents(ListStudentsRequest req) {
		ListStudentsResponse resp = new ListStudentsResponse();

		PersistenceManager pm = getPersistenceManager();

		resp.students = (List<Student>) pm.newQuery(Student.class).execute();

		return resp;
	}

	public ListStudentsResponse listStudents() {
		return listStudents(new ListStudentsRequest());
	}

	private PersistenceManager getPersistenceManager()
	{
		return PersistenceService.getPersistenceManager();
	}
}

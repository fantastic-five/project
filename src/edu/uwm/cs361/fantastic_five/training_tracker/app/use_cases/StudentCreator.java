package edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases;

import javax.jdo.PersistenceManager;

import edu.uwm.cs361.fantastic_five.training_tracker.app.entities.Student;
import edu.uwm.cs361.fantastic_five.training_tracker.app.services.PersistenceService;
import edu.uwm.cs361.fantastic_five.training_tracker.app.services.StudentValidator;
import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.requests.CreateStudentRequest;
import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.responses.CreateStudentResponse;

public class StudentCreator {
	public CreateStudentResponse createStudent(CreateStudentRequest req) {
		PersistenceManager pm = getPersistenceManager();
		CreateStudentResponse resp = new CreateStudentResponse();

		resp.errors = new StudentValidator().validate(req.firstName, req.lastName, req.DOB, req.email, req.primary);
		if (!resp.errors.isEmpty()) {
			resp.success = false;
			return resp;
		}

		try {
			Student student = new Student(req.firstName, req.lastName, req.DOB, req.email, req.password, req.primary);
			pm.makePersistent(student);
			resp.student = Long.toString(student.getKey().getId());
			resp.success = true;
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

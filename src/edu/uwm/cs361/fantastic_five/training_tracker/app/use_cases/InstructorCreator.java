package edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases;

import javax.jdo.PersistenceManager;

import edu.uwm.cs361.fantastic_five.training_tracker.app.entities.Instructor;
import edu.uwm.cs361.fantastic_five.training_tracker.app.services.PersistenceService;
import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.requests.CreateInstructorRequest;
import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.responses.CreateInstructorResponse;
import edu.uwm.cs361.fantastic_five.training_tracker.app.services.InstructorValidator;

public class InstructorCreator {
	public CreateInstructorResponse createInstructor(CreateInstructorRequest req) {
		PersistenceManager pm = getPersistenceManager();
		CreateInstructorResponse resp = new CreateInstructorResponse();

		resp.errors = new InstructorValidator().validate(req.firstName, req.lastName, req.username, req.password);
		if (!resp.errors.isEmpty() ) {
			resp.success = false;
			return resp;
		}

		try {
			Instructor instructor = new Instructor(req.firstName, req.lastName, req.username, req.password);
			pm.makePersistent(instructor);
			resp.instructor = Long.toString(instructor.getKey().getId());
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

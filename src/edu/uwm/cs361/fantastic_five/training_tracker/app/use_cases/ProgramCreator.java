package edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases;

import javax.jdo.PersistenceManager;

import edu.uwm.cs361.fantastic_five.training_tracker.app.services.PersistenceService;
import edu.uwm.cs361.fantastic_five.training_tracker.app.services.ProgramValidator;
import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.requests.CreateProgramRequest;
import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.responses.CreateProgramResponse;
import edu.uwm.cs361.fantastic_five.training_tracker.app.entities.Instructor;
import edu.uwm.cs361.fantastic_five.training_tracker.app.entities.Program;

public class ProgramCreator {
	public CreateProgramResponse createProgram(CreateProgramRequest req) {
		PersistenceManager pm = getPersistenceManager();
		CreateProgramResponse resp = new CreateProgramResponse();
		
		Instructor instructor = pm.getObjectById(Instructor.class, Long.parseLong(req.instructor));
		resp.errors = new ProgramValidator().validate(req.name, instructor, req.price);

		if (!resp.errors.isEmpty()) {
			resp.success = false;
			return resp;
		}

		try {
			pm.makePersistent(new Program(req.name, instructor, Double.parseDouble(req.price), Double.parseDouble(req.discount), req.dates));
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

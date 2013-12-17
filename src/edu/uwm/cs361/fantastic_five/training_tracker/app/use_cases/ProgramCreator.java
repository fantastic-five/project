package edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases;

import javax.jdo.PersistenceManager;

import edu.uwm.cs361.fantastic_five.training_tracker.app.services.PersistenceService;
import edu.uwm.cs361.fantastic_five.training_tracker.app.services.ProgramValidator;
import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.requests.CreateProgramRequest;
import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.requests.CreateSessionsRequest;
import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.responses.CreateProgramResponse;
import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.responses.CreateSessionsResponse;
import edu.uwm.cs361.fantastic_five.training_tracker.app.entities.Instructor;
import edu.uwm.cs361.fantastic_five.training_tracker.app.entities.Program;

public class ProgramCreator extends PersistenceService {
	public CreateProgramResponse createProgram(CreateProgramRequest req) {
		PersistenceManager pm = getPersistenceManager();
		CreateProgramResponse resp = new CreateProgramResponse();
		Instructor instructor = null;

		try {
			instructor = pm.getObjectById(Instructor.class, Long.parseLong(req.instructor));
		}catch(NumberFormatException e)
		{ }
		resp.errors = new ProgramValidator().validate(req.name, instructor, req.price, req.startDate, req.endDate);

		if (!resp.errors.isEmpty()) {
			resp.success = false;
			return resp;
		}

		try {
			Program program = new Program(req.name, instructor, Double.parseDouble(req.price), req.dates, req.startDate, req.endDate);
			pm.makePersistent(program);

			CreateSessionsRequest createReq = new CreateSessionsRequest();
			createReq.programId = Long.toString(program.getKey().getId());
			CreateSessionsResponse createResp = new SessionsCreator().createSessions(createReq);

			if (createResp.errors != null && !createResp.errors.isEmpty()) {
				resp.success = false;
				return resp;
			}
			resp.success = createResp.success;

		} finally {
			pm.close();
		}

		return resp;
	}
}

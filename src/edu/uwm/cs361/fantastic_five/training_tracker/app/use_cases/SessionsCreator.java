package edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.jdo.PersistenceManager;

import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.requests.CreateProgramRequest;
import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.requests.CreateSessionsRequest;
import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.responses.CreateProgramResponse;
import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.responses.CreateSessionsResponse;
import edu.uwm.cs361.fantastic_five.training_tracker.app.entities.Instructor;
import edu.uwm.cs361.fantastic_five.training_tracker.app.entities.Program;
import edu.uwm.cs361.fantastic_five.training_tracker.app.entities.Session;
import edu.uwm.cs361.fantastic_five.training_tracker.app.entities.Time;
import edu.uwm.cs361.fantastic_five.training_tracker.app.services.PersistenceService;

public class SessionsCreator extends PersistenceService {
	public CreateSessionsResponse createSessions(CreateSessionsRequest req) {
		PersistenceManager pm = getPersistenceManager();
		CreateSessionsResponse resp = new CreateSessionsResponse();
		Program program;
		try {
			program = pm.getObjectById(Program.class,Long.parseLong(req.programId));

		} catch(Exception e) {
			resp.success = false;
			resp.errors = "Invalid program";
			return resp;
		}

		Calendar start = Calendar.getInstance();
		start.setLenient(true);
		Calendar end = Calendar.getInstance();
		end.setLenient(true);

		int[] startDate = program.getStartDateArray();
		int[] endDate = program.getEndDateArray();
		start.set(startDate[2], startDate[0]-1, startDate[1]);
		end.set(endDate[2], endDate[0]-1, endDate[1]);

		try {
			while (start.compareTo(end) <= 0) {
				for (Time t : program.getTimes()) {
					if (start.get(Calendar.DAY_OF_WEEK) == t.getDayInt()) {
						Session session = new Session(t.getDay(),start.get(Calendar.YEAR),start.get(Calendar.MONTH)+1,start.get(Calendar.DAY_OF_MONTH));
						pm.makePersistent(session);
						program.addSession(session);
					}
				}
				start.add(Calendar.DAY_OF_YEAR, 1);
			}
			resp.success = true;
		} finally {
			pm.close();
		}

		return resp;
	}
}
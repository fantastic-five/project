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
import edu.uwm.cs361.fantastic_five.training_tracker.app.entities.time;
import edu.uwm.cs361.fantastic_five.training_tracker.services.PersistenceService;

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
		
		Calendar cal = Calendar.getInstance(); 
		cal.setLenient(true);
		try {
			for (int i=0;i<42;++i) {
				cal.add(Calendar.DAY_OF_YEAR, 1);
				for (time t : req.dates) {
					if (cal.get(Calendar.DAY_OF_WEEK) == t.getDayInt()) {
						Session session = new Session(t.getDay(),cal.get(Calendar.YEAR),cal.get(Calendar.MONTH)+1,cal.get(Calendar.DAY_OF_MONTH));
						pm.makePersistent(session);
						program.addSession(session);
					}
				}
			}
			resp.success = true;
		} finally {
			pm.close();
		}
		
		return resp;
	}
}
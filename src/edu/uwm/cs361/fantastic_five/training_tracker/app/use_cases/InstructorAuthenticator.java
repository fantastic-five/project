package edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import edu.uwm.cs361.fantastic_five.training_tracker.app.entities.Instructor;
import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.requests.LogInRequest;
import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.responses.LogInResponse;

public class InstructorAuthenticator extends Authenticator {
	@Override
	@SuppressWarnings("unchecked")
	public LogInResponse authenticate(LogInRequest req) {
		PersistenceManager pm = getPersistenceManager();

		Query q = pm.newQuery(Instructor.class);
		q.setFilter("_username == paramUsername && _password == paramPassword");
		q.declareParameters("String paramUsername, String paramPassword");
	
		LogInResponse resp = new LogInResponse();
		List<Instructor> results = (List<Instructor>) q.execute(req.username, req.password);

		if (results.iterator().hasNext()) {
			resp.success = true;
			resp.id = results.iterator().next().getKey().getId();
		} else {
			resp.success = false;
		}

		return resp;
	}
	
} //end class

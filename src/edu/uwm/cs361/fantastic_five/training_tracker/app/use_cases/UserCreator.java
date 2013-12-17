package edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases;

import javax.jdo.PersistenceManager;

import edu.uwm.cs361.fantastic_five.training_tracker.app.entities.User;
import edu.uwm.cs361.fantastic_five.training_tracker.app.services.PersistenceService;
import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.requests.SignUpRequest;
import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.responses.SignUpResponse;

public class UserCreator {
	public SignUpResponse signUp(SignUpRequest req) {
		SignUpResponse resp = new SignUpResponse();

		if (req.password.equals("password")) {
			PersistenceManager pm = getPersistenceManager();

			try {
				pm.makePersistent(new User("admin", "password"));
				resp.success = true;
			} finally {
				pm.close();
			}
		}

		return resp;
	}

	private PersistenceManager getPersistenceManager()
	{
		return PersistenceService.getPersistenceManager();
	}
}

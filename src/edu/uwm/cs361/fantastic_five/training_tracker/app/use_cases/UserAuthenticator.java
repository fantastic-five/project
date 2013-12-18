package edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import edu.uwm.cs361.fantastic_five.training_tracker.app.entities.User;
import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.requests.LogInRequest;
import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.responses.LogInResponse;

public class UserAuthenticator extends Authenticator {
	@Override
	@SuppressWarnings("unchecked")
	public LogInResponse authenticate(LogInRequest req) {
		PersistenceManager pm = getPersistenceManager();

		Query q = pm.newQuery(User.class);
		q.setFilter("username == paramUsername");
		q.declareParameters("String paramUsername");

		LogInResponse resp = new LogInResponse();

		List<User> results = (List<User>) q.execute(req.username);

		if (!results.iterator().hasNext()) {
			resp.success = false;
			return resp;
		}

		User user = results.iterator().next();
		resp.success = user.passwordMatches(req.password);

		return resp;
	}

} //end class

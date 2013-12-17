package edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases;

import java.util.List;

import javax.jdo.PersistenceManager;

import edu.uwm.cs361.fantastic_five.training_tracker.app.entities.Instructor;
import edu.uwm.cs361.fantastic_five.training_tracker.app.services.PersistenceService;
import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.requests.ListInstructorsRequest;
import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.responses.ListInstructorsResponse;

public class InstructorFinder {
	@SuppressWarnings("unchecked")
	public ListInstructorsResponse listInstructors(ListInstructorsRequest req) {
		ListInstructorsResponse resp = new ListInstructorsResponse();

		PersistenceManager pm = getPersistenceManager();

		resp.instructors = (List<Instructor>) pm.newQuery(Instructor.class).execute();

		return resp;
	}

	public ListInstructorsResponse listInstructors() {
		return listInstructors(new ListInstructorsRequest() );
	}

	private PersistenceManager getPersistenceManager()
	{
		return PersistenceService.getPersistenceManager();
	}
}




package edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases;

import java.util.List;

import javax.jdo.PersistenceManager;

import edu.uwm.cs361.fantastic_five.training_tracker.app.entities.Program;
import edu.uwm.cs361.fantastic_five.training_tracker.app.entities.Team;
import edu.uwm.cs361.fantastic_five.training_tracker.app.services.PersistenceService;
import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.requests.ListProgramsRequest;
import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.requests.ListTeamsRequest;
import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.responses.ListProgramsResponse;
import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.responses.ListTeamResponse;

public class TeamFinder {
	@SuppressWarnings("unchecked")
	public ListTeamResponse listTeams(ListTeamsRequest req) {
		ListTeamResponse resp = new ListTeamResponse();

		PersistenceManager pm = getPersistenceManager();

		resp.teams = (List<Team>) pm.newQuery(Team.class).execute();

		return resp;
	}

	public ListTeamResponse listTeams() {
		return listTeams(new ListTeamsRequest());
	}

	private PersistenceManager getPersistenceManager()
	{
		return PersistenceService.getPersistenceManager();
	}
}
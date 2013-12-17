package edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases;

import javax.jdo.PersistenceManager;

import edu.uwm.cs361.fantastic_five.training_tracker.app.entities.Program;
import edu.uwm.cs361.fantastic_five.training_tracker.app.entities.Team;
import edu.uwm.cs361.fantastic_five.training_tracker.app.services.PersistenceService;
import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.requests.ViewProgramRequest;
import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.requests.ViewTeamRequest;
import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.responses.ViewProgramResponse;
import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.responses.ViewTeamResponse;

public class TeamViewer {
	public ViewTeamResponse viewTeam(ViewTeamRequest req) {
		ViewTeamResponse resp = new ViewTeamResponse();
		PersistenceManager pm = getPersistenceManager();

		String id = req.id;
		long idLong = Long.parseLong(id);
		Team team = pm.getObjectById(Team.class, idLong);

		resp.team = team;
		if (team != null) resp.students = team.listStudents();

		return resp;
	}

	private PersistenceManager getPersistenceManager()
	{
		return PersistenceService.getPersistenceManager();
	}
}

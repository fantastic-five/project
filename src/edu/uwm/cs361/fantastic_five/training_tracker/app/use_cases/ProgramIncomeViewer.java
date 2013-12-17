package edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases;

import java.util.List;

import javax.jdo.PersistenceManager;

import edu.uwm.cs361.fantastic_five.training_tracker.app.entities.Program;
import edu.uwm.cs361.fantastic_five.training_tracker.app.services.PersistenceService;
import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.requests.ViewProgramIncomeRequest;
import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.responses.ViewProgramIncomeResponse;

public class ProgramIncomeViewer {
	@SuppressWarnings("unchecked")
	public ViewProgramIncomeResponse viewProgramIncome(ViewProgramIncomeRequest req) {
		ViewProgramIncomeResponse resp = new ViewProgramIncomeResponse();

		PersistenceManager pm = getPersistenceManager();

		resp.programs = (List<Program>) pm.newQuery(Program.class).execute();

		return resp;
	}

	public ViewProgramIncomeResponse viewProgramIncome() {
		return viewProgramIncome(new ViewProgramIncomeRequest());
	}

	private PersistenceManager getPersistenceManager()
	{
		return PersistenceService.getPersistenceManager();
	}

}

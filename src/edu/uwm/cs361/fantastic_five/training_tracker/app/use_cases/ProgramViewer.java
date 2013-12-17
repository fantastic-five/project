package edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases;

import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;

import edu.uwm.cs361.fantastic_five.training_tracker.app.entities.Program;
import edu.uwm.cs361.fantastic_five.training_tracker.app.services.PersistenceService;
import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.requests.ViewProgramRequest;
import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.responses.ViewProgramResponse;

public class ProgramViewer {
	public ViewProgramResponse viewProgram(ViewProgramRequest req) {
		ViewProgramResponse resp = new ViewProgramResponse();
		PersistenceManager pm = getPersistenceManager();

		String id = req.id;
		long idLong;

		try {
			idLong = Long.parseLong(id);
		} catch (NumberFormatException ex) {
			resp.program = null;
			return resp;
		}

		Program program;
		try {
			program = pm.getObjectById(Program.class, idLong);

			resp.program = program;
		} catch (JDOObjectNotFoundException ex) {
			resp.program = null;
			return resp;
		}

		if (program != null) resp.students = program.getStudents();

		return resp;
	}

	private PersistenceManager getPersistenceManager()
	{
		return PersistenceService.getPersistenceManager();
	}
}

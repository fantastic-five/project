package edu.uwm.cs361.fantastic_five.training_tracker.servlets;

import java.io.IOException;

import javax.jdo.PersistenceManager;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import edu.uwm.cs361.fantastic_five.training_tracker.app.entities.Instructor;
import edu.uwm.cs361.fantastic_five.training_tracker.app.entities.Program;
import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.ProgramFinder;
import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.responses.ListProgramsResponse;

@SuppressWarnings("serial")
public class ProgramsServlet extends BaseServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		ListProgramsResponse listProgramsResp = new ProgramFinder().listPrograms();
		PersistenceManager pm = getPersistenceManager();
		long id = -1;
		Cookie[] cookies = req.getCookies();

		if (cookies != null) {
			for (Cookie c : cookies) {
				if (c.getName().equals("id")) {
					id = Long.parseLong(c.getValue());
				}
			}
		}
		Instructor instructor;
		try {
		 instructor = pm.getObjectById(Instructor.class,id);
		}
		catch(Exception e){
			instructor = null;
		}
		if (instructor != null) {
			for(Program program : listProgramsResp.programs) {
				if (!program.getInstructor().equals(instructor)) {
					listProgramsResp.programs.remove(program);
				}
			}
		}
		req.setAttribute("programs", listProgramsResp.programs);

		forwardToJsp("programs.jsp", req, resp);
	}
}

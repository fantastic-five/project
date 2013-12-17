package edu.uwm.cs361.fantastic_five.training_tracker.servlets;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.jdo.PersistenceManager;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import edu.uwm.cs361.fantastic_five.training_tracker.app.entities.Instructor;
import edu.uwm.cs361.fantastic_five.training_tracker.app.entities.Program;
import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.ProgramFinder;
import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.responses.ListProgramsResponse;

@SuppressWarnings("serial")
public class InstructorProgramsServlet extends BaseServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		PersistenceManager pm = getPersistenceManager();
		Cookie[] cookies = req.getCookies();
		long id = 0;
		
		if (cookies != null) {
			for (Cookie c : cookies) {
				if (c.getName().equals("id")) {
					id = Long.parseLong(c.getValue());
				}
			}
		}
		Instructor instructor = null;
		try {
		 instructor = pm.getObjectById(Instructor.class,id);
		}catch(Exception e){
			resp.sendRedirect("/logout");
		}
		if (instructor != null) {
			ListProgramsResponse listProgramsResp = new ProgramFinder().listPrograms();
			Set<Program> programs = new HashSet<Program>();
			for (Program program : listProgramsResp.programs){
				if (program.getInstructor().equals(instructor))	
					programs.add(program);
			}
			req.setAttribute("programs", programs);
			
			forwardToJsp("instructor_programs.jsp", req, resp);
		}
	}
}

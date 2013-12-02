package edu.uwm.cs361.fantastic_five.training_tracker.servlets;

import java.io.IOException;

import javax.jdo.PersistenceManager;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import edu.uwm.cs361.fantastic_five.training_tracker.app.entities.Program;
import edu.uwm.cs361.fantastic_five.training_tracker.app.entities.Session;

@SuppressWarnings("serial")
public class InstructorScheduleServlet extends BaseServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		PersistenceManager pm = getPersistenceManager();
		
		String id = req.getParameter("id");
		long idLong = Long.parseLong(id);
		Program program = pm.getObjectById(Program.class, idLong);

		req.setAttribute("program", program);
		if (program != null) req.setAttribute("students", program.listStudents());

		forwardToJsp("instructor_schedule.jsp", req, resp);
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException
	{
		PersistenceManager pm = getPersistenceManager();
		String id = req.getParameter("session");
		long idLong = Long.parseLong(id);
		Session session = pm.getObjectById(Session.class, idLong);
		if (session.getAttendance().size() == 0)
			resp.sendRedirect("/attendance?id=req.getParameter('session')");
		else
			resp.sendRedirect("/attendance/view?id=req.getParameter('session')");
	}
}

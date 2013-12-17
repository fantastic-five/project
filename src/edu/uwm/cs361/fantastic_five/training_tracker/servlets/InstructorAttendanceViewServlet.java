package edu.uwm.cs361.fantastic_five.training_tracker.servlets;

import java.io.IOException;

import javax.jdo.PersistenceManager;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import edu.uwm.cs361.fantastic_five.training_tracker.app.entities.Program;
import edu.uwm.cs361.fantastic_five.training_tracker.app.entities.Session;
import edu.uwm.cs361.fantastic_five.training_tracker.app.entities.Student;

@SuppressWarnings("serial")
public class InstructorAttendanceViewServlet extends BaseServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		PersistenceManager pm = getPersistenceManager();
		long idLong = Long.parseLong(req.getParameter("id"));
		req.setAttribute("program", pm.getObjectById(Program.class,idLong));

		forwardToJsp("instructor_attendance_view.jsp", req, resp);
	}
}
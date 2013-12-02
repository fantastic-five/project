package edu.uwm.cs361.fantastic_five.training_tracker.servlets;

import java.io.IOException;

import javax.jdo.PersistenceManager;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import edu.uwm.cs361.fantastic_five.training_tracker.app.entities.Session;

@SuppressWarnings("serial")
public class ViewAttendanceServlet extends BaseServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		PersistenceManager pm = getPersistenceManager();

		String id = req.getParameter("id");
		long idLong = Long.parseLong(id);
		Session session = pm.getObjectById(Session.class, idLong);

		req.setAttribute("session", session);
		if (session != null) req.setAttribute("students", session.getAttendance());
		
		forwardToJsp("view_attendance.jsp", req, resp);
	}
}

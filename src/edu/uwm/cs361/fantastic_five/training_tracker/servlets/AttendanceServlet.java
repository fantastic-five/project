package edu.uwm.cs361.fantastic_five.training_tracker.servlets;

import java.io.IOException;

import javax.jdo.PersistenceManager;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import edu.uwm.cs361.fantastic_five.training_tracker.app.entities.Program;
import edu.uwm.cs361.fantastic_five.training_tracker.app.entities.Session;
import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.AttendanceTaker;
import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.requests.AttendanceRequest;
import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.responses.AttendanceResponse;

@SuppressWarnings("serial")
public class AttendanceServlet extends BaseServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		PersistenceManager pm = getPersistenceManager();

		String id = req.getParameter("id");
		long idLong = Long.parseLong(id);
		Session session = pm.getObjectById(Session.class, idLong);
		req.setAttribute("session", session);
		if (session != null) req.setAttribute("students", session.getProgram().listStudents());

		forwardToJsp("attendance.jsp", req, resp);
	}
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException
	{
		AttendanceRequest attendanceRequest = new AttendanceRequest();
		attendanceRequest.studentIds = req.getParameterValues("attended");
		attendanceRequest.sessionId = req.getParameter("id");
		
		AttendanceResponse attendanceResponse = new AttendanceTaker().takeAttendance(attendanceRequest);
		resp.sendRedirect("/attendance/view?id=req.getParameter('id')");
	}
}

package edu.uwm.cs361.fantastic_five.training_tracker.servlets;

import java.io.IOException;

import javax.jdo.PersistenceManager;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import edu.uwm.cs361.fantastic_five.training_tracker.app.entities.Program;
import edu.uwm.cs361.fantastic_five.training_tracker.app.entities.Session;
import edu.uwm.cs361.fantastic_five.training_tracker.app.entities.Student;
import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.AttendanceTaker;
import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.StudentEnroller;
import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.requests.AttendanceRequest;
import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.responses.AttendanceResponse;
import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.responses.EnrollStudentResponse;

@SuppressWarnings("serial")
public class AttendanceServlet extends BaseServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		PersistenceManager pm = getPersistenceManager();
		long idLong = Long.parseLong(req.getParameter("id"));
		req.setAttribute("program", pm.getObjectById(Program.class,idLong));

		forwardToJsp("attendance.jsp", req, resp);
	}
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		AttendanceRequest attendanceReq = new AttendanceRequest();
		attendanceReq.ids = req.getParameterValues("students");
		attendanceReq.sessionId = req.getParameter("date");
		
		AttendanceResponse attendanceResponse = new AttendanceTaker().takeAttendance(attendanceReq);

		if (attendanceResponse.success) {
			resp.sendRedirect("/programs/instructor");
		} else {
			resp.setContentType("text/html");

			resp.getWriter().println("<h3>Error: " + attendanceResponse.error + "</h3>");
			resp.getWriter().println("<form action='/programs/instructor'>");
			resp.getWriter().println("<input type='submit' value='Back to Programs Page'>");
			resp.getWriter().println("</form>");
		}
	
	}
}

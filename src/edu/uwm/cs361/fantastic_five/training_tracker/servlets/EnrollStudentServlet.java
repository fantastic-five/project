package edu.uwm.cs361.fantastic_five.training_tracker.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.StudentEnroller;
import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.requests.EnrollStudentRequest;
import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.requests.ListUnenrolledStudentsRequest;
import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.responses.EnrollStudentResponse;
import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.responses.ListUnenrolledStudentsResponse;

@SuppressWarnings("serial")
public class EnrollStudentServlet extends BaseServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		ListUnenrolledStudentsRequest listUnenrolledStudentsReq = new ListUnenrolledStudentsRequest();
		listUnenrolledStudentsReq.programId = req.getParameter("id");

		ListUnenrolledStudentsResponse listUnenrolledStudentsResponse = new StudentEnroller().listUnenrolledStudents(listUnenrolledStudentsReq);

		req.setAttribute("program", listUnenrolledStudentsResponse.program);
		req.setAttribute("StudentsList", listUnenrolledStudentsResponse.unenrolledStudents);
		forwardToJsp("enroll_student.jsp", req, resp);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException
	{
		EnrollStudentRequest enrollStudentRequest = new EnrollStudentRequest();
		enrollStudentRequest.studentId = req.getParameter("student");
		enrollStudentRequest.programId = req.getParameter("id");



		EnrollStudentResponse enrollStudentResponse = new StudentEnroller().enrollStudent(enrollStudentRequest);

		if (enrollStudentResponse.success) {
			resp.sendRedirect("/programs");
		} else {
			resp.setContentType("text/html");

			resp.getWriter().println("<h3>Error: " + enrollStudentResponse.error + "</h3>");
			resp.getWriter().println("<form action='/programs'>");
			resp.getWriter().println("<input type='submit' value='Back to Programs Page'>");
			resp.getWriter().println("</form>");
		}
	}
}

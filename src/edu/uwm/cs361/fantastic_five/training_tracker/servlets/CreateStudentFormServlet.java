package edu.uwm.cs361.fantastic_five.training_tracker.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.StudentCreator;
import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.requests.CreateAccountRequest;
import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.requests.CreateStudentRequest;
import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.responses.CreateStudentResponse;

@SuppressWarnings("serial")
public class CreateStudentFormServlet extends BaseServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		forwardToJsp("create_student_form.jsp", req, resp);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException
	{
		CreateStudentRequest createRequest = new CreateStudentRequest();
		createRequest.firstName = req.getParameter("firstName");
		createRequest.lastName = req.getParameter("lastName");
		createRequest.DOB = req.getParameter("dob");
		createRequest.email = req.getParameter("email");
		createRequest.password = req.getParameter("password");
		if (req.getParameter("primary").equals("true")) {
			createRequest.primary = true;
		}
		else
			createRequest.primary = false;
		
		CreateStudentResponse createResponse = new StudentCreator().createStudent(createRequest);
		
		if (createResponse.success) {
			resp.sendRedirect("/account/new?id=" + createResponse.student);
		} 
		else {
			req.setAttribute("firstName", req.getParameter("firstName"));
			req.setAttribute("lastName", req.getParameter("lastName"));
			req.setAttribute("email", req.getParameter("email"));

			req.setAttribute("errors", createResponse.errors);

			forwardToJsp("create_student_form.jsp", req, resp);
		}
	}
}

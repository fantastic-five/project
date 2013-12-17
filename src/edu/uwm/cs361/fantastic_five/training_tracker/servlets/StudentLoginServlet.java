package edu.uwm.cs361.fantastic_five.training_tracker.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.StudentAuthenticator;
import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.requests.LogInRequest;
import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.responses.LogInResponse;

@SuppressWarnings("serial")
public class StudentLoginServlet extends BaseServlet {
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		forwardToJsp("studentlogin.jsp", req, resp);
	}
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException
	{
		LogInRequest logInRequest = new LogInRequest();
		logInRequest.username = req.getParameter("username");
		logInRequest.password = req.getParameter("password");

		LogInResponse logInResponse = new StudentAuthenticator().authenticate(logInRequest);

		if (logInResponse.success) {
			Cookie c = new Cookie("id",Long.toString(logInResponse.id));
			resp.addCookie(c);
			resp.sendRedirect("/student");
		} else {
			req.setAttribute("error", true);

			forwardToJsp("studentlogin.jsp", req, resp);
		}
	}

} //end class

package edu.uwm.cs361.fantastic_five.training_tracker.servlets;

import java.io.IOException;

import javax.jdo.PersistenceManager;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.InstructorAuthenticator;
import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.requests.LogInRequest;
import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.responses.LogInResponse;

@SuppressWarnings("serial")
public class InstructorLoginServlet extends BaseServlet {
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		forwardToJsp("instructorlogin.jsp", req, resp);
	}
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException
	{
		LogInRequest logInRequest = new LogInRequest();
		logInRequest.username = req.getParameter("username");
		logInRequest.password = req.getParameter("password");

		LogInResponse logInResponse = new InstructorAuthenticator().authenticate(logInRequest);

		if (logInResponse.success) {
			Cookie c = new Cookie("id",Long.toString(logInResponse.id));
			resp.addCookie(c);
			resp.sendRedirect("/instructor");
		} else {
			req.setAttribute("error", true);

			forwardToJsp("instructorlogin.jsp", req, resp);
		}
	}

} //end class
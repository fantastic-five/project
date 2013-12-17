package edu.uwm.cs361.fantastic_five.training_tracker.servlets;

import java.io.IOException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class StudentServlet extends BaseServlet
{
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException
	{
		String id = null;
		Cookie[] cookies = req.getCookies();

		if (cookies != null) {
			for (Cookie c : cookies) {
				if (c.getName().equals("id")) {
					id = c.getValue();
				}
			}
		}

		if (id != null) {
			resp.sendRedirect("/studenthomepage");
		}
		else {
			resp.setContentType("text/html");
			resp.getWriter().println("<h1>Not logged in</h1>");
			resp.getWriter().println("<a href='/login' method='POST'> Back to Log In</a>");
		}
	}
}


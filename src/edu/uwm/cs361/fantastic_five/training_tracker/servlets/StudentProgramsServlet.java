package edu.uwm.cs361.fantastic_five.training_tracker.servlets;

import java.io.IOException;

import javax.jdo.PersistenceManager;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import edu.uwm.cs361.fantastic_five.training_tracker.app.entities.Student;

@SuppressWarnings("serial")
public class StudentProgramsServlet extends BaseServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		PersistenceManager pm = getPersistenceManager();
		Cookie[] cookies = req.getCookies();
		long id = 0;
		
		if (cookies != null) {
			for (Cookie c : cookies) {
				if (c.getName().equals("id")) {
					id = Long.parseLong(c.getValue());
				}
			}
		}
		
		Student student = null;
		try {
		 student = pm.getObjectById(Student.class,id);
		}catch(Exception e){
			resp.sendRedirect("/logout");
		}
		if (student != null) {
			req.setAttribute("programs", student.getPrograms());
			forwardToJsp("student_programs.jsp", req, resp);
		}
	}
}

package edu.uwm.cs361.fantastic_five.training_tracker.servlets;

import java.io.IOException;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.uwm.cs361.fantastic_five.training_tracker.app.services.PersistenceService;

@SuppressWarnings("serial")
public abstract class BaseServlet extends HttpServlet {
	protected PersistenceManager getPersistenceManager()
	{
		return PersistenceService.getPersistenceManager();
		//return JDOHelper.getPersistenceManagerFactory("transactions-optional").getPersistenceManager();
	}

	protected void forwardToJsp(String file, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/pages/" + file);
		dispatcher.forward(req, resp);
	}
}

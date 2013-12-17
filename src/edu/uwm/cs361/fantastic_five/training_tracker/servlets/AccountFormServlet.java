package edu.uwm.cs361.fantastic_five.training_tracker.servlets;

import java.io.IOException;

import javax.jdo.PersistenceManager;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import edu.uwm.cs361.fantastic_five.training_tracker.app.entities.Student;
import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.AccountCreator;
import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.AccountFinder;
import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.requests.CreateAccountRequest;
import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.responses.CreateAccountResponse;
import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.responses.ListAccountsResponse;

@SuppressWarnings("serial")
public class AccountFormServlet extends BaseServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		PersistenceManager pm = getPersistenceManager();
		
		try {
			Student student = pm.getObjectById(Student.class,Long.parseLong(req.getParameter("id")));
			if (student.isPrimary())
				forwardToJsp("create_account_form.jsp", req, resp);
			else {
				ListAccountsResponse listAccountsResp = new AccountFinder().listAccounts();
				req.setAttribute("accounts", listAccountsResp.accounts);
				forwardToJsp("create_dependent_form.jsp",req,resp);
			}
		} catch (Exception e) {
			resp.sendRedirect("/students");
		}
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException
	{
		PersistenceManager pm = getPersistenceManager();
		CreateAccountRequest createRequest = new CreateAccountRequest();
		Student student = pm.getObjectById(Student.class,Long.parseLong(req.getParameter("id")));
		if (!student.isPrimary()) {
			createRequest.primary = req.getParameter("primary");
			createRequest.student = Long.toString(student.getKey().getId());
			
			CreateAccountResponse createResponse = new AccountCreator().createDependent(createRequest);
			if (createResponse.success) {
				resp.sendRedirect("/students");
			} else {
				ListAccountsResponse listAccountsResp = new AccountFinder().listAccounts();
				req.setAttribute("accounts", listAccountsResp.accounts);
				req.setAttribute("errors", createResponse.errors);
				forwardToJsp("create_dependent_form.jsp",req,resp);
			}
		}
		else {
			createRequest.address = req.getParameter("address");
			createRequest.phone = req.getParameter("phone");
			createRequest.primary = req.getParameter("id");
			
			CreateAccountResponse createResponse = new AccountCreator().createAccount(createRequest);
	
			if (createResponse.success) {
				resp.sendRedirect("/students");
			} else {
				req.setAttribute("errors", createResponse.errors);
				forwardToJsp("create_account_form.jsp",req,resp);
			}
		}
	}
}

package edu.uwm.cs361.fantastic_five.training_tracker.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.AccountFinder;
import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.responses.ListAccountsResponse;

@SuppressWarnings("serial")
public class AccountsServlet extends BaseServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		ListAccountsResponse listAccountsResp = new AccountFinder().listAccounts();

		req.setAttribute("accounts", listAccountsResp.accounts);

		forwardToJsp("accounts.jsp", req, resp);
	}
}

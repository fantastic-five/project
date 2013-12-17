package edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases;

import java.util.List;

import javax.jdo.PersistenceManager;

import edu.uwm.cs361.fantastic_five.training_tracker.app.entities.Account;
import edu.uwm.cs361.fantastic_five.training_tracker.app.services.PersistenceService;
import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.requests.ListAccountsRequest;
import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.responses.ListAccountsResponse;

public class AccountFinder {
	@SuppressWarnings("unchecked")
	public ListAccountsResponse listAccounts(ListAccountsRequest req) {
		ListAccountsResponse resp = new ListAccountsResponse();

		PersistenceManager pm = getPersistenceManager();

		resp.accounts = (List<Account>) pm.newQuery(Account.class).execute();

		return resp;
	}

	public ListAccountsResponse listAccounts() {
		return listAccounts(new ListAccountsRequest());
	}

	private PersistenceManager getPersistenceManager()
	{
		return PersistenceService.getPersistenceManager();
	}
}
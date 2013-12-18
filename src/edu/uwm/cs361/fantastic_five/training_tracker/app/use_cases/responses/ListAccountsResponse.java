package edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.responses;

import java.util.List;

import edu.uwm.cs361.fantastic_five.training_tracker.app.entities.Account;

public class ListAccountsResponse extends UseCaseResponse {
	public List<Account> accounts;
}

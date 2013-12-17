package edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.PersistenceManager;

import edu.uwm.cs361.fantastic_five.training_tracker.app.entities.Student;
import edu.uwm.cs361.fantastic_five.training_tracker.app.entities.Account;
import edu.uwm.cs361.fantastic_five.training_tracker.app.services.AccountValidator;
import edu.uwm.cs361.fantastic_five.training_tracker.app.services.DependentValidator;
import edu.uwm.cs361.fantastic_five.training_tracker.app.services.PersistenceService;
import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.requests.CreateAccountRequest;
import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.responses.CreateAccountResponse;

public class AccountCreator {
	public CreateAccountResponse createAccount(CreateAccountRequest req) {
		PersistenceManager pm = getPersistenceManager();
		CreateAccountResponse resp = new CreateAccountResponse();

		resp.success = false;
		resp.errors = new AccountValidator().validate(req.primary, req.address, req.phone);
		if (!resp.errors.isEmpty()) {
			return resp;
		}

		try {
			Student student = pm.getObjectById(Student.class,Long.parseLong(req.primary));
			pm.makePersistent(new Account(student, req.address, req.phone));
			resp.success = true;
		} finally {
			pm.close();
		}

		return resp;
	}
	@SuppressWarnings("unchecked")
	public CreateAccountResponse createDependent(CreateAccountRequest req) {
		PersistenceManager pm = getPersistenceManager();
		CreateAccountResponse resp = new CreateAccountResponse();

		resp.success = false;
		resp.errors = new DependentValidator().validate(req.primary, req.student);
		if (!resp.errors.isEmpty()) {
			return resp;
		}

		Student primary = pm.getObjectById(Student.class,Long.parseLong(req.primary));
		Student student = pm.getObjectById(Student.class,Long.parseLong(req.student));

		List<Account> accounts = (List<Account>) pm.newQuery(Account.class).execute();

		for (Account account : accounts) {
			if (account.getPrimary().equals(primary)) {
				account.addDependent(student);
				resp.success = true;
				return resp;
			}
		}

		return resp;
	}
	private PersistenceManager getPersistenceManager()
	{
		return PersistenceService.getPersistenceManager();
	}
}

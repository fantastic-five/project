package edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.PersistenceManager;

import edu.uwm.cs361.fantastic_five.training_tracker.app.entities.Student;
import edu.uwm.cs361.fantastic_five.training_tracker.app.entities.account;
import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.requests.CreateAccountRequest;
import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.responses.CreateAccountResponse;
import edu.uwm.cs361.fantastic_five.training_tracker.services.AccountValidator;
import edu.uwm.cs361.fantastic_five.training_tracker.services.DependentValidator;
import edu.uwm.cs361.fantastic_five.training_tracker.services.PersistenceService;

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
			pm.makePersistent(new account(student, req.address, req.phone));
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
		
		List<account> accounts = (List<account>) pm.newQuery(account.class).execute();
		
		for (account account : accounts) {
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

package edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases;

import javax.jdo.PersistenceManager;

import edu.uwm.cs361.fantastic_five.training_tracker.app.entities.Student;
import edu.uwm.cs361.fantastic_five.training_tracker.app.entities.Account;
import edu.uwm.cs361.fantastic_five.training_tracker.app.services.AccountValidator;
import edu.uwm.cs361.fantastic_five.training_tracker.app.services.PersistenceService;
import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.requests.CreateAccountRequest;
import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.responses.CreateAccountResponse;

public class AccountCreator {
	public CreateAccountResponse createAccount(CreateAccountRequest req) {
		PersistenceManager pm = getPersistenceManager();
		CreateAccountResponse resp = new CreateAccountResponse();
		
		resp.success = false;
		resp.errors = new AccountValidator().validate(req.address, req.phone);
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
	public CreateAccountResponse createDependent(CreateAccountRequest req) {
		PersistenceManager pm = getPersistenceManager();
		CreateAccountResponse resp = new CreateAccountResponse();
		try {
			Account account = pm.getObjectById(Account.class,Long.parseLong(req.primary));
			Student student = pm.getObjectById(Student.class,Long.parseLong(req.student));
			account.addDependent(student);
			student.addAccount(account);
			
			resp.success = true;
			} catch(Exception e) {
				resp.success = false;
			}
		
		return resp;
	}
	private PersistenceManager getPersistenceManager()
	{
		return PersistenceService.getPersistenceManager();
	}
}

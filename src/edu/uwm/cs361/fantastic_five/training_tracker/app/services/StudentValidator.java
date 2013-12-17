package edu.uwm.cs361.fantastic_five.training_tracker.app.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.AccountFinder;
import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.responses.ListAccountsResponse;

public class StudentValidator {
	private Map<String, List<String>> errors;

	// Not a very robust solution, but it should work fine for now.
	private static final String EMAIL_PATTERN =
			"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	public Map<String, List<String>> validate(String firstName, String lastName, String DOB, String email, boolean primary) {
		errors = new HashMap<String, List<String>>();

		validateFirstName(firstName);
		validateLastName(lastName);
		validateDOB(DOB);
		validateEmail(email);
		validatePrimary(primary);
		
		return errors;
	}

	private void validateFirstName(String firstName) {
		List<String> nameErrors = new ArrayList<String>();

		if (firstName == null || firstName.isEmpty()) {
			nameErrors.add("First name must not be blank.");
		}

		if (!nameErrors.isEmpty()) errors.put("firstName", nameErrors);
	}

	private void validateLastName(String lastName) {
		List<String> nameErrors = new ArrayList<String>();

		if (lastName == null || lastName.isEmpty()) {
			nameErrors.add("Last name must not be blank.");
		}

		if (!nameErrors.isEmpty()) errors.put("lastName", nameErrors);
	}
	
	private void validateDOB(String DOB) {
		List<String> dobErrors = new ArrayList<String>();
		
		int i;
		boolean err = false;
		for (i=0; i<10 && i<DOB.length() && !err; ++i)
		{
			if (i == 2 || i == 5) {
				if (DOB.charAt(i) != '/')
					err = true;
			}
			else
				if (!Character.isDigit(DOB.charAt(i)))
					err = true;
		}
		if (i < 10)
			err = true;
		
		if (err == true) {
			dobErrors.add("DOB format is not correct; should be MM/DD/YYYY");
		}
		
		if (!dobErrors.isEmpty()) errors.put("dob",dobErrors);
	}
	
	private void validateEmail(String email) {
		List<String> emailErrors = new ArrayList<String>();

		if (email == null || email.isEmpty()) {
			emailErrors.add("Email must not be blank.");
		}
		if (!emailIsValid(email)) {
			emailErrors.add("Email must be valid.");
		}

		if (!emailErrors.isEmpty()) errors.put("email", emailErrors);
	}

	private boolean emailIsValid(String email) {
		Pattern pattern = Pattern.compile(EMAIL_PATTERN);
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}
	
	private void validatePrimary(boolean primary) {
		List<String> primaryErrors = new ArrayList<String>();
		
		if (!primary) {
			ListAccountsResponse resp = new AccountFinder().listAccounts();
			if (resp.accounts.size() == 0)
				primaryErrors.add("No available account holders.");
		}
		
		if (!primaryErrors.isEmpty()) errors.put("primary", primaryErrors);
	}
}

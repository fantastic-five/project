package edu.uwm.cs361.fantastic_five.training_tracker.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.requests.CreateInstructorRequest;

public class InstructorValidator {

	private Map<String, List<String>> errors;
	
	//****************************************************
	
	public Map<String, List<String>> validate(
			String firstName, String lastName, String _username, String _password) {
		
		validateFirstName(firstName);
		validateLastName(lastName);
		validateUsername(_username);
		validatePassword(_password);
		
		return errors;
	} //end validate
	
	//****************************************************
	
	private void validateFirstName(String firstName) {
		List<String> nameErrors = new ArrayList<String>();

		if (firstName == null || firstName.isEmpty()) {
			nameErrors.add("First name must not be blank.");
		}

		if (!nameErrors.isEmpty() ) {
			errors.put("firstName", nameErrors);
		}
	} //end validateFirstName

	private void validateLastName(String lastName) {
		List<String> nameErrors = new ArrayList<String>();

		if (lastName == null || lastName.isEmpty()) {
			nameErrors.add("Last name must not be blank.");
		}

		if (!nameErrors.isEmpty() ) {
			errors.put("lastName", nameErrors);
		}
	} //end validateLastName
	
	private void validateUsername(String _username) {
		List<String> nameErrors = new ArrayList<String>();

		if (_username == null || _username.isEmpty()) {
			nameErrors.add("Last name must not be blank.");
		}

		if (!nameErrors.isEmpty() ) {
			errors.put("lastName", nameErrors);
		}
	} //end validateUserName
	
	private void validatePassword(String _password) {
		List<String> nameErrors = new ArrayList<String>();

		if (_password == null || _password.isEmpty()) {
			nameErrors.add("Last name must not be blank.");
		}

		if (!nameErrors.isEmpty() ) {
			errors.put("lastName", nameErrors);
		}
	} //end validateUserName
	
} //end class

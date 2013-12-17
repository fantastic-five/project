package edu.uwm.cs361.fantastic_five.training_tracker.app.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;

import edu.uwm.cs361.fantastic_five.training_tracker.app.entities.Student;

public class DependentValidator extends PersistenceService{
	private Map<String, List<String>> errors;

	public Map<String, List<String>> validate(String primary, String student) {
		errors = new HashMap<String, List<String>>();

		validatePrimary(primary);
		validateStudent(student);

		return errors;
	}
	
	private void validatePrimary(String primary) {
		List<String> primaryErrors = new ArrayList<String>();
		PersistenceManager pm = getPersistenceManager();
		long l = 1;
		
		if (primary == null || primary.isEmpty()) {
			primaryErrors.add("Primary must not be empty.");
		}
		
		try {
			l = Long.parseLong(primary);
		} catch(NumberFormatException e) {
			primaryErrors.add("Invalid key for primary student");
		}
		
		try {
			pm.getObjectById(Student.class,l);
		} catch (JDOObjectNotFoundException e) {
			primaryErrors.add("Primary student does not exist");
		}
		
		if (!primaryErrors.isEmpty()) errors.put("primary", primaryErrors);
	}
	
	private void validateStudent(String student) {
		List<String> dependentErrors = new ArrayList<String>();
		PersistenceManager pm = getPersistenceManager();
		long l = 1;
		
		if (student == null || student.isEmpty()) {
			dependentErrors.add("Student must not be empty.");
		}
		
		try {
			l = Long.parseLong(student);
		} catch(NumberFormatException e) {
			dependentErrors.add("Invalid key for dependent student");
		}
		
		try {
			pm.getObjectById(Student.class,l);
		} catch (JDOObjectNotFoundException e) {
			dependentErrors.add("Dependent does not exist");
		}
		
		if (!dependentErrors.isEmpty()) errors.put("dependent", dependentErrors);
	}
}
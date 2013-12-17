package edu.uwm.cs361.fantastic_five.training_tracker.app.services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.uwm.cs361.fantastic_five.training_tracker.app.entities.Instructor;

public class ProgramValidator {
	private Map<String, List<String>> errors;

	public Map<String, List<String>> validate(String name, Instructor instructor, String price, String start, String end) {
		errors = new HashMap<String, List<String>>();

		validateName(name);
		validateInstructor(instructor);
		validatePrice(price);
		validateDates(start,end);
		
		return errors;
	}

	private void validateName(String name) {
		List<String> nameErrors = new ArrayList<String>();

		if (name == null || name.isEmpty()) {
			nameErrors.add("Name must not be blank.");
		}

		if (!nameErrors.isEmpty()) errors.put("name", nameErrors);
	}

	private void validateInstructor(Instructor instructor) {
		List<String> instructorErrors = new ArrayList<String>();

		if (instructor == null)
			instructorErrors.add("Invalid instructor");

		if (!instructorErrors.isEmpty()) errors.put("instructor", instructorErrors);
	}

	private void validatePrice(String price) {
		List<String> numberErrors = validateDouble(price, "Price");
		if (!numberErrors.isEmpty()) errors.put("price", numberErrors);
	}

	private List<String> validateDouble(String numberString, String name) {
		ArrayList<String> numberErrors = new ArrayList<String>();

		if (numberString == null || numberString.isEmpty()) {
			numberErrors.add(name + " must not be blank.");
		} else {
			try {
				Double.parseDouble(numberString);
			} catch (NumberFormatException ex) {
				numberErrors.add(name + " is invalid.");
			}
		}

		return numberErrors;
	}
	
	private void validateDates(String start, String end) {
		List<String> dateErrors = new ArrayList<String>();
		boolean err = false;
		if (start == null || end == null)
			err = true;
		else if (start.length() != 10 || end.length() != 10)
			err = true;
		else {
			try {
				int startMonth, endMonth, startDay, endDay, startYear, endYear;
				startMonth = Integer.parseInt(start.substring(0,2));
				endMonth = Integer.parseInt(end.substring(0,2));
				startDay = Integer.parseInt(start.substring(3,5));
				endDay = Integer.parseInt(end.substring(3,5));
				startYear = Integer.parseInt(start.substring(6,10));
				endYear = Integer.parseInt(end.substring(6,10));
				
				if (start.charAt(2) != '/' || end.charAt(2) != '/' || start.charAt(5) != '/' || end.charAt(5) != '/')
					err = true;
				
				boolean err2 = false;
				if (startYear == endYear) {
					if (startMonth > endMonth)
						err2 = true;
					else if (startMonth == endMonth) {
						if (startDay > endDay)
							err2 = true;
					}
				}
				else if (startYear > endYear)
					err2 = true;
				else {
					if (!validateDate(startYear,startMonth,startDay) || !validateDate(endYear,endMonth,endDay)) 
						err2 = true;
				}
					
				if (err2)
					dateErrors.add("Invalid date(s). Enter as (MM/DD/YYYY)");
			} catch (NumberFormatException e) {
				err = true;
			}
		}
		if (err) 
			dateErrors.add("Date isn't in correct format (MM/DD/YYYY)");
		
		if (!dateErrors.isEmpty()) errors.put("date",dateErrors);
	}
	
	private boolean validateDate(int year, int month, int day) {
		Calendar cal = Calendar.getInstance();
		cal.set(year,month-1,day);
		if (cal.get(Calendar.YEAR) != year || cal.get(Calendar.MONTH)!= month-1 || cal.get(Calendar.DAY_OF_MONTH) != day)
			return false;
		return true;
	}
}

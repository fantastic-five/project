package edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.responses;

import java.util.List;
import java.util.Map;

public class CreateInstructorResponse extends UseCaseResponse {
	public boolean success;
	public Map<String, List<String>> errors;
	public String instructor;
	
} //end CreateInstructorResponse

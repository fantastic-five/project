package edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.requests;

public class CreateStudentRequest extends UseCaseRequest {
	public String firstName;
	public String lastName;
	public String email;
	public String password;
	public boolean primary;
	public String DOB;
}

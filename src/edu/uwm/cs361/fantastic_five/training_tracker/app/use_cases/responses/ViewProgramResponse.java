package edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.responses;

import java.util.Set;

import edu.uwm.cs361.fantastic_five.training_tracker.app.entities.Program;
import edu.uwm.cs361.fantastic_five.training_tracker.app.entities.Student;

public class ViewProgramResponse extends UseCaseResponse {
	public Program program;
	public Set<Student> students;
}

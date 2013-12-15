package edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.responses;
import edu.uwm.cs361.fantastic_five.training_tracker.app.entities.Team;



import java.util.Set;

import edu.uwm.cs361.fantastic_five.training_tracker.app.entities.Program;
import edu.uwm.cs361.fantastic_five.training_tracker.app.entities.Student;

public class ViewTeamResponse extends UseCaseResponse {
	public Team team;
	public Set<Student> students;
}

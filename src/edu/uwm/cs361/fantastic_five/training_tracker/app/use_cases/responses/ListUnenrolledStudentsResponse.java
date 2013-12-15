package edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.responses;

import java.util.List;


// Yes, this is a violation of the concept of a response object. It makes
// things *way* easier though. This way, I don't have to create duplicate
// data structures for students and programs. Everything just works.
import edu.uwm.cs361.fantastic_five.training_tracker.app.entities.Program;
import edu.uwm.cs361.fantastic_five.training_tracker.app.entities.Student;
import edu.uwm.cs361.fantastic_five.training_tracker.app.entities.Team;

public class ListUnenrolledStudentsResponse extends UseCaseResponse {
	public Program program;
	public List<Student> unenrolledStudents;
	public Team team;

}

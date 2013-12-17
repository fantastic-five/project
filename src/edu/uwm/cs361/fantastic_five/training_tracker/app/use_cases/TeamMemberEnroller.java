package edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import edu.uwm.cs361.fantastic_five.training_tracker.app.entities.Program;
import edu.uwm.cs361.fantastic_five.training_tracker.app.entities.Student;
import edu.uwm.cs361.fantastic_five.training_tracker.app.entities.Team;
import edu.uwm.cs361.fantastic_five.training_tracker.app.services.PersistenceService;
import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.requests.EnrollStudentRequest;
import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.requests.ListUnenrolledStudentsRequest;
import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.requests.AddTeamMemberRequest;
import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.responses.EnrollStudentResponse;
import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.responses.ListUnenrolledStudentsResponse;
import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.responses.AddTeamMemberResponse;

public class TeamMemberEnroller {
	@SuppressWarnings("unchecked")
	public ListUnenrolledStudentsResponse listUnenrolledStudents(ListUnenrolledStudentsRequest req) {
		ListUnenrolledStudentsResponse resp = new ListUnenrolledStudentsResponse();

		PersistenceManager pm = getPersistenceManager();
		try {
			Query q = pm.newQuery(Student.class);
			List<Student> allStudents = (List<Student>) q.execute();

			long teamId = Long.parseLong(req.teamId);
			Team team = pm.getObjectById(Team.class, teamId);

			ArrayList<Student> unenrolledStudents = new ArrayList<Student>(allStudents);
			unenrolledStudents.removeAll(team.listStudents());

			resp.team = team;
			resp.unenrolledStudents = unenrolledStudents;
		} finally {
			pm.close();
		}

		return resp;
	}

	public AddTeamMemberResponse addTeamMember(AddTeamMemberRequest req) {
		AddTeamMemberResponse resp = new AddTeamMemberResponse();

		PersistenceManager pm = getPersistenceManager();

		try {
			if (req.studentId == null) {
				resp.success = false;
				resp.error = "No student selected";
			} else {
				long studentId = Long.parseLong(req.studentId);
				long teamId = Long.parseLong(req.teamId);

				Team team = pm.getObjectById(Team.class, teamId);
				Student student = pm.getObjectById(Student.class, studentId);

				team.addStudent(student);

				resp.success = true;
			}
		} finally {
			pm.close();
		}

		return resp;
	}

	private PersistenceManager getPersistenceManager()
	{
		return PersistenceService.getPersistenceManager();
	}
}
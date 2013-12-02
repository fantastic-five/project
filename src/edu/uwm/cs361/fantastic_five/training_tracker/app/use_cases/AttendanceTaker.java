package edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases;

import javax.jdo.PersistenceManager;

import edu.uwm.cs361.fantastic_five.training_tracker.app.entities.Session;
import edu.uwm.cs361.fantastic_five.training_tracker.app.entities.Student;
import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.requests.AttendanceRequest;
import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.responses.AttendanceResponse;
import edu.uwm.cs361.fantastic_five.training_tracker.services.PersistenceService;

public class AttendanceTaker {
	public AttendanceResponse takeAttendance(AttendanceRequest req) {
		AttendanceResponse resp = new AttendanceResponse();

		PersistenceManager pm = getPersistenceManager();

		try {
			long sessionId = Long.parseLong(req.sessionId);
			Session session = pm.getObjectById(Session.class, sessionId);
			for (String id : req.studentIds){				
				long studentId = Long.parseLong(id);
				Student student = pm.getObjectById(Student.class, studentId);
				session.addStudent(student);
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

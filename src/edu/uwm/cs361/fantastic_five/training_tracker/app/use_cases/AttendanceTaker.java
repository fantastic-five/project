package edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases;

import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;

import edu.uwm.cs361.fantastic_five.training_tracker.app.entities.Session;
import edu.uwm.cs361.fantastic_five.training_tracker.app.entities.Student;
import edu.uwm.cs361.fantastic_five.training_tracker.app.services.PersistenceService;
import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.requests.AttendanceRequest;
import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.responses.AttendanceResponse;

public class AttendanceTaker {
	public AttendanceResponse takeAttendance(AttendanceRequest req) {
		AttendanceResponse resp = new AttendanceResponse();

		PersistenceManager pm = getPersistenceManager();
		long studentId;
		long sessionId;
		Session session;
		Student student = null;
		
		try {
			sessionId = Long.parseLong(req.sessionId);
		} catch (NumberFormatException ex) {
			resp.success = false;
			resp.error = "Invalid session id.";

			return resp;
		}
		try {
			try {
				session = pm.getObjectById(Session.class, sessionId);
	
			} catch (JDOObjectNotFoundException ex) {
				resp.success = false;
				resp.error = "The specified session does not exist.";
				return resp;
			}
			try {
				if (req.ids != null) {
					for (String id : req.ids){
						studentId = Long.parseLong(id);
						try {
							student = pm.getObjectById(Student.class,studentId);
							session.addStudent(student);
						} catch (JDOObjectNotFoundException e) {
							resp.success = false;
							resp.error = "The specified student does not exist";
							return resp;
						}
					}
				}
			}
			catch (NumberFormatException ex) {
				resp.success = false;
				resp.error = "Invalid student id.";
	
				return resp;
			}
		} finally {
			pm.close();
		}
		resp.success = true;

		return resp;
	}

	private PersistenceManager getPersistenceManager()
	{
		return PersistenceService.getPersistenceManager();
	}
}

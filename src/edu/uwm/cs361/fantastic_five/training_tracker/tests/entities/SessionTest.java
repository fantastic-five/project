package edu.uwm.cs361.fantastic_five.training_tracker.tests.entities;

import static org.junit.Assert.*;

import javax.jdo.PersistenceManager;

import edu.uwm.cs361.fantastic_five.training_tracker.app.entities.Session;
import edu.uwm.cs361.fantastic_five.training_tracker.app.entities.Student;
import edu.uwm.cs361.fantastic_five.training_tracker.tests.AppEngineTest;

import org.junit.Before;
import org.junit.Test;

public class SessionTest extends AppEngineTest {
	Session session;
	Student student;
	@Before
	public void setUpTest() {
		PersistenceManager pm = getPersistenceManager();
		session = new Session("Thursday",2013,12,12);
		pm.makePersistent(session);
		student = new Student("Cassie","Dowling","11/23/1992","dowling@uwm.edu","password",true);
		pm.makePersistent(student);
	}

	@Test
	public void testGetDate() {
		assertEquals("12/12/2013", session.getDate());
	}

	@Test
	public void testGetYear() {
		assertEquals(2013, session.getYear());
	}

	@Test
	public void testGetMonth() {
		assertEquals(12, session.getMonth());
	}

	@Test
	public void testGetDayInt(){
		assertEquals(12, session.getDayInt());
	}
	@Test
	public void testGetDay() {
		assertEquals("Thursday", session.getDay());
	}

	@Test
	public void testAddStudent() {
		session.addStudent(student);
		assertEquals(1, session.getStudents().size());
		assertEquals(student,session.getStudents().iterator().next());
	}
}

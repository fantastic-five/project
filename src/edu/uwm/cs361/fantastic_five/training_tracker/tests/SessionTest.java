package edu.uwm.cs361.fantastic_five.training_tracker.tests;

import static org.junit.Assert.*;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;

import edu.uwm.cs361.fantastic_five.training_tracker.app.entities.Session;
import edu.uwm.cs361.fantastic_five.training_tracker.app.entities.Student;

import org.junit.Before;
import org.junit.Test;

public class SessionTest {
	Session session;

	@Before
	public void setUpTest() {
		this.session = new Session("12/8/13");
	}

	@Test
	public void testGetDate() {
		assertEquals("12/8/13", session.getDate());
	}
/*
	@Test
	public void testAddStudent() {
		PersistenceManager pm = getPersistenceManager();
		Student student = new Student("Cassie","Dowling", "11/23/1992", "dowling@uwm.edu","password",true);
		pm.makePersistent(student);
		session.addStudent(student);
		assertEquals(1, session.getStudents().size());
		assertEquals(student,session.getStudents().iterator().next());
	}
	*/
	private PersistenceManager getPersistenceManager()
	{
		return JDOHelper.getPersistenceManagerFactory("transactions-optional").getPersistenceManager();
	}
}
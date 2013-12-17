package edu.uwm.cs361.fantastic_five.training_tracker.tests.entities;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

import javax.jdo.PersistenceManager;

import org.junit.Before;
import org.junit.Test;

import edu.uwm.cs361.fantastic_five.training_tracker.app.entities.Student;
import edu.uwm.cs361.fantastic_five.training_tracker.tests.AppEngineTest;

public class AppEngineStudentTest extends AppEngineTest {
	Student student;

	@Before
	public void setUpTest() {
		this.student = new Student("George", "Washington", "11/11/1911","gw@gmail.com", "test_password", true);
	}

	@Test
	public void testKey() {
		Student student2 = new Student("George", "Washington", "11/11/1911", "gw@gmail.com", "test_password", true);

		PersistenceManager pm = getPersistenceManager();

		pm.makePersistent(student);
		pm.makePersistent(student2);

		assertThat(student.getKey(), is(not(student2.getKey())));
	}
}

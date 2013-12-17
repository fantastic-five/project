package edu.uwm.cs361.fantastic_five.training_tracker.tests.entities;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

import javax.jdo.PersistenceManager;

import org.junit.Before;
import org.junit.Test;

import edu.uwm.cs361.fantastic_five.training_tracker.app.entities.Instructor;
import edu.uwm.cs361.fantastic_five.training_tracker.tests.AppEngineTest;

public class AppEngineInstructorTest extends AppEngineTest {
	Instructor instructor;

	@Before
	public void setUpTest() {
		this.instructor = new Instructor("Abraham", "Lincoln", "coln267", "mypass");
	}

	@Test
	public void testKey() {
		Instructor instructor2 = new Instructor("Abraham", "Lincoln", "coln267", "mypass");

		PersistenceManager pm = getPersistenceManager();

		pm.makePersistent(instructor);
		pm.makePersistent(instructor2);

		assertThat(instructor.getKey(), is(not(instructor2.getKey())));
	}
}

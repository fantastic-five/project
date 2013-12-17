package edu.uwm.cs361.fantastic_five.training_tracker.tests.entities;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

import javax.jdo.PersistenceManager;

import org.junit.Before;
import org.junit.Test;

import edu.uwm.cs361.fantastic_five.training_tracker.app.entities.Instructor;
import edu.uwm.cs361.fantastic_five.training_tracker.app.entities.Program;
import edu.uwm.cs361.fantastic_five.training_tracker.tests.AppEngineTest;

public class AppEngineProgramTest extends AppEngineTest {
	Program program;
	Instructor instructor;
	
	@Before
	public void setUpTests() {
		this.instructor = new Instructor("Andrew", "Meyer", "andrew", "password");
		this.program = new Program("Example Program", instructor, 22.50);
	}

	@Test
	public void testKey() {
		Program program2 = new Program("Example Program", instructor, 22.50);

		PersistenceManager pm = getPersistenceManager();

		pm.makePersistent(program);
		pm.makePersistent(program2);

		assertThat(program.getKey(), is(not(program2.getKey())));
	}
}

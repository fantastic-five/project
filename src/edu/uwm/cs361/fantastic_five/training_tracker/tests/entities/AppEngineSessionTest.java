package edu.uwm.cs361.fantastic_five.training_tracker.tests.entities;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

import javax.jdo.PersistenceManager;

import org.junit.Before;
import org.junit.Test;

import edu.uwm.cs361.fantastic_five.training_tracker.app.entities.Session;
import edu.uwm.cs361.fantastic_five.training_tracker.tests.AppEngineTest;

public class AppEngineSessionTest extends AppEngineTest {
	Session session;

	@Before
	public void setUpTests() {
		this.session = new Session("Monday",2013,12,9);
	}

	@Test
	public void testKey() {
		Session session2 = new Session("Monday",2013,12,9);

		PersistenceManager pm = getPersistenceManager();

		pm.makePersistent(session);
		pm.makePersistent(session2);

		assertThat(session.getKey(), is(not(session2.getKey())));
	}
}

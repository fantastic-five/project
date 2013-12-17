package edu.uwm.cs361.fantastic_five.training_tracker.tests.entities;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import edu.uwm.cs361.fantastic_five.training_tracker.app.entities.Instructor;

public class InstructorTest {
	Instructor instructor;

	@Before
	public void setUpTest() {
		this.instructor = new Instructor("Abraham", "Lincoln", "coln267", "mypass");
	}

	@Test
	public void testGetFirstName() {
		assertEquals("Abraham", instructor.getFirstName() );
	}

	@Test
	public void testGetLastName() {
		assertEquals("Lincoln", instructor.getLastName() );
	}

	@Test
	public void testGetUsername() {
		assertEquals("coln267", instructor.getUsername());
	}

	@Test
	public void testGetPassword() {
		assertEquals("mypass", instructor.getPassword());
	}

	@Test
	public void testSetFirstName() {
		instructor.setFirstName("Benjamin");
		assertEquals("Benjamin", instructor.getFirstName() );
	}

	@Test
	public void testSetLastName() {
		instructor.setLastName("Franklin");
		assertEquals("Franklin", instructor.getLastName() );
	}

	@Test
	public void testSetUsername() {
		instructor.setUsername("abester");
		assertEquals("abester", instructor.getUsername());
	}

	@Test
	public void testSetPassword() {
		instructor.setPassword("happy");
		assertEquals("happy", instructor.getPassword());
	}
}

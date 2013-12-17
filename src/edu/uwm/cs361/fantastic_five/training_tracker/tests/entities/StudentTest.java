package edu.uwm.cs361.fantastic_five.training_tracker.tests.entities;

import static org.junit.Assert.*;

import edu.uwm.cs361.fantastic_five.training_tracker.app.entities.Student;

import org.junit.Before;
import org.junit.Test;

public class StudentTest {
	Student student;

	@Before
	public void setUpTest() {
		this.student = new Student("George", "Washington", "11/11/1911","gw@gmail.com", "mypass",true);
	}

	@Test
	public void testGetFirstName() {
		assertEquals("George", student.getFirstName() );
	}

	@Test
	public void testGetLastName() {
		assertEquals("Washington", student.getLastName() );
	}

	@Test
	public void testGetEmail() {
		assertEquals("gw@gmail.com", student.get_email());
	}

	@Test
	public void testGetPassword() {
		assertEquals("mypass", student.getPassword());
	}

	@Test
	public void testGetDOB() {
		assertEquals("11/11/1911", student.getDOB());
	}
	
	@Test
	public void testSetFirstName() {
		student.setFirstName("Benjamin");
		assertEquals("Benjamin", student.getFirstName() );
	}

	@Test
	public void testSetLastName() {
		student.setLastName("Franklin");
		assertEquals("Franklin", student.getLastName() );
	}

	@Test
	public void testSetEmail() {
		student.setEmail("al@gmail.com");
		assertEquals("al@gmail.com", student.get_email());
	}

	@Test
	public void testSetPassword() {
		student.setPassword("happy");
		assertEquals("happy", student.getPassword());
	}
	
	@Test
	public void testBalance() {
		student.updateBalance(20.5);
		assertEquals(20.5,student.getBalance(),.001);
	}
} // end class

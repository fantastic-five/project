package edu.uwm.cs361.fantastic_five.training_tracker.tests.entities;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import edu.uwm.cs361.fantastic_five.training_tracker.app.entities.Instructor;
import edu.uwm.cs361.fantastic_five.training_tracker.app.entities.Program;

public class ProgramTest {
	Program program;
	Instructor instructor;
	
	@Before
	public void setUpTests() {
		this.instructor = new Instructor("Andrew","Meyer","andrew","password");
		this.program = new Program("Example Program", instructor, 22.50);
	}

	@Test
	public void testGetName() {
		assertEquals("Example Program", program.getName());
	}
	@Test
	public void testSetName() {
		program.setName("Example Program 2");

		assertEquals("Example Program 2", program.getName());
	}

	@Test
	public void testGetInstructor() {
		assertEquals(instructor, program.getInstructor());
	}
	@Test
	public void testSetInstructor() {
		Instructor instructor2 = new Instructor("Charlie","Liberski","charlie","password");
		program.setInstructor(instructor2);

		assertEquals(instructor2, program.getInstructor());
	}

	@Test
	public void testGetPrice() {
		assertEquals(22.50, program.getPrice(), 0.001);
	}
	public void testSetPrice() {
		program.setPrice(20.00);

		assertEquals(20.00, program.getPrice(), 0.001);
	}
}

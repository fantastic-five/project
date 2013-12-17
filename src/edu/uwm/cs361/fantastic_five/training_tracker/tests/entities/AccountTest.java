package edu.uwm.cs361.fantastic_five.training_tracker.tests.entities;

import static org.junit.Assert.*;

import java.util.Iterator;

import javax.jdo.PersistenceManager;

import org.junit.Before;
import org.junit.Test;

import edu.uwm.cs361.fantastic_five.training_tracker.app.entities.Student;
import edu.uwm.cs361.fantastic_five.training_tracker.app.entities.Account;
import edu.uwm.cs361.fantastic_five.training_tracker.tests.AppEngineTest;

public class AccountTest extends AppEngineTest {
	Account account;
	Student primary;
	Student student1;
	Student student2;

	@Before
	public void setUpTests() {
		PersistenceManager pm = getPersistenceManager();
		this.primary = new Student("Andrew","Meyer","11/11/2011","andrew","password", true);
		pm.makePersistent(primary);

		this.student1 = new Student("FirstName1","LastName1","11/11/2011","user1","password",false);
		pm.makePersistent(student1);
		this.student2 = new Student("FirstName2","LastName2","11/11/2011","user2","password",false);
		pm.makePersistent(student2);
		this.account = new Account(primary, "123 Main St", "414-123-1234");
		pm.makePersistent(account);
	}

	@Test
	public void testGetPrimary() {
		assertEquals(primary, account.getPrimary());
	}

	@Test
	public void testGetAddress() {
		assertEquals("123 Main St", account.getAddress());
	}

	@Test
	public void testGetPhone() {
		assertEquals("414-123-1234", account.getPhone());
	}

	@Test
	public void testGetEmail() {
		assertEquals(primary.get_email(), account.getEmail());
	}

	@Test
	public void testAddDependents() {
		account.addDependent(student1);
		account.addDependent(student2);

		Iterator<Student> it = account.getDependents().iterator();
		assertEquals(2, account.getDependents().size());
		assertEquals("11/11/2011",it.next().getDOB());
		assertEquals("11/11/2011",it.next().getDOB());
	}

	@Test
	public void testUpdateBalance() {
		account.addDependent(student1);
		account.addDependent(student2);
		Iterator<Student> it = account.getDependents().iterator();

		account.getPrimary().updateBalance(20.5);
		it.next().updateBalance(-10.0);
		it.next().updateBalance(5.75);
		assertEquals(16.25,account.getBalance(),.001);
	}
}

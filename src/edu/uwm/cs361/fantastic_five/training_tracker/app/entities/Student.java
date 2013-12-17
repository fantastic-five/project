package edu.uwm.cs361.fantastic_five.training_tracker.app.entities;

import java.util.Set;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.datanucleus.annotations.Unowned;

@PersistenceCapable
public class Student {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;

	@Persistent
	private String firstName;

	@Persistent
	private String lastName;
	
	@Persistent
	private String DOB;
	
	@Persistent
	private String _email;
	
	@Persistent
	private String _password;
	
	@Persistent
	private boolean primary;
	
	@Persistent
	private double balance;
	@Unowned
	@Persistent
	private Set<Program> programs;

	//****************************************************

	public Student(String firstName, String lastName, String DOB, String _email, String _pass, boolean primary) {

		this.firstName = firstName;
		this.lastName = lastName;
		this.DOB = DOB;
		this._email = _email;
		this._password = _pass;
		this.primary = primary;
		this.balance = 0;
	}

	//****************************************************

	@Override
	public boolean equals(Object other) {
		if (other == null || !(other instanceof Student)) return false;
		if (other == this) return true;

		Student otherS = (Student) other;
		if (!bothNullOrEqual(this.getKey(), otherS.getKey())) return false;
		if (!bothNullOrEqual(this.firstName, otherS.firstName)) return false;
		if (!bothNullOrEqual(this.lastName, otherS.lastName)) return false;
		if (!bothNullOrEqual(this._email, otherS._email)) return false;

		return true;
	}
	private boolean bothNullOrEqual(Object object1, Object object2) {
		return (object1 == null ? object2 == null : object1.equals(object2));
	}

	public Key getKey() {
		return this.key;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public String get_email(){
		return this._email;
	}
	
	public String getPassword() {
		return this._password;
	}

	public String getFullName() {
		return(this.firstName +" " + this.lastName);
	}

	public void setFirstName(String newFirstName) {
		this.firstName = newFirstName;
	}

	public void setLastName(String newLastName) {
		this.lastName = newLastName;
	}

	public void setEmail(String e) {
		this._email = e;
	}
	
	public void setPassword(String p) {
		this._password = p;
	}

	public String getDOB() {
		return DOB;
	}
	
	public void addProgram(Program program){
		programs.add(program);
	}
	public Set<Program> getPrograms() {
		return programs;
	}

	public boolean isPrimary() {
		return primary;
	}
	
	public double getBalance(){
		return balance;
	}
	public String balanceToString(){
		return String.format("$%.2f", new Double(balance));
	}
	public void updateBalance(double price){
		balance+=price;
	}
} //end class
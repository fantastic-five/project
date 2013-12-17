package edu.uwm.cs361.fantastic_five.training_tracker.app.entities;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable
public class Instructor {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;

	@Persistent
	private String firstName;

	@Persistent
	private String lastName;

	@Persistent
	private String _username;
	
	@Persistent
	private String _password;



	//****************************************************

	public Instructor(String firstName, String lastName, String _username, String _pass) {

		this.firstName = firstName;
		this.lastName = lastName;
		this._username = _username;
		this._password = _pass;
	}

	//****************************************************
	public boolean equals(Object other) {
		if (other == null || !(other instanceof Instructor)) return false;
		if (other == this) return true;

		Instructor otherI = (Instructor) other;
		if (!bothNullOrEqual(this.getKey(), otherI.getKey())) return false;
		if (!bothNullOrEqual(this.firstName, otherI.firstName)) return false;
		if (!bothNullOrEqual(this.lastName, otherI.lastName)) return false;
		if (!bothNullOrEqual(this._username, otherI._username)) return false;

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

	public String getUsername(){
		return this._username;
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
	
	public void setUsername(String u) {
		this._username = u;
	}

	public void setPassword(String p) {
		this._password = p;
	}
	
} //end class

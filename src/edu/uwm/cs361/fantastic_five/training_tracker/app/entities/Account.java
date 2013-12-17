package edu.uwm.cs361.fantastic_five.training_tracker.app.entities;
import java.util.Set;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.datanucleus.annotations.Unowned;

@PersistenceCapable
public class Account {
	
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;

	@Unowned
	@Persistent
	private Student primary;
	
	@Unowned
	@Persistent
	private Set<Student> dependents;
	
	@Persistent
	private String address;
	
	@Persistent
	private String phone;
	
	@Persistent
	private double balance;
	
	public Account(Student owner, String address, String phone) {
		this.primary = owner;
		this.address = address;
		this.phone = phone;
	}
	
	public Key getKey() {
		return key;
	}
	
	public Student getPrimary() {
		return this.primary;
	}
	
	public Set<Student> getDependents() {
		return dependents;
	}
	public void addDependent(Student s) {
		dependents.add(s);
	}
	
	public String getEmail() {
		return primary.get_email();
	}
	
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public double getBalance(){
		double balance = primary.getBalance();
		for (Student s : dependents) {
			balance += s.getBalance();
		}
		return balance;
	}
	public String balanceToString(){
		return String.format("$%.2f", new Double(getBalance()));
	}
}

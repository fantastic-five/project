package edu.uwm.cs361.fantastic_five.training_tracker.app.entities;

import java.util.Set;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.datanucleus.annotations.Unowned;

@PersistenceCapable
public class Session implements Comparable<Session> {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;
	
	@Unowned
	@Persistent
	private Set<Student> students;
	
	@Persistent
	private String date;
	
	@Persistent
	private String day;
	
	@Persistent
	private int dayInt;
	
	@Persistent
	private int month;
	
	@Persistent
	private int year;
	
	public Session(String day, int year, int month, int dayInt){
		this.day = day;
		this.year = year;
		this.month = month;
		this.dayInt = dayInt;
		this.date = "" + this.month + "/" + this.dayInt + "/" + this.year;
	}

	public Key getKey(){
		return key;
	}
	
	public String getDate() {
		return date;
	}
	
	public String getDay() {
		return day;
	}
	public int getDayInt() {
		return dayInt;
	}
	public int getMonth() {
		return month;
	}
	public int getYear() {
		return year;
	}
	
	public void addStudent(Student student){
		students.add(student);
	}
	
	public Set<Student> getStudents(){
		return students;
	}

	public int compareTo(Session session) {
		if (this.year > session.year)
			return 1;
		else if (this.year < session.year)
			return -1;
		else if (this.month > session.month)
			return 1;
		else if (this.month < session.month)
			return -1;
		else if (this.dayInt > session.dayInt)
			return 1;
		else if (this.dayInt < session.dayInt)
			return -1;
		
		return 0;
	}
}
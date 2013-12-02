package edu.uwm.cs361.fantastic_five.training_tracker.app.entities;

import java.util.Set;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.datanucleus.annotations.Unowned;

@PersistenceCapable
public class Session {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;
	
	@Unowned
	@Persistent
	private Program program;
	@Unowned
	@Persistent
	private Set<Student> students;
	@Unowned
	@Persistent
	private String date;
	public Session(Program program, String date){
		this.program = program;
		this.date = date;
	}

	public Key getKey(){
		return key;
	}
	public String getString() {
		return program.getName() + ": " + date;
	}
	public Program getProgram(){
		return program;
	}
	public String getDate(){
		return date;
	}
	public void addStudent(Student student){
		students.add(student);
	}
	public Set<Student> getAttendance(){
		return students;
	}
}

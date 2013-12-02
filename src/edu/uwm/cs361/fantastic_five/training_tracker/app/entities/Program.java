package edu.uwm.cs361.fantastic_five.training_tracker.app.entities;

import java.util.List;
import java.util.Set;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.datanucleus.annotations.Unowned;

@PersistenceCapable
public class Program {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;

	@Persistent
	private String name;

	@Persistent
	private String instructor;

	@Persistent
	private double price;
	
	
	@Persistent
	private double discount;
	
	@Persistent
	private boolean chooseTimes;

	@Unowned
	@Persistent
	private Set<Student> students;
	
	
	@Persistent
	private List<time> times;
	
	
	public Program(String name, String instructor, double price)//, List<time> times)
	{
		this.name = name;
		this.instructor = instructor;
		this.price = price;
		//this.times= times;
		
	}

	public Program(String name, String instructor, double price, List<time> times)
	{
		this.name = name;
		this.instructor = instructor;
		this.price = price;
		this.times= times;
		
	}

	public Key getKey()
	{
		return key;
	}

	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}

	public String getInstructor()
	{
		return instructor;
	}
	public void setInstructor(String instructor)
	{
		this.instructor = instructor;
	}

	public double getPrice(){
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}

	public double getRevenue(){
		return students.size()*price;
	}

	public void addStudent(Student student){
		students.add(student);
	}
	public Set<Student> listStudents(){
		return students;
	}

	public String getTimes(){
		String timeOut = "";
		for(time t: times){
			//if(timeOut == null)timeOut = t.toString();
			timeOut = (timeOut + "\n" + t.toString());
		}
		return timeOut;
	}

	private class dates{

	}

	public void addSession(Session session){
		sessions.add(session);
	}
	public Set<Session> getSessions() {
		return sessions;
	}

}




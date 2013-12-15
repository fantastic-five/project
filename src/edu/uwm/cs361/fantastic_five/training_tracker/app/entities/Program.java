package edu.uwm.cs361.fantastic_five.training_tracker.app.entities;

import java.util.List;
import java.util.Set;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import com.google.appengine.datanucleus.annotations.Unowned;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable
public class Program {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;

	@Persistent
	private String name;

	@Unowned
	@Persistent
	private Instructor instructor;

	@Persistent
	private double price;
	
	@Persistent
	private double discount;
	
	@Persistent
	private boolean chooseTimes;

	@Unowned
	@Persistent
	private Set<Student> students;
	
	@Unowned
	@Persistent
	private List<Session> sessions;
	
	@Persistent
	private List<time> times;
	
	
	public Program(String name, Instructor instructor, double price)
	{
		this.name = name;
		this.instructor = instructor;
		this.price = price;		
	}

	public Program(String name, Instructor instructor, double price, List<time> times)
	{
		this.name = name;
		this.instructor = instructor;
		this.price = price;
		this.times= times;
		
	}
	
	public boolean getchooseTimes(){
		return chooseTimes;
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

	public Instructor getInstructor()
	{
		return instructor;
	}
	public void setInstructor(Instructor instructor)
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
	public Set<Student> getStudents(){
		return students;
	}
	
	public List<time> getTimes(){
		return times;
	}
	
	public void addSession(Session session){
		int i;
		for (i=0;i<sessions.size() && session.compareTo(sessions.get(i))>0;++i)
		{}
		sessions.add(i,session);
	}
	public List<Session> getSessions(){
		return sessions;
	}
	
}




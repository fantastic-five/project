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
	private double revenue;
	
	@Persistent
	private double discount;
	
	@Persistent
	private boolean chooseTimes;

	@Unowned
	@Persistent
	private Set<Student> students;
	
	@Unowned
	@Persistent
	private Award prereq;
	
	@Unowned
	@Persistent
	private Set<Session> sessions;
	
	@Persistent
	private List<Time> times;
	
	
	public Program(String name, Instructor instructor, double price)
	{
		this.name = name;
		this.instructor = instructor;
		this.price = price;		
	}

	public Program(String name, Instructor instructor, double price, List<Time> times)
	{
		this.name = name;
		this.instructor = instructor;
		this.price = price;
		this.times= times;
		
	}
	
	public Program(String name, Instructor instructor, double price, double discount, List<Time> times)
	{
		this.name = name;
		this.instructor = instructor;
		this.price = price;
		this.times= times;
		this.discount = discount;
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
	
	public void setPrereq(Award prereq){
		this.prereq = prereq;
	}
	
	public Award getPrereq(){
		return prereq;
	}

	public double getPrice(){
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	
	public double getDiscount(){
		return discount;
	}
	public void setDisctount(double discount) {
		this.discount = discount;
	}

	public double updateRevenue(double charge){
		return revenue += charge;
	}
	
	public double getRevenue(){
		return revenue;
	}

	public void addStudent(Student student){
		students.add(student);
	}
	public Set<Student> getStudents(){
		return students;
	}
	
	public List<Time> getTimes(){
		return times;
	}
	
	public void addSession(Session session){
		sessions.add(session);
	}
	public Set<Session> getSessions(){
		return sessions;
	}
	
	private class dates{
		
	}
	
}




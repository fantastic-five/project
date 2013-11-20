package edu.uwm.cs361.fantastic_five.training_tracker.entities;

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

	@Unowned
	@Persistent
	private Set<Student> students;

	public Program(String name, String instructor, double price)
	{
		this.name = name;
		this.instructor = instructor;
		this.price = price;
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
}

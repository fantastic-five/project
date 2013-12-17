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
	private List<Time> times;

	@Persistent
	private String startDate;

	@Persistent
	private String endDate;

	public Program(String name, Instructor instructor, double price)
	{
		this.name = name;
		this.instructor = instructor;
		this.price = price;
	}

	public Program(String name, Instructor instructor, double price, List<Time> times, String start, String end)
	{
		this.name = name;
		this.instructor = instructor;
		this.price = price;
		this.times= times;
		this.startDate = start;
		this.endDate = end;
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

	public List<Time> getTimes(){
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

	public String getStartDate() {
		return startDate;
	}
	public int[] getStartDateArray() {
		return getDateArray(startDate);
	}
	public String getEndDate() {
		return endDate;
	}
	public int[] getEndDateArray() {
		return getDateArray(endDate);
	}
	private int[] getDateArray(String date) {
		int[] dateArray = new int[3];
		dateArray[0] = Integer.parseInt(""+date.charAt(0)+date.charAt(1));		//month
		dateArray[1] = Integer.parseInt(""+date.charAt(3)+date.charAt(4));		//day
		dateArray[2] = Integer.parseInt(""+date.charAt(6)+date.charAt(7)+date.charAt(8)+date.charAt(9));	//year
		return dateArray;
	}
}




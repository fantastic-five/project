package edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.requests;

import java.util.List;

import edu.uwm.cs361.fantastic_five.training_tracker.app.entities.Time;

public class CreateProgramRequest extends UseCaseRequest {
	public String name;
	public String instructor;
	public String price;
	public String discount;
	public List<Time> dates;
	public String startDate;
	public String endDate;
}

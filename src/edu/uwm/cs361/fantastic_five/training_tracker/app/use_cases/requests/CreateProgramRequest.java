package edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.requests;

import java.util.List;

import edu.uwm.cs361.fantastic_five.training_tracker.app.entities.time;

public class CreateProgramRequest {
	public String name;
	public String instructor;
	public String price;
	public String discount;
	public List<time> dates;
	public String startDate;
	public String endDate;
}

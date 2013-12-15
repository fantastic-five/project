package edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.requests;

import java.util.List;
import edu.uwm.cs361.fantastic_five.training_tracker.app.entities.time;

public class CreateSessionsRequest {
	public List<time> dates;
	public String programId;
}

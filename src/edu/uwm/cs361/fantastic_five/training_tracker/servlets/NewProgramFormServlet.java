package edu.uwm.cs361.fantastic_five.training_tracker.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.uwm.cs361.fantastic_five.training_tracker.app.entities.Time;
import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.InstructorFinder;
import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.ProgramCreator;
import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.requests.CreateProgramRequest;
import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.responses.CreateProgramResponse;
import edu.uwm.cs361.fantastic_five.training_tracker.app.use_cases.responses.ListInstructorsResponse;

@SuppressWarnings("serial")
public class NewProgramFormServlet extends BaseServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		ListInstructorsResponse listInstructorsResp = new InstructorFinder().listInstructors();

		if (listInstructorsResp.instructors != null && listInstructorsResp.instructors.size() != 0) {
			req.setAttribute("instructors", listInstructorsResp.instructors);
			forwardToJsp("new_program_form.jsp", req, resp);
		}
		else {
			forwardToJsp("new_program_error.jsp", req, resp);
		}
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException
	{
		CreateProgramRequest createRequest = new CreateProgramRequest();
		createRequest.name = req.getParameter("name");
		createRequest.instructor = req.getParameter("instructor");
		createRequest.price = req.getParameter("price");

		createRequest.startDate = req.getParameter("startDate");
		createRequest.endDate = req.getParameter("endDate");
		
		boolean chooseDays;
		if(req.getParameter("chooseDays") != null && req.getParameter("chooseDays").equals("yes"))chooseDays = true;
		

		String[] day_array = req.getParameterValues("day");
		//String[] day_array = days.split(",");
		List<Time> datesAndTimes = new ArrayList<Time>();
		for(String s:day_array){
			if(s.equals("sun")){
				datesAndTimes.add(new Time("Sunday",1,req.getParameter("sun_start"), req.getParameter("mon_end")));
			}
			else if(s.equals("mon")){
				datesAndTimes.add(new Time("Monday",2,req.getParameter("mon_start"), req.getParameter("mon_end")));
			}else if(s.equals("tue")){
				datesAndTimes.add(new Time("Tuesday",3,req.getParameter("tue_start"), req.getParameter("tue_end")));
			}else if(s.equals("wed")){
				datesAndTimes.add(new Time("Wednesday",4,req.getParameter("wed_start"), req.getParameter("wed_end")));
			}else if(s.equals("thu")){
				datesAndTimes.add(new Time("Thursday",5,req.getParameter("thu_start"), req.getParameter("thu_end")));
			}else if(s.equals("fri")){
				datesAndTimes.add(new Time("Friday",6,req.getParameter("fri_start"), req.getParameter("fri_end")));
			}else if(s.equals("sat")){
				datesAndTimes.add(new Time("Saturday",7,req.getParameter("sat_start"), req.getParameter("sat_end")));
			}
		}
		createRequest.dates = datesAndTimes;

		CreateProgramResponse createResponse = new ProgramCreator().createProgram(createRequest);

		if (createResponse.success) {
			resp.sendRedirect("/programs");
		} else {
			req.setAttribute("name", req.getParameter("name"));
			req.setAttribute("instructor", req.getParameter("instructor"));
			req.setAttribute("price", req.getParameter("price"));

			req.setAttribute("errors", createResponse.errors);

			forwardToJsp("new_program_form.jsp", req, resp);
		}
	}
}

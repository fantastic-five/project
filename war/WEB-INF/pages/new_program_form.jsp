<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:mainLayout title="New Program">
	<t:validationErrors errors="${errors}">
		<h2>Errors occurred while attempting to create a new program!</h2>
	</t:validationErrors>

	<form class='well' action='' method='POST'>
		<label for='name'>Program name:</label>
		<input type='text' id='name' name='name' value='${name}' />
		<br>
		<br>
		
		<label for='instructor'>Instructor:</label>
		<select name='instructor'>
		<c:forEach items="${instructors}" var="instructor">
			<option id='instructor' name='instructor' value='${instructor.key.id}'> ${instructor.fullName} </option>
		</c:forEach>
		</select>
		<br>
		<br>
		<label for='price'>Price:</label>
		<input type='text' id='price' name='price' value='${price}' />
		<label for='discountPrice'>Family Price:</label>
		<input type='text' id='discountprice' name='discountPrice' />
		<br>
		<br>
		<label for='startDate'>Start Date:</label>
		<input type='text' id='startDate' name='startDate' value='${startDate}' />
		<label for='endDate'>End Date:</label>
		<input type='text' id='endDate' name='endDate' value='${endDate}' />
		<br>
		<ul class="accordion">
		<li>
		<h2><input type="checkbox" name="day" value="sun"> Sunday</h2>
		<div class ="content">
		<label for='sun_start'>start:</label>
		<input type='text' id='sun_start' name='sun_start' />
		<label for='sun_start'>end:</label>
		<input type='text' id='sun_end' name='sun_end' />
		</div>
		</li>
		
		<ul class="accordion">
		<li>
		<h2><input type="checkbox" name="day" value="mon"> Monday</h2>
		<div class ="content">
		<label for='sun_start'>start:</label>
		<input type='text' id='mon_start' name='mon_start' />
		<label for='mon_end'>end:</label>
		<input type='text' id='mon_end' name='mon_end' />
		</div>
		</li>
		
		<ul class="accordion">
		<li>
		<h2><input type="checkbox" name="day" value="tue"> Tuesday</h2>
		<div class ="content">
		<label for='sun_start'>start:</label>
		<input type='text' id='tue_start' name='tue_start' />
		<label for='tue_end'>end:</label>
		<input type='text' id='tue_end' name='tue_end' />
		</div>
		</li>
		
		<ul class="accordion">
		<li>
		<h2><input type="checkbox" name="day" value="wed"> Wednesday</h2>
		<div class ="content">
		<label for='sun_start'>start:</label>
		<input type='text' id='wed_start' name='wed_start' />
		<label for='wed_end'>end:</label>
		<input type='text' id='wed_end' name='wed_end' />
		</div>
		</li>
		
	
		<ul class="accordion">
		<li>
		<h2><input type="checkbox" name="day" value="thu"> Thursday</h2>
		<div class ="content">
		<label for='sun_start'>start:</label>
		<input type='text' id='thu_start' name='thu_start' />
		<label for='thu_end'>end:</label>
		<input type='text' id='thu_end' name='thu_end' />
		</div>
		</li>
		
		<ul class="accordion">
		<li>
		<h2><input type="checkbox" name="day" value="fri"> Friday</h2>
		<div class ="content">
		<label for='fri_start'>start:</label>
		<input type='text' id='fri_start' name='fri_start' />
		<label for='fri_end'>end:</label>
		<input type='text' id='fri_end' name='fri_end' />
		</div>
		</li>
		
		<ul class="accordion">
		<li>
		<h2><input type="checkbox" name="day" value="sat"> Saturday</h2>
		<div class ="content">
		<label for='sat_start'>start:</label>
		<input type='text' id='sat_start' name='sat_start' />
		<label for='sat_end'>end:</label>
		<input type='text' id='sat_end' name='end_end' />
		</div>
		</li>
		
		<input type="checkbox" name="chooseDays" value="yes"> Allow students to choose days to attend?
		<input type='submit' value='Create program' />
	</form>
</t:mainLayout>

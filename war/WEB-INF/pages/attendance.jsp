<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<t:mainLayout title="Attendance">
	<h1>Attendance for ${program.name}:</h1>
	
	<form class='well' action='' method='POST'>
	<label for='date'>Date:</label>
	<select name='date'>
		<c:forEach items="${program.sessions}" var="session">
			<option id='date' name='date' value='${session.key.id}'> ${session.date} </option>
		</c:forEach>
		</select>
		<br>
		<br>
		<c:if test='${not empty program}'>
			<form action='' method = 'POST'>
				<c:forEach items="${program.students}" var="student">
					<input type='checkbox' name='students' value='${student.key.id}'/> ${student.fullName}
					<br/>
				</c:forEach>
				<input type='submit' value='Take Attendance'>
			</form>
		</c:if>
</t:mainLayout>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<t:mainLayout title="Student Programs">
		<ul class="accordion">
		<c:forEach items="${programs}" var="program">
			<li>
			<h2>${program.name} 
				(${program.startDate} - ${program.endDate})
				<a href="/attendance/student?id=${program.key.id}">View Attendance</a>
			</h2>
			<div class ="content">
			<strong>Instructor: </strong>${program.instructor.fullName}
			<br/>
			<strong>Price: </strong>${program.price}
			<br/>
			<br/>
			<center><strong>Schedule: </strong></center>
			<c:if test="${not empty program.times}">
				<c:forEach items= "${program.times}" var="time">
					<br/>
					${time.day}:
					${time._times}
					<hr/>
				</c:forEach>
			</c:if>
		</c:forEach>
		</ul>
		<br/>
		<br/>
</t:mainLayout>
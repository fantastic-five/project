<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<t:mainLayout title="Instructor Programs">
		<ul class="accordion">
		<c:forEach items="${programs}" var="program">
			<li>
			<h2>${program.name} <a href="/attendance/student?id=${program.key.id}">View Attendance</a></h2>
			<div class ="content">${program.instructor.fullName}
			<br/>
			${program.price}
			<c:if test="${not empty program.times}">
				<c:forEach items= "${program.times}" var="time">
					<br/>
					${time.day}
					${time._times}
					<hr/>
				</c:forEach>
			</c:if>
		</c:forEach>
		</ul>
		<br/>
		<br/>
</t:mainLayout>
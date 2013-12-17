<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<t:mainLayout title="Instructor Attendance View">
		<ul class="accordion">
		<c:forEach items="${program.sessions}" var="session">
			<li>
			<h2>${session.date} (${session.day})</h2>
			<div class ="content">
			<c:if test="${not empty session.students}">
				<c:forEach items= "${session.students}" var="student">
					${student.fullName}
					<br/>
				</c:forEach>
			</c:if>
		</c:forEach>
		</ul>
		<br/>
		<br/>
</t:mainLayout>
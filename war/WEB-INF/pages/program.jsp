<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:mainLayout title="${program.name}">
	<c:if test="${not empty program}">
		<h1>Enrolled Students in ${program.name}:</h1>
		<ul>
			<c:forEach items="${students}" var="student">
				<li>${student.fullName}</li>
			</c:forEach>
		</ul>
		<a href='/programs/enroll?id=${program.key.id}'>Enroll Student</a>
		<a href='/schedule/instructor?id=${program.key.id}'>View Schedule</a>
	</c:if>
</t:mainLayout>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:mainLayout title="${session.program.name}">
	<c:if test="${not empty session}">
		<h1>Attendance for ${session.program.name}:</h1>
		<form action='' method = 'POST'>
		<table>
		<tr>
			<th>Student</th>
			<th>Attended</th>
		</tr>
		<tr>
			<c:forEach items="${students}" var="student">
				<td>${student.fullName}</td>
				<td>input type='checkbox' name='attended' value='${student.key.id}'</td>
			</c:forEach>
		</tr>
		</table>
		<input type="submit" value="Take Attendance"/>
	</c:if>
</t:mainLayout>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<t:mainLayout title="Student Attendance">
		<div class="well">
		<h2> ${program.name} Attendance</h2>
		<table cellspacing='10'>
		<c:forEach items="${program.sessions}" var="session">
			<tr>
				<c:set var="found" value="false"/>
				<td><strong>${session.date}: </strong> </td>
				<c:if test="${empty session.students}">
					<c:set var="found" value="true"/>
					<td>--</td>
				</c:if>
				<c:forEach items="${session.students}" var="s">
					<c:if test="${student.key.id == s.key.id}">
						<td>Attended</td>
						<c:set var="found" value="true"/>
					</c:if>
				</c:forEach>
				<c:if test="${found == 'false'}">
					<td>Absent</td>
				</c:if>
			</tr>
		</c:forEach>
		</table>
		</div>
		<br/>
		<br/>
</t:mainLayout>
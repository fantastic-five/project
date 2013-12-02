<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:mainLayout title="Schedule">
	<form class='well' action='' method='POST'>

		<c:if test='${not empty program}'>

			<h1> Schedule for ${program.name}:</h1>
			<form action='' method = 'POST'>
				<c:forEach items="${program.sessions}" var="session">
					<br><input type='radio' name='session' value='${session.key.id}'/> '${session.string} '
				</c:forEach>
					<br><input type='submit' value='Attendance'>
			</form>
		</c:if>
</t:mainLayout>

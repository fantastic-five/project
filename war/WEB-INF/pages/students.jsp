<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<t:mainLayout title="Students">
	<ul class="accordion">
		<c:forEach items="${students}" var="student">
			<li>
				<h2>${student.fullName}</h2>
				<div class="content">
					<strong>Email: </strong>${student._email} 
					<br/>
					<strong>Date of Birth: </strong>${student.DOB}
					<br/>
				</div>
			</li>
			<hr/>
		</c:forEach>
	</ul>
	<footer>
		<a href='students/new' class="button button-action blue">New Student</a>
	</footer>
</t:mainLayout>
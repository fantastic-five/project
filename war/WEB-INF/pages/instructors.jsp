<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<t:mainLayout title="Instructors">
<ul class="accordion">
		<c:forEach items="${instructors}" var="instructor">

			<li>
			<h2><a href=""> ${instructor.fullName}</a></h2>
			<div class ="content">
			${instructor.username}
			<br/>
			</div>
			</li>
			<hr/>
		</c:forEach>
		</ul>
		<footer>
		<a href='instructors/new' class="button button-action blue">New Instructor</a>
		</footer>
		<br/>
		<br/>
</t:mainLayout>
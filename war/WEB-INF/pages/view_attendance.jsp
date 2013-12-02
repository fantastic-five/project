<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<t:mainLayout title="Attended Students">
<ul class="accordion">
		<c:forEach items="${students}" var="student">
			<li>
			<h2>${student.fullName}</h2>
			<div class ="content">
			${student._email}
			<br/>
			balance $
			${student.balance}
			</div>
			</li>
			<hr/>
		</c:forEach>
		</ul>
		<br/>
		<br/>
</t:mainLayout>

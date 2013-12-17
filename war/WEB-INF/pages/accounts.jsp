<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<t:mainLayout title="Accounts">
		<ul class="accordion">
		<c:forEach items="${accounts}" var="account">
			<li>
			<h2>${account.primary.fullName} (${account.email})</a></h2>
			<div class ="content">
			<br/>
			<strong>Address:</strong> ${account.address}
			<br/>
			<strong>Phone:</strong> ${account.phone}
			<br/>
			<strong>Balance:</strong> ${account.balance}
			<hr/>
			<strong>Dependents:</strong> 
			<c:if test="${not empty account.dependents}">
				<c:forEach items= "${account.dependents}" var="student">
					<br/>
					${student.fullName}
				</c:forEach>
			</c:if>
		</c:forEach>
		</ul>
		<br/>
		<br/>
</t:mainLayout>

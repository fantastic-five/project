<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:validationErrors errors="${errors}">
		<h2>Errors occurred while attempting to create a new dependent!</h2>
	</t:validationErrors>
	
<t:mainLayout title="New Dependent">
	<form class='well' method='POST'>
		<label for='account'>Primary Account: </label>
		<select name="primary">
		<c:forEach items="${accounts}" var="account">
			<option value='${account.primary.key.id}'> ${account.primary.fullName} (${account.email})</option>
		</c:forEach>
		</select>
		<input type='submit' value='Add to Account' />
	</form>
</t:mainLayout>
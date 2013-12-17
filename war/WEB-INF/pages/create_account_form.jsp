<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:mainLayout title="New Account">
	<t:validationErrors errors="${errors}">
		<h2>Errors occurred while attempting to create a new account!</h2>
	</t:validationErrors>

	<form class='well' method='POST'>
		Primary Account Holder Information:
		<br>
		<label for='address'>Address: </label>
		<input type='text' id='address' name='address' value='${address}' />
		<br>
		<label for='phone'>Phone: </label>
		<input type='text' id='phone' name='phone' value='${phone}' />
		<br>
		<br>
		<input type='submit' value='Create Account' />
	</form>
</t:mainLayout>
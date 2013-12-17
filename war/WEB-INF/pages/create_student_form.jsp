<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:mainLayout title="New Student">
	<t:validationErrors errors="${errors}">
		<h2>Errors occurred while attempting to create a new student!</h2>
	</t:validationErrors>

	<form class='well' method='POST'>
		<label for='firstName'>First Name: </label>
		<input type='text' id='firstName' name='firstName' value='${firstName}' />
		<br>
		<label for='lastName'>Last Name: </label>
		<input type='text' id='lastName' name='lastName' value='${lastName}' />
		<br>
		<label for='dob'>Date of Birth: </label>
		<input type='text' id='dob' name='dob' value='${dob}' />
		<br>
		<label for='email'>E-mail: </label>
		<input type='text' id='email' name='email' value='${email}' />
		<br>
		<label for='password'>Password: </label>
		<input type='password' id='password' name='password' />
		<br>
		<br>
		Primary Account Holder:
		<br>
		<input type='radio' id='primary' name='primary' value='${true}' checked='checked'/>Yes
		<input type='radio' id='primary' name='primary' value='${false}'/> No
		<br>
		<input type='submit' value='Create Student' />
	</form>
</t:mainLayout>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:mainLayout title="New Student">
	<form class='well' method='POST'>
		<label for='firstName'>First Name: </label>
		<input type='text' id='firstName' name='firstName' />
		<br>
		<label for='lastName'>Last Name: </label>
		<input type='text' id='lastName' name='lastName' />
		<br>
		<label for='username'>Username: </label>
		<input type='text' id='username' name='username' />
		<br>
		<label for='password'>Password: </label>
		<input type='password' id='password' name='password' />
		<br>
		<input type='submit' value='Create Instructor' />
	</form>
</t:mainLayout>
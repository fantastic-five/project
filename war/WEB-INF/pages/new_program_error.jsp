<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<t:mainLayout title="New Program Error">
	
	<div class="error-message">
		<h2>**Error: You must create an instructor before adding a new program.**</h2>
		<a href='/instructors/new' class="button blue">Create Instructor</a>
	</div>
	
</t:mainLayout>

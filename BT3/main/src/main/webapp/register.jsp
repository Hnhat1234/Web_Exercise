<%@ page language="java" contentType="text/html; charset=US-ASCII" pageEncoding="US-ASCII"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "https://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>Register</title>
<style>
	form {
		width: 300px;
		margin: 80px auto;
	}
	label, input {
		display: block;
		width: 100%;
		margin-bottom: 10px;
	}
	form a {
		display: block;
		margin-top: 10px;
	}
</style>
</head>

<body>

<form action="register" method="post">
	<label for="email">Email</label>
	<input type="email" id="email" name="email">
	<label for="username">Username</label>
	<input type="text" id="username" name="username">
	<label for="password">Password</label>
	<input type="password" id="password" name="password">
	<label for="full_name">Full name</label>
	<input type="text" id="full_name" name="full_name">
	<label for="phone_number">Phone number</label>
	<input type="text" id="phone_number" name="phone_number">
	<input type="submit" value="Submit">

	<a href="login">Back to login</a>

	<% if (request.getAttribute("error") != null) { %>
		<p><%= request.getAttribute("error") %></p>
	<% } %>
</form>

</body>
</html>

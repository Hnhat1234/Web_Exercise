<%@ page language="java" contentType="text/html; charset=US-ASCII" pageEncoding="US-ASCII"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "https://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>Login</title>
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

<form action="login" method="post">
	<label for="username">Username</label>
	<input type="text" id="username" name="username">
	<label for="password">Password</label>
	<input type="password" id="password" name="password">
	<input type="submit" value="Submit">
	
	<a href="register">Register</a>
	
	<% if (request.getAttribute("error") != null) { %>
		<p><%= request.getAttribute("error") %></p>
	<% } %>
	<% if ("1".equals(request.getParameter("registered"))) { %>
		<p>Registration successful. Please log in.</p>
	<% } %>
</form>

</body>
</html>

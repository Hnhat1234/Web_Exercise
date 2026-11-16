<%@ page language="java" contentType="text/html; charset=US-ASCII" pageEncoding="US-ASCII"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "https://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>Verify OTP</title>
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

<%
	String email = (String) request.getAttribute("email");
	if (email == null) {
		email = (String) session.getAttribute("otpEmail");
	}
%>

<body>

<form action="register" method="post">
	<input type="hidden" name="action" value="verify">
	<p>We sent an OTP to <b><%= email %></b>. Enter it below to activate your account.</p>
	<label for="otp">OTP</label>
	<input type="text" id="otp" name="otp" maxlength="4" inputmode="numeric" autocomplete="one-time-code">
	<input type="submit" value="Verify">

	<a href="register">Back to register</a>

	<% if (request.getAttribute("error") != null) { %>
		<p><%= request.getAttribute("error") %></p>
	<% } %>
</form>

</body>
</html>

<%@ page language="java" contentType="text/html; charset=US-ASCII" pageEncoding="US-ASCII"%>
<%
	String username = (String) session.getAttribute("username");
	if (username == null) {
		response.sendRedirect("login");
		return;
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "https://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>Home</title>
<style>
	.header {
		display: flex;
		justify-content: space-between;
		align-items: center;
		padding: 10px 20px;
	}
	.content {
		width: 600px;
		margin: 80px auto;
	}
</style>
</head>

<body>

<div class="header">
	<h1>Home</h1>
	<a href="logout">Logout</a>
</div>

<div class="content">
	<h2>Welcome, <%= username %></h2>
	<p>You have logged in successfully.</p>
</div>

</body>
</html>

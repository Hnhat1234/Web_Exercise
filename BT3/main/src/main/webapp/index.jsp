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
	.content {
		width: 600px;
		margin: 80px auto;
	}
</style>
</head>

<body>

<div class="content">
	<div class="text-center">
		<h2>Welcome, <%= username %></h2>
	</div>

	<h3>Recent Videos</h3>
	<%
		java.util.List<String> latest = (java.util.List<String>) request.getAttribute("videos");
		if (latest == null || latest.isEmpty()) {
	%>
		<p>You have not uploaded any videos yet.</p>
	<%
		} else {
			for (String video : latest) {
	%>
		<p><a href="watch?name=<%= video %>"><%= video %></a></p>
	<%
			}
		}
	%>
</div>

</body>
</html>

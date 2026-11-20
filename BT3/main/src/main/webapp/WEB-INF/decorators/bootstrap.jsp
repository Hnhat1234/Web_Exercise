<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String username = (String) session.getAttribute("username");
	String ctx = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title><sitemesh:write property='title'>BT3</sitemesh:write></title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<sitemesh:write property='head'/>
</head>

<body class="bg-body-tertiary d-flex flex-column min-vh-100">

<nav class="navbar navbar-expand-lg navbar-dark bg-primary">
	<div class="container">
		<a class="navbar-brand fw-semibold" href="<%= ctx %>/home">Test</a>
		<button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#mainNav" aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>
		<div class="collapse navbar-collapse" id="mainNav">
			<ul class="navbar-nav ms-auto">
				<% if (username != null) { %>
				<li class="nav-item"><a class="nav-link" href="<%= ctx %>/home">Home</a></li>
				<li class="nav-item"><a class="nav-link" href="<%= ctx %>/Videos">Videos</a></li>
				<li class="nav-item"><a class="nav-link" href="<%= ctx %>/profile">Profile</a></li>
				<li class="nav-item"><a class="nav-link" href="<%= ctx %>/logout">Logout</a></li>
				<% } else { %>
				<li class="nav-item"><a class="nav-link" href="<%= ctx %>/login">Login</a></li>
				<li class="nav-item"><a class="nav-link" href="<%= ctx %>/register">Register</a></li>
				<% } %>
			</ul>
		</div>
	</div>
</nav>

<main class="container my-4 flex-grow-1">
	<sitemesh:write property='body'/>
</main>

<footer class="py-3 mt-auto text-center text-body-secondary small border-top">
	BT3 &mdash; SiteMesh 3 + Bootstrap
</footer>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>

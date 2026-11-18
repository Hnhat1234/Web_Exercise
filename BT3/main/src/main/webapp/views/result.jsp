<%@ page language="java" contentType="text/html; charset=US-ASCII" pageEncoding="US-ASCII"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "https://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>Video</title>
<style>
	.content {
		width: 600px;
		margin: 80px auto;
	}
	video {
		width: 100%;
		margin-bottom: 20px;
	}
</style>
</head>

<%
	String name = (String) request.getAttribute("name");
	String fileName = (String) request.getAttribute("fileName");
%>

<body>

<div class="content">
	<h2><%= name %></h2>
	<video src="<%= request.getContextPath() %>/videos/<%= fileName %>" controls></video>
	<p><a href="Videos">Upload another video</a></p>
	<p><a href="home">Back to home</a></p>
</div>

</body>
</html>

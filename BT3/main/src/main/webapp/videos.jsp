<%@ page language="java" contentType="text/html; charset=US-ASCII" pageEncoding="US-ASCII"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "https://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>Videos</title>
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
	.videos {
		width: 300px;
		margin: 0 auto 80px auto;
	}
</style>
</head>

<body>

<form action="Videos" method="post" enctype="multipart/form-data">
	<label for="uploadfile">Select file to upload</label>
	<input type="file" id="uploadfile" name="uploadfile" accept="video/*">
	<label for="name">Name</label>
	<input type="text" id="name" name="name">
	<input type="submit" value="Upload">

	<a href="home">Back to home</a>

	<% if (request.getAttribute("error") != null) { %>
		<p><%= request.getAttribute("error") %></p>
	<% } %>
</form>

<div class="videos">
	<h3>All Videos</h3>
	<%
		java.util.List<String> videos = (java.util.List<String>) request.getAttribute("videos");
		if (videos == null || videos.isEmpty()) {
	%>
		<p>You have not uploaded any videos yet.</p>
	<%
		} else {
			for (String video : videos) {
	%>
		<p><a href="watch?name=<%= video %>"><%= video %></a></p>
	<%
			}
		}
	%>
</div>

</body>
</html>

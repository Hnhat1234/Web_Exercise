<%@ page language="java" contentType="text/html; charset=US-ASCII" pageEncoding="US-ASCII"%>
<%@ page import="Model.UserProfile" %>
<%@ page import="Validation.InputChecks" %>
<%
	String username = (String) session.getAttribute("username");
	if (username == null) {
		response.sendRedirect("login");
		return;
	}
	UserProfile profile = (UserProfile) request.getAttribute("profile");
	String fullName = profile == null ? "" : profile.getFullname();
	String phoneNumber = profile == null ? "" : profile.getPhonenumber();
	String email = profile == null ? "" : profile.getEmail();
	String image = profile == null ? "" : profile.getImage();
	boolean updating = "1".equals(request.getParameter("updated")) || request.getAttribute("error") != null;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "https://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>Profile</title>
<style>
	label, input {
		display: block;
		width: 100%;
		margin-bottom: 10px;
	}
	.tab-content form {
		max-width: 320px;
	}
	.avatar {
		width: 160px;
		height: 160px;
		object-fit: cover;
		border-radius: 50%;
		border: 1px solid #dee2e6;
	}
	.avatar-empty {
		width: 160px;
		height: 160px;
		border-radius: 50%;
		border: 1px dashed #adb5bd;
		display: flex;
		align-items: center;
		justify-content: center;
		color: #adb5bd;
	}
	#uploadForm {
		margin-top: 15px;
	}
</style>
</head>

<body>

<ul class="nav nav-tabs" role="tablist">
	<li class="nav-item" role="presentation">
		<button class="nav-link <%= updating ? "" : "active" %>" id="view-tab" data-bs-toggle="tab" data-bs-target="#view" type="button" role="tab">Profile</button>
	</li>
	<li class="nav-item" role="presentation">
		<button class="nav-link <%= updating ? "active" : "" %>" id="update-tab" data-bs-toggle="tab" data-bs-target="#update" type="button" role="tab">Update</button>
	</li>
</ul>

<div class="tab-content pt-3">

	<div class="tab-pane fade <%= updating ? "" : "show active" %>" id="view" role="tabpanel">
		<% if (image == null || image.isEmpty()) { %>
			<div class="avatar-empty">No image</div>
		<% } else { %>
			<img class="avatar" src="<%= InputChecks.escapeHtml(image) %>" alt="Profile image">
		<% } %>

		<form id="uploadForm" action="profile" method="post" enctype="multipart/form-data">
			<input type="hidden" name="action" value="image">
			<label for="image">Profile image</label>
			<input type="file" id="image" name="image" accept="image/*">
			<input type="submit" value="Upload image">

			<% if (request.getAttribute("imageError") != null) { %>
				<p><%= InputChecks.escapeHtml((String) request.getAttribute("imageError")) %></p>
			<% } %>
			<% if ("1".equals(request.getParameter("uploaded"))) { %>
				<p>Image updated.</p>
			<% } %>
		</form>

		<p>Username: <b><%= InputChecks.escapeHtml(username) %></b></p>
		<p>Full name: <b><%= InputChecks.escapeHtml(fullName) %></b></p>
		<p>Phone number: <b><%= InputChecks.escapeHtml(phoneNumber) %></b></p>
		<p>Email: <b><%= InputChecks.escapeHtml(email) %></b></p>
	</div>

	<div class="tab-pane fade <%= updating ? "show active" : "" %>" id="update" role="tabpanel">
		<form action="profile" method="post">
			<label for="full_name">Full name</label>
			<input type="text" id="full_name" name="full_name" value="<%= InputChecks.escapeHtml(fullName) %>">
			<label for="phone_number">Phone number</label>
			<input type="text" id="phone_number" name="phone_number" value="<%= InputChecks.escapeHtml(phoneNumber) %>">
			<label for="email">Email</label>
			<input type="email" id="email" name="email" value="<%= InputChecks.escapeHtml(email) %>">
			<input type="submit" value="Save changes">

			<% if (request.getAttribute("error") != null) { %>
				<p><%= InputChecks.escapeHtml((String) request.getAttribute("error")) %></p>
			<% } %>
			<% if ("1".equals(request.getParameter("updated"))) { %>
				<p>Profile updated.</p>
			<% } %>
		</form>
	</div>

</div>

<p class="mt-3"><a href="home">Back to home</a></p>

</body>
</html>

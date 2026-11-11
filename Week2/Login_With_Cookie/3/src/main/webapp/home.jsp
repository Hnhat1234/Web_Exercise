<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Home</title>
</head>
<body>
    <main class="card">
        <h1>Welcome, <strong><c:out value="${user.username}"/></strong>!</h1>
        <p>You have successfully logged in.</p>
        <a class="button" href="${pageContext.request.contextPath}/">Back to Start</a>
    </main>
</body>
</html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login Failed</title>
</head>
<body>
    <main class="card">
        <div class="icon">!</div>
        <h1>Login Failed</h1>
        <p>Invalid username or password. Please try again.</p>
        <a class="button" href="${pageContext.request.contextPath}/login">Back to Login</a>
    </main>
</body>
</html>

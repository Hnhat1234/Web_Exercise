<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login</title>
</head>
<body>
    <main class="card">
        <h1>Sign In</h1>
        <p class="subtitle">Enter your credentials to continue.</p>
        <form method="post" action="${pageContext.request.contextPath}/login">
            <label for="username">Username</label>
            <input type="text" id="username" name="username" autocomplete="username" required autofocus>

            <label for="password">Password</label>
            <input type="password" id="password" name="password" autocomplete="current-password" required>

            <label class="checkbox" for="rememberMe">
                <input type="checkbox" id="rememberMe" name="rememberMe">
                Remember me
            </label>

            <button type="submit">Login</button>
        </form>
        <p class="hint">Test account: admin / admin123</p>
    </main>
</body>
</html>

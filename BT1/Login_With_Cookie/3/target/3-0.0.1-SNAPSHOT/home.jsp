<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Home</title>
    <style>
        * { box-sizing: border-box; }
        body {
            margin: 0;
            min-height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
            font-family: system-ui, -apple-system, "Segoe UI", Roboto, sans-serif;
            background: linear-gradient(135deg, #059669, #047857);
            color: #1f2937;
        }
        .card {
            background: #fff;
            padding: 2.5rem;
            border-radius: 14px;
            box-shadow: 0 18px 40px rgba(0, 0, 0, .2);
            text-align: center;
            width: min(400px, 90vw);
        }
        h1 { margin: 0 0 .5rem; font-size: 1.6rem; }
        p { margin: 0 0 1.75rem; color: #6b7280; }
        strong { color: #059669; }
        a.button {
            display: inline-block;
            padding: .75rem 1.75rem;
            border-radius: 8px;
            background: #e5e7eb;
            color: #374151;
            text-decoration: none;
            font-weight: 600;
        }
        a.button:hover { background: #d1d5db; }
    </style>
</head>
<body>
    <main class="card">
        <h1>Welcome, <strong><c:out value="${user.username}"/></strong>!</h1>
        <p>You have successfully logged in.</p>
        <a class="button" href="${pageContext.request.contextPath}/">Back to Start</a>
    </main>
</body>
</html>

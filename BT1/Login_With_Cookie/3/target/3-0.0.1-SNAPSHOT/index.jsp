<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Welcome</title>
    <style>
        * { box-sizing: border-box; }
        body {
            margin: 0;
            min-height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
            font-family: system-ui, -apple-system, "Segoe UI", Roboto, sans-serif;
            background: linear-gradient(135deg, #4f46e5, #7c3aed);
            color: #1f2937;
        }
        .card {
            background: #fff;
            padding: 2.5rem;
            border-radius: 14px;
            box-shadow: 0 18px 40px rgba(0, 0, 0, .2);
            text-align: center;
            width: min(360px, 90vw);
        }
        h1 { margin: 0 0 .5rem; font-size: 1.75rem; }
        p { margin: 0 0 1.75rem; color: #6b7280; }
        a.button {
            display: inline-block;
            padding: .75rem 1.75rem;
            border-radius: 8px;
            background: #4f46e5;
            color: #fff;
            text-decoration: none;
            font-weight: 600;
        }
        a.button:hover { background: #4338ca; }
    </style>
</head>
<body>
    <main class="card">
        <h1>Simple Login Demo</h1>
        <p>Sign in to continue to your home page.</p>
        <a class="button" href="${pageContext.request.contextPath}/login">Go to Login</a>
    </main>
</body>
</html>

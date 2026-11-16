<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login</title>
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
            width: min(360px, 90vw);
        }
        h1 { margin: 0 0 .5rem; font-size: 1.6rem; text-align: center; }
        .subtitle { margin: 0 0 1.75rem; text-align: center; color: #6b7280; font-size: .9rem; }
        label { display: block; margin-bottom: .35rem; font-size: .85rem; font-weight: 600; }
        input {
            width: 100%;
            padding: .7rem .85rem;
            margin-bottom: 1.1rem;
            border: 1px solid #d1d5db;
            border-radius: 8px;
            font-size: 1rem;
        }
        input:focus { outline: none; border-color: #4f46e5; box-shadow: 0 0 0 3px rgba(79, 70, 229, .15); }
        button {
            width: 100%;
            padding: .8rem;
            border: none;
            border-radius: 8px;
            background: #4f46e5;
            color: #fff;
            font-size: 1rem;
            font-weight: 600;
            cursor: pointer;
        }
        button:hover { background: #4338ca; }
        .hint { margin-top: 1.25rem; text-align: center; font-size: .8rem; color: #9ca3af; }
    </style>
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

            <button type="submit">Login</button>
        </form>
        <p class="hint">Test account: admin / admin123</p>
    </main>
</body>
</html>

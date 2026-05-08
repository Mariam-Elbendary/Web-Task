<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create Account</title>
    <style>
        body { font-family: 'Segoe UI', sans-serif; background-color: teal; display: flex; justify-content: center; align-items: center; height: 100vh; margin: 0; }
        .card { background: white; padding: 2rem; border-radius: 12px; box-shadow: 0 4px 15px rgba(0,0,0,0.1); width: 350px; }
        h2 { color: darkslategray; text-align: center; margin-bottom: 1.5rem; }
        input { width: 100%; padding: 12px; margin: 8px 0; border: 1px solid #ddd; border-radius: 6px; box-sizing: border-box; }
        button { width: 100%; padding: 12px; background: teal; color: white; border: none; border-radius: 6px; font-weight: bold; cursor: pointer; margin-top: 10px; }
        button:hover { background: darkslategray; }
        .link { display: block; text-align: center; margin-top: 15px; color: teal; text-decoration: none; font-size: 16px; }
    </style>
</head>
<body>
<div class="card">
    <h2>Sign Up</h2>
    <%
        String error = (String) request.getAttribute("errorMessage");
        if (error != null) {
    %>
    <div style="color: red; text-align: center; padding: 10px;">
        <%= error %>
    </div>
    <%
        }
    %>
    <form action="register" method="post">
        <input type="text" name="username" placeholder="Username" required>
        <input type="email" name="email" placeholder="Email Address" required>
        <input type="password" name="password" placeholder="Password" required>
        <button type="submit">Create Account</button>
    </form>
    <a href="login.jsp" class="link">Already have an account? Login here</a>
</div>
</body>
</html>
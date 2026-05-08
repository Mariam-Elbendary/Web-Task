<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <title>Login - E-Commerce System</title>
  <style>
    body {
      font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
      background: linear-gradient(135deg, mediumseagreen 0%, teal 100%);
      height: 100vh;
      margin: 0;
      display: flex;
      justify-content: center;
      align-items: center;
    }
    .login-card {
      background: white;
      padding: 40px;
      border-radius: 12px;
      box-shadow: 0 10px 25px rgba(0,0,0,0.2);
      width: 100%;
      max-width: 350px;
      text-align: center;
    }
    h2 { color: teal; margin-bottom: 25px; }
    input {
      width: 100%;
      padding: 12px;
      margin-bottom: 15px;
      border: 1px solid #ddd;
      border-radius: 6px;
      box-sizing: border-box;
    }
    button {
      width: 100%;
      padding: 12px;
      background-color: teal;
      color: white;
      border: none;
      border-radius: 6px;
      cursor: pointer;
      font-size: 16px;
      font-weight: bold;
      transition: background 0.3s;
    }
    button:hover {
      background-color: darkslategrey;
    }
    .error-msg {
      color: #dc3545;
      background: #f8d7da;
      padding: 10px;
      border-radius: 4px;
      margin-bottom: 15px;
      font-size: 0.9em;
    }
  </style>
</head>
<body>

<div class="login-card">
  <h2>Login</h2>


  <% if (request.getParameter("error") != null) { %>
  <div class="error-msg">
    Invalid username or password!
  </div>
  <% } %>

  <form method="post" action="login">
    <input type="text" name="username" placeholder="Username" required />
    <input type="password" name="password" placeholder="Password" required />
    <button type="submit">Sign In</button>
  </form>

  <div style="margin-top: 20px; font-size: 0.8em; color: #888;">
    <p>Try: <b>admin/123#</b> for Admin access</p>
    <a href="./register.jsp" class="link" style="color: teal; font-size:14px">New user? Register now</a>
  </div>

</div>

</body>
</html>
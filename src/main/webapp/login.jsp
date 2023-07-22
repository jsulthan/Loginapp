<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
    <title>Login</title>
</head>
<body>
	<div align=center>
    <h2>Login</h2>
    <% if (request.getParameter("error") != null && request.getParameter("error").equals("empty")) { %>
        <p style="color: red;">Username and password are required.</p>
    <% } %>
    
    <% if (request.getParameter("error") != null && request.getParameter("error").equals("db")) { %>
        <p style="color: red;">Invalid username and password</p>
    <% } %>
    
    <form action="LoginServlet" method="POST">
        <label for="username">Username:</label>
        <input type="text" id="username" name="username" required><br><br>
        <label for="password">Password:</label>
        <input type="password" id="password" name="password" required><br><br>
        <input type="submit" value="Login">
    </form>
    <p>Don't have an account? <a href="register.jsp">Register here</a>.</p>
    </div>

</body>
</html>
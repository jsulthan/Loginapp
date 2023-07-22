<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Create New User</title>
</head>
<body>
<div align=center>
<h2>Create New User</h2>
    <% if (request.getParameter("error") != null && request.getParameter("error").equals("empty")) { %>
        <p style="color: red;">All fields are required.</p>
    <% } %>

    <% if (request.getParameter("error") != null && request.getParameter("error").equals("db")) { %>
        <p style="color: red;">An error occurred while registering. Please try again later.</p>
    <% } %>
    
    <form action="RegisterServlet" method="POST">
    	<label for="firstName">First Name:</label>
        <input type="text" id="firstName" name="firstName" required><br><br>
        <label for="lastName">Last Name:</label>
        <input type="text" id="lastName" name="lastName" required><br><br>
        <label for="username">Username:</label>
        <input type="text" id="username" name="username" required><br><br>
        <label for="password">Password:</label>
        <input type="password" id="password" name="password" required><br><br>
        <label for="userData">What is your favorite food?:</label>
        <input type="text" id="userData" name="userData"><br><br>
        <input type="submit" value="Register">
    </form>
</div>
</body>
</html>
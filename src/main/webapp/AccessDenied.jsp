<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Access Denied</title>
</head>
<body>
    <h1>Access Denied</h1>
    <p>Sorry, you do not have the required role to access this page.</p>
    <p>Please contact the administrator if you believe you should have access.</p>
    <p><a href="<%= request.getContextPath() %>/">Back to Home</a></p>
</body>
</html>
In this "AccessDenied.jsp" page:

It provides a title and a heading indicating "Access Denied."
A brief message informs the user that they do not have the required role


<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<html>
<head>
    <title>Books Store Application</title>
</head>
<body>
    <c:set var="user" value="${sessionScope.user}" /> <!-- Retrieve user object from the session -->

    <c:choose>
        <c:when test="${not empty user}">
            <div>
                <span align="left">User: <c:out value="${user.name}" /></span>
                <a align="right" href="/BookManagement/logout">Logout</a>
            </div>
        </c:when>
        <c:otherwise>
            <a align="right" href="/BookManagement/login">Login</a>
        </c:otherwise>
    </c:choose>
    <center>
        <h1>Books Management</h1>
        <h2>
            <a href="/BookManagement/new">Add New Book</a>
            &nbsp;&nbsp;&nbsp;
            <a href="/BookManagement/list">List All Books</a>

        </h2>
    </center>
    <div align="center">
        <table border="1" cellpadding="5">
            <caption><h2>List of Books</h2></caption>
            <tr>
                <th>ID</th>
                <th>Title</th>
                <th>Author</th>
                <th>Price</th>
                <th>Actions</th>
            </tr>
            <c:forEach var="book" items="${listBook}">
                <tr>
                    <td><c:out value="${book.id}" /></td>
                    <td><c:out value="${book.title}" /></td>
                    <td><c:out value="${book.author}" /></td>
                    <td><c:out value="${book.price}" /></td>
                    <td>
                        <a href="/BookManagement/edit?id=<c:out value='${book.id}' />">Edit</a>
                        &nbsp;&nbsp;&nbsp;&nbsp;
                        <a href="/BookManagement/delete?id=<c:out value='${book.id}' />">Delete</a>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>
</body>
</html>

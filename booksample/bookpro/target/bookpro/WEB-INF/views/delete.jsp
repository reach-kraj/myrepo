<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Enter Book to delete for collection</title>
   <style>
table {
  border-collapse: collapse;
  width: 70%;
}

th, td {
  text-align: left;
  padding: 8px;
}

tr:nth-child(even){background-color: #f2f2f2}

th {
  background-color: #04AA6D;
  color: white;
}
</style>
</head>
<body>
<!-- <h1>Enter "Book ID" to Delete Book from collection</h1>
<form action="${pageContext.request.contextPath}/deletebook" method="post">
<label for= "bookid">Book ID:</label>
<input type="number" id="bookid" name="bookid" required><br><br>
<input type="submit" value="Delete book >>">
</form>
<br>
<br>
<p align="center">
<input type="button" value="Go to HomePage" onclick="window.location='${pageContext.request.contextPath}/'" />
</p>-->

<h1>Our Book Collection</h1>
    <table border="1">
        <thead>
            <tr>
                <th>Book ID</th>
                <th>Book Name</th>
                <th>Book Author</th>
                <th>Book Print Location</th>
                <th>Action</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="Book" items="${delete}">
                <tr>
                    <td>${Book.bookid}</td>
                    <td>${Book.bookname}</td>
                    <td>${Book.bookauthor}</td>
                    <td>${Book.bookprint}</td>
                    <td>
                        <!-- Delete button -->
                        <form action="${pageContext.request.contextPath}/deletebook" method="post">
                            <input type="hidden" name="bookid" value="${Book.bookid}">
                            <button type="submit">Delete Book</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    <br>
<br>
<p align="center">
<input type="button" value="Go to HomePage" onclick="window.location='${pageContext.request.contextPath}/'" />
</p>

</body>
</html>
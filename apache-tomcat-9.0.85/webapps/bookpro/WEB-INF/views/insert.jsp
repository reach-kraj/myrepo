<%@ page language="java" contentType="text/html; charset=UTF-8" %>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>ADD BOOK</title>
</head>
<body>
    <h1>Insert New Book</h1>
    <form action="${pageContext.request.contextPath}/addbook" method="post">
      <label for= "bookid">Book ID:</label>
      <input type="number" id="bookid" name="bookid" required><br><br>
        <label for= "bookname">Book Name:</label> 
        <input type="text" id="bookname" name="bookname" required><br><br>
       <label for= "bookauthor"> Book Author:</label> 
       <input type="text"id="bookauthor" name="bookauthor" required><br><br>
        <label for= "bookprint">Book Print Location:</label>
         <input type="text" id="bookprint" name="bookprint" required><br><br>
        <input type="submit" value="Add Book>>">
    </form>
    <br>
<br>
<p align="center">
<input type="button" value="Go to HomePage" onclick="window.location='${pageContext.request.contextPath}/'" />
</p>
</body>
</html>
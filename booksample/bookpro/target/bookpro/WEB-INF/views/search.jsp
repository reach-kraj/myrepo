<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Search Book</title>

</head>
<body>
<h1>Search Book</h1>
<form action="${pageContext.request.contextPath}/searchresult" method="get">
<label for= "bookid">Book ID:</label>
<input type="number" id="bookid" name="bookid" required><br><br>
<input type="submit" value="Search book >>">
</form>
<br>
<br>
<p align="left">
<input type="button" value="Show all book" onclick="window.location='${pageContext.request.contextPath}/list'" />
</p>
<br>
<br>
<p align="center">
<input type="button" value="Go to HomePage" onclick="window.location='${pageContext.request.contextPath}/'" />
</p>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Success</title>

</head>
<body>
<h1 align="center">Operation Successfully Executed</h1>
<p align="center">
To View All Books Click down below
</p>
<p align="center">
<input type="button" value="Show all book" onclick="window.location='${pageContext.request.contextPath}/list'" />
</p>
<br>
<br>
<p align="center">
<input type="button" value="Go to HomePage" onclick="window.location='${pageContext.request.contextPath}/'" />
</p>
</body>
</html>
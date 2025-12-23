<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>IG Book Collection</title>
<style>
.button {
  background-color: #04AA6D; /* Green */
  border: none;
  color: white;
  padding: 20px;
  text-align: center;
  text-decoration: none;
  display: inline-block;
  font-size: 16px;
  margin: 4px 2px;
  cursor: pointer;
}
.button1 {border-radius: 12px;}
</style>
</head>

<body>
<h1 align="center">
Welcome To InfoGrass Book Collection
</h1>


<p align="center">
<input type="button" class="button button1" value="Add book" onclick="window.location='${pageContext.request.contextPath}/add'" />
</p>
<p align="center">
<input type="button" class="button button1" value="Search book" onclick="window.location='${pageContext.request.contextPath}/search'" />
</p>
<p align="center">
<input type="button" class="button button1" value="Show all book" onclick="window.location='${pageContext.request.contextPath}/list'" />
</p>
<p align="center">
<input type="button" class="button button1" value="Edit book" onclick="window.location='${pageContext.request.contextPath}/editlist'" />
</p>
<p align="center">
<input type="button" class="button button1" value="Delete book" onclick="window.location='${pageContext.request.contextPath}/deletelist'" />
</p>
</body>
</html>
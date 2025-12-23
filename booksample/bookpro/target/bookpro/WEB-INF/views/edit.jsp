<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Enter ID to Edit</title>
<style>
input[type=text], select {

  width: 30%;
  padding: 12px 20px;
  margin: 8px 0;
  display: inline-block;
  border: 1px solid #ccc;
  border-radius: 4px;
  box-sizing: border-box;
}

input[type=submit] {

  width: 10%;
  background-color: #4CAF50;
  color: white;
  padding: 14px 20px;
  margin: 8px 0;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

input[type=submit]:hover {
  background-color: #45a049;
}

div {
  border-radius: 5px;
  background-color: #f2f2f2;
  padding: 10px;
}
</style>
</head>
<body>
<h1>Enter Book ID to Edit</h1>
<form action="${pageContext.request.contextPath}/editbookinsert" method="post">

<label for= "bookid">Book ID:</label>         
<input type="number" id="bookid" name="bookid" value="${bookid}" required><br><br>
 <label for= "bookname">Book Name:</label> 
<input type="text" id="bookname" name="bookname" value="${bookname}" required><br><br>
<label for= "bookauthor"> Book Author:</label>
<input type="text" id="bookauthor" name="bookauthor" value="${bookauthor}" required><br><br>
 <label for= "bookprint">Book Print Location:</label>
<input type="text" id="bookprint" name="bookprint" value="${bookprint}" required><br><br>
         
        <input type="submit" value="Edit Book>>">
    </form>
    <br>
<br>
<p align="center">
<input type="button" value="Go to HomePage" onclick="window.location='${pageContext.request.contextPath}/'" />
</p>
</body>
</html>
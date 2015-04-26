<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Error Page For Examples</title>
</head>
<body bgcolor="white">
Invalid username and/or password, please try
<a href='<%= response.encodeURL("/LoginTests/login.jsp") %>'>again</a>.
</body>
</html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">


<%
	if (request.getParameter("logout") != null) {
		session.invalidate();
		response.sendRedirect("index.jsp");
		return;
	}
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login Tests</title>
</head>
<body>
	Go to protected
	<a href="frontend/index.jsp">frontend</a>
	<br /> Go to protected
	<a href="backend/index.jsp">backend</a>
	<br />
</body>
</html>
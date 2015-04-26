<%@page import="org.apache.catalina.realm.GenericPrincipal"%>
<%@page import="org.apache.catalina.Role"%>
<%@page import="java.util.Iterator"%>
<%@page import="org.apache.catalina.users.MemoryUser"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
Protected Frontend Content! <br />

<img alt="Profile Image" src="/LoginTests/GetUserImage">

<a href="../index.jsp?logout=true">Logout</a> <br />

<strong>Debug</strong> <br />
Remote user: <%= request.getRemoteUser() %> <br />
User prinicipal: <%= request.getUserPrincipal().getName() %> <br />
Class Name: <%= request.getUserPrincipal().getClass() %>
<%
//MemoryUser memoryUser = (MemoryUser) request.getUserPrincipal();
GenericPrincipal genericPrincipal = (GenericPrincipal) request.getUserPrincipal();
String[] roles = genericPrincipal.getRoles();
%>
<ul>
	<% for(String role : roles) { %>
		<li><%= role %></li>
	<% } %>
</ul>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">


<%
  if (request.getRemoteUser() != null) {
    response.sendRedirect("frontend/index.jsp");
    return;
  }
%>


<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login Page for Examples</title>
<body bgcolor="white">
	<form method="POST"
		action='<%=response.encodeURL("j_security_check")%>'>
		<table border="0" cellspacing="5">
			<tr>
				<th align="right">Username:</th>
				<td align="left"><input type="text" name="j_username"></td>
			</tr>
			<tr>
				<th align="right">Password:</th>
				<td align="left"><input type="password" name="j_password"></td>
			</tr>
			<tr>
				<td align="right"><input type="submit" value="Log In"></td>
				<td align="left"><input type="reset"></td>
			</tr>
		</table>
	</form>
</body>
</html>
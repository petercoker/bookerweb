<%@ tag language="java" pageEncoding="ISO-8859-1"%>
<%@ attribute name="title" required="true" rtexprvalue="true" %>
<%@ attribute name="content" fragment="true" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>${title }</title>
</head>
<body>
	
	<a href="https://www.w3schools.com">Visit W3Schools.com!</a>
	<a href="index.jsp">Home</a>
	<a href="aboutus.jsp">About us</a>
	<a href="news.jsp">New</a>
	<br>
	<jsp:invoke fragment="content"></jsp:invoke>
	<br>
	Copyright booker.ie 2019
	
</body>
</html>
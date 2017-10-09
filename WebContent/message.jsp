<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>文件上传结果</title>
<link href="css/button.css" rel="stylesheet" type="text/css" />
</head>
<body>
<%
if(session==null||session.getAttribute("user")==null)
{
	response.sendRedirect(request.getContextPath()+"/login.html");
	return;
}
%>
 <center>
        <h2><% String message=(String)request.getAttribute("message");%>
        <%=message %>
        &nbsp;&nbsp;<a href="#" onclick="window.history.back(-1);">返回</a>
        </h2>
    </center>
</body>
</html>
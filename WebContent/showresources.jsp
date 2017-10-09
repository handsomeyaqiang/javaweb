<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.io.*,java.util.*,java.sql.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>SELECT 操作</title>
<style>
#content{
	float:left;
}
</style>
</head>
<body>
<!--
JDBC 驱动名及数据库 URL 
数据库的用户名与密码，需要根据自己的设置
useUnicode=true&characterEncoding=utf-8 防止中文乱码
 -->
<sql:setDataSource var="snapshot" driver="com.microsoft.sqlserver.jdbc.SQLServerDriver"
     url="jdbc:sqlserver://192.168.1.23:1433;database=gongxiangwang"
     user="sa"  password="yaqiang1"/>
     
<!-- 查询数据 -->
<%
	String showtype=request.getParameter("type");
%>

<sql:query dataSource="${snapshot}" var="result">
SELECT * from resource_table where type=?;
<sql:param value="<%=showtype %>"/>
</sql:query>
<div id="content">
<%! int i=0; %>
<c:forEach var="row" items="${result.rows}">
<%
	i++; 
%>
	<div id="<%=i%>">
	<table>
		<tr>
		<td>
		<%if(showtype.equals("images")) {%>
		<img src="${row.path}"  alt="${row.name }" width="600px" height="400px">
		<%}else if(showtype.equals("shipin")) {%>
		<video src="${row.path}" controls="controls"  width="410px" height="320px">
		您的浏览器不支持 video 标签。
		</video>
		<% }else if(showtype.equals("yinyue")){%>
		<audio src="${row.path }" controls="controls" width="410px" height="320px"></audio>
		<%}else if(showtype.equals("files")){} 
		else{
			//重定向到新地址
			String site=new String("showerrorpage.jsp");
			response.setStatus(response.SC_MOVED_TEMPORARILY);
			response.setHeader("Location", site);
		}%>
		</td>
		</tr>
		<tr><td>title: ${row.name}</td></tr>
		<tr><td>共享人：${row.publicname}</td></tr>
		<tr><td>发布时间：${row.publictime}</td></tr>
		<tr><td>大小：${row.size}</td></tr>
		<tr>
		<td><a href="/GongXiangWang/DownloadServlet?name=${row.realname }&type=<%=request.getParameter("type")%>">下载</a></td>
		</tr>
	</table>
	</div>
</c:forEach>
</div>
</body>
</html>
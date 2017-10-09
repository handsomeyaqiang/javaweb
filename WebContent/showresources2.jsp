
<%@page import="com.itextpdf.text.log.SysoCounter"%>
<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="utils.DbUtil2"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<div id="content">
<%
if(session==null||session.getAttribute("user")==null)
{
	response.sendRedirect(request.getContextPath()+"/login.html");
	return;
}
else{
	
	String showtype=request.getParameter("type");
	String sqlString="select * from resource_table where type='"+showtype+"'";
	ResultSet rs=null;
	Connection conn=DbUtil2.getConnection();
	Statement st=null;
	try {
		conn.setAutoCommit(false);
		st=conn.createStatement();
		rs=st.executeQuery(sqlString);
		int i=0;
		while(rs.next())
		{
			i++;
		%>
		<div id="<%=i %>">
		<table>
		<tr><td>
	<%
		String path=rs.getString("path");
		String title=rs.getString("name");
		String publictime=rs.getString("publictime");
		String publicname=rs.getString("publicname");
		String size=rs.getString("size");
		String realname=rs.getString("realname");
		String remark=rs.getString("remark");
		System.out.println(path);
		if(showtype.equals("images"))
		{
		%>
			<img src="<%=path %>"  alt="${row.name }" width="600px" height="400px"></img>
		<%
		}else if(showtype.equals("shipin"))
		{
		%>
		<video src="<%=path %>" controls="controls"  width="410px" height="320px">
		您的浏览器不支持 video 标签。
		</video>
		<%
		}else if(showtype.equals("yinyue"))
		{
		%>
		<audio src="<%=path %>" controls="controls" width="410px" height="320px"></audio>
		<% 
		}else if(showtype.equals("files"))
		{
			
		}else{
			response.sendRedirect("showerrorpage.jsp");
		}
		%>
		</td></tr>
		<tr><td>title: <%=title %></td></tr>
		<tr><td>共享人：<%=publicname %></td></tr>
		<tr><td>发布时间：<%=publictime%></td></tr>
		<tr><td>大小：<%=size%></td></tr>
		<tr>
		<td><a href="/GongXiangWang/DownloadServlet?name=<%=realname %>&type=<%=request.getParameter("type")%>">下载</a></td>
		</tr>
		</table>
		</div>
<%	}
	conn.commit();
} catch (SQLException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}finally{
	try {
		conn.rollback();
	} catch (SQLException e) {
		e.printStackTrace();
	}
	DbUtil2.closeSource(rs, st, conn);
}	
}

%>
</div>
</body>
</html>
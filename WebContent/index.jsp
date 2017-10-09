<%@page import="com.gongxiangwang.cn.SessionUserBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>共享网</title>
  <script>
	var xmlhttp;
	function loadXMLDoc(url,cfunc)
	{
		if (window.XMLHttpRequest)
		{// IE7+, Firefox, Chrome, Opera, Safari 代码
		  xmlhttp=new XMLHttpRequest();
		}
		else
		 {// IE6, IE5 代码
			 xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
		 }
		xmlhttp.onreadystatechange=cfunc;
		xmlhttp.open("GET",url,true);
		xmlhttp.send();
	}
	function myFunction(url)
	{
		loadXMLDoc(url,function()
		{
			if (xmlhttp.readyState==4 && xmlhttp.status==200)
			{
				document.getElementById("section").innerHTML=xmlhttp.responseText;
			}
		});
	}
 </script>
<style>
#header {
    background-color:#0FC;
    color:#000;
    text-align:center;
    padding:5px;
}
#nav {
    line-height:30px;
    background-color:#eeeeee;
    height:550px;
    width:150px;
    float:left;
    padding:5px;	      
}
#section {
    width:1300px;
    float:left;
    padding:10px;	 	 
}
#footer {
    background-color:black;
    color:white;
    clear:both;
    text-align:center;
    padding:5px;	 	 
}
</style>
</head>
<body>
<div id="container">
<div id="header" >
<%
SessionUserBean user=null;
if(session==null||session.getAttribute("user")==null)
{
	response.sendRedirect(request.getContextPath()+"/login.html");
	return;
}
user=(SessionUserBean)session.getAttribute("user");
%>
<center><h2>欢迎<%=user.getNickname() %>来到共享网</h2></center>
</div>

<div id="nav">
<ul>
<li><a href="#" onclick="myFunction('showresources2.jsp?type=images')">图片</a></li>
<li><a href="#" onclick="myFunction('showresources2.jsp?type=shipin')">视频</a></li>
<li><a href="#" onclick="myFunction('showresources2.jsp?type=yinyue')">音乐</a></li>
<li><a href="#" onclick="myFunction('showresources2.jsp?type=files')">文件</a></li>
<li><a href="#" onclick="myFunction('showresources2.jsp?type=toutiao')">每日头条</a></li>
<li><a href="#" onclick="myFunction('upload.jsp')">上传资源</a></li>
<li><a href="login.html" >退出</a></li>
</ul>
</div>

<div id="section">
<br/><br/><br/><br/>
<center>

<jsp:include page='<%="/PageHitCounter"%>' flush="true"></jsp:include>
<h1>网站总访问量：<br/><%=request.getAttribute("hitCount") %></h1>
</center>
</div>

<div id="footer">
<center>王亚强<sup>&reg;</sup>&nbsp;&nbsp;版权所有<sup>&copy;</sup>2017-2018</center>
</div>
</div>
</body>
</html>
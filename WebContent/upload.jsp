<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>文件上传实例</title>
</head>
<body>
<%
if(session==null||session.getAttribute("user")==null)
{
	response.sendRedirect(request.getContextPath()+"/login.html");
	return;
}
%>
<center><h1>上传资源后就可以共享啦，亲。</h1></center>
<table align="center">
<tr><td>
<form method="post" action="/GongXiangWang/UploadServlet" enctype="multipart/form-data" target="iframeUpload">
	请选择一个文件:
	<input type="file" name="uploadFile" />
	<br/><br/>
	请输入资源名字：<input type="text" name="modifyname"/><label>可以不修改</label><br/><br/>
	请输入备注信息：<textarea rows="5" cols="20"  name="remark"></textarea><br/><br/>
	<input type="submit" value="上传" /><br/>
</form>
上传进度：<label id="fileUploadProcess"></label>  
<iframe name="iframeUpload" src="" width="350" height="35" frameborder=0  SCROLLING="no" style="display:NONE"></iframe>
</td></tr>
</table>
</body>
</html>
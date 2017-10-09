package com.gongxiangwang.cn;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class DownloadServlet
 */
@WebServlet("/DownloadServlet")
public class DownloadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DownloadServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session=request.getSession(false);
		
		if(session==null||session.getAttribute("user")==null)
		{
			   request.setAttribute("message",
			           "非法链接，请返回!");
			    // 跳转到 message.jsp
			    getServletContext().getRequestDispatcher("/message.jsp").forward(
			             request, response);
		}
		else{
			   //获取文件名
		    String filename=request.getParameter("name");
		    String type=request.getParameter("type");
		    //防止读取name名乱码
		    filename=new String(filename.getBytes("iso-8859-1"),"utf-8");
		    //在控制台打印文件名
		    System.out.println("文件名："+filename);
		    
		     //设置文件MIME类型  
		    response.setContentType(getServletContext().getMimeType(filename));  
		    //设置Content-Disposition  
		    response.setHeader("Content-Disposition", "attachment;filename="+filename);
		    
		    //获取要下载的文件绝对路径，我的文件都放到WebRoot/download目录下
		    ServletContext context=this.getServletContext();
		    System.out.println(context);
		    System.out.println("--------------------------");
		    String fullFileName=null;
		    if(type.equals("image"))
		    {
		    	fullFileName=context.getRealPath("/download/images/"+filename);
		    }
		    else if(type.equals("shipin"))
		    {
		    	fullFileName=context.getRealPath("/download/shipin/"+filename);
		    }
		    else if(type.equals("file")) {
		    	fullFileName=context.getRealPath("/download/files/"+filename);
		    }
		    else if(type.equals("yinyue"))
		    {
		    	fullFileName=context.getRealPath("/download/yinyue/"+filename);
		    }
		    else {
		        request.setAttribute("message",
		                "文件下载失败!");
		        // 跳转到 message.jsp
		        getServletContext().getRequestDispatcher("/message.jsp").forward(
		                request, response);
		    }
		    //String fullFileName="c://"+filename;
		    System.out.println("--------------------------");
		    System.out.println(fullFileName);
		    //输入流为项目文件，输出流指向浏览器
		    InputStream is=new FileInputStream(fullFileName);
		    ServletOutputStream os =response.getOutputStream();
		    
		    /*
		     * 设置缓冲区
		     * is.read(b)当文件读完时返回-1
		     */
		    int len=-1;
		    byte[] b=new byte[1024];
		    while((len=is.read(b))!=-1){
		        os.write(b,0,len);
		    }
		    //关闭流
		    is.close();
		    os.close();
	     request.setAttribute("message",
	             "文件下载成功!");
	     // 跳转到 message.jsp
	     getServletContext().getRequestDispatcher("/message.jsp").forward(
	             request, response);
		}	    
}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//System.out.println("hello");
		doGet(request, response);
	}

}

package com.gongxiangwang.cn;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import utils.SQLHelper;

/**
 * Servlet implementation class Register
 */
@WebServlet("/Register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Register() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html;charset=utf-8");
		HttpSession session=request.getSession();  
		PrintWriter out=response.getWriter();
		String username=new String(request.getParameter("username").getBytes("iso-8859-1"),"UTF-8");
		String password=new String(request.getParameter("password").getBytes("iso-8859-1"),"UTF-8");
		String nickname=new String(request.getParameter("nickname").getBytes("iso-8859-1"),"UTF-8");
		Object[] parms= {username,password,nickname};
		String sqlString="insert into user_table(username,password,nickname) values(?,?,?)";
		int i=SQLHelper.insertBySQL(sqlString, parms);
		if(i==1)
		{
			System.out.println("注册成功！");
			SessionUserBean user=new SessionUserBean();
			user.setNickname(nickname);
			user.setStatus("ok");
			user.setUsername(username);
			session.setAttribute("user", user);
		    getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
		}else {
			System.out.println("注册失败！");
			request.setAttribute("message", "注册失败！");
		    getServletContext().getRequestDispatcher("/message.jsp").forward(request, response);
		}
		
	}

}

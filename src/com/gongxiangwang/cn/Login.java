package com.gongxiangwang.cn;

import java.io.IOException;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import utils.DbUtil2;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		getServletContext().getRequestDispatcher("/login.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html;charset=utf-8");//防止跳转页面后出现乱码
		PrintWriter out=response.getWriter();
		HttpSession session=request.getSession(); 
		String username=new String(request.getParameter("username").getBytes("iso-8859-1"),"UTF-8");
		String password=new String(request.getParameter("password").getBytes("iso-8859-1"),"UTF-8");
/*		Pattern pattern=Pattern.compile("^1(3[0-9]|47|5[([0-3]|[5-9])]|8[0-9])\\d{8}$");
		Matcher isMatch = pattern.matcher(username);
		if(!isMatch.matches())
		{
			
		}*/
		String sqlString="select * from user_table where username=? AND password=?";
		ResultSet rs=null;
		String nickname=null;
		PreparedStatement ps=null;
		int id=0;
		Connection conn=DbUtil2.getConnection();
		try {
			conn.setAutoCommit(false);
			ps= conn.prepareStatement(sqlString);
			ps.setString(1, username);
			ps.setString(2, password);
			rs = ps.executeQuery();
			if(rs.next())
			{
				nickname=rs.getString("nickname");
				id=Integer.parseInt(rs.getString("id"));
				System.out.println(username);
				System.out.println(password);
				System.out.println(nickname);
				System.out.println("登陆成功！");
				//session.setAttribute("id",id);
				SessionUserBean user=new SessionUserBean();
				user.setNickname(nickname);
				user.setStatus("ok");
				user.setUsername(username);
				session.setAttribute("user",user);
			    getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
			}
			else {
				System.out.println("登陆失败！");
				request.setAttribute("message","登陆失败！");
			    getServletContext().getRequestDispatcher("/message.jsp").forward(request, response);
			}
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			try {
				conn.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			DbUtil2.closeSource(rs, ps, conn);
		}
	}

}

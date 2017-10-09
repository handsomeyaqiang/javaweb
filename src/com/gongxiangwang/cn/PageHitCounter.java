package com.gongxiangwang.cn;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.set.SynchronizedSet;

import utils.SQLHelper;

/**很多时候，您可能有兴趣知道网站的某个特定页面上的总点击量。
 * Servlet implementation class PageHitCounter
 */
@WebServlet("/PageHitCounter")
public class PageHitCounter extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private int hitCount;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PageHitCounter() {
        // TODO Auto-generated constructor stub
    }

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		super.destroy();
		
		String sqlString="update fangwencount_table set count="+hitCount+" where id=1";
		int i=SQLHelper.updateBySQL(sqlString);
		System.out.println("destory----------------"+i);
	}

	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		String sqlString="select top 1 * from fangwencount_table ";
		
		hitCount=Integer.parseInt(SQLHelper.selectBySQLString(sqlString, "count"));

		System.out.println("init--------------"+hitCount);

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html;charset=UTF-8");
		// 增加 hitCount 
		hitCount++; 
		request.setAttribute("hitCount", hitCount);
/*		PrintWriter out = response.getWriter();
		String title = "总访问量";
		String docType = "<!DOCTYPE html> \n";
		out.println(docType +
	        "<html>\n" +
	        "<head><title>" + title + "</title></head>\n" +
	        "<body bgcolor=\"#f0f0f0\">\n" +
	        "<h1 align=\"center\">" + title + "</h1>\n" +
	        "<h2 align=\"center\">" + hitCount + "</h2>\n" +
	        "</body></html>");*/
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

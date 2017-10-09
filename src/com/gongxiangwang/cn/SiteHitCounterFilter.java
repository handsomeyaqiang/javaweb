package com.gongxiangwang.cn;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

/**很多时候，您可能有兴趣知道整个网站的总点击量。
 * Servlet Filter implementation class SiteHitCounterFilter
 */
@WebFilter("/SiteHitCounterFilter")
public class SiteHitCounterFilter implements Filter {
	private int hitCount;
    /**
     * Default constructor. 
     */
    public SiteHitCounterFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here

		// pass the request along the filter chain
		  // 把计数器的值增加 1
	      hitCount++;

	      // 输出计数器
	      System.out.println("网站访问统计："+ hitCount );

	      // 把请求传回到过滤器链
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
		//重置点击计数器
		hitCount=0;
	}

}

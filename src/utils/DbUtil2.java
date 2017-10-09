package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DbUtil2 {
	private static String driverName="com.microsoft.sqlserver.jdbc.SQLServerDriver";
	private static String url="jdbc:sqlserver://localhost:1433;"
			+ "database=gongxiangwang";
	private static final String user="sa";
	private static final String password="yaqiang1";
	private DbUtil2()
	{}
	static{
		try
		{
			Class.forName(driverName);
			System.out.println("注册驱动成功！");
		}catch(ClassNotFoundException e)
		{
			throw new ExceptionInInitializerError(e);
		}
	}
	public static Connection getConnection() {
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(url,user,password);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return connection;
	}

	public static void closeSource(ResultSet rs, Statement statement, Connection conn) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (statement != null) {
			try {
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) throws Exception {
		getConnection();
	}
}

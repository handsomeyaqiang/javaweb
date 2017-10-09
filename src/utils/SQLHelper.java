/**  
 * @author wangyaqiang

 * @date 2013-9-9
 */
package utils;

import java.sql.*;





import java.util.*;
public class SQLHelper {

	public static int insertBySQL(String sqlString) {
		return updateBySQL(sqlString);
	}

	public static int insertBySQL(String sqlString, Object[] parms ) {
		return updateBySQL(sqlString, parms);
	}
	public static int insertBySQL(String sqlString, Object[] parms, int[] types) {
		return updateBySQL(sqlString, parms, types);
	}

	public static int createTable(String sql){
		Connection conn = DbUtil2.getConnection();
		Statement stmt = null;
		 try {  
			 stmt = conn.createStatement();
			 stmt.executeUpdate(sql);
			 return 1;
	        } catch (SQLException e) {  
	            e.printStackTrace();  
	        }
		 DbUtil2.closeSource(null,stmt, conn);
		return 0;
	}
	public static ResultSet selectByString(String sqlString)
	{
		ResultSet rs=null;
		Connection conn=DbUtil2.getConnection();
		Statement st=null;
		try {
			conn.setAutoCommit(false);
			st=conn.createStatement();
			rs=st.executeQuery(sqlString);
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
		
		return rs;
		
	}
	public static String selectBySQLString(String sqlString,String onecolumn){
		String string = null;
		Connection conn = DbUtil2.getConnection();
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		try {
			conn.setAutoCommit(false);
			prepareStatement = conn.prepareStatement(sqlString);
			resultSet = prepareStatement.executeQuery();
			while(resultSet.next())
				string = resultSet.getString(onecolumn);
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
			DbUtil2.closeSource(resultSet, prepareStatement, conn);
		}
		return string;
	}
	public static ArrayList<HashMap<String, Object>> selectBySQL(String sqlString) {
		ArrayList<HashMap<String, Object>> rows = new ArrayList<HashMap<String, Object>>();
		Connection conn = DbUtil2.getConnection();
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		ResultSetMetaData metaData = null;
		try {
			conn.setAutoCommit(false);
			prepareStatement = conn.prepareStatement(sqlString);
			resultSet = prepareStatement.executeQuery();
			metaData = resultSet.getMetaData();    //获取此 ResultSet 对象的列的编号、类型和属性。 
			int columnCount = metaData.getColumnCount();
			for (; resultSet.next();) {
				HashMap<String, Object> row = new HashMap<String, Object>();
				for (int i = 0; i < columnCount; i++) {
					String columnName = metaData.getColumnName(i + 1);
					row.put(columnName, resultSet.getObject(i + 1));
				}
				rows.add(row);
			}
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			try {
				//System.out.println(rows);
				conn.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			DbUtil2.closeSource(resultSet, prepareStatement, conn);
		}
		return rows;
	}
   
	public static int selectColumnCount(String sqlString) {
		Connection conn = DbUtil2.getConnection();
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		int columnCount = 0;
		try {
			conn.setAutoCommit(false);
			prepareStatement = conn.prepareStatement(sqlString);
			resultSet = prepareStatement.executeQuery();
			for (; resultSet.next();) {
				columnCount++;
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
			DbUtil2.closeSource(resultSet, prepareStatement, conn);
		}
		return columnCount;
	}
	
	public static ArrayList<HashMap<String, Object>> selectBySQL(String sqlString, Object[] parms, int[] types) {
		if (parms == null || types == null || parms.length != types.length) {
			throw new RuntimeException("我也不知道这是什么异常！");
		}
		ArrayList<HashMap<String, Object>> rows = new ArrayList<HashMap<String, Object>>();
		Connection conn = DbUtil2.getConnection();
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		ResultSetMetaData metaData = null;
		try {
			conn.setAutoCommit(false);
			prepareStatement = conn.prepareStatement(sqlString);
			for (int len = parms.length, i = 0; i < len; i++) {
				prepareStatement.setObject(i + 1, parms[i], types[i]);
			}
			resultSet = prepareStatement.executeQuery();
			metaData = resultSet.getMetaData();
			int columnCount = metaData.getColumnCount();
			for (; resultSet.next();) {
				HashMap<String, Object> row = new HashMap<String, Object>();
				for (int i = 0; i < columnCount; i++) {
					String columnName = metaData.getColumnName(i + 1);
					row.put(columnName, resultSet.getObject(i + 1));
				}
				rows.add(row);
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
			DbUtil2.closeSource(resultSet, prepareStatement, conn);
		}
		return rows;
	}

	public static int updateBySQL(String sqlString) {
		Connection conn = DbUtil2.getConnection();
		PreparedStatement prepareStatement = null;
		int result = 0;
		try {
			conn.setAutoCommit(false);
			prepareStatement = conn.prepareStatement(sqlString);
			result = prepareStatement.executeUpdate();
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			try {
				conn.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			DbUtil2.closeSource(null, prepareStatement, conn);
		}
		return result;
	}
	public static int deleteBySQL(String sqlString) {
		Connection conn = DbUtil2.getConnection();
		PreparedStatement prepareStatement = null;
		int result = 0;
		try {
			conn.setAutoCommit(false);
			prepareStatement = conn.prepareStatement(sqlString);
			result = prepareStatement.executeUpdate();
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
			DbUtil2.closeSource(null, prepareStatement, conn);
		}
		return result;
	}

	public static int updateBySQL(String sqlString, Object[] parms ) {
		Connection conn = DbUtil2.getConnection();
		PreparedStatement prepareStatement = null;
		int result = 0;
		try {
			conn.setAutoCommit(false);
			prepareStatement = conn.prepareStatement(sqlString);
			for (int len = parms.length, i = 0; i < len; i++) {
				prepareStatement.setObject(i + 1, parms[i]);
			}
			result = prepareStatement.executeUpdate();
			conn.commit();
		} catch (SQLException e) {
			//System.out.println(sqlString);
			e.printStackTrace();
			//throw new RuntimeException(e);
		} finally {
			try {
				conn.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			DbUtil2.closeSource(null, prepareStatement, conn);
		}
		return result;
	}
	public static int updateBySQL1(String sqlString) {
		Connection conn = DbUtil2.getConnection();
		Statement st=null;
		int result = 0;
		try {
			conn.setAutoCommit(false);
			result = st.executeUpdate(sqlString);
			conn.commit();
		} catch (SQLException e) {
			//System.out.println(sqlString);
			e.printStackTrace();
			//throw new RuntimeException(e);
		} finally {
			try {
				conn.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			DbUtil2.closeSource(null, st, conn);
		}
		return result;
	}
	public static int updateBySQL(String sqlString, Object[] parms, int[] types) {
		Connection conn = DbUtil2.getConnection();
		PreparedStatement prepareStatement = null;
		int result = 0;
		try {
			conn.setAutoCommit(false);
			prepareStatement = conn.prepareStatement(sqlString);
			for (int len = parms.length, i = 0; i < len; i++) {
				prepareStatement.setObject(i + 1, parms[i], types[i]);
			}
			result = prepareStatement.executeUpdate();
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
			DbUtil2.closeSource(null, prepareStatement, conn);
		}
		return result;
	}
}

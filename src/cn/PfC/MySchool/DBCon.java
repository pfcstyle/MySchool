package cn.PfC.MySchool;
import java.sql.*;
public class DBCon {  
	public static Connection dbCon; 
	public ResultSet rs=null;
    public Statement stmt=null;
		public DBCon(){
	        String driverName = "oracle.jdbc.driver.OracleDriver"; // 加载JDBC驱动  
	        String dbURL = "jdbc:oracle:thin:@127.0.0.1:1521:ORCLPFC"; // 连接服务器和数据库                                                                              
	        String userName = "pfc";   
	        String userPwd = "pfc110"; 
	        try {  
	            Class.forName(driverName);  
	            dbCon = DriverManager.getConnection(dbURL, userName, userPwd);  
	            stmt = dbCon.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_READ_ONLY);
	           System.out.println("Connection Successful!");                                            
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        }  
		}
		/*
		 * 执行查询操作
		 */
		public ResultSet executeQuery(String sql) {
			  try {
			    rs = stmt.executeQuery(sql);
			  }
			  catch (SQLException ex) {
			    System.err.println(ex.getMessage());
			  }
			  return rs;
			}
		/*
		 *功能:执行添加、修改或者删除操作
		 */
		public int executeUpdate(String sql) {
		  int result = 0;
		  try {
		    result = stmt.executeUpdate(sql);
		  }
		  catch (SQLException ex) {
		    result = 0;
		  }
		  try {
		    stmt.close();
		  }
		  catch (SQLException ex1) {
		  }
		  return result;
		}

		    /*
		     *功能:关闭数据库的连接
		     */
		    public void close() {
		      try {
		        if (rs != null) {
		          rs.close();
		        }
		      }
		      catch (Exception e) {
		        e.printStackTrace(System.err);
		      }
		      try {
		        if (stmt != null) {
		          stmt.close();
		        }
		      }
		      catch (Exception e) {
		        e.printStackTrace(System.err);
		      }
		      try {
		        if (dbCon != null) {
		          dbCon.close();
		        }
		      }
		      catch (Exception e) {
		        e.printStackTrace(System.err);
		      }
		  }
}  

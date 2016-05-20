package cn.PfC.MySchool;
import java.sql.*;
public class DBCon {  
	public static Connection dbCon; 
	public ResultSet rs=null;
    public Statement stmt=null;
		public DBCon(){
	        String driverName = "oracle.jdbc.driver.OracleDriver"; // ����JDBC����  
	        String dbURL = "jdbc:oracle:thin:@127.0.0.1:1521:ORCLPFC"; // ���ӷ����������ݿ�                                                                              
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
		 * ִ�в�ѯ����
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
		 *����:ִ����ӡ��޸Ļ���ɾ������
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
		     *����:�ر����ݿ������
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

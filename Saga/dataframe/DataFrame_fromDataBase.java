package dataframe;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.mysql.cj.jdbc.DatabaseMetaData;
import com.mysql.cj.jdbc.MysqlDataSource;

import particles.Particle;

public class DataFrame_fromDataBase {
	
	static Connection conn;
	static Statement stmt = null;
	private static ArrayList<String> columnTypes;
	private static ArrayList<String> columnNames;
	
	public static DataFrame fromDataBase(String dbURL,String user, String password,String datatable) {
		columnTypes = new ArrayList<String>();
		columnNames = new ArrayList<String>();
		 try{
			 Class.forName("com.mysql.jdbc.Driver");
			 MysqlDataSource ds = new MysqlDataSource();
			 ds.setURL(dbURL);
			 ds.setUser(user);
			 ds.setPassword(password);
			 conn = ds.getConnection();
	         // Step 1: Allocate a database 'Connection' object
			 // The format is: "jdbc:mysql://hostname:port/databaseName", "username", "password"
			 if (conn != null)              
				 System.out.println("Connected");             
			 else            
				 System.out.println("Not Connected"); 
			 
			 getColumns(datatable);
			 DataFrame df = new DataFrame();
			 for(int i = 0; i < columnNames.size(); i++) {
				 df.columnNames.add(columnNames.get(i));
			 }
			 ResultSet rs = null;
			 stmt = conn.createStatement();
	         rs = stmt.executeQuery("SELECT * FROM "+datatable);
	         while (rs.next()) {
	        	 for(int i = 0; i < columnNames.size(); i++) {
	        		 if(columnTypes.get(i).contentEquals("INT") || columnTypes.get(i).contentEquals("MEDIUMINT") || columnTypes.get(i).contentEquals("TINYINT") || columnTypes.get(i).contentEquals("SMALLINT")) {
	        			 Particle p = Particle.resolveType(rs.getInt(i+1));
	                 	if(p.type == 'i' || p.type == 'd') {
	                 		df.numericIndexes.add(i);
	                 		Column c = new Column(df.columnNames.get(i));
	                 		c.add(p);
	                 		c.setType('N');
	                 		df.columns.add(c);

	                 	}else if(p.type == 'n') {
	                 		Column c = new Column(df.columnNames.get(i));
	                 		c.add(p);
	                 		c.setType('M');
	                 		df.columns.add(c);
	                 			
	                 	}else {
	                 		Column c = new Column(df.columnNames.get(i));
	                 		c.add(p);
	                 		c.setType('C');
	                 		df.columns.add(c);

	                 	}
	        		 }
	        		 if(columnTypes.get(i).contentEquals("VARCHAR")) {
	        			 Particle p = Particle.resolveType(rs.getString(i+1));
		                 	if(p.type == 'i' || p.type == 'd') {
		                 		df.numericIndexes.add(i);
		                 		Column c = new Column(df.columnNames.get(i));
		                 		c.add(p);
		                 		c.setType('N');
		                 		df.columns.add(c);

		                 	}else if(p.type == 'n') {
		                 		Column c = new Column(df.columnNames.get(i));
		                 		c.add(p);
		                 		c.setType('M');
		                 		df.columns.add(c);
		                 			
		                 	}else {
		                 		Column c = new Column(df.columnNames.get(i));
		                 		c.add(p);
		                 		c.setType('C');
		                 		df.columns.add(c);

		                 	}
	        		 }
	        	 }
	         }
			 conn.close(); 
		 } 
		 catch(Exception e) { 
			 System.out.println(e); 
		 }
		return null;
		
	}
	public static void describeTables() throws SQLException {
	      ResultSet rs = null;
	      DatabaseMetaData meta = (DatabaseMetaData) conn.getMetaData();
	      rs = meta.getTables(null, null, null, new String[] {
	         "TABLE"
	      });
	      int count = 0;
	      System.out.println("All table names are in test database:");
	      while (rs.next()) {
	         String tblName = rs.getString("TABLE_NAME");
	         System.out.println(tblName);
	         count++;
	      }
	      System.out.println(count + " Rows in set ");   
	}
	public static void describeColumns(String dataTable) throws SQLException {
		stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM "+dataTable);
		ResultSetMetaData rsMetaData = rs.getMetaData();
		int numberOfColumns = rsMetaData.getColumnCount();
		System.out.println("Column Count: "+ numberOfColumns);
		// get the column names; column indexes start from 1
		for (int i = 1; i < numberOfColumns + 1; i++) {
			String columnName = rsMetaData.getColumnName(i);
			// Get the name of the column's table name
			String tableName = rsMetaData.getTableName(i);
			String coltype = rsMetaData.getColumnTypeName(i);
			System.out.println("column name: " + columnName + " Type: "+coltype+" table: " + tableName + "");
		}
	}
	public static void getColumns(String dataTable) throws SQLException {
		stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM "+dataTable);
		ResultSetMetaData rsMetaData = rs.getMetaData();
		int numberOfColumns = rsMetaData.getColumnCount();
		System.out.println("Column Count: "+ numberOfColumns);
		// Get the name of the column's table name
		//String tableName = rsMetaData.getTableName(i);
		// get the column names; column indexes start from 1
		for (int i = 1; i < numberOfColumns + 1; i++) {
			columnNames.add(rsMetaData.getColumnName(i));
			columnTypes.add(rsMetaData.getColumnTypeName(i));
		}
	}
}

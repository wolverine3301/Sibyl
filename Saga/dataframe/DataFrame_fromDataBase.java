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
			 //System.out.println(columnNames);
			 //System.out.println(columnTypes);
			 DataFrame df = new DataFrame();
			 for(int i = 0; i < columnNames.size(); i++) {
				 df.columnNames.add(columnNames.get(i));
			 }
			 ResultSet rs = null;
			 stmt = conn.createStatement();
	         rs = stmt.executeQuery("SELECT * FROM "+datatable);
	         boolean init = true;
	         int cnt1 = 0;
	         int cnt = 0;
	         while (rs.next()) {
	        	 cnt = 0;
	        	 Row row = new Row();
	        	 for(int i = 1; i <= columnNames.size(); i++) {
	        		 
	        		 if(init){
	        			 Particle p = Particle.resolveType(rs.getString(i));
	                 	if(p.type == 'i' || p.type == 'd') {
	                 		
	                 		df.numericIndexes.add(cnt);
	                 		Column c = new Column(df.columnNames.get(cnt));
	                 		c.add(p);
	                 		c.setType('N');
	                 		df.columns.add(c);

	                 	}else if(p.type == 'n') {
	                 		
	                 		Column c = new Column(df.columnNames.get(cnt));
	                 		c.add(p);
	                 		c.setType('M');
	                 		df.columns.add(c);
	                 		
	                 	}else {
	                 		
	                 		Column c = new Column(df.columnNames.get(cnt));
	                 		c.add(p);
	                 		c.setType('C');
	                 		df.columns.add(c);

	                 	}
	                 	row.add(p);
	                 	
	        			 /*
		        		 if(columnTypes.get(i).contentEquals("INT") || columnTypes.get(i).contentEquals("MEDIUMINT") || columnTypes.get(i).contentEquals("TINYINT") || columnTypes.get(i).contentEquals("SMALLINT") || columnTypes.get(i).contentEquals("BIT")) {
		        			 
		        			 Particle p = Particle.resolveType(rs.getInt(columnNames.get(i)));
		        			 System.out.println("TO STRING: "+p.toString() + " TYPE: "+p.getType());
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
		        		 else if(columnTypes.get(i).contentEquals("VARCHAR")) {
		        			 Particle p = Particle.resolveType(rs.getString(columnNames.get(i)));
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
		        		 */
		        		 //df.rows.add(row);
		        		 
		        		 //System.out.println("COLS: "+df.columns.size());
		        	 }//end init
	        		 else {
	        			 Particle p = Particle.resolveType(rs.getString(i));
	        			 df.columns.get(cnt).add(p);
		                 row.add(p);
	        		 }
	                 
	        		 cnt++;
	        	 }
	        	 cnt1++;
	        	 init = false;
	        	 df.rows.add(row);
	        	 if(cnt1 > 1000) {
	        		 System.out.println("BREAKING");
	        		 break;
	        	 }
	        	 
	         }
	        for(int i = 0;i < df.numericIndexes.size();i++) {
	        	System.out.println(df.getColumn(df.numericIndexes.get(i)).getName());
	        	df.getColumn(df.numericIndexes.get(i)).setStatistics();
	        	//df.columnTypes.add(df.columns.get(i).getType());
	        	
	        }
	        System.out.println("STATS SET");
	        df.numRows = df.rows.size();
	        df.numColumns = df.columns.size();
	        System.out.println("SIZE: "+df.columns.size());
			conn.close(); 
			return df;
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

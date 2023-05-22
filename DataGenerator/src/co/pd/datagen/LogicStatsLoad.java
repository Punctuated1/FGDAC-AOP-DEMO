package co.pd.datagen;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import com.microsoft.sqlserver.jdbc.SQLServerDriver;

public class LogicStatsLoad {
	public static final String STATS_FOLDER = "D:\\\\Documents\\\\Punctuated-Dev\\\\POCWork\\\\";
	public static final String STATS_FILE_NAME = "PG-BM-DetailStats-2023-03-08133321.csv";
	public static final String DELIMITER = ",";
	public static final String connectionUrl = "jdbc:sqlserver://127.0.0.1:1433;encrypt=false;databaseName=Adjudication;user=hmtadj;password=password1";
	public static final String statInsertStatement = "INSERT INTO dbo.[PG-BM-DetailStats-2023-03-08133321] "
			+ "           (Operation "
			+ "           ,SetId"
			+ "           ,Start_TS"
			+ "           ,End_TS"
			+ "           ,Duration_Nanos"
			+ "           ,Duration"
			+ "           ,Duration2"
			+ "           ,Duration3"
			+ "           ,Duration4"
			+ "           ,Duration5) VALUES (?,?,?,?,?,?,?,?,?,?)";
	
	public void doIt() {
		readStatsFile(STATS_FOLDER+STATS_FILE_NAME);
		
	}

	   private void readStatsFile(String csvFile) {
		   //log
		   try {
			 Connection con = DriverManager.getConnection(connectionUrl);  
			 PreparedStatement insertStatstmt = con.prepareStatement(statInsertStatement);
	         File file = new File(csvFile);
	         FileReader fr = new FileReader(file);
	         BufferedReader br = new BufferedReader(fr);
	         String line = "";
	         String[] tempArr;
	         int i=0;
	         while((line = br.readLine()) != null) {
	        	 if(i>0) {
	        		 
		        	tempArr = line.split(DELIMITER);
		            String operation  = tempArr[0];
		            String setId = tempArr[1];
		            LocalDateTime start_ts = LocalDateTime.parse(tempArr[2]);
		            LocalDateTime end_ts = LocalDateTime.parse(tempArr[3]);
		            long duration1 = Long.parseLong(tempArr[4]);
 
		            insertStatstmt.setString(1, operation);
		            insertStatstmt.setString(2, setId);
		            insertStatstmt.setTimestamp(3, Timestamp.valueOf(start_ts));
		            insertStatstmt.setTimestamp(4, Timestamp.valueOf(end_ts));
		            insertStatstmt.setLong(5, duration1);
		            insertStatstmt.setLong(6, duration1);
		            insertStatstmt.setLong(7, duration1);
		            insertStatstmt.setLong(8, duration1);
		            insertStatstmt.setLong(9, duration1);
		            insertStatstmt.setLong(10, duration1);
		            
		            int resultRow = insertStatstmt.executeUpdate();
		            if(i/10000*10000==i) {
		            	System.out.println("i: "+i+"  result Row: "+resultRow);
		            }
	        	 }
	        	 i++;
	         } 
		     br.close();
	    } catch(Exception ex) {
	            ex.printStackTrace();
	    }
   	}	

}

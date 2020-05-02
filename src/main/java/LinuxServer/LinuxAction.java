package LinuxServer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import CommonPlatform.CommonOSModule;
import MyAction.ActionModule;
import MyVariable.VariableModule;

public class LinuxAction {

	
	
	public static String[] TokenizerAction(String splitString, String delims) //http://crunchify.com/java-stringtokenizer-and-string-split-example/
	{
		
		//String delims = "[=+]";
		//writingLog("Input String: " +splitString);
		
		String[] tokens=splitString.split(delims);
		//writingLog("String Lenght: " +tokens.length);
/*		    for (int i=0; i< tokens.length; i++){
	      writingLog("StringTokenizer Output: " +tokens[i]);
	    }*/
	    //System.out.println("StringTokenizer: " +tokens[3]);
	    return tokens;
	}
	
	
	public static void CommandExecModule(String command, int durationmili1, int comflag) {
		String line;
    	
		//Runtime rt = Runtime.getRuntime();
		Process P;
		long freemem;
        try {
        	
        	//P=Runtime.getRuntime().exec(TokenizerAction(command,"\\s"));
        	P=Runtime.getRuntime().exec(command);
        	//freemem=Runtime.getRuntime().freeMemory();
        	//writingLog("Free Memory: "+freemem/1024);
        	try {
				P.waitFor();
				//Thread.sleep(10000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            BufferedReader input = new BufferedReader(new InputStreamReader(P.getInputStream()));
            //System.out.println("Input: "+input);
            if(comflag==1) //CPU Parser
            {

            	LinuxServerParser.LinuxCPUParser(input,50);
            }
            
            else if(comflag==2) { 
            	while ((line = input.readLine()) != null) {
            		//ConsolPrint(line);
            		writingLog(line);
            	}

            }
            
            else if(comflag==3) //Memory Parser
            {

            	LinuxServerParser.LinuxMemoryParser(input,50);
            }
            
            else if(comflag==4) //Disk Write Speed Parser
            {

            	LinuxServerParser.LinuxDiskWriteSpeedParser(input,50);
            }
            
            else if(comflag==5) //Disk Read Speed Parser
            {

            	LinuxServerParser.LinuxDiskReadSpeedParser(input,50);
            }
            
            else if(comflag==6) //Disk Space Parser
            {

            	LinuxServerParser.LinuxDiskSpaceParser(input,50);
            }
            
            input.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
	}
	
    public static void ConsolPrint(String wget) {

    	System.out.println("\n"+wget+"\n");
    }
    
    public static String getCurrentDate() {
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat dateobject = new SimpleDateFormat("ddMMyyyy-HHmmss");
        return dateobject.format(date);
    }
    
    
    public static void writingLog(String wget) {
        try {
            //Whatever the file path is.
        	File WFile = new File("/usr/local/SFD/Logs/Output.txt");
        	//File WFile = new File(file_name);
        	FileWriter fw = new FileWriter(WFile,true);
/*            FileOutputStream is = new FileOutputStream(WFile);
            OutputStreamWriter osw = new OutputStreamWriter(is);    
            Writer w = new BufferedWriter(osw);*/
        	
        	if(wget!=null)
        	{
        	fw.write("\n"+wget+"\n");
        	}
            //fw.write(wget);
            //fw.write("\n");
            fw.close();
        } catch (IOException e) {
        	//LinuxAction.writingLog("Problem writing to the file Output.txt");
        }
    }
    
    public static double DecimalFormat(double decimalvalue) {
    	//writingLog("CPU Value: "+decimalvalue);
    	DecimalFormat four = new DecimalFormat("0.00");
    	double result=Double.parseDouble(four.format(decimalvalue));
    	return result;
    }
    
    public static Connection MysqlConnection()
    {
	    Connection conn = null;
	    try {
	        // STEP 2: Register JDBC driver
	        Class.forName("com.mysql.jdbc.Driver");

	        // STEP 3: Open a connection
	        //System.out.print("\nConnecting to database...");
	        conn = DriverManager.getConnection(LinuxVariable.mysqlurl, LinuxVariable.dbusername, LinuxVariable.dbpassword);
	        LinuxAction.writingLog(" Database Connected!\n");
	    }catch(SQLException se) {
	        se.printStackTrace();
	    } catch(Exception e) {
	        e.printStackTrace();
	    }
		return conn;
    }
    
	public static void MysqlInsertData(String tablename, String columnname, double value, int service,int disk)
	{
		String sql=null;
	    Connection conn = null;
	    String[] DiskArray = null;
	    String[] DiskArray1 = null;
	    java.sql.Statement stmt = null;
	    try {
	    	conn=MysqlConnection();
	        stmt = conn.createStatement();
	        
	        
	        if(service==3)
	        {
	        	DiskArray = columnname.split(",");
	        	DiskArray1 = tablename.split(",");
	        	tablename=DiskArray1[0];
	        }
	        
	        
	        if(tablename.equals("cpudata"))
	        {
		        sql = "INSERT INTO " + tablename + " (cdOSType,cdServerIP," + columnname + ",cdDayID)" +
			            " VALUES (3,'"+CommonOSModule.MyIPAddress+"'," + value + ",'" + getDayID() + "');";
		        LinuxAction.writingLog("SQL: "+sql);
	        }
	        
	        else if(tablename.equals("memorydata"))
	        {
		        sql = "INSERT INTO " + tablename + " (mdOSType,mdServerIP," + columnname + ",mdDayID)" +
			            " VALUES (3,'"+CommonOSModule.MyIPAddress+"'," + value + ",'" + getDayID() + "');";
		        LinuxAction.writingLog("SQL: "+sql);
	        }
	        
	        else if(tablename.equals("diskdata"))
	        {
		        sql = "INSERT INTO " + tablename + " (ddOSType,ddServerIP," + DiskArray[0]+","+DiskArray[1] + ",ddType,ddDayID)" +
			            " VALUES (3,'"+CommonOSModule.MyIPAddress+"'," + value +",'"+DiskArray1[1]+ "'," + disk + ",'" + getDayID() + "');";
		        LinuxAction.writingLog("SQL: "+sql);
	        }
	        
	        else if(tablename.equals("diskspace"))
	        {

		        sql = "INSERT INTO " + tablename + " (dsOSType,dsServerIP," + columnname + ",dsDayID)" +
			            " VALUES (3,'"+CommonOSModule.MyIPAddress+"'," + value + ",'" + getDayID() + "');";
		        LinuxAction.writingLog("SQL: "+sql);
	        }
	        
	        else if(tablename.equals("alarmtracker"))
	        {
		        sql = "INSERT INTO " + tablename + " (atOSType,atServerIP," + columnname + ",atServiceID,atDayID)" +
			            " VALUES (3,'"+CommonOSModule.MyIPAddress+"'," + value + "," + service + ",'" + getDayID() + "');";
		        LinuxAction.writingLog("SQL: "+sql);
	        }
	        stmt.executeUpdate(sql);

	        LinuxAction.writingLog(" Insert Successful!\n");

	    } catch(SQLException se) {
	        se.printStackTrace();
	    } catch(Exception e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if(stmt != null)
	                conn.close();
	        } catch(SQLException se) {
	        }
	        try {
	            if(conn != null)
	                conn.close();
	        } catch(SQLException se) {
	            se.printStackTrace();
	        }
	    }
	    
	}
	
    public static String getDayID() {
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat dateobject = new SimpleDateFormat("ddMMyyyy");
        return dateobject.format(date);
    }
    
	public static void FileBackupAction(String flag) {
		

		File filelog = new File(LinuxVariable.driverlog+"/SFD/Logs");
        
        
        FolderCreateAction(filelog);
		String currentDate = ActionModule.getCurrentDate();
		int randomValue = (int )(Math.random() * 500 + 1);
        File oldName = new File(LinuxVariable.driverlog+"/SFD/Logs/Output.txt");
        File newName = new File(LinuxVariable.driverlog+"/SFD/Logs/Output-"+currentDate+".txt");
        oldName.renameTo(newName);
/*        if(flag.equals("1"))
        {
	        File fileexcel = new File(LinuxVariable.driverlog+"/SFD/Reports");
	        FolderCreateAction(fileexcel);
	        CommonOSModule.ExcelFileCreateAction();
        }*/
	}
	
	public static void FolderCreateAction(File T) {
        if(!T.exists()){  
        	if(T.mkdir()){ 
        		//System.out.println("directory is  created"); 
        		}
        	}  
        else { 
        	//System.out.println("directory exist");  
        	}
	}
}

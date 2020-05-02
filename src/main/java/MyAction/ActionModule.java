package MyAction;

import java.awt.AWTException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputMethodListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

import javax.swing.SwingUtilities;

import com.mysql.jdbc.Statement;

import CommonPlatform.CommonOSModule;
import LinuxServer.LinuxAction;
import LinuxServer.LinuxVariable;
import MyGraph.GraphGeneratorModule;
import MyVariable.VariableModule;
import ServerParser.ServerParserModule;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;


public class ActionModule {

	
	public static void FileBackupAction(String flag) {
		VariableModule variabledo = new VariableModule();

		File filelog = new File(variabledo.driverlog+"/SFD/Logs");
        
        
        FolderCreateAction(filelog);
		String currentDate = ActionModule.getCurrentDate();
		int randomValue = (int )(Math.random() * 500 + 1);
        File oldName = new File(variabledo.driverlog+"/SFD/Logs/Output.txt");
        File newName = new File(variabledo.driverlog+"/SFD/Logs/Output-"+currentDate+".txt");
        oldName.renameTo(newName);
        if(flag.equals("1"))
        {
	        File fileexcel = new File(variabledo.driverlog+"/SFD/Reports");
	        FolderCreateAction(fileexcel);
	        CommonOSModule.ExcelFileCreateAction();
        }
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
	
	public static void ExecWebRequest(int flag) {
		try {
        URL yahoo = new URL("https://script.googleusercontent.com/macros/echo?user_content_key=y8yCqBm1LZRCnmIXm5f7nRb8oYAK47xClv0AydFootWMkEp9nY15btjwNcO854IWcbZ0afk9-WgXFY_sc7NhEFVmLTc8-4d2m5_BxDlH2jW0nuo2oDemN9CCS2h10ox_1xSncGQajx_ryfhECjZEnJ9GRkcRevgjTvo8Dc32iw_BLJPcPfRdVKhJT5HNzQuXEeN3QFwl2n0M6ZmO-h7C6bwVq0tbM60-u75L0xvd6HiaTderZSNV8A&lib=MwxUjRcLr2qLlnVOLh12wSNkqcO1Ikdrk");
        URLConnection yc = yahoo.openConnection();
        BufferedReader in = new BufferedReader(
                                new InputStreamReader(
                                yc.getInputStream()));
        
        StandardTimeParser(in,flag);

		}
		catch (IOException e) {
            System.err.println("Problem in executing web url");
        }
	}
	
	public static void StandardTimeParser(BufferedReader time,int flag) {
        String inputLine;
        String[] timeArray = null;
        
        String[] DateTime = new String[10];
        try {
			while ((inputLine = time.readLine()) != null) {
			    //System.out.println(inputLine);
				timeArray= inputLine.split(",");

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        //System.out.println("Time Array: "+timeArray.length);
        for(int i=0;i<timeArray.length;i++) {
        	//System.out.println("Time Array"+i+" : "+timeArray[i]);
        	if(i==2) { //day
        		String[] timeArray2=timeArray[2].split(":");
        		DateTime[0]=AddZero(timeArray2[1]);
        		
        	}
        	else if(i==3) { //month
        		String[] timeArray3=timeArray[3].split(":");
        		DateTime[1]=AddZero(timeArray3[1]);
        	}
        	else if(i==5) { //year
        		String[] timeArray5=timeArray[5].split(":");
        		DateTime[2]=AddZero(timeArray5[1]);
        	}
        	else if(i==6) { //hour
        		String[] timeArray6=timeArray[6].split(":");
        		DateTime[3]=AddZero(timeArray6[1]);
        	}
        	else if(i==7) { //minute
        		String[] timeArray7=timeArray[7].split(":");
        		DateTime[4]=AddZero(timeArray7[1]);
        	}
        	else if(i==8) { //second
        		String[] timeArray8=timeArray[8].split(":");
        		DateTime[5]=AddZero(timeArray8[1]);
        	}
        	else if(i==9) { //milisecond
        		String[] timeArray9=timeArray[9].split(":");
        		DateTime[6]=AddZero(timeArray9[1]);
        	}
        }
        String[] CurDateTime=getCurrentDateTime();
        
        String stddatetime = DateTime[0]+DateTime[1]+DateTime[2]+"-"+DateTime[3]+DateTime[4]+DateTime[5];
        String curdatetime = CurDateTime[0]+CurDateTime[1]+CurDateTime[2]+"-"+CurDateTime[3]+CurDateTime[4]+CurDateTime[5];
        
        LocalTime start = LocalTime.parse( DateTime[3]+":"+DateTime[4]+":"+DateTime[5] );
        LocalTime stop = LocalTime.parse( CurDateTime[3]+":"+CurDateTime[4]+":"+CurDateTime[5] );
        Duration duration = Duration.between( start, stop );
        
        if(flag==1)
        {
	        ServerParserModule.ServerArray[12]=stddatetime;
	        ServerParserModule.ServerArray[13]=curdatetime;
	        String duration11=duration.toString().replaceAll("PT", "");
	        ServerParserModule.ServerArray[14]=duration11;
        }
        else {
        	
    		ActionModule.ConsolPrint("\n*********Server Time Parser-"+getCurrentDate()+"*********\n");
    		ActionModule.writingLog("\n********Server Time Parser-"+getCurrentDate()+"**********\n");
    		
        ConsolPrint("Standard Date and Time: \t"+stddatetime);
    	writingLog("Standard Date and Time: \t"+stddatetime);
    	
        ConsolPrint("System current Date and Time:   "+curdatetime);
    	writingLog("System current Date and Time:   "+curdatetime);
    	String duration1=null;
		if(!DateTime[0].equals(CurDateTime[0]) || !DateTime[1].equals(CurDateTime[1]) || !DateTime[2].equals(CurDateTime[2])) {
			
	        ConsolPrint("Time Difference is exceeded the threshold time difference");
	    	writingLog("Time Difference is exceeded the threshold time difference");
	    	PlayAlarmAction();
			
		}
		else {
    		if(duration.toString().contains("M") || duration.toString().contains("H")) {
    	        ConsolPrint("Time Difference is exceeded the threshold time difference");
    	    	writingLog("Time Difference is exceeded the threshold time difference");
    	    	PlayAlarmAction();
    		}
    		else if(duration.toString().contains("S")) {
    			String[] timedif;
    			if(duration.toString().contains("-")) {
    				timedif= duration.toString().split("-");
    				duration1=duration.toString().replaceAll("PT-", "");
    			}
    			else {
    				timedif = duration.toString().split("PT");
    				duration1=duration.toString().replaceAll("PT", "");
    			}
    			String timedif1 = timedif[1].replaceAll("S", "");
    			//String duration1=duration.toString().replaceAll("PT", "");
    			
		        ConsolPrint("Time difference between timezone standard time and system current time: "+duration1);
		    	writingLog("Time difference between timezone standard time and system current time: "+duration1);
		    	
    			if(Double.parseDouble(timedif1)>VariableModule.TimeDifferencevalue) {
    				

        	        ConsolPrint("Time Difference is exceeded the threshold time difference");
        	    	writingLog("Time Difference is exceeded the threshold time difference");
        	    	PlayAlarmAction();
    			}

    		}
		}
    		
    	
        }
        
	}
	
    public static String AddZero(String value) {
    	int intvalue=Integer.parseInt(value);
    	
    	if(intvalue<10) {
    		value="0"+value;
    		
    	}
    	
    	return value;
    }
    
    
    public static String getCurrentDate() {
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat dateobject = new SimpleDateFormat("ddMMyyyy-HHmmss");
        return dateobject.format(date);
    }
    
    public static String getDayID() {
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat dateobject = new SimpleDateFormat("ddMMyyyy");
        return dateobject.format(date);
    }
    
    public static String[] getCurrentDateTime() {
    	String[] DateTime = new String[10];
    	for(int i=0;i<6;i++) {
    		if(i==0) //day
    		{
		        Date date = Calendar.getInstance().getTime();
		        SimpleDateFormat dateobject = new SimpleDateFormat("dd");
		        DateTime[i]=dateobject.format(date);
    		}
    		else if(i==1) //month
    		{
		        Date date = Calendar.getInstance().getTime();
		        SimpleDateFormat dateobject = new SimpleDateFormat("MM");
		        DateTime[i]=dateobject.format(date);
    		}
    		else if(i==2) //year
    		{
		        Date date = Calendar.getInstance().getTime();
		        SimpleDateFormat dateobject = new SimpleDateFormat("yyyy");
		        DateTime[i]=dateobject.format(date);
    		}
    		else if(i==3) //hour
    		{
		        Date date = Calendar.getInstance().getTime();
		        SimpleDateFormat dateobject = new SimpleDateFormat("HH");
		        DateTime[i]=dateobject.format(date);
    		}
    		else if(i==4) //minute
    		{
		        Date date = Calendar.getInstance().getTime();
		        SimpleDateFormat dateobject = new SimpleDateFormat("mm");
		        DateTime[i]=dateobject.format(date);
    		}
    		else if(i==5) //second
    		{
		        Date date = Calendar.getInstance().getTime();
		        SimpleDateFormat dateobject = new SimpleDateFormat("ss");
		        DateTime[i]=dateobject.format(date);
    		}
    		//System.out.println("Current Time Array"+i+" : "+DateTime[i]);
    	}
        return DateTime;
    }
    

    public static Connection MysqlConnection()
    {
	    Connection conn = null;
	    try {
	        // STEP 2: Register JDBC driver
	        Class.forName("com.mysql.jdbc.Driver");

	        // STEP 3: Open a connection
	        //System.out.print("\nConnecting to database...");
	        conn = DriverManager.getConnection(VariableModule.mysqlurl, VariableModule.dbusername, VariableModule.dbpassword);
	        LinuxAction.writingLog(" Database Connected!\n");
	    }catch(SQLException se) {
	        se.printStackTrace();
	    } catch(Exception e) {
	        e.printStackTrace();
	    }
		return conn;
    }
    
	public static String MysqlConnectionAction(String Myquery)
	{

		String columnValue1="";
		String columnValue="";
		String[] URL=null;
	    Connection connection = null;
	    java.sql.Statement stmt = null;
		    
	    try {
	    	connection=MysqlConnection();
	    	stmt = connection.createStatement();
		    ResultSet rs=stmt.executeQuery(Myquery);
		    ResultSetMetaData rsmd = rs.getMetaData();
		    int columnsNumber = rsmd.getColumnCount();
		    while (rs.next()) {

		        for (int i = 1; i <= columnsNumber; i++) {
		            if (i > 1) System.out.print("\n");
		            columnValue = rs.getString(i);
		            //DBValues[i]=columnValue;
		            //System.out.print(columnValue);
		            //System.out.print(columnValue + " " + rsmd.getColumnName(i));
		            columnValue1=columnValue1.concat(columnValue+",");
		        }
		        //columnValue1=columnValue.concat(",");
		    }
		    //System.out.print("Test:"+columnValue1);
		    stmt.close();
		    connection.close();
		} catch (SQLException e) {
		    throw new IllegalStateException("Cannot connect the database!", e);
		}
		return columnValue1;
	}
	
	public static void MysqlInsertData(String tablename, String columnname, double value, int service,int disk)
	{
		String sql=null;
	    Connection conn = null;
	    java.sql.Statement stmt = null;
	    String[] DiskArray = null;
	    String[] DiskArray1 = null;
	    try {
	    	conn=MysqlConnection();


	        // STEP 5: Excute query
	        System.out.print("\nInserting records into table...");
	        stmt = conn.createStatement();
	        
	        if(service==3)
	        {
	        	DiskArray = columnname.split(",");
	        	DiskArray1 = tablename.split(",");
	        	tablename=DiskArray1[0];
	        }

	        if(tablename.equals("cpudata"))
	        {
		        sql = "INSERT INTO " + tablename + " (cdServerIP," + columnname + ",cdDayID)" +
			            " VALUES ('"+CommonOSModule.MyIPAddress+"'," + value + ",'" + getDayID() + "');";
		        System.out.println("SQL: "+sql);
	        }
	        else if(tablename.equals("memorydata"))
	        {
		        sql = "INSERT INTO " + tablename + " (mdServerIP," + columnname + ",mdDayID)" +
			            " VALUES ('"+CommonOSModule.MyIPAddress+"'," + value + ",'" + getDayID() + "');";
		        System.out.println("SQL: "+sql);
	        }
	        else if(tablename.equals("diskdata"))
	        {

		        sql = "INSERT INTO " + tablename + " (ddServerIP," + DiskArray[0]+","+DiskArray[1] + ",ddType,ddDayID)" +
			            " VALUES ('"+CommonOSModule.MyIPAddress+"'," + value +",'"+DiskArray1[1]+ "'," + disk + ",'" + getDayID() + "');";
		        System.out.println("SQL: "+sql);
	        }
	        else if(tablename.equals("diskspace"))
	        {

		        sql = "INSERT INTO " + tablename + " (dsServerIP," + columnname + ",dsDayID)" +
			            " VALUES ('"+CommonOSModule.MyIPAddress+"'," + value + ",'" + getDayID() + "');";
		        System.out.println("SQL: "+sql);
	        }
	        else if(tablename.equals("alarmtracker"))
	        {
		        sql = "INSERT INTO " + tablename + " (atServerIP," + columnname + ",atServiceID,atDayID)" +
			            " VALUES ('"+CommonOSModule.MyIPAddress+"'," + value + "," + service + ",'" + getDayID() + "');";
		        System.out.println("SQL: "+sql);
	        }
	        stmt.executeUpdate(sql);

	        System.out.println(" SUCCESS!\n");

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
	    System.out.println("Thank you for your patronage!");
	    
	}
	
	
    public void writing(String wget, int flag) {
        try {
        	if(flag==1) {
        		File WFile = new File(VariableModule.driverlog+"/SFD/Config/Variable.txt");
            	FileWriter fw = new FileWriter(WFile,true);
            	        	
            		        	if(wget!=null)
            		        	{
            		        	fw.write(wget);
            		        	}
            	            fw.close();
        	}
        	else {
        		File WFile = new File(VariableModule.driverlog+"/SFD/Config/Threshold.txt");
            	FileWriter fw = new FileWriter(WFile,true);
            	        	
            		        	if(wget!=null)
            		        	{
            		        	fw.write(wget);
            		        	}

            	            fw.close();
        	}
        	//File WFile = new File(file_name);

        } catch (IOException e) {
            System.err.println("Problem writing to the file Variable.txt");
        }
    }
    
    public static void writingLog(String wget) {
        try {
            //Whatever the file path is.
        	File WFile = new File(VariableModule.driverlog+"/SFD/Logs/Output.txt");
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
            System.err.println("Problem writing to the file Output.txt");
        }
    }
    
    
	
    
    public static void replaceThreshold(String replaceWith, String newthreshold) {
        try {
            // input the file content to the StringBuffer "input"
            BufferedReader file = new BufferedReader(new FileReader(VariableModule.driverlog+"/SFD/Config/Threshold.txt"));
            String line;
            StringBuffer inputBuffer = new StringBuffer();
            String matchstr = null;
            while ((line = file.readLine()) != null) {
                if (line.contains(replaceWith)) {
                    matchstr=line;
                    
                }
                inputBuffer.append(line);
                inputBuffer.append('\n');
            }
            String inputStr = inputBuffer.toString();

            file.close();

            //System.out.println("Replace Threshold: \n"+inputStr); // check that it's inputted right

            // this if structure determines whether or not to replace "0" or "1"
            
            //System.out.printf("%.2f",newthreshold);
            //newthreshold = String.format("%.2f");
            if (inputStr.contains(replaceWith)) {
                inputStr = inputStr.replace(matchstr, replaceWith+newthreshold+";"); 
                ConsolPrint("Threshold Value is updated");
            }

            
            // check if the new input is right
            //System.out.println("----------------------------------\n"  + inputStr);

            // write the new String with the replaced line OVER the same file
            FileOutputStream fileOut = new FileOutputStream(VariableModule.driverlog+"/SFD/Config/Threshold.txt");
            fileOut.write(inputStr.getBytes());
            fileOut.close();

        } catch (Exception e) {
            System.out.println("Problem reading file.");
        }
    }
    
    public void ThresholdInitialization() {
    	ServerParserModule.CommandExecModule("systeminfo", 1000,3);
    }
    
    public static double DecimalFormat(double decimalvalue) {
    	DecimalFormat four = new DecimalFormat("0.00");
    	double result=Double.parseDouble(four.format(decimalvalue));
    	return result;
    }
    
    public boolean ReadFile(String wget) {
        // The name of the file to open.
    	boolean flag = false;
        String fileName = VariableModule.driverlog+"/SFD/Config/Variable.txt";

        // This will reference one line at a time
        String line = null;

        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader = 
                new FileReader(fileName);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = 
                new BufferedReader(fileReader);
            

	            while((line = bufferedReader.readLine()) != null) {
	                //System.out.println(line);
		            	String[] line1=line.split("=");
		            	if(wget.equals(line1[0])) {
		            		flag=true;
		            		break;
		            	}
	            }   

            // Always close files.
            bufferedReader.close();         
        }
        catch(FileNotFoundException ex) {
            System.out.println(
                "Unable to open file '" + 
                fileName + "'");                
        }
        catch(IOException ex) {
            System.out.println(
                "Error reading file '" 
                + fileName + "'");                  
            // Or we could just do this: 
            // ex.printStackTrace();
        }
        return flag;
    }
    
		public static String[] TokenizerAction(String splitString) //http://crunchify.com/java-stringtokenizer-and-string-split-example/
		{
			
			String delims = ".";
			//String delims = "[=+]";
			String[] tokens=splitString.split(delims);
/*		    for (int i=2; i< tokens.length-1; i++){
		      System.out.println("StringTokenizer Output: " +tokens[i]);
		    }*/
		    //System.out.println("StringTokenizer: " +tokens[3]);
		    return tokens;
		}
		
        
	    public static void ConsolPrint(String wget) {

	    	System.out.println("\n"+wget+"\n");
	    }
	    
	    public static void PlayAlarmAction()
	    {
	        // open the sound file as a Java input stream
	        String alarmFile = VariableModule.driverlog+"/SFD/MyAlarm.wav";
	        
	/*		try {
				in = new FileInputStream(alarmFile);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/

	        // create an audiostream from the inputstream
	        InputStream in;
	        AudioStream audioStream = null;
			try {
				in = new FileInputStream(alarmFile);
				audioStream = new AudioStream(in);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	        // play the audio clip with the audioplayer class
	        AudioPlayer.player.start(audioStream);
	    }
	    
	    
	    public static void TrayAction() {
	 	   if(!SystemTray.isSupported()){
	 	        System.out.println("System tray is not supported !!! ");
	 	        //return ;
	 	    }
	 	   
	 	   	Image image = Toolkit.getDefaultToolkit().getImage(VariableModule.driverlog+"/SFD/MyIcon.gif");
	 	    //Image image = Toolkit.getDefaultToolkit().getImage(VariableModule.driverlog+"/SFD/MyIcon.gif");
	 	    PopupMenu trayPopupMenu = new PopupMenu();
	 	    final TrayIcon trayIcon = new TrayIcon(image, "SFD Tool", trayPopupMenu);
	 	    SystemTray systemTray = SystemTray.getSystemTray();


	 	    //showNotification(1,"Warning!!!", "Memory Usage: \t"+ServerParserModule.MemoryAverage+"\nCPU Usage:       \t"+ServerParserModule.CPUAverage,trayIcon);
	 	    
	 	    MenuItem close = new MenuItem("Close");
	 	    MenuItem SystemInfo = new MenuItem("System Info");
	 	    MenuItem logfile = new MenuItem("Open log File");
	 	    close.addActionListener(new ActionListener() {
	 	        @Override
	 	        public void actionPerformed(ActionEvent e) {
	 	            System.exit(0);
	 	        	//ConsolPrint("Memory Usage: "+EmailReportModule.MemoryAverage);
	 	        	//ConsolPrint("CPU Usage: "+EmailReportModule.CPUAverage);
	 	        }
	 	    });
	 	   SystemInfo.addActionListener(new ActionListener() {
	 	        @Override
	 	        public void actionPerformed(ActionEvent e) {
	 	        	ServerParserModule.CommandExecModule("systeminfo", 1000,3);
	 	        	
	 	        }
	 	    });
	 	   logfile.addActionListener(new ActionListener() {
	 	        @Override
	 	        public void actionPerformed(ActionEvent e) {
	 	        	ServerParserModule.CommandExecModule("C:\\Windows\\system32\\notepad.exe "+VariableModule.driverlog+"\\SFD\\Logs\\Output.txt", 1000,0);
	 	        	
	 	        }
	 	    });
	 	    trayPopupMenu.add(close);
	 	    trayPopupMenu.add(SystemInfo);
	 	   trayPopupMenu.add(logfile);
	 	    trayIcon.setImageAutoSize(true);

	 	    try{
	 	        systemTray.add(trayIcon);
	 	    }catch(AWTException awtException){
	 	        awtException.printStackTrace();
	 	    }
	     }
	     
	     public static void showNotification(final int flag, final String title, final String msg, final TrayIcon trayIcon) {


	 	    trayIcon.addMouseListener(new MouseAdapter() {
	 	        public void mouseClicked(MouseEvent e) {
	 	            if (e.getClickCount() == 1) {
	 	                // single click
	 	            	trayIcon.displayMessage(title, msg, TrayIcon.MessageType.INFO);
	 	            }
	 	        }
	 	        
	 	    });
	 	    
	     	//trayIcon.setToolTip(msg);
	 	    
	 }
	     
}

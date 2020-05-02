package CommonPlatform;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Properties;
import java.util.Random;

import com.plivo.helper.api.client.*;
import com.plivo.helper.api.response.message.MessageResponse;
import com.plivo.helper.exception.PlivoException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.swing.SwingUtilities;

import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import LinuxServer.LinuxAction;
import LinuxServer.LinuxCPUModule;
import LinuxServer.LinuxDiskSpeedModule;
import LinuxServer.LinuxMemoryModule;
import LinuxServer.LinuxServerParser;
import MACServer.MACAction;
import MACServer.MACCPUModule;
import MACServer.MACDiskSpeedModule;
import MACServer.MACGUIModule;
import MACServer.MACMemoryModule;
import MACServer.MACServerParser;
import MyAction.ActionModule;
import MyServerFault.FaultDetectionModule;
import MyVariable.VariableModule;
import NonGUIServerMode.NonGUICPUModule;
import NonGUIServerMode.NonGUIDiskModule;
import NonGUIServerMode.NonGUIMemoryModule;
import NonGUIServerMode.NonGUITimeModule;
import NonGUIServerMode.Timechecker;
import ServerParser.ServerParserModule;

public class CommonOSModule {

	static NonGUIMemoryModule ThreadMemorydo = new NonGUIMemoryModule();
	static NonGUICPUModule ThreadCPUdo = new NonGUICPUModule();
	static NonGUIDiskModule ThreadDiskdo = new NonGUIDiskModule();
	static Timechecker ThreadTimecheckerdo = new Timechecker();
	static NonGUITimeModule ThreadTimedo = new NonGUITimeModule();
	static OTPExpireModule Threadotpexipredo = new OTPExpireModule();
	
	static LinuxCPUModule LinuxThreadCPUdo = new LinuxCPUModule();
	static LinuxMemoryModule LinuxThreadMemorydo = new LinuxMemoryModule();
	static LinuxDiskSpeedModule LinuxThreadDiskdo= new LinuxDiskSpeedModule();
	
	static MACCPUModule MACThreadCPUdo = new MACCPUModule();
	static MACMemoryModule MACThreadMemorydo = new MACMemoryModule();
	static MACDiskSpeedModule MACThreadDiskdo= new MACDiskSpeedModule();
	
	static EmailSentThread EmailSentThreaddo= new EmailSentThread();
	
	public static String initialPath="G:/WorkStation";
	static int RowCount=1;
	public static String MyIPAddress="";
	public static String isWindows = readVariable("isWindows",10);
	public static String iSGUI = readVariable("iSGUI",10);
	public static String isDB = readVariable("isDB",10);
	public static String isExcelWrite = readVariable("isExcelWrite",10);
	public static String driverlog = readVariable("driverlog",10);
	public static String SleepTime = readVariable("SleepTime",10);
	public static long sleepvalue=Long.parseLong(SleepTime)*60000;
	public static String Reportmailtime = readVariable("Reportmailtime",10);
	
	public static String mysqlurl = readVariable("mysqlurl",10);
	public static String dbusername = readVariable("dbusername",10);
	public static String dbpassword = readVariable("dbpassword",10);
	
	public static String isSMSAlert = readVariable("isSMSAlert",10);
	public static String SrcPhone = readVariable("SrcPhone",10);
	public static String DstPhone = readVariable("DstPhone",10);
	
	
    // SMTP info
    public static String mailhost = readVariable("mailhost",10);
    public static String mailport = readVariable("mailport",10);
    public static String mailFrom = readVariable("mailFrom",10);
    public static String mailpassword = readVariable("mailpassword",10);

    // permission sql variable
    public static String mailTo = readVariable("mailTo",10);
    public static String mailsubject = readVariable("mailsubject",10);
    public static String mailmessage = readVariable("mailmessage",10);
    
    
    // attachments
    public  static String[] attachFiles = {
    		driverlog+"/SFD/Reports/Report.xlsx",
			//"/GetClient.do?id=",
			//"/ViewAccountRecharge.do?clCustomerID="
    };
    
    public static void StartModule() {
    	

    	//ExcelFileCreateAction();
    	
    	if(isWindows.equals("1")) //for Windows
    	{
    		Windows();
    		
    	}
    	
    	else if(isWindows.equals("2")) //for MAC
    	{
    		MACOS();

    	}
    	else //for Linux
    	{

    		Linux();
    		
    	}
    }
    
    public static Connection MysqlConnection()
    {
	    Connection conn = null;
	    try {
	        // STEP 2: Register JDBC driver
	        Class.forName("com.mysql.jdbc.Driver");

	        // STEP 3: Open a connection
	        //System.out.print("\nConnecting to database...");
	        conn = DriverManager.getConnection(mysqlurl, dbusername, dbpassword);
	        writingLog(" Database Connected!\n");
	    }catch(SQLException se) {
	        se.printStackTrace();
	    } catch(Exception e) {
	        e.printStackTrace();
	    }
		return conn;
    }
    
    public static String getCurrentDate() {
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat dateobject = new SimpleDateFormat("ddMMyyyy-HHmmss");
        return dateobject.format(date);
    }
    
    public static String getCurrentTime() {
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat dateobject = new SimpleDateFormat("HH");
        return dateobject.format(date);
    }
    
    public static void Windows()
    {
		ActionModule.ConsolPrint("\n**********Windows Server-"+ActionModule.getCurrentDate()+"**********\n");
		ActionModule.writingLog("\n*********Windows Server-"+ActionModule.getCurrentDate()+"*********\n");
		ActionModule.TrayAction();
		ActionModule.FileBackupAction(isExcelWrite);
		MyIPAddress=ServerParserModule.IPParser1();
		//System.out.println("IP: "+MyIPAddress);
		//System.out.println("Test Query: "+MysqlConnectionAction("select uIsLive,uExpireDate from user where uServerIP='"+MyIPAddress+"'"));
		String live=MysqlConnectionAction("select uIsLive,uExpireDate from user where uServerIP='"+MyIPAddress+"'");
		String[] liveArray = live.split(",");
		if(liveArray[0].equals("1"))
		{

		String iSGUI = readVariable("iSGUI",10);
    	if(iSGUI.equals("1"))
    	{
    		ThreadTimecheckerdo.setPriority(ThreadTimecheckerdo.MAX_PRIORITY);
    		ThreadTimecheckerdo.start();
    		
    		Threadotpexipredo.setPriority(ThreadTimecheckerdo.getPriority()-1);
			Threadotpexipredo.start();
	        SwingUtilities.invokeLater(new Runnable() {
	        	
	
	            @Override
	            public void run() {
	                new FaultDetectionModule().setVisible(true);
	                
	                
	            }
	        });
    	}


    	else
    	{
    		ThreadTimecheckerdo.setPriority(ThreadTimecheckerdo.MAX_PRIORITY);
    		Threadotpexipredo.setPriority(ThreadTimecheckerdo.getPriority()-1);
    		ThreadMemorydo.setPriority(Threadotpexipredo.getPriority()-1);
    		ThreadCPUdo.setPriority(ThreadMemorydo.getPriority()-1);
    		ThreadDiskdo.setPriority(ThreadCPUdo.getPriority()-1);
    		ThreadTimedo.setPriority(ThreadDiskdo.getPriority()-1);
    		EmailSentThreaddo.setPriority(ThreadTimedo.getPriority()-1);
			
    			ThreadTimecheckerdo.start();
    			Threadotpexipredo.start();
    			ThreadMemorydo.start();
    			ThreadCPUdo.start();
    			ThreadDiskdo.start();
    			ThreadTimedo.start();
    			EmailSentThreaddo.start();
    			
    	}
	}
		else
		{
			System.exit(0);
		}
    }
    
    public static void MACOS()
    {
		//MACAction.ConsolPrint("\n**********MAC Server-"+ActionModule.getCurrentDate()+"**********\n");
		MACAction.writingLog("\n*********MAC Server-"+MACAction.getCurrentDate()+"*********\n");
		//ActionModule.TrayAction();
		//MACAction.FileBackupAction(isExcelWrite);
		//MyIPAddress=MACServerParser.IPParser1();
		String iSGUI = readVariable("iSGUI",10);
    	if(iSGUI.equals("1"))
    	{

	        SwingUtilities.invokeLater(new Runnable() {
	        	
	
	            @Override
	            public void run() {
	                new MACGUIModule().setVisible(true);
	                
	                
	            }
	        });
	        
    	}
    	
    	else
    	{
    		MACAction.writingLog("\n*********MAC Server-"+MACAction.getCurrentDate()+"*********\n");
    		MACAction.FileBackupAction(isExcelWrite);
    		//MyIPAddress=MACServerParser.IPParser1();
    		MACThreadMemorydo.setPriority(MACThreadMemorydo.MAX_PRIORITY);
    		MACThreadMemorydo.start();

    		MACThreadCPUdo.setPriority(MACThreadMemorydo.getPriority()-1);
    		MACThreadCPUdo.start();
    		
    		MACThreadDiskdo.setPriority(MACThreadCPUdo.getPriority()-1);
    		MACThreadDiskdo.start();
    		
    		//EmailSentThreaddo.setPriority(MACThreadDiskdo.getPriority()-1);
			//EmailSentThreaddo.start();
			

    	}
    }
    
    public static void Linux()
    {
		LinuxAction.writingLog("\n*********Linux Server-"+LinuxAction.getCurrentDate()+"*********\n");
		LinuxAction.FileBackupAction(isExcelWrite);
		MyIPAddress=LinuxServerParser.IPParser1();
		LinuxAction.writingLog("My IP: "+MyIPAddress);
		String live=MysqlConnectionAction("select uIsLive,uExpireDate from user where uServerIP='"+MyIPAddress+"'");
		String[] liveArray = live.split(",");
		if(liveArray[0].equals("1"))
		{
			
			ThreadTimecheckerdo.setPriority(ThreadTimecheckerdo.MAX_PRIORITY);
			ThreadTimecheckerdo.start();
			
	    	Threadotpexipredo.setPriority(ThreadTimecheckerdo.getPriority()-1);
	    	Threadotpexipredo.start();
	    	
	    	LinuxThreadMemorydo.setPriority(Threadotpexipredo.getPriority()-1);
			LinuxThreadMemorydo.start();
	
			LinuxThreadCPUdo.setPriority(LinuxThreadMemorydo.getPriority()-1);
			LinuxThreadCPUdo.start();
			
			LinuxThreadDiskdo.setPriority(LinuxThreadCPUdo.getPriority()-1);
			LinuxThreadDiskdo.start();
			
		}
		else
		{
			System.exit(0);
		}
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
	
	public static void MysqlConnectionActionUpdate(String Myquery)
	{

		String columnValue1="";
		String columnValue="";
		String[] URL=null;
	    Connection connection = null;
	    java.sql.Statement stmt = null;
		    
	    try {
	    	connection=MysqlConnection();
	        stmt = connection.createStatement();
	        stmt.executeUpdate(Myquery);
	        ConsolPrint("User is updated");
		    stmt.close();
		    connection.close();
	} catch (SQLException e) {
	    throw new IllegalStateException("Cannot connect the database!", e);
	}
	}
	
    public static void ConsolPrint(String wget) {

    	System.out.println("\n"+wget+"\n");
    }
    
    public static String longToIp(long ip) {

    	return ((ip >> 24) & 0xFF) + "."
    	+ ((ip >> 16) & 0xFF) + "."
    	+ ((ip >> 8) & 0xFF) + "."
    	+ (ip & 0xFF);

      }
    
    public static long IPtolong(String ip)
    {
    	
    	String[] addrArray = ip.split("\\.");
    	//System.out.println(addrArray.length);
    	long num = 0; 
    	   for (int i = 0; i < addrArray.length; i++) { 
    	     int power = 3 - i; 
    	     num += ((Integer.parseInt(addrArray[i]) * Math.pow(256, power))); 
    	   }
    	   return num;
    }
    public static void SerialKey()
    {
    	String saltStr="";
    	for(int i=1;i<=5;i++)
    	{
	        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
	        StringBuilder salt = new StringBuilder();
	        Random rnd = new Random();
	        while (salt.length() < 5) { // length of the random string.
	            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
	            salt.append(SALTCHARS.charAt(index));
	        }
	        if(i!=5)
	        {
	        	saltStr=saltStr.concat(salt.toString()).concat("-");
	        }
	        else
	        {
	        	saltStr=saltStr.concat(salt.toString());
	        }
	        
    	}
    	System.out.println("String: "+saltStr);
        
    }
    
    public static String RandomOTP()
    {
    	Random rnd = new Random();
    	int n = 100000 + rnd.nextInt(900000);
    	return Integer.toString(n);
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
    
	public static String readVariable(String var, int configflag)
	{
		int flag=0;
		int gotvar=0;
		Character ch = new Character('a');
		StringBuilder Test = new StringBuilder("");
		StringBuilder tempvar = new StringBuilder("");
		String path=null;
		String path1=initialPath+"/SFD/Config/Variable.txt";
		int data;
		try {
			
			File f = new File(path1);
			if(f.exists() && !f.isDirectory()) { 

					path=initialPath+"/SFD/Config/Variable.txt";

			}
			else
			{

					path="/usr/local/SFD/Config/Variable.txt";
			}
			

			

			
			Reader fileReader = new FileReader(path);
			data = fileReader.read();
			while(data != -1) {
				ch=(char)data;
				//System.out.println("String: "+ch+flag+gotvar);
				if(flag==0 && ch!='=') {
					Test.append(ch);
				}
				else if(flag==1 && gotvar==1) {
					if(ch!='=' && ch!=';') {
						tempvar.append(ch);
						
					}
				}
				if(ch=='\n') {
					flag=0;
					Test = new StringBuilder("");
				}
				else if(ch=='='){
					flag=1;
					String Test1=Test.toString();
					//System.out.println("Flag:"+Test1);
					//System.out.println("Actual:"+var);
					if(Test1.equals(var))
					{
						//System.out.println("Match Equal"+Test);
						gotvar=1;
					}
				}
				else if(ch==';' && gotvar==1) {
					//System.out.println("Variable Found:"+tempvar);
					break;
				}

				data = fileReader.read();
			  //System.out.println((char)data);
			}
			//String tempvar1=tempvar.toString();
			
			fileReader.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tempvar.toString();
	}
	
	
	public static void SMSwithPlivo(String text)
	{
		
		 String authId = "MAZGUWOGE1OTE1NJRJMG";
	        String authToken = "MGY2MDM3MTZkMDk1ZjgzZWQ4NmUwNDZjMGY1OWEz";
	        RestAPI api = new RestAPI(authId, authToken, "v1");

	        LinkedHashMap<String, String> parameters = new LinkedHashMap<String, String>();
	        parameters.put("src", SrcPhone); // Sender's phone number with country code
	        parameters.put("dst", DstPhone); // Receiver's phone number with country code
	        parameters.put("text", text); // Your SMS text message
	        // Send Unicode text
	        //parameters.put("text", "こんにちは、元気ですか？"); // Your SMS text message - Japanese
	        //parameters.put("text", "Ce est texte généré aléatoirement"); // Your SMS text message - French
	        //parameters.put("url", "http://example.com/report/"); // The URL to which with the status of the message is sent
	        //parameters.put("method", "GET"); // The method used to call the url

	        try {
	            // Send the message
	            MessageResponse msgResponse = api.sendMessage(parameters);

	            // Print the response
	            System.out.println(msgResponse);
	            // Print the Api ID
	            System.out.println("Api ID : " + msgResponse.apiId);
	            // Print the Response Message
	            System.out.println("Message : " + msgResponse.message);

	            if (msgResponse.serverCode == 202) {
	                // Print the Message UUID
	                System.out.println("Message UUID : " + msgResponse.messageUuids.get(0).toString());
	            } else {
	                System.out.println(msgResponse.error);
	            }
	        } catch (PlivoException e) {
	            System.out.println(e.getLocalizedMessage());
	        }
	        
	}
	
	
	public static void ExcelFileCreateAction() {
		 try {
				String currentDate = getCurrentDate();
				int randomValue = (int )(Math.random() * 500 + 1);
		        File oldName = new File(driverlog+"/SFD/Reports/Report.xlsx");
		        File newName = new File(driverlog+"/SFD/Reports/Report"+'-'+currentDate+'-'+randomValue+".xlsx");
		        oldName.renameTo(newName);
			XSSFWorkbook workbook = new XSSFWorkbook();
			FileOutputStream out = new FileOutputStream(new File(driverlog+"/SFD/Reports/Report.xlsx"));
			XSSFSheet Spreadsheet = workbook.createSheet("Report-"+currentDate);
			XSSFRow header = Spreadsheet.createRow(0);
			//XSSFCellStyle style = workbook.createCellStyle();
		    header.createCell(0).setCellValue("Usage Value");
		    header.createCell(1).setCellValue("Usage Type");
		    header.createCell(2).setCellValue("Usage Time");
/*			    style.setBorderTop(BorderStyle.THIN);
		    style.setBorderBottom(BorderStyle.MEDIUM);
		    style.setBorderLeft(BorderStyle.MEDIUM);
		    style.setBorderRight(BorderStyle.MEDIUM);*/
			workbook.write(out);
			out.close();
		}
		catch(Exception e) {
			System.out.println(e);
			
		}
	}

public static void ExcelFileWriteAction(Double value, String type, String time) {
		
		
		XSSFCell cell = null; 
	
			//System.out.println("Writer Enabled"+RowCount);
			try {
				FileInputStream fIPS= new FileInputStream(driverlog+"/SFD/Reports/Report.xlsx");
				
				XSSFWorkbook workbook = new XSSFWorkbook(fIPS);
				XSSFSheet worksheet = workbook.getSheetAt(0);
				
				fIPS.close();
				FileOutputStream out = new FileOutputStream(new File(driverlog+"/SFD/Reports/Report.xlsx"));
				//XSSFSheet worksheet = workbook.getSheetAt(0);
		        //XSSFCellStyle headerStyle = workbook.createCellStyle();
		        //Font headerFont = workbook.createFont();
				XSSFRow row = worksheet.createRow(RowCount);

			       //indexOf return -1 if String does not contain specified word
			           //System.err.printf("Yes '%s' contains word 'Failed' %n" , result);
				        //font.setColor(IndexedColors.RED.getIndex());
				        //style.setFont(font);
						row.createCell(0).setCellValue(value);
					    //header.createCell(1).setCellValue(result);
					    cell = row.createCell(1);
					    cell.setCellValue(type);
					    cell = row.createCell(2);
					    cell.setCellValue(time);

				        //headerStyle.setFillForegroundColor(IndexedColors.GREEN.getIndex());
				        //headerFont.setColor(IndexedColors.RED.getIndex());
				        //headerStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
					    
				        //headerStyle.setFont(headerFont);
					    //cell.setCellStyle(headerStyle);
				workbook.write(out);
				out.close();
				
				
			} catch (IOException e) {
	
				e.printStackTrace();
			}
			RowCount++;

}

public static void Mail(String host, String port,
        final String userName, final String password, String toAddress,
        String subject, String message, String[] attachFiles)
{
    try{

        Properties props = new Properties();
        props.put("mail.smtp.host", host); // for gmail use smtp.gmail.com
        props.put("mail.smtp.auth", "true");
        props.put("mail.debug", "true"); 
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.port", port);
        props.put("mail.smtp.socketFactory.port", port);
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");

        Session mailSession = Session.getInstance(props, new javax.mail.Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userName, password);
            }
        });

        mailSession.setDebug(true); // Enable the debug mode

        Message msg = new MimeMessage( mailSession );

        //--[ Set the FROM, TO, DATE and SUBJECT fields
        msg.setFrom( new InternetAddress( userName ) );
        msg.setRecipients( Message.RecipientType.TO,InternetAddress.parse(toAddress) );
        msg.setSentDate( new Date());
        msg.setSubject( subject );

        //--[ Create the body of the mail
        msg.setText( message );

        
        // creates message part
        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent(message, "text/html");
        
        // creates multi-part
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);
        
        // adds attachments
        if (attachFiles != null && attachFiles.length > 0) {
            for (String filePath : attachFiles) {
                MimeBodyPart attachPart = new MimeBodyPart();
 
                try {
                    attachPart.attachFile(filePath);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
 
                multipart.addBodyPart(attachPart);
            }
        }
 
        // sets the multi-part as e-mail's content
        msg.setContent(multipart);
        
        //--[ Ask the Transport class to send our mail message
        Transport.send( msg );

    }catch(Exception E){
        System.out.println( "Oops something has gone pearshaped!");
        System.out.println( E );
    }
}

}

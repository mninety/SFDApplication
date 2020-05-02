package MyVariable;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Date;
import java.util.Properties;

import java.io.IOException;
import java.util.Date;
import java.util.Properties;
 
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import javax.swing.SwingUtilities;

import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;

import CommonPlatform.CommonOSModule;
import LinuxServer.LinuxCPUModule;
import LinuxServer.LinuxMemoryModule;
import MyAction.ActionModule;
import MyServerFault.FaultDetectionModule;
import NonGUIServerMode.NonGUICPUModule;
import NonGUIServerMode.NonGUIDiskModule;
import NonGUIServerMode.NonGUIMemoryModule;
import NonGUIServerMode.NonGUITimeModule;
import ServerParser.ServerParserModule;



public class VariableModule {
	ActionModule actiondo = new ActionModule();
	CommonOSModule commondo = new CommonOSModule();
	
/*	if(commondo.isWindows.equals("1"))
	{*/
	public static String driverlog = readVariable("driverlog",1);
	public static String mysqlurl = readVariable("mysqlurl",1);
	public static String dbusername = readVariable("dbusername",1);
	public static String dbpassword = readVariable("dbpassword",1);
	

	 public  static String isDynamicThreshold = readVariable("isDynamicThreshold",1);
	 
	 public  static String SleepTime = readVariable("SleepTime",1);
	 public static long sleepvalue=Long.parseLong(SleepTime)*60000;
	 //public static String MyIPAddress;
	 //public static long sleepvariable;
	 public static String RouterIP = readVariable("RouterIP",1);
	 public static String pinglatency = readVariable("pinglatency",2);
	 public static double pinglatencyvalue=Double.parseDouble(pinglatency);
	 
	 public static String TimeDifference = readVariable("TimeDifference",1);
	 public static double TimeDifferencevalue=Double.parseDouble(TimeDifference);
	 
	 public static String Percentagevalue = readVariable("Percentagevalue",1);
	 public static double percenvalue=Double.parseDouble(Percentagevalue);
	 
	 public static String MemoryLimitValue = readVariable("MemoryLimitValue",2);
	 public static double memvalue=Double.parseDouble(MemoryLimitValue);
	 
	 public static String DiskFreeSpace = readVariable("DiskFreeSpace",2);
	 public static double DiskFreeSpacevalue=Double.parseDouble(DiskFreeSpace);
	 
	 public static String DiskReadlimit = readVariable("DiskReadlimit",2);
	 public static double DiskReadlimitvalue=Double.parseDouble(DiskReadlimit);
	 
	 public static String DiskWritelimit = readVariable("DiskWritelimit",2);
	 public static double DiskWritelimitvalue=Double.parseDouble(DiskWritelimit);
	 
	 public static String CPULimitValue = readVariable("CPULimitValue",2);
	 public static double cpuvalue=Double.parseDouble(CPULimitValue);
	 
	 public static String Pcapduration = readVariable("Pcapduration",1);
	 public static double Pcapvalue = Double.parseDouble(Pcapduration);
	 public static String Packetpersec = readVariable("Packetpersec",2);
	 public static double packvalue=Double.parseDouble(Packetpersec);
	 
	 public static String ProcesswiseMemory = readVariable("ProcesswiseMemory",2);
	 public static double promemvalue=Double.parseDouble(ProcesswiseMemory);
	 public static String ProcesswiseCPU = readVariable("ProcesswiseCPU",2);
	 public static double procpuvalue=Double.parseDouble(ProcesswiseCPU);
	//public String expectedTitle2 = readVariable("expectedTitle2",1);

	    // SMTP info
	    public static String mailhost = readVariable("mailhost",1);
	    public static String mailport = readVariable("mailport",1);
	    public static String mailFrom = readVariable("mailFrom",1);
	    public static String mailpassword = readVariable("mailpassword",1);

	    // permission sql variable
	    public static String mailTo = readVariable("mailTo",1);
	    public static String mailsubject = readVariable("mailsubject",1);
	    public static String mailmessage = readVariable("mailmessage",1);
	    
    // attachments
    public  static String[] attachFiles = {
    		driverlog+"/SFD/Reports/Report.xlsx",
			//"/GetClient.do?id=",
			//"/ViewAccountRecharge.do?clCustomerID="
    };
    

    //attachFiles[0] = "C:\\WebDriver\\Reports\\Report.xlsx";
    //attachFiles[1] = "e:/Test/Music.mp3";
    //attachFiles[2] = "e:/Test/Video.mp4";
    
	public String[] ServerInfo = {
			"Host Name",
			"OS Name",
			"System Manufacturer",
			"System Model",
			"System Type",
			"Time Zone",
			"Total Physical Memory",
			"CPU Core",
			"Current Clock Speed",
			"Maximum Clock Speed",
			"IP Address",
			"MAC Address",
			"Standard Date and Time",
			"System current Date and Time",
			"Time Difference",
			//"Network Card(s)",
			};
	public static String readVariable(String var, int configflag)
	{
		int flag=0;
		int gotvar=0;
		Character ch = new Character('a');
		StringBuilder Test = new StringBuilder("");
		StringBuilder tempvar = new StringBuilder("");
		String path = null;
		int data;
		try {
			
			if(configflag==1)
			{
				path=CommonOSModule.initialPath+"/SFD/Config/Variable.txt";
			}
			else if(configflag==2)
			{
				path=CommonOSModule.initialPath+"/SFD/Config/Threshold.txt";

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
	        props.put("mail.smtp.port", "465");
	        props.put("mail.smtp.socketFactory.port", "465");
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

package LinuxServer;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import NonGUIServerMode.NonGUICPUModule;
import NonGUIServerMode.NonGUIDiskModule;
import NonGUIServerMode.NonGUIMemoryModule;
import NonGUIServerMode.NonGUITimeModule;

public class LinuxVariable {

	
	//public String file_name="E:/Automation/Logs/Output.txt";
/*	public static String isWindows = readVariable("isWindows",4);
	public static String iSGUI = readVariable("iSGUI",4);*/
	public static String driverlog = readVariable("driverlog",4);
	public static String mysqlurl = readVariable("mysqlurl",4);
	public static String dbusername = readVariable("dbusername",4);
	public static String dbpassword = readVariable("dbpassword",4);
	
	
	 public  static String isDynamicThreshold = readVariable("isDynamicThreshold",4);
	 
	 public  static String SleepTime = readVariable("SleepTime",4);
	 public static long sleepvalue=Long.parseLong(SleepTime)*60000;
	 //public static String MyIPAddress;
	 //public static long sleepvariable;
	 public static String RouterIP = readVariable("RouterIP",4);
	 public static String pinglatency = readVariable("pinglatency",2);
	 public static double pinglatencyvalue=Double.parseDouble(pinglatency);
	 
	 public static String TimeDifference = readVariable("TimeDifference",4);
	 public static double TimeDifferencevalue=Double.parseDouble(TimeDifference);
	 
	 public static String Percentagevalue = readVariable("Percentagevalue",4);
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
	 
	 public static String Pcapduration = readVariable("Pcapduration",4);
	 public static double Pcapvalue = Double.parseDouble(Pcapduration);
	 public static String Packetpersec = readVariable("Packetpersec",2);
	 public static double packvalue=Double.parseDouble(Packetpersec);
	 
	 public static String ProcesswiseMemory = readVariable("ProcesswiseMemory",2);
	 public static double promemvalue=Double.parseDouble(ProcesswiseMemory);
	 public static String ProcesswiseCPU = readVariable("ProcesswiseCPU",2);
	 public static double procpuvalue=Double.parseDouble(ProcesswiseCPU);
	//public String expectedTitle2 = readVariable("expectedTitle2",4);

	    // SMTP info
	    public static String mailhost = readVariable("host",4);
	    public static String mailport = readVariable("port",4);
	    public static String mailFrom = readVariable("mailFrom",4);
	    public static String mailpassword = readVariable("password",4);

	    // permission sql variable
	    public static String mailTo = readVariable("mailTo",4);
	    public static String mailsubject = readVariable("subject",4);
	    public static String mailmessage = readVariable("mailmessage",4);
   
   // attachments
   public  static String[] attachFiles = {
   		driverlog+"/SFD/Logs/Output.txt",
			//"/GetClient.do?id=",
			//"/ViewAccountRecharge.do?clCustomerID="
   };
   
	public static String readVariable(String var, int configflag)
	{
		int flag=0;
		int gotvar=0;
		Character ch = new Character('a');
		StringBuilder Test = new StringBuilder("");
		StringBuilder tempvar = new StringBuilder("");
		String path=null;
		int data;
		try {

			if(configflag==4) //linux
			{
				path="/usr/local/SFD/Config/Variable.txt";
			}
			else //linux
			{
				path="/usr/local/SFD/Config/Threshold.txt";
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
	
}

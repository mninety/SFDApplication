package ServerParser;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import com.sun.xml.internal.bind.v2.runtime.RuntimeUtil.ToStringAdapter;

import CommonPlatform.CommonOSModule;

/*import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;*/

//import com.sun.org.apache.xpath.internal.operations.Equals;

import MyAction.ActionModule;
import MyGraph.LineGraphCPUModule;
import MyGraph.LineGraphDiskModule;
import MyGraph.LineGraphDiskReadModule;
import MyGraph.LineGraphMemoryModule;
import MyVariable.VariableModule;



public class ServerParserModule {
	static VariableModule variabledo = new VariableModule();
	static ActionModule actiondo = new ActionModule();
	public static String[] ServerArray = new String[200];
	public static double MemoryAverage;
	public static double CPUAverage;
	
//    @SuppressWarnings("null")
	public static void MemoryParser(BufferedReader mem,int outcount) throws IOException {
		ActionModule.ConsolPrint("\n*********Memory Parser-"+ActionModule.getCurrentDate()+"*********\n");
		ActionModule.writingLog("\n*********Memory Parser-"+ActionModule.getCurrentDate()+"*********\n");
    	String cvsSplitBy=",";
    	String line;
    	//ActionModule.ConsolPrint(outcount);
    	
    	double[] memstore = new double[outcount];
    	int j=0;
    	double memvalue=Double.parseDouble(variabledo.MemoryLimitValue);
    	//ActionModule.ConsolPrint(memvalue);
        while ((line = mem.readLine()) != null) {
        	//ActionModule.ConsolPrint(line);
        	String[] MemArray = line.split(cvsSplitBy);
        	if(MemArray[0].contains("\"")) {
	        	String temstr=MemArray[1].substring(1, MemArray[1].length()-1);
	        	//ActionModule.ConsolPrint("Test: "+temstr);
	        	if(j>0) {
		        	memstore[j-1]=Double.parseDouble(temstr);
		        	ActionModule.ConsolPrint("Memory Output: " +Double.parseDouble(temstr));
		        	ActionModule.writingLog("Memory Output: " +Double.parseDouble(temstr));
	        	}
	        	j++;
        	}
          }
	      double memavg=0.0;
        for (int i=0; i< j-1; i++){
	      //ActionModule.ConsolPrint("Memory Output: " +memstore[i]);
	      memavg=memavg+memstore[i];
	    }
        memavg=memavg/j-1;
        MemoryAverage=ActionModule.DecimalFormat(memavg);
        
        if(CommonOSModule.isDB.equals("1"))
        {
        	ActionModule.MysqlInsertData("memorydata","mdValue",MemoryAverage,1,1);
        	
        }
        if(CommonOSModule.isExcelWrite.equals("1"))
        {
        	CommonOSModule.ExcelFileWriteAction(MemoryAverage,"Memory",CommonOSModule.getCurrentDate());
        }
    	ActionModule.ConsolPrint("Average Memory: "+MemoryAverage);
    	ActionModule.writingLog("Average Memory: "+MemoryAverage);
    	
        //actiondo.ConsolPrint("Mem AVG: "+MemoryAverage);
        //actiondo.ConsolPrint("Threshold Mem: "+variabledo.memvalue);
        if(memavg<variabledo.memvalue) {
            if(CommonOSModule.isDB.equals("1"))
            {
            	ActionModule.MysqlInsertData("alarmtracker","atValue",MemoryAverage,1,1);
            }
        	ActionModule.ConsolPrint("Average Memory is reached threshold value");
        	ActionModule.writingLog("Average Memory is reached threshold value");
        	ActionModule.PlayAlarmAction();
        	
        	if(CommonOSModule.isSMSAlert.equals("1"))
        	{
        		CommonOSModule.SMSwithPlivo("Hi Admin,\nMemory Usage: "+MemoryAverage);
        	}
        	if(variabledo.isDynamicThreshold.equals("1"))
        	{
	        	if(variabledo.memvalue-(VariableModule.memvalue*VariableModule.percenvalue/100)<MemoryAverage) {
	        		ActionModule.replaceThreshold("MemoryLimitValue=",String.valueOf(VariableModule.memvalue-(VariableModule.memvalue*VariableModule.percenvalue/100)));
	        	}
	        	else if(variabledo.memvalue+(VariableModule.memvalue*VariableModule.percenvalue/100)<MemoryAverage) {
	        		actiondo.replaceThreshold("MemoryLimitValue=",String.valueOf(VariableModule.memvalue+(VariableModule.memvalue*VariableModule.percenvalue/100)));
	        	}
        	}
        	CommandExecModule("tasklist", 1000,2);
        }
    	if(CommonOSModule.iSGUI.equals("1") && CommonOSModule.isDB.equals("1"))
    	{
    		LineGraphMemoryModule.LineGraphCreator();
    	}
    }
    
    
    
	public static void ProcessParserforMemory(BufferedReader mem,int outcount) throws IOException {
    	String line;        	
    	int k=0;
    	String cvsSplitBy=";";
    	String[] procName = new String[outcount];
    	double[] procmemstore = new double[outcount];
    	int j=0;
    	int m=0;
    	//double memvalue=Double.parseDouble(variabledo.MemoryLimitValue);
        while ((line = mem.readLine()) != null) {
            //ActionModule.ConsolPrint(line);
            String line1 = line.replaceAll("\\s+", ";");
            //ActionModule.ConsolPrint("After: "+line1);
        	String[] MemArray = line1.split(cvsSplitBy);
        	//ActionModule.ConsolPrint("Array Lenght: "+MemArray.length);
        	//String procmem = MemArray[4].replaceAll(",", "");
/*        	for(;k<MemArray.length;k++) {
        		ActionModule.ConsolPrint("Process"+k+": "+MemArray[k]);
        	}
        	k=0;*/
        	if(j>3)
        	{

        		if(MemArray.length<6)
        		{
        			procName[m]=MemArray[0];
        			procmemstore[m]=Double.parseDouble(MemArray[3].replaceAll(",", ""));
        			procmemstore[m]=procmemstore[m]/1024;
        			//ActionModule.ConsolPrint("Mem:"+MemArray[3].replaceAll(",", ""));
        			m++;
        		}
        		else
        		{
        			procName[m]=MemArray[0];
        			procmemstore[m]=Double.parseDouble(MemArray[4].replaceAll(",", ""));
        			procmemstore[m]=procmemstore[m]/1024;
        			//ActionModule.ConsolPrint("Mem:"+MemArray[4].replaceAll(",", ""));
        			m++;
        		}
        		
        	}
        	j++;

          }
        int t=0;
        int[] topmem=new int[m];
        for(k=0;k<m;k++) {
        	//ActionModule.ConsolPrint("Process"+k+": "+procName[k]+"\t\t"+"ProcessMemory"+k+": "+procmemstore[k]);
        	if(procmemstore[k]>variabledo.promemvalue)
        	{
        		//ActionModule.ConsolPrint("Process wise Memory Limit is also exceeded for few process");
        		//ActionModule.writingLog("Process wise Memory Limit is also exceeded for few process");
        		//ActionModule.ConsolPrint("Process"+k+": "+procName[k]+"\t\t"+"ProcessMemory"+k+": "+procmemstore[k]);
        		topmem[t]=k;
        		t++;
        	}
		//ActionModule.ConsolPrint("ProcessMemory"+k+": "+procmemstore[k]);
        }
        insertionSort(procName,procmemstore,topmem,t);
    	for(k=t-1;k>=0;k--) {
    		if(procName[topmem[k]].length()<10)
    		{
	    		ActionModule.ConsolPrint("Top Process: "+procName[topmem[k]]+"\t\t"+"Top Process Memory: "+procmemstore[topmem[k]]);
	    		ActionModule.writingLog("Top Process: "+procName[topmem[k]]+"\t\t"+"Top Process Memory: "+procmemstore[topmem[k]]);
    		}
    		else {
	    		ActionModule.ConsolPrint("Top Process: "+procName[topmem[k]]+"\t"+"Top Process Memory: "+procmemstore[topmem[k]]);
	    		ActionModule.writingLog("Top Process: "+procName[topmem[k]]+"\t"+"Top Process Memory: "+procmemstore[topmem[k]]);	
    		}
    	}

    }
    
	
	public static void ServerInfoParser(BufferedReader mem,int outcount) throws IOException {
		
		ActionModule.ConsolPrint("\n*********System Info Parser-"+ActionModule.getCurrentDate()+"*********\n");
		ActionModule.writingLog("\n*********System Info Parser-"+ActionModule.getCurrentDate()+"*********\n");
    	String line;        	
    	int k=0;
    	String cvsSplitBy=":|(es)";
    	
//    	double[] procmemstore = new double[outcount];
    	int j=0;
//    	int m=0;
    	//double memvalue=Double.parseDouble(variabledo.MemoryLimitValue);
        while ((line = mem.readLine()) != null) {
            //ActionModule.ConsolPrint(line);
            if(j>0) {
	        	String[] InfoArray = line.split(cvsSplitBy);
	        	String line1 = InfoArray[1].replaceAll("\\s+", ";");
	        	String[] line2=line1.split(";");
	        	//ActionModule.ConsolPrint("Array Lenght: "+line2[0]+" 2nd:"+line2[1]);
	        	if(InfoArray[0].equals("Host Name"))
	        	{
	        		ServerArray[0]=line2[1]; //Host Name
	        	}
	        	else if(InfoArray[0].equals("OS Name"))
	        	{
	        		ServerArray[1]=line2[1].concat(" "+line2[2]+" "+line2[3]+" "+line2[4]); //OS Name
	        	}
	        	else if(InfoArray[0].equals("System Manufacturer"))
	        	{
	        		ServerArray[2]=line2[1]; //System Manufacturer
	        	}
	        	else if(InfoArray[0].equals("System Model"))
	        	{
	        		ServerArray[3]=line2[1].concat(" "+line2[2]); //System Model
	        	}
	        	else if(InfoArray[0].equals("System Type"))
	        	{
	        		ServerArray[4]=line2[1]; //System Type
	        	}
	        	else if(InfoArray[0].equals("Time Zone"))
	        	{
	        		ServerArray[5]=line2[1].concat(":"+InfoArray[2]); //Time Zone
	        	}
	        	else if(InfoArray[0].equals("Total Physical Memory"))
	        	{
	        		ServerArray[6]=line2[1].replaceAll(",", ""); //Total Physical Memory
	        		boolean filefound=actiondo.ReadFile("TotalRamMemory");
	        		
	        		if(filefound==false)
	        		{
	        			actiondo.writing("\nTotalRamMemory="+ServerArray[6]+";",1);
	        		}
	        		
	        	}

	        }
	        j++;
        }
        //ActionModule.ConsolPrint("***********Test***********");
    	String cpuinfo="wmic cpu get name,CurrentClockSpeed,MaxClockSpeed";
    	CommandExecModule(cpuinfo, 1000,4);
    	CommandExecModule("ipconfig", 1000,9);
    	MACParser();
		boolean filefound=actiondo.ReadFile("MACAddress");
		
		if(filefound==false)
		{
			actiondo.writing("\nMACAddress="+ServerArray[11]+";",1);
		}
		
    	ActionModule.ExecWebRequest(1);
    	//String curdateandtime=ActionModule.getCurrentDate();
    	
        for(k=0;k<variabledo.ServerInfo.length;k++) {
        	
        	if(k==2 || k==6 || k==8 || k==9 || k==14) {
        		ActionModule.ConsolPrint(variabledo.ServerInfo[k]+" :\t\t"+ServerArray[k]);
        		ActionModule.writingLog(variabledo.ServerInfo[k]+" :\t\t"+ServerArray[k]);
        	}
        	else if(k==12 || k==13) {
        		ActionModule.ConsolPrint(variabledo.ServerInfo[k]+" :\t"+ServerArray[k]);
        		ActionModule.writingLog(variabledo.ServerInfo[k]+" :\t"+ServerArray[k]);
        	}
        	else {
        		ActionModule.ConsolPrint(variabledo.ServerInfo[k]+" :\t\t\t"+ServerArray[k]);
        		ActionModule.writingLog(variabledo.ServerInfo[k]+" :\t\t\t"+ServerArray[k]);
        	}	
        }
        
    }
	
	public static void insertionSort(String[] prosortArray,double[] sortArray,int[] sorttop,int len)
	{
	   for (int i=1; i<len; i++)
	   {
	      double index = sortArray[sorttop[i]];
	      String indexpro=prosortArray[sorttop[i]];
	      int j = i;
	      while (j > 0 && sortArray[sorttop[j-1]] > index)
	      {
	    	  sortArray[sorttop[j]] = sortArray[sorttop[j-1]];
	    	  prosortArray[sorttop[j]] = prosortArray[sorttop[j-1]];
	           j--;
	      }
	      sortArray[sorttop[j]] = index;
	      prosortArray[sorttop[j]] = indexpro;
	} }
	
	
	public static void CPUInfoParser(BufferedReader mem,int outcount) throws IOException {
    	String line;        	
    	//int k=0;
    	
    	String cvsSplitBy=";";
    	//String[] CPUArray = new String[outcount];
//    	double[] procmemstore = new double[outcount];
    	int j=0;
    	
    	//double memvalue=Double.parseDouble(variabledo.MemoryLimitValue);
        while ((line = mem.readLine()) != null) {
            if(j==2) {
            	//ActionModule.ConsolPrint(line);
            	String line1 = line.replaceAll("\\s+", ";");
	        	String[] InfoArray = line1.split(cvsSplitBy);
	        	ServerArray[7]=InfoArray[2].concat(" "+InfoArray[3]+" "+InfoArray[4]+" "+InfoArray[5]+" "+InfoArray[6]+" "+InfoArray[7]);
	        	ServerArray[8]=InfoArray[0];
	        	ServerArray[9]=InfoArray[1];
/*	        	for(;k<InfoArray.length;k++) {
	        		ActionModule.ConsolPrint("Process"+k+": "+InfoArray[k]);
	        	}
	        	k=0;*/
/*	            for(k=0;k<variabledo.ServerInfo.length;k++) {
	            	ActionModule.ConsolPrint(variabledo.ServerInfo[k]+" :\t\t\t"+ServerArray[k]);
	            }*/
            }
            
            j++;
        }
		
        
	}
	public static void CommandExecModule(String command, int durationmili1, int comflag) {
		String line;
    	int count=0;
		Runtime rt = Runtime.getRuntime();
        //Process proc = rt.exec(command);
        try {
        	//String testcom="tshark -i 4 -p -a duration:"+duration+" -w "+pcapfile+"Trace.pcap";
        	Process proc = rt.exec(command);
            BufferedReader input = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            if(comflag==1) //Memory Parser
            {

            	MemoryParser(input,50);
            }
            
            else if(comflag==2) //Memory Process Parser
            {
            	ProcessParserforMemory(input,200);
            }
            else if(comflag==3) { //Server INFO Parser
            	ServerInfoParser(input,200);
            }

            else if(comflag==4) { //CPU INFO Parser
/*                while ((line = input.readLine()) != null) {
                	ActionModule.ConsolPrint(line);
                }*/
            	CPUInfoParser(input,20);
            	
            }
            else if(comflag==5) {  //Packet Parser
                try {
                    Thread.sleep(durationmili1);                 //1000 milliseconds is one second.
                } catch(InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            }
            else if(comflag==6) { //CPU Parser
            	CPUParser(input,50);
            }
            else if(comflag==7) { //CPU Process Parser
            	ProcessParserforCPU(input,200);
            }
            
            else if(comflag==8) { 
            	while ((line = input.readLine()) != null) {
            		ActionModule.ConsolPrint(line);
            		ActionModule.writingLog(line);
            	}
            }
            
            else if(comflag==9) { 
            	IPParser(input,4);

            }
            else if(comflag==10) { 
            	PingParser(input,50);

            }
            else if(comflag==11) { 
            	while ((line = input.readLine()) != null) {
            		ActionModule.ConsolPrint(line);
            		ActionModule.writingLog(line);
            	}

            }
            else if(comflag==12) { 
            	while ((line = input.readLine()) != null) {
            		WifiParser(input, 1);

            	}

            }
            input.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        

        

	}
	
	//runtimeProcess = Runtime.getRuntime().exec(new String[] { "runas /profile /user:Administrator \"cmd.exe", executeCmd });
	public static void DiskParser(String diskcom, int flag) {
		
		ActionModule.ConsolPrint("\n*********Disk Parser-"+ActionModule.getCurrentDate()+"*********\n");
		ActionModule.writingLog("\n********Disk Parser-"+ActionModule.getCurrentDate()+"**********\n");
		
		Runtime rt = Runtime.getRuntime();
		String line;
        //Process proc = rt.exec(command);
        try {
        	//String testcom="tshark -i 4 -p -a duration:"+duration+" -w "+pcapfile+"Trace.pcap";
        	
        	Process proc = rt.exec(diskcom);
            BufferedReader input = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            while ((line = input.readLine()) != null) {
            	//ActionModule.ConsolPrint("Output: "+line);
            	if(flag==1) { //read speed
            		
            		if(line.contains("Disk  Sequential"))
            		{
                    	String[] DiskReadArray = line.split("Read");
                        String line1 = DiskReadArray[1].replaceAll("\\s+", ",");
                        String[] DiskReadArray1 = line1.split(",");
                        String readspeed1=DiskReadArray1[1];
                        String readspeed2=DiskReadArray1[2];
                        Double readspeed3=Double.parseDouble(readspeed1);
                        Double readspeed=ActionModule.DecimalFormat(readspeed3);
                        if(CommonOSModule.isDB.equals("1"))
                        {
                        	ActionModule.MysqlInsertData("diskdata,"+readspeed2,"ddValue,ddUnit",readspeed,3,1);
                        	
                        }
                        if(CommonOSModule.isExcelWrite.equals("1"))
                        {
                        	CommonOSModule.ExcelFileWriteAction(readspeed,"Disk Read Speed",CommonOSModule.getCurrentDate());
                        }
                        ActionModule.ConsolPrint("Disk Read Speed: "+readspeed+" "+readspeed2);
                        ActionModule.writingLog("Disk Read Speed: "+readspeed+" "+readspeed2);
                        if(readspeed>VariableModule.DiskReadlimitvalue) //Config value is in MB/s
                        {
                            if(CommonOSModule.isDB.equals("1"))
                            {
                            	ActionModule.MysqlInsertData("alarmtracker","atValue",readspeed,3,1);
                            }
                        	ActionModule.ConsolPrint("Disk read speed is exceeded the threshold value");
                            ActionModule.writingLog("Disk read speed is exceeded the threshold value");
                            ActionModule.PlayAlarmAction();
                        	if(variabledo.isDynamicThreshold.equals("1"))
                        	{
                	        	if(variabledo.DiskReadlimitvalue-(VariableModule.DiskReadlimitvalue*VariableModule.percenvalue/100)<readspeed) {
                	        		ActionModule.replaceThreshold("DiskReadlimit=",String.valueOf(VariableModule.DiskReadlimitvalue-(VariableModule.DiskReadlimitvalue*VariableModule.percenvalue/100)));
                	        	}
                	        	else if(variabledo.DiskReadlimitvalue+(VariableModule.DiskReadlimitvalue*VariableModule.percenvalue/100)<readspeed) {
                	        		actiondo.replaceThreshold("DiskReadlimit=",String.valueOf(VariableModule.DiskReadlimitvalue+(VariableModule.DiskReadlimitvalue*VariableModule.percenvalue/100)));
                	        	}
                        	}
                        }
            		}
/*                	if(CommonOSModule.iSGUI.equals("1") && CommonOSModule.isDB.equals("1"))
                	{
                		LineGraphDiskReadModule.LineGraphCreator();
                	}*/
            	}
            	else if(flag==2) { //write speed
            		if(line.contains("Disk  Random"))
            		{
                    	String[] DiskWriteArray = line.split("Write");
                        String line1 = DiskWriteArray[1].replaceAll("\\s+", ",");
                        String[] DiskWriteArray1 = line1.split(",");
                        String writespeed1=DiskWriteArray1[1];
                        String writespeed2=DiskWriteArray1[2];
                        Double writespeed3=Double.parseDouble(writespeed1);
                        Double writespeed=ActionModule.DecimalFormat(writespeed3);
                        if(CommonOSModule.isDB.equals("1"))
                        {
                        	ActionModule.MysqlInsertData("diskdata,"+writespeed2,"ddValue,ddUnit",writespeed,3,2);
                        	
                        }
                        if(CommonOSModule.isExcelWrite.equals("1"))
                        {
                        	CommonOSModule.ExcelFileWriteAction(writespeed,"Disk Write Speed",CommonOSModule.getCurrentDate());
                        }
                        ActionModule.ConsolPrint("Disk Write Speed: "+writespeed+" "+writespeed2);
                        ActionModule.writingLog("Disk Write Speed: "+writespeed+" "+writespeed2);
                        if(writespeed>VariableModule.DiskWritelimitvalue) //Config Value is in MB/s
                        {
                            if(CommonOSModule.isDB.equals("1"))
                            {
                            	ActionModule.MysqlInsertData("alarmtracker","atValue",writespeed,3,1);
                            }
                        	ActionModule.ConsolPrint("Disk write speed is exceeded the threshold value");
                            ActionModule.writingLog("Disk write speed is exceeded the threshold value");
                            ActionModule.PlayAlarmAction();
                        	if(variabledo.isDynamicThreshold.equals("1"))
                        	{
                	        	if(variabledo.DiskWritelimitvalue-(VariableModule.DiskWritelimitvalue*VariableModule.percenvalue/100)<writespeed) {
                	        		ActionModule.replaceThreshold("DiskWritelimit=",String.valueOf(VariableModule.DiskWritelimitvalue-(VariableModule.DiskWritelimitvalue*VariableModule.percenvalue/100)));
                	        	}
                	        	else if(variabledo.DiskWritelimitvalue+(VariableModule.DiskWritelimitvalue*VariableModule.percenvalue/100)<writespeed) {
                	        		actiondo.replaceThreshold("DiskWritelimit=",String.valueOf(VariableModule.DiskWritelimitvalue+(VariableModule.DiskWritelimitvalue*VariableModule.percenvalue/100)));
                	        	}
                        	}
                        }
            		}
/*                	if(CommonOSModule.iSGUI.equals("1") && CommonOSModule.isDB.equals("1"))
                	{
                		LineGraphDiskModule.LineGraphCreator();
                	}*/
            	}
            	else if(flag==3) {
            		double gb =(double)Math.pow(1024, 3);
            		//ActionModule.ConsolPrint("Power: "+gb);
            		double totalfreespace = 0;
            		if(line.contains("Total # of free bytes"))
            		{
                        String[] Diskfreespace = line.split(":");
                        String line1 = Diskfreespace[1].replaceAll("\\s+", "");
                        double totalfreespace1=Double.parseDouble(line1)/gb;
                        totalfreespace=ActionModule.DecimalFormat(totalfreespace1);
                        if(CommonOSModule.isDB.equals("1"))
                        {
                        	ActionModule.MysqlInsertData("diskspace","dsValue",totalfreespace,4,2);
                        	
                        }
                        if(CommonOSModule.isExcelWrite.equals("1"))
                        {
                        	CommonOSModule.ExcelFileWriteAction(totalfreespace,"Disk Space",CommonOSModule.getCurrentDate());
                        }
                        ActionModule.ConsolPrint("Free Space: "+totalfreespace);
                        ActionModule.writingLog("Free Space: "+totalfreespace);
                		if(VariableModule.DiskFreeSpacevalue>totalfreespace) {
                	        if(CommonOSModule.isDB.equals("1"))
                	        {
                	        	ActionModule.MysqlInsertData("alarmtracker","atValue",totalfreespace,4,1);
                	        }
                			ActionModule.ConsolPrint("C drive available space is exceeded the threshold value");
                            ActionModule.writingLog("C drive available space is exceeded the threshold value");
                            ActionModule.PlayAlarmAction();
                        	if(variabledo.isDynamicThreshold.equals("1"))
                        	{
                	        	if(variabledo.DiskFreeSpacevalue-(VariableModule.DiskFreeSpacevalue*VariableModule.percenvalue/100)<totalfreespace) {
                	        		ActionModule.replaceThreshold("DiskFreeSpace=",String.valueOf(VariableModule.DiskFreeSpacevalue-(VariableModule.DiskFreeSpacevalue*VariableModule.percenvalue/100)));
                	        	}
                	        	else if(variabledo.DiskFreeSpacevalue+(VariableModule.DiskFreeSpacevalue*VariableModule.percenvalue/100)<totalfreespace) {
                	        		actiondo.replaceThreshold("DiskFreeSpace=",String.valueOf(VariableModule.DiskFreeSpacevalue+(VariableModule.DiskFreeSpacevalue*VariableModule.percenvalue/100)));
                	        	}
                        	}
                		}
                		break;
            		}
/*            		else if(line.contains("of bytes")) {
                        String[] DiskTotalspace = line.split(":");
                        String line1 = DiskTotalspace[1].replaceAll("\\s+", "");
                        double totalspace=Double.parseDouble(line1)/gb;
            		}*/

            	}
            }
            input.close();
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void PingParser(BufferedReader mem,int outcount) {
		
		ActionModule.ConsolPrint("\n*********Ping Parser-"+ActionModule.getCurrentDate()+"*********\n");
		ActionModule.writingLog("\n********Ping Parser-"+ActionModule.getCurrentDate()+"**********\n");
		
		String cvsSplitBy=":";
		String line;
		String line1 = "";
		int j=0;
		int m=1;
		String firstpack = null;
        try {
			while ((line = mem.readLine()) != null) {
				ActionModule.ConsolPrint(line);
				ActionModule.writingLog(line);
				if(j==0) {
					if(line.contains("Ping request could not find host")) {
						firstpack="Ping request could not find host";
						ActionModule.ConsolPrint("First Packet: "+firstpack);
						ActionModule.ConsolPrint("Server is out of network");
						ActionModule.writingLog("Server is out of network");
						break;
					}
				}
				if(j>1 && m<=Integer.parseInt(VariableModule.Pcapduration)) {

					if(m==1)
					{
						if(line.contains("TTL")) {
							firstpack="TTL";
							ActionModule.ConsolPrint("First Packet: "+firstpack);
						}
						else if(line.contains("Request timed out.")) {
							
							firstpack="Request timed out.";
							ActionModule.ConsolPrint("First Packet: "+firstpack);
						}
						else if(line.contains("Destination host unreachable.") && line.contains("Reply from")) {
							firstpack="Destination host unreachable. with Reply from";
							ActionModule.ConsolPrint("First Packet: "+firstpack);
						}
						else if(line.contains("Destination host unreachable.")) {
							firstpack="Destination host unreachable.";
							ActionModule.ConsolPrint("First Packet: "+firstpack);
						}
						else if(line.contains("Ping request could not find host")) {
							firstpack="Ping request could not find host";
							ActionModule.ConsolPrint("First Packet: "+firstpack);
							break;
						}
						else if(line.contains("General failure.")) {
							firstpack="General failure.";
							ActionModule.ConsolPrint("First Packet: "+firstpack);
							break;
						}
					}
					else {
						if(!line.contains(firstpack)) {
							break;
						}
						else {
						if(m==Integer.parseInt(VariableModule.Pcapduration)) {
							if(line.contains("TTL")) {
								ActionModule.ConsolPrint("Connection is OK");
								ActionModule.writingLog("Connection is OK");
							}
							else if(line.contains("Request timed out.")) {
								ActionModule.ConsolPrint("Node is disconnected from network or Traffic congestion is occured");
								ActionModule.writingLog("Node is disconnected from network or Traffic congestion is occured");
							}
							else if(line.contains("Destination host unreachable.") && line.contains("Reply from")) {
								ActionModule.ConsolPrint("Either routing problem or node is temporarilly disconnected");
								ActionModule.writingLog("Either routing problem or node is temporarilly disconnected");
							}
							else if(line.contains("Destination host unreachable.")) {
								ActionModule.ConsolPrint("No Route Found from source node");
								ActionModule.writingLog("No Route Found from source node");
							}
							else if(line.contains("Ping request could not find host")) {//General failure.
								ActionModule.ConsolPrint("DNS is not reachable");
								ActionModule.writingLog("DNS is not reachable");
							}
							else if(line.contains("General failure.")) {
								ActionModule.ConsolPrint("Server is out of network");
								ActionModule.writingLog("Server is out of network");
							}
						}
						}
					}	
					m++;
				}
				if(line.contains("Average")) {
					
					String[] PingArray = line.split(",");
					String[] PingArray1 = PingArray[2].split("=");
					String latency1 = PingArray1[1].replaceAll(" ", "");
					String latency = latency1.replaceAll("ms", "");
					int netlatency= Integer.parseInt(latency);
					ActionModule.ConsolPrint("Found Network Latency: "+netlatency);
					ActionModule.writingLog("Found Network Latency: "+netlatency);
					if(netlatency>variabledo.pinglatencyvalue)
					{
						ActionModule.ConsolPrint("Network latency is greater than threshold value");
						ActionModule.writingLog("Network latency is greater than threshold value");
						actiondo.PlayAlarmAction();
			        	if(variabledo.isDynamicThreshold.equals("1"))
			        	{
            	        	if(variabledo.pinglatencyvalue-(VariableModule.pinglatencyvalue*VariableModule.percenvalue/100)<netlatency) {
            	        		ActionModule.replaceThreshold("pinglatency=",String.valueOf(VariableModule.pinglatencyvalue-(VariableModule.pinglatencyvalue*VariableModule.percenvalue/100)));
            	        	}
            	        	else if(variabledo.pinglatencyvalue+(VariableModule.pinglatencyvalue*VariableModule.percenvalue/100)<netlatency) {
            	        		actiondo.replaceThreshold("pinglatency=",String.valueOf(VariableModule.pinglatencyvalue+(VariableModule.pinglatencyvalue*VariableModule.percenvalue/100)));
            	        	}
			        	}
					}
				}
				j++;
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
	}
	
	public static void IPParser(BufferedReader mem,int outcount) throws IOException {
		

		
		String cvsSplitBy=":";
		String line;
		String line1 = "";
        while ((line = mem.readLine()) != null) {
        	//ActionModule.ConsolPrint(line);
        	if(line.contains("IPv4 Address")) {
        	String[] IPArray = line.split(cvsSplitBy);
        	line1 = IPArray[1].replaceAll(" ", "");
        		ServerArray[10]=line1;
        	}
        }
        
	}
	
	public static void WifiParser(BufferedReader mem,int outcount) throws IOException {
		
		ActionModule.ConsolPrint("\n*********Wifi Parser-"+ActionModule.getCurrentDate()+"*********\n");
		ActionModule.writingLog("\n*********Wifi Parser-"+ActionModule.getCurrentDate()+"*********\n");
		
		String cvsSplitBy=":";
		String line;
		String line1 = "";
        while ((line = mem.readLine()) != null) {
        	//ActionModule.ConsolPrint(line);
        	if(line.contains("State")) {
        	String[] WifiArray = line.split(cvsSplitBy);
        	line1 = WifiArray[1].replaceAll(" ", "");
	        	if(line1.equals("connected"))
	        	{
	        		ActionModule.ConsolPrint("Wifi is connected");
	        		ActionModule.writingLog("Wifi is connected");
	        		
	        		ActionModule.ConsolPrint("IP Address is : "+IPParser1());
	        		ActionModule.writingLog("IP Address is : "+IPParser1());
	        	}
	        	else {
	        		ActionModule.ConsolPrint("Wifi is disconnected");
	        		ActionModule.writingLog("Wifi is disconnected");
	        	}
        	}
        	else if(line.contains("Receive rate (Mbps)")) {
            	String[] WifiArray = line.split(cvsSplitBy);
            	line1 = WifiArray[1].replaceAll(" ", "");
        		ActionModule.ConsolPrint("Receive rate (Mbps): "+line1);
        		ActionModule.writingLog("Receive rate (Mbps): "+line1);
            }
        	else if(line.contains("Transmit rate (Mbps)")) {
            	String[] WifiArray = line.split(cvsSplitBy);
            	line1 = WifiArray[1].replaceAll(" ", "");
        		ActionModule.ConsolPrint("Transmit rate (Mbps): "+line1);
        		ActionModule.writingLog("Transmit rate (Mbps): "+line1);
            }
        }
        
	}
	
	public static String IPParser1() {
		String line1 = "";
		Runtime rt = Runtime.getRuntime();
        //Process proc = rt.exec(command);
        try {
        	//String testcom="tshark -i 4 -p -a duration:"+duration+" -w "+pcapfile+"Trace.pcap";
        	Process proc = rt.exec("ipconfig");
            BufferedReader input = new BufferedReader(new InputStreamReader(proc.getInputStream()));
    		String cvsSplitBy=":";
    		String line;
    		
            while ((line = input.readLine()) != null) {
            	//ActionModule.ConsolPrint(line);
            	if(line.contains("IPv4 Address")) {
            	String[] IPArray = line.split(cvsSplitBy);
            	line1 = IPArray[1].replaceAll(" ", "");
            	break;
            	}
            }
            input.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        return line1;
	}
	
	public static void MACParser() {
		
		
		Runtime rt = Runtime.getRuntime();
		String line;
        //Process proc = rt.exec(command);
        try {
        	//String testcom="tshark -i 4 -p -a duration:"+duration+" -w "+pcapfile+"Trace.pcap";
        	
        	Process proc = rt.exec("getmac");
            BufferedReader input = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            while ((line = input.readLine()) != null) {
            	//ActionModule.ConsolPrint("Output: "+line);
            	if(line.contains("Device")) {
	            	String[] MACArray = line.split("\\\\Device");
	            	String line1 = MACArray[0].replaceAll("\\s+", "");
	            	//ActionModule.ConsolPrint("MAC Address: "+line1);
	            	ServerArray[11]=line1;
            	}
            }
            input.close();
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void MACSpoofingChecker() {
		
		ActionModule.ConsolPrint("\n*********MACSpoofingChecker-"+ActionModule.getCurrentDate()+"*********\n");
		ActionModule.writingLog("\n*********MACSpoofingChecker-"+ActionModule.getCurrentDate()+"*********\n");
		
		Runtime rt = Runtime.getRuntime();
		String line;
		String MACAddress=null;
		String newMAC = null;
		boolean filefound=actiondo.ReadFile("MACAddress");
		
		if(filefound==false)
		{
			MACParser();
			actiondo.writing("\nMACAddress="+ServerArray[11]+";",1);
			MACAddress=ServerArray[11];
		}
		else {
			MACAddress = variabledo.readVariable("MACAddress",1);
		}
        //Process proc = rt.exec(command);
		
        try {
        	//String testcom="tshark -i 4 -p -a duration:"+duration+" -w "+pcapfile+"Trace.pcap";
        	
        	Process proc = rt.exec("getmac");
            BufferedReader input = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            while ((line = input.readLine()) != null) {
            	//ActionModule.ConsolPrint("Output: "+line);
            	if(line.contains("Device")) {
	            	String[] MACArray = line.split("\\\\Device");
	            	newMAC = MACArray[0].replaceAll("\\s+", "");
	            	//ActionModule.ConsolPrint("MAC Address: "+line1);
	            	break;
            	}
            }
            if(MACAddress.equals(newMAC)) {
            	ActionModule.ConsolPrint("MAC Address is not spoofed");
            	ActionModule.writingLog("MAC Address is not spoofed");
            	
            	ActionModule.ConsolPrint("System MAC Address: "+MACAddress);
            	ActionModule.writingLog("System MAC Address: "+MACAddress);
            	
            	ActionModule.ConsolPrint("System MAC Address: "+newMAC);
            	ActionModule.writingLog("System MAC Address: "+newMAC);
            }
            else {
            	ActionModule.ConsolPrint("MAC Address is spoofed");
            	ActionModule.writingLog("MAC Address is spoofed");
            	
            	ActionModule.ConsolPrint("System MAC Address: "+MACAddress);
            	ActionModule.writingLog("System MAC Address: "+MACAddress);
            	
            	ActionModule.ConsolPrint("System MAC Address: "+newMAC);
            	ActionModule.writingLog("System MAC Address: "+newMAC);
            	
            	ActionModule.PlayAlarmAction();
            }
            input.close();
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void CPUParser(BufferedReader mem,int outcount) throws IOException {
		
		ActionModule.ConsolPrint("\n*********CPU Parser-"+ActionModule.getCurrentDate()+"*********\n");
		ActionModule.writingLog("\n*********CPU Parser-"+ActionModule.getCurrentDate()+"*********\n");
		
    	String cvsSplitBy=",";
    	String line;
    	//ActionModule.ConsolPrint(outcount);
    	double[] cpustore = new double[outcount];
    	int j=0;
    	double memvalue=Double.parseDouble(variabledo.CPULimitValue);
    	//ActionModule.ConsolPrint(memvalue);
        while ((line = mem.readLine()) != null) {
        	//ActionModule.ConsolPrint(line);
        	String[] CPUArray = line.split(cvsSplitBy);
        	if(CPUArray[0].contains("\"")) {
	        	String temstr=CPUArray[1].substring(1, CPUArray[1].length()-1);
	        	//ActionModule.ConsolPrint("Test: "+temstr);
	        	if(j>0) {
	        		cpustore[j-1]=Double.parseDouble(temstr);
		        	ActionModule.ConsolPrint("CPU Output: " +Double.parseDouble(temstr));
		        	ActionModule.writingLog("CPU Output: " +Double.parseDouble(temstr));
	        	}
	        	j++;
        	}
          }
	      double cpuavg=0.0;
	      //System.out.println("J Value: "+j);
        for (int i=0; i< j-1; i++){
	      //ActionModule.ConsolPrint("Memory Output: " +cpustore[i]);
        	cpuavg=cpuavg+cpustore[i];
	    }
        
        cpuavg=cpuavg/variabledo.Pcapvalue;
        
        CPUAverage=ActionModule.DecimalFormat(cpuavg);
        if(CommonOSModule.isDB.equals("1"))
        {
        	ActionModule.MysqlInsertData("cpudata","cdValue",CPUAverage,1,1);
        	
        }
        if(CommonOSModule.isExcelWrite.equals("1"))
        {
        	CommonOSModule.ExcelFileWriteAction(CPUAverage,"CPU",CommonOSModule.getCurrentDate());
        }
        ActionModule.ConsolPrint("Average CPU: "+CPUAverage);
    	ActionModule.writingLog("Average CPU: "+CPUAverage);
    	
        if(CPUAverage>variabledo.cpuvalue) {
            if(CommonOSModule.isDB.equals("1"))
            {
            	ActionModule.MysqlInsertData("alarmtracker","atValue",CPUAverage,2,1);
            }
        	ActionModule.ConsolPrint("Average CPU is reached threshold value");
        	ActionModule.writingLog("Average CPU is reached threshold value");
        	ActionModule.PlayAlarmAction();
        	
        	if(CommonOSModule.isSMSAlert.equals("1"))
        	{
        		CommonOSModule.SMSwithPlivo("Hi Admin,\nCPU Usage: "+CPUAverage);
        	}
        	
        	//double min=four.format(variabledo.cpuvalue-(VariableModule.cpuvalue*VariableModule.percenvalue/100));
        	if(variabledo.isDynamicThreshold.equals("1"))
        	{
	        	if(variabledo.cpuvalue-(VariableModule.cpuvalue*VariableModule.percenvalue/100)<CPUAverage) {
	        		ActionModule.replaceThreshold("CPULimitValue=",String.valueOf(ActionModule.DecimalFormat(VariableModule.cpuvalue-(VariableModule.cpuvalue*VariableModule.percenvalue/100))));
	        	}
	        	else if(variabledo.cpuvalue+(VariableModule.cpuvalue*VariableModule.percenvalue/100)<CPUAverage) {
	        		ActionModule.replaceThreshold("CPULimitValue=",String.valueOf(ActionModule.DecimalFormat(VariableModule.cpuvalue+(VariableModule.cpuvalue*VariableModule.percenvalue/100))));
	        	}
        	}
        	CommandExecModule("typeperf \"\\Process(*)\\% Processor Time\" -sc "+Double.toHexString(variabledo.Pcapvalue-(variabledo.Pcapvalue-1.0)), 1000,7);
        }
    	if(CommonOSModule.iSGUI.equals("1") && CommonOSModule.isDB.equals("1"))
    	{
    		LineGraphCPUModule.LineGraphCreator();
    	}
    }
	

	public static void ProcessParserforCPU(BufferedReader mem,int outcount) throws IOException {
    	String line;        	
    	int k=0;
    	String cvsSplitBy=",";
    	String[] procName = new String[outcount];
    	double[] procmemstore = new double[outcount];
    	int j=0;
    	int m=1;
    	//double memvalue=Double.parseDouble(variabledo.MemoryLimitValue);
        while ((line = mem.readLine()) != null) {
            //ActionModule.ConsolPrint(line);
        	if(line.contains("\"")) {
	        	String[] CPUArray = line.split(cvsSplitBy);
	        	//ActionModule.ConsolPrint("Array Lenght: "+CPUArray.length);
	        	
	        	if(j==0)
	        	{
	        		for(;m<CPUArray.length-1;m++)
	        		{
	        			CPUArray[m] = CPUArray[m].replaceAll("Process", ",");
	        			//ActionModule.ConsolPrint("Array 1: "+CPUArray[1]);
	        			String[] CPUArray1 = CPUArray[m].split(",|%");
	        			procName[m-1] = CPUArray1[1].substring(1, CPUArray1[1].length()-2);
	        		}
	        		m=1;
	        	}
	        	else
	        	{
	        		for(;m<CPUArray.length-1;m++)
	        		{
	        			CPUArray[m]=CPUArray[m].substring(1, CPUArray[m].length()-1);
	        			procmemstore[m-1] = Double.parseDouble(CPUArray[m]);
	        		}

	        	}
	        	
	        	j++;
        	}
          }
/*        for(k=0;k<m-2;k++) {
        	ActionModule.ConsolPrint("Process"+(k+1)+": "+procName[k]+"\t\t"+"Process"+(k+1)+" CPU: "+procmemstore[k]);
        	ActionModule.writingLog("Process"+(k+1)+": "+procName[k]+"\t\t"+"Process"+(k+1)+" CPU: "+procmemstore[k]);
        }*/
        
        
        int t=0;
        int[] topmem=new int[m];
        for(k=0;k<m;k++) {
        	//ActionModule.ConsolPrint("Process"+k+": "+procName[k]+"\t\t"+"ProcessCPU"+k+": "+procmemstore[k]);
        	if(procmemstore[k]>variabledo.procpuvalue)
        	{
        		
        		//ActionModule.ConsolPrint("Process"+k+": "+procName[k]+"\t\t"+"ProcessMemory"+k+": "+procmemstore[k]);
        		topmem[t]=k;
        		t++;
        	}
		//ActionModule.ConsolPrint("ProcessMemory"+k+": "+procmemstore[k]);
        }
        insertionSort(procName,procmemstore,topmem,t);
		ActionModule.ConsolPrint("Process wise CPU usage is also exceeded for few processes");
		ActionModule.writingLog("Process wise CPU usage is also exceeded for few processes");
    	for(k=t-1;k>=0;k--) {
    		ActionModule.ConsolPrint("Top Process: "+procName[topmem[k]]+"\t"+"Top Process CPU: "+procmemstore[topmem[k]]);
    		ActionModule.writingLog("Top Process: "+procName[topmem[k]]+"\t"+"Top Process CPU: "+procmemstore[topmem[k]]);
    	}

    }

	

}

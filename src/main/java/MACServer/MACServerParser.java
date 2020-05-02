package MACServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import CommonPlatform.CommonOSModule;
import MyAction.ActionModule;
import MyGraph.LineGraphCPUModule;
import MyGraph.LineGraphMemoryModule;
import MyVariable.VariableModule;

public class MACServerParser {

	
	public static void MACCPUParser(BufferedReader mem,int outcount) throws IOException {
		
		//ActionModule.ConsolPrint("\n*********CPU Parser-"+ActionModule.getCurrentDate()+"*********\n");
		MACAction.writingLog("\n*********MAC CPU Parser-"+MACAction.getCurrentDate()+"*********\n");
    	String line;
    	//double[] cpustore = new double[outcount];
    	int j=0;
    	double cpuvalue=Double.parseDouble(MACVariable.CPULimitValue);
    	double cpuavg=0.0;

    	while ((line = mem.readLine()) != null) {
    		//MACAction.writingLog("Output Line: " +line);
    		if(line.contains("CPU usage:"))
    		{
	    		String line1 = line.replaceAll("\\s+", "");
	    		line1 = line1.replaceAll("%user|%sys", "");
	    		String[] CPUArray = line1.split(":");
	    		String[] CPUArray1 = CPUArray[1].split(",");
	    		
	/*        	for (int i=0; i< CPUArray.length; i++){
		      	      MACAction.writingLog("CPU Output: " +CPUArray[i]);
		      	      
		      	}*/
	    		cpuavg=Double.parseDouble(CPUArray1[0])+Double.parseDouble(CPUArray1[1]);
	    		break;
    		}
    	}
    	cpuavg=MACAction.DecimalFormat(cpuavg);
    	MACAction.writingLog("Average CPU: "+cpuavg);
        if(CommonOSModule.isDB.equals("1"))
        {
        	MACAction.MysqlInsertData("cpudata","cdValue",cpuavg,1,1);
        	
        }
/*        if(CommonOSModule.isExcelWrite.equals("1"))
        {
        	CommonOSModule.ExcelFileWriteAction(cpuavg,"CPU",CommonOSModule.getCurrentDate());
        }*/
        
        if(cpuavg>cpuvalue)
        {
            if(CommonOSModule.isDB.equals("1"))
            {
            	MACAction.MysqlInsertData("alarmtracker","atValue",cpuavg,2,1);
            }
        	MACAction.writingLog("Average CPU is reached threshold value");
        	MACAction.PlayAlarmAction();
        	if(CommonOSModule.isSMSAlert.equals("1"))
        	{
        		CommonOSModule.SMSwithPlivo("Hi Admin,\nCPU Usage: "+cpuavg);
        	}
        }
    	
    	if(CommonOSModule.isWindows.equals("2") && CommonOSModule.iSGUI.equals("1") && CommonOSModule.isDB.equals("1"))
    	{
    		LineGraphCPUModule.LineGraphCreator();
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
            	}
            }
            input.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        return line1;
	}
	
	public static void MACMemoryParser(BufferedReader mem,int outcount) throws IOException {
		
		//ActionModule.ConsolPrint("\n*********CPU Parser-"+ActionModule.getCurrentDate()+"*********\n");
		MACAction.writingLog("\n*********MAC Memory Parser-"+MACAction.getCurrentDate()+"*********\n");
    	String line;
    	double[] memstore = new double[outcount];
    	int j=0;
    	double memvalue=Double.parseDouble(MACVariable.MemoryLimitValue);
    	double memavg=0.0;
        while ((line = mem.readLine()) != null) {
        	
    		if(line.contains("PhysMem"))
    		{
	    		String line1 = line.replaceAll("\\s+", "");
	    		String line2 = line1.replaceAll("Mused", ",");
	    		String line3 = line2.replaceAll("Munused", "");
	    		String[] CPUArray = line3.split(":");
	    		String[] CPUArray1 = CPUArray[1].split(",");
	    		
	/*        	for (int i=0; i< CPUArray.length; i++){
		      	      MACAction.writingLog("CPU Output: " +CPUArray[i]);
		      	      
		      	}*/
	    		memavg=Double.parseDouble(CPUArray1[2]);
	    		memavg=MACAction.DecimalFormat(memavg);
	    		break;
    		}

          }

        if(CommonOSModule.isDB.equals("1"))
        {
        	MACAction.MysqlInsertData("memorydata","mdValue",memavg,1,1);
        	
        }
/*        if(CommonOSModule.isExcelWrite.equals("1"))
        {
        	CommonOSModule.ExcelFileWriteAction(memavg,"Memory",CommonOSModule.getCurrentDate());
        }*/
        MACAction.writingLog("Average Memory: "+memavg);
        if(memavg<memvalue)
        {
            if(CommonOSModule.isDB.equals("1"))
            {
            	MACAction.MysqlInsertData("alarmtracker","atValue",memavg,1,1);
            }
        	MACAction.writingLog("Average Memory is reached threshold value");
        	MACAction.PlayAlarmAction();
        	if(CommonOSModule.isSMSAlert.equals("1"))
        	{
        		CommonOSModule.SMSwithPlivo("hi Admin,\nMemory Usage: "+memavg);
        	}
        	
        }
    	
        if(CommonOSModule.isWindows.equals("2") && CommonOSModule.iSGUI.equals("1") && CommonOSModule.isDB.equals("1"))
    	{
    		LineGraphMemoryModule.LineGraphCreator();
    	}

    }
	
	
	public static void MACDiskWriteSpeedParser(BufferedReader mem,int outcount) throws IOException {
		
		//ActionModule.ConsolPrint("\n*********CPU Parser-"+ActionModule.getCurrentDate()+"*********\n");
		MACAction.writingLog("\n*********MAC Disk Write Speed Parser-"+MACAction.getCurrentDate()+"*********\n");
    	String line;
    	//double[] cpustore = new double[outcount];
    	int j=0;
    	double writespeed=0.0;
    	String writespeed1 = null;
    	while ((line = mem.readLine()) != null) {
    		MACAction.writingLog("Disk Write: " +line);
    		if(j>1)
    		{
    		String[] line1 = line.split(",");
    		writespeed1 = line1[2].replaceAll("\\s+", ",");
    		String[] writespeed2 = line.split(",");
/*        	for (int i=0; i< CPUArray.length; i++){
	      	      MACAction.writingLog("CPU Output: " +CPUArray[i]);
	      	      
	      	}*/
    		Double writespeed3=Double.parseDouble(writespeed2[1]);
    		writespeed=MACAction.DecimalFormat(writespeed3);
    		}
    		j++;
    	}
    	
    	MACAction.writingLog("Disk Write Speed: "+writespeed);
        if(CommonOSModule.isDB.equals("1"))
        {
        	MACAction.MysqlInsertData("diskdata","ddValue",writespeed,1,2);
        	
        }
/*        if(CommonOSModule.isExcelWrite.equals("1"))
        {
        	CommonOSModule.ExcelFileWriteAction(writespeed,"Disk Write Speed",CommonOSModule.getCurrentDate());
        }*/
        if(writespeed>MACVariable.DiskWritelimitvalue)
        {
            if(CommonOSModule.isDB.equals("1"))
            {
            	MACAction.MysqlInsertData("alarmtracker","atValue",writespeed,3,1);
            }
        	MACAction.writingLog("Disk write speed is reached threshold value");
        	MACAction.PlayAlarmAction();
        	
        }
    	

    }
	
	public static void MACDiskReadSpeedParser(BufferedReader mem,int outcount) throws IOException {
		
		//ActionModule.ConsolPrint("\n*********CPU Parser-"+ActionModule.getCurrentDate()+"*********\n");
		MACAction.writingLog("\n*********MAC Disk Read Speed Parser-"+MACAction.getCurrentDate()+"*********\n");
    	String line;
    	//double[] cpustore = new double[outcount];
    	int j=0;
    	double readspeed = 0.0;
    	String readspeed1 = null;
    	String readspeed3=null;
    	while ((line = mem.readLine()) != null) {
    		if(line.contains("Disks"))
    		{
	    		String line1 = line.replaceAll("\\s+", "");
	    		String line2 = line1.replaceAll("Mread", "");
	    		
	    		String[] CPUArray = line2.split(":");
	    		String[] CPUArray1 = CPUArray[1].split(",");
	    		
	/*        	for (int i=0; i< CPUArray.length; i++){
		      	      MACAction.writingLog("CPU Output: " +CPUArray[i]);
		      	      
		      	}*/
	    		Double readspeed4=Double.parseDouble(CPUArray1[2]);
	    		readspeed=MACAction.DecimalFormat(readspeed4);
	    		break;
    		}
    	}
    	
    	MACAction.writingLog("Disk Read Speed: "+readspeed+" "+readspeed3);
        if(CommonOSModule.isDB.equals("1"))
        {
        	MACAction.MysqlInsertData("diskdata,"+readspeed3,"ddValue,ddUnit",readspeed,3,1);
        	
        }
/*        if(CommonOSModule.isExcelWrite.equals("1"))
        {
        	CommonOSModule.ExcelFileWriteAction(readspeed,"Disk Read Speed",CommonOSModule.getCurrentDate());
        }*/
        if(readspeed>MACVariable.DiskReadlimitvalue)
        {
            if(CommonOSModule.isDB.equals("1"))
            {
            	MACAction.MysqlInsertData("alarmtracker","atValue",readspeed,3,1);
            }
        	MACAction.writingLog("Disk read speed is reached threshold value");
        	MACAction.PlayAlarmAction();
        	
        }
    	

    }
	
	public static void MACDiskSpaceParser(BufferedReader mem,int outcount) throws IOException {
		
		//ActionModule.ConsolPrint("\n*********CPU Parser-"+ActionModule.getCurrentDate()+"*********\n");
		MACAction.writingLog("\n*********MAC Disk Space Parser-"+MACAction.getCurrentDate()+"*********\n");
    	String line;
    	//double[] cpustore = new double[outcount];
    	int j=0;
    	double readspeed = 0.0;
    	String readspeed1 = null;
    	while ((line = mem.readLine()) != null) {
    		//MACAction.writingLog("Disk Space: " +line);
    		if(j>0)
    		{
    		//String[] line1 = line.split("=");
    		readspeed1 = line.replaceAll("\\s+", ",");
    		readspeed1 = readspeed1.replaceAll("G", "");
    		String[] readspeed2 = readspeed1.split(",");
    		readspeed1 = readspeed2[3].replaceAll("G", "");
/*        	for (int i=0; i< CPUArray.length; i++){
	      	      MACAction.writingLog("CPU Output: " +CPUArray[i]);
	      	      
	      	}*/
    		Double readspeed4=Double.parseDouble(readspeed1);
    		readspeed=MACAction.DecimalFormat(readspeed4);
    		break;
    		}
    		j++;
    	}
    	
        if(CommonOSModule.isDB.equals("1"))
        {
        	MACAction.MysqlInsertData("diskspace","dsValue",readspeed,4,2);
        	
        }
    	MACAction.writingLog("Disk Space: "+readspeed);
/*        if(CommonOSModule.isExcelWrite.equals("1"))
        {
        	CommonOSModule.ExcelFileWriteAction(readspeed,"Disk Space",CommonOSModule.getCurrentDate());
        }*/

        if(readspeed>MACVariable.DiskFreeSpacevalue)
        {
            if(CommonOSModule.isDB.equals("1"))
            {
            	MACAction.MysqlInsertData("alarmtracker","atValue",readspeed,4,1);
            }
        	MACAction.writingLog("Disk space is reached threshold value");
        	MACAction.PlayAlarmAction();
        	
        }
    	

    }
	
}

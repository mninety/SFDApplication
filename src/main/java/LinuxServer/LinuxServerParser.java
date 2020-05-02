package LinuxServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import CommonPlatform.CommonOSModule;
import MyAction.ActionModule;
import MyVariable.VariableModule;

public class LinuxServerParser {

	
	public static void LinuxCPUParser(BufferedReader mem,int outcount) throws IOException {
		
		//ActionModule.ConsolPrint("\n*********CPU Parser-"+ActionModule.getCurrentDate()+"*********\n");
		LinuxAction.writingLog("\n*********Linux CPU Parser-"+LinuxAction.getCurrentDate()+"*********\n");
    	String line;
    	//double[] cpustore = new double[outcount];
    	int j=0;
    	double cpuvalue=Double.parseDouble(LinuxVariable.CPULimitValue);
    	double cpuavg=0.0;

    	while ((line = mem.readLine()) != null) {
    		//LinuxAction.writingLog("Output Line: " +line);
    		String line1 = line.replaceAll("\\s+", ",");
    		String[] CPUArray = line1.split(",");
/*        	for (int i=0; i< CPUArray.length; i++){
	      	      LinuxAction.writingLog("CPU Output: " +CPUArray[i]);
	      	      
	      	}*/
    		cpuavg=((Double.parseDouble(CPUArray[1])+Double.parseDouble(CPUArray[3]))*100)/(Double.parseDouble(CPUArray[1])+Double.parseDouble(CPUArray[3])+Double.parseDouble(CPUArray[4]));
    		break;
    	}
    	cpuavg=LinuxAction.DecimalFormat(cpuavg);
    	LinuxAction.writingLog("Average CPU: "+cpuavg);
        if(CommonOSModule.isDB.equals("1"))
        {
        	LinuxAction.MysqlInsertData("cpudata","cdValue",cpuavg,1,1);
        	
        }
/*        if(CommonOSModule.isExcelWrite.equals("1"))
        {
        	CommonOSModule.ExcelFileWriteAction(cpuavg,"CPU",CommonOSModule.getCurrentDate());
        }*/
        if(cpuavg>cpuvalue)
        {
            if(CommonOSModule.isDB.equals("1"))
            {
            	LinuxAction.MysqlInsertData("alarmtracker","atValue",cpuavg,2,1);
            }
        	LinuxAction.writingLog("Average CPU is reached threshold value");
        	if(CommonOSModule.isSMSAlert.equals("1"))
        	{
        		CommonOSModule.SMSwithPlivo("Hi Admin,\nCPU Usage: "+cpuavg);
        	}
        }
    	

    }
	
	public static void LinuxMemoryParser(BufferedReader mem,int outcount) throws IOException {
		
		//ActionModule.ConsolPrint("\n*********CPU Parser-"+ActionModule.getCurrentDate()+"*********\n");
		LinuxAction.writingLog("\n*********Linux Memory Parser-"+LinuxAction.getCurrentDate()+"*********\n");
    	String line;
    	double[] memstore = new double[outcount];
    	int j=0;
    	double memvalue=Double.parseDouble(LinuxVariable.MemoryLimitValue);
    	double memavg=0.0;
        while ((line = mem.readLine()) != null) {
        	if(j>0)
        	{
        		String line1 = line.replaceAll("\\s+", ",");
        		String[] MemArray = line1.split(",");
        		//LinuxAction.writingLog("Memory Output: " +MemArray[3]);
        		memavg=Double.parseDouble(MemArray[3]);
        		memavg=LinuxAction.DecimalFormat(memavg);
                break;
        	}
	        	j++;
          }

        if(CommonOSModule.isDB.equals("1"))
        {
        	LinuxAction.MysqlInsertData("memorydata","mdValue",memavg,1,1);
        	
        }
/*        if(CommonOSModule.isExcelWrite.equals("1"))
        {
        	CommonOSModule.ExcelFileWriteAction(memavg,"Memory",CommonOSModule.getCurrentDate());
        }*/
        LinuxAction.writingLog("Average Memory: "+memavg);
        if(memavg<memvalue)
        {
            if(CommonOSModule.isDB.equals("1"))
            {
            	LinuxAction.MysqlInsertData("alarmtracker","atValue",memavg,1,1);
            }
        	LinuxAction.writingLog("Average Memory is reached threshold value");
        	if(CommonOSModule.isSMSAlert.equals("1"))
        	{
        		CommonOSModule.SMSwithPlivo("Hi Admin,\nMemory Usage: "+memavg);
        	}
        }
    	

    }
	
	
	public static void LinuxDiskWriteSpeedParser(BufferedReader mem,int outcount) throws IOException {
		
		//ActionModule.ConsolPrint("\n*********CPU Parser-"+ActionModule.getCurrentDate()+"*********\n");
		LinuxAction.writingLog("\n*********Linux Disk Write Speed Parser-"+LinuxAction.getCurrentDate()+"*********\n");
    	String line;
    	//double[] cpustore = new double[outcount];
    	int j=0;
    	double writespeed=0.0;
    	String writespeed1 = null;
    	while ((line = mem.readLine()) != null) {
    		LinuxAction.writingLog("Disk Write: " +line);
    		if(j>1)
    		{
    		String[] line1 = line.split(",");
    		writespeed1 = line1[2].replaceAll("\\s+", ",");
    		String[] writespeed2 = line.split(",");
/*        	for (int i=0; i< CPUArray.length; i++){
	      	      LinuxAction.writingLog("CPU Output: " +CPUArray[i]);
	      	      
	      	}*/
    		Double writespeed3=Double.parseDouble(writespeed2[1]);
    		writespeed=LinuxAction.DecimalFormat(writespeed3);
    		}
    		j++;
    	}
    	
    	LinuxAction.writingLog("Disk Write Speed: "+writespeed);
        if(CommonOSModule.isDB.equals("1"))
        {
        	LinuxAction.MysqlInsertData("diskdata","ddValue",writespeed,1,2);
        	
        }
/*        if(CommonOSModule.isExcelWrite.equals("1"))
        {
        	CommonOSModule.ExcelFileWriteAction(writespeed,"Disk Write Speed",CommonOSModule.getCurrentDate());
        }*/
        if(writespeed>LinuxVariable.DiskWritelimitvalue)
        {
            if(CommonOSModule.isDB.equals("1"))
            {
            	LinuxAction.MysqlInsertData("alarmtracker","atValue",writespeed,3,1);
            }
        	LinuxAction.writingLog("Disk write speed is reached threshold value");
        }
    	

    }
	
	public static void LinuxDiskReadSpeedParser(BufferedReader mem,int outcount) throws IOException {
		
		//ActionModule.ConsolPrint("\n*********CPU Parser-"+ActionModule.getCurrentDate()+"*********\n");
		LinuxAction.writingLog("\n*********Linux Disk Read Speed Parser-"+LinuxAction.getCurrentDate()+"*********\n");
    	String line;
    	//double[] cpustore = new double[outcount];
    	int j=0;
    	double readspeed = 0.0;
    	String readspeed1 = null;
    	String readspeed3=null;
    	while ((line = mem.readLine()) != null) {
    		//LinuxAction.writingLog("Disk Read: " +line);
    		if(j>2)
    		{
    		String[] line1 = line.split("=");
    		readspeed1 = line1[1].replaceAll("\\s+", ",");
    		String[] readspeed2 = readspeed1.split(",");
/*        	for (int i=0; i< CPUArray.length; i++){
	      	      LinuxAction.writingLog("CPU Output: " +CPUArray[i]);
	      	      
	      	}*/
    		readspeed3=readspeed2[2];
    		Double readspeed4=Double.parseDouble(readspeed2[1]);
    		readspeed=LinuxAction.DecimalFormat(readspeed4);
    		}
    		j++;
    	}
    	
    	LinuxAction.writingLog("Disk Read Speed: "+readspeed+" "+readspeed3);
        if(CommonOSModule.isDB.equals("1"))
        {
        	LinuxAction.MysqlInsertData("diskdata,"+readspeed3,"ddValue,ddUnit",readspeed,3,1);
        	
        }
/*        if(CommonOSModule.isExcelWrite.equals("1"))
        {
        	CommonOSModule.ExcelFileWriteAction(readspeed,"Disk Read Speed",CommonOSModule.getCurrentDate());
        }*/
        if(readspeed>LinuxVariable.DiskReadlimitvalue)
        {
            if(CommonOSModule.isDB.equals("1"))
            {
            	LinuxAction.MysqlInsertData("alarmtracker","atValue",readspeed,3,1);
            }
        	LinuxAction.writingLog("Disk read speed is reached threshold value");
        }
    	

    }
	
	public static void LinuxDiskSpaceParser(BufferedReader mem,int outcount) throws IOException {
		
		//ActionModule.ConsolPrint("\n*********CPU Parser-"+ActionModule.getCurrentDate()+"*********\n");
		LinuxAction.writingLog("\n*********Linux Disk Space Parser-"+LinuxAction.getCurrentDate()+"*********\n");
    	String line;
    	//double[] cpustore = new double[outcount];
    	int j=0;
    	double readspeed = 0.0;
    	String readspeed1 = null;
    	while ((line = mem.readLine()) != null) {
    		//LinuxAction.writingLog("Disk Space: " +line);
    		if(j>0)
    		{
    		//String[] line1 = line.split("=");
    		readspeed1 = line.replaceAll("\\s+", ",");
    		String[] readspeed2 = readspeed1.split(",");
    		readspeed1 = readspeed2[4].replaceAll("G", "");
/*        	for (int i=0; i< CPUArray.length; i++){
	      	      LinuxAction.writingLog("CPU Output: " +CPUArray[i]);
	      	      
	      	}*/
    		Double readspeed3=Double.parseDouble(readspeed1);
    		readspeed=LinuxAction.DecimalFormat(readspeed3);
    		break;
    		}
    		j++;
    	}
        if(CommonOSModule.isDB.equals("1"))
        {
        	LinuxAction.MysqlInsertData("diskspace","dsValue",readspeed,4,2);
        	
        }
/*        if(CommonOSModule.isExcelWrite.equals("1"))
        {
        	CommonOSModule.ExcelFileWriteAction(readspeed,"Disk Space",CommonOSModule.getCurrentDate());
        }*/
    	LinuxAction.writingLog("Disk Space: "+readspeed);
        

        if(readspeed>LinuxVariable.DiskFreeSpacevalue)
        {
            if(CommonOSModule.isDB.equals("1"))
            {
            	LinuxAction.MysqlInsertData("alarmtracker","atValue",readspeed,4,1);
            }
        	LinuxAction.writingLog("Disk space is reached threshold value");
        }
    	

    }
	
	public static String IPParser1() {
		String line1 = "";
		Runtime rt = Runtime.getRuntime();
		Process P;
        //Process proc = rt.exec(command);
        try {
        	//String testcom="tshark -i 4 -p -a duration:"+duration+" -w "+pcapfile+"Trace.pcap";| grep \"inet addr\"
        	
        	P=Runtime.getRuntime().exec("ifconfig");
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
    		String cvsSplitBy=":";
    		String line;
    		
            while ((line = input.readLine()) != null) {
            	if(line.contains("inet addr")) {
	            	String[] line2=line.split(":");
	            	line1 = line2[1].replaceAll("\\s+", "#");
	            	String[] IPArray = line1.split("#");
	            	line1=IPArray[0];
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
	
}

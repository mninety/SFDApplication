package MACServer;

public class MACDiskSpeedModule extends Thread {

	@Override
    public void run() {
    	while(true) {
			try {
				//String diskwcommand="dd if=/dev/zero of=/tmp/test2.img bs=100M count=10 oflag=dsync";
				
				//LinuxAction.CommandExecModule(diskwcommand, 5000,4);
				
				String diskrcommand="top -l 1";
				//LinuxAction.writingLog("CPU Command: "+cpucommand);
				//MACAction.CommandExecModule(diskrcommand, 5000,5);
				
				
				String diskscommand="df -H";
				//LinuxAction.writingLog("CPU Command: "+cpucommand);
				MACAction.CommandExecModule(diskscommand, 5000,6);
				
				
				//ActionModule.ConsolPrint("Linux CPU Parser Module is going to sleep");
				MACAction.writingLog("MAC Disk Parser Module is going to sleep-"+MACAction.getCurrentDate());
				//ActionModule.ConsolPrint("Sleep Time: "+VariableModule.sleepvariable);
				Thread.sleep(MACVariable.sleepvalue);
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

    	}
    }
}
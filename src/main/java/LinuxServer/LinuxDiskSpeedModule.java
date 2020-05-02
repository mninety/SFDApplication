package LinuxServer;

public class LinuxDiskSpeedModule extends Thread {

	@Override
    public void run() {
    	while(true) {
			try {
				//String diskwcommand="dd if=/dev/zero of=/tmp/test2.img bs=100M count=10 oflag=dsync";
				
				//LinuxAction.CommandExecModule(diskwcommand, 5000,4);
				
				String diskrcommand="hdparm -Tt /dev/sda";
				//LinuxAction.writingLog("CPU Command: "+cpucommand);
				LinuxAction.CommandExecModule(diskrcommand, 5000,5);
				
				
				String diskscommand="df -hT";
				//LinuxAction.writingLog("CPU Command: "+cpucommand);
				LinuxAction.CommandExecModule(diskscommand, 5000,6);
				
				
				//ActionModule.ConsolPrint("Linux CPU Parser Module is going to sleep");
				LinuxAction.writingLog("Linux Disk R/W Parser Module is going to sleep-"+LinuxAction.getCurrentDate());
				//ActionModule.ConsolPrint("Sleep Time: "+VariableModule.sleepvariable);
				Thread.sleep(LinuxVariable.sleepvalue);
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

    	}
    }
}
package LinuxServer;

public class LinuxMemoryModule extends Thread {

	@Override
    public void run() {
    	while(true) {
			try {
				
				String memcommand="free -m";
				//LinuxAction.writingLog("CPU Command: "+cpucommand);
				LinuxAction.CommandExecModule(memcommand, 5000,3);
				//LinuxAction.CommandExecModule(LinuxVariable.readVariable("command", 4), 5000,3);
				//ActionModule.ConsolPrint("Linux CPU Parser Module is going to sleep");
				LinuxAction.writingLog("Linux Memory Parser Module is going to sleep-"+LinuxAction.getCurrentDate());
				//ActionModule.ConsolPrint("Sleep Time: "+VariableModule.sleepvariable);
				Thread.sleep(LinuxVariable.sleepvalue);
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

    	}
    }
}

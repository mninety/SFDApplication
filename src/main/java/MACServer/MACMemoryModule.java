package MACServer;

public class MACMemoryModule extends Thread {

	@Override
    public void run() {
    	while(true) {
			try {
				
				String memcommand="top -l 1";
				//LinuxAction.writingLog("CPU Command: "+cpucommand);
				MACAction.CommandExecModule(memcommand, 5000,3);
				//LinuxAction.CommandExecModule(LinuxVariable.readVariable("command", 4), 5000,3);
				//ActionModule.ConsolPrint("Linux CPU Parser Module is going to sleep");
				MACAction.writingLog("MAC Memory Parser Module is going to sleep-"+MACAction.getCurrentDate());
				//ActionModule.ConsolPrint("Sleep Time: "+VariableModule.sleepvariable);
				Thread.sleep(MACVariable.sleepvalue);
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

    	}
    }
}

package LinuxServer;

import MyAction.ActionModule;
import MyVariable.VariableModule;
import ServerParser.ServerParserModule;

public class LinuxCPUModule extends Thread {

	@Override
    public void run() {
    	while(true) {
			try {
				String cpucommand="cat /proc/stat";
				//LinuxAction.writingLog("CPU Command: "+cpucommand);
				LinuxAction.CommandExecModule(cpucommand, 5000,1);
				//ActionModule.ConsolPrint("Linux CPU Parser Module is going to sleep");
				LinuxAction.writingLog("Linux CPU Parser Module is going to sleep-"+LinuxAction.getCurrentDate());
				//ActionModule.ConsolPrint("Sleep Time: "+VariableModule.sleepvariable);
				Thread.sleep(LinuxVariable.sleepvalue);
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

    	}
    }
}

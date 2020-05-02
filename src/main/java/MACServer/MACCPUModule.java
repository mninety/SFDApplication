package MACServer;

import MyAction.ActionModule;
import MyVariable.VariableModule;
import ServerParser.ServerParserModule;

public class MACCPUModule extends Thread {

	@Override
    public void run() {
    	while(true) {
			try {
				String cpucommand="top -l 1";
				//LinuxAction.writingLog("CPU Command: "+cpucommand);
				MACAction.CommandExecModule(cpucommand, 5000,1);
				//ActionModule.ConsolPrint("Linux CPU Parser Module is going to sleep");
				MACAction.writingLog("MAC CPU Parser Module is going to sleep-"+MACAction.getCurrentDate());
				//ActionModule.ConsolPrint("Sleep Time: "+VariableModule.sleepvariable);
				Thread.sleep(MACVariable.sleepvalue);
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

    	}
    }
}

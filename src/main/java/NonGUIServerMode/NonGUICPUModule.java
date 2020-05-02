package NonGUIServerMode;

import MyAction.ActionModule;
import MyVariable.VariableModule;
import ServerParser.ServerParserModule;


public class NonGUICPUModule extends Thread {

	@Override
    public void run() {
    	while(true) {
			try {
	        	String cpuinfo="typeperf -sc "+VariableModule.Pcapduration+" \"\\processor(_total)\\% processor time\"";
	        	ServerParserModule.CommandExecModule(cpuinfo, 5000,6);
	        	
				ActionModule.ConsolPrint("CPU Parser Module is going to sleep");
				//ActionModule.ConsolPrint("Sleep Time: "+VariableModule.sleepvariable);
				Thread.sleep(VariableModule.sleepvalue);
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


			
    	}
    }
    
}

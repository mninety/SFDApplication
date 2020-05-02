package NonGUIServerMode;

import MyAction.ActionModule;
import MyVariable.VariableModule;
import ServerParser.ServerParserModule;

public class NonGUIMemoryModule extends Thread {
		//long i=0;
	@Override
    public void run() {
    	while(true) {
 
	    	String memoryusage="typeperf -sc "+VariableModule.Pcapduration+" \"" + "\\Memory\\Available MBytes" + "\"";
	    	ServerParserModule.CommandExecModule(memoryusage, 1000,1);
			try {
				
				ActionModule.ConsolPrint("Memory Parser Module is going to sleep");
				Thread.sleep(VariableModule.sleepvalue);

				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//i++;
    	}
    }
    
}


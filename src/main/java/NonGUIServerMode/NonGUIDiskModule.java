package NonGUIServerMode;

import MyAction.ActionModule;
import MyVariable.VariableModule;
import ServerParser.ServerParserModule;

public class NonGUIDiskModule extends Thread {
	@Override
    public void run() {
    	while(true) {
			try {
				ServerParserModule.DiskParser("fsutil volume diskfree c:",3);
	        	ServerParserModule.DiskParser("winsat disk -seq -read -drive c",1);
	        	ServerParserModule.DiskParser("winsat disk -ran -write -drive c",2);
	        	
	        	
				ActionModule.ConsolPrint("Disk Parser Module is going to sleep");
				Thread.sleep(VariableModule.sleepvalue);
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


			
    	}
    }
}

package NonGUIServerMode;

import MyAction.ActionModule;
import MyVariable.VariableModule;
import ServerParser.ServerParserModule;

public class NonGUITimeModule extends Thread {
	@Override
    public void run() {
    	while(true) {
			try {
				ActionModule.ConsolPrint("Time Parser Module is going to sleep");
				Thread.sleep(VariableModule.sleepvalue+50000);
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ActionModule.ExecWebRequest(2);
			
    	}
    }
}

package NonGUIServerMode;

import CommonPlatform.CommonOSModule;
import MyAction.ActionModule;
import MyVariable.VariableModule;

public class Timechecker extends Thread {
	@Override
    public void run() {
    	while(true) {
			try {
				CommonOSModule.ConsolPrint("Time checker is going to sleep");
				CommonOSModule.writingLog("Time checker is going to sleep");
				//Thread.sleep(VariableModule.sleepvalue);
				Thread.sleep(120000);
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			CommonOSModule.ConsolPrint("Time checker is started at "+CommonOSModule.getCurrentDate());
			CommonOSModule.writingLog("Time checker is started at "+CommonOSModule.getCurrentDate());
			String live=CommonOSModule.MysqlConnectionAction("select uExpireDate from user where uServerIP='"+CommonOSModule.MyIPAddress+"'");
			
			live = live.replaceAll(",", "");
			//CommonOSModule.ConsolPrint("Expire Date: "+live);
			
			long expiredate=Long.parseLong(live);
			
			long currentUnixTime = System.currentTimeMillis();
			
			if(currentUnixTime>expiredate)
			{
				CommonOSModule.MysqlConnectionActionUpdate("update user set uIsLive=0 where uServerIP='"+CommonOSModule.MyIPAddress+"'");
				CommonOSModule.ConsolPrint("User is blocked");
				CommonOSModule.writingLog("User is blocked");
				break;
				
			}
			
    	}
    	System.exit(0);
    }
}

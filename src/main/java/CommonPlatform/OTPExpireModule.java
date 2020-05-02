package CommonPlatform;

public class OTPExpireModule extends Thread{
	@Override
    public void run() {
    	while(true) {
			try {
				CommonOSModule.ConsolPrint("OTP expire module is going to sleep at "+CommonOSModule.getCurrentDate());
				CommonOSModule.writingLog("OTP expire module is going to sleep at "+CommonOSModule.getCurrentDate());
				//Thread.sleep(VariableModule.sleepvalue);
				Thread.sleep(5000);
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			CommonOSModule.ConsolPrint("OTP expire module is started at "+CommonOSModule.getCurrentDate());
			CommonOSModule.writingLog("OTP expire module is started at "+CommonOSModule.getCurrentDate());
			String user=CommonOSModule.MysqlConnectionAction("select uUsername from user where uServerIP='"+CommonOSModule.MyIPAddress+"'");
			//CommonOSModule.ConsolPrint("Expire Date: "+user);
			user = user.replaceAll(",", "");
			if(!user.equals(""))
			{
				String expiretime=CommonOSModule.MysqlConnectionAction("select otExpireDate from otptoken where otFor='"+user+"' and otOTPToken>0");
				expiretime = expiretime.replaceAll(",", "");
				if(!expiretime.equals(""))
				{
					long expiredate=Long.parseLong(expiretime);
					
					long currentUnixTime = System.currentTimeMillis();
					
					if(currentUnixTime>expiredate)
					{
						CommonOSModule.MysqlConnectionActionUpdate("UPDATE otptoken set otOTPToken=-1*otOTPToken where otFor='"+user+"' and otOTPToken>0");
						CommonOSModule.ConsolPrint("OTP is expired");
						CommonOSModule.writingLog("OTP is expired");
						
						
					}
				}
			}
			else
			{
				break;
			}
			
    	}
    	
    }
}

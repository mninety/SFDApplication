package CommonPlatform;

import MyAction.ActionModule;
import MyVariable.VariableModule;
import ServerParser.ServerParserModule;

	public class EmailSentThread extends Thread {
		long waiting=CommonOSModule.sleepvalue;
		@Override
	    public void run() {
	    	while(true) {
	    		
				try {
					
					Thread.sleep(waiting);
					
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				waiting=CommonOSModule.sleepvalue;
	    		String time=CommonOSModule.getCurrentTime();
	    		//System.out.println("Time: "+time);
	    		if(time.equals(CommonOSModule.Reportmailtime))
	    		{
	    	        if(CommonOSModule.isExcelWrite.equals("1"))
	    	        {
                	  try {
                	      CommonOSModule.Mail(CommonOSModule.mailhost, CommonOSModule.mailport, CommonOSModule.mailFrom, CommonOSModule.mailpassword, CommonOSModule.mailTo,CommonOSModule.mailsubject, CommonOSModule.mailmessage, CommonOSModule.attachFiles);
                	      System.out.println("Email sent.");
                	      waiting=3660000;
                	  } catch (Exception ex) {
                	      System.out.println("Could not send email.");
                	      ex.printStackTrace();
                	  }
	    	        }
	    		}



				
	    	}
	    }
	    
	}
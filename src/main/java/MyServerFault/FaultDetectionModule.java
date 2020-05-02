package MyServerFault;


import java.awt.Container;

import java.io.Console;
import java.util.Arrays;
import java.io.IOException;

import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.Random;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;

import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.text.MaskFormatter;

import CommonPlatform.CommonOSModule;
import LinuxServer.LinuxAction;
import LinuxServer.LinuxCPUModule;
import LinuxServer.LinuxDiskSpeedModule;
import LinuxServer.LinuxMemoryModule;
import MACServer.MACAction;
import MACServer.MACCPUModule;
import MACServer.MACDiskSpeedModule;
import MACServer.MACMemoryModule;
import MyAction.ActionModule;
import MyGraph.GraphGeneratorModule;
import MyGraph.LineGraphCPUModule;
import MyGraph.LineGraphDiskModule;
import MyGraph.LineGraphDiskReadModule;
import MyGraph.LineGraphMemoryModule;
import MyVariable.VariableModule;
import NonGUIServerMode.NonGUICPUModule;
import NonGUIServerMode.NonGUIDiskModule;
import NonGUIServerMode.NonGUIMemoryModule;
import NonGUIServerMode.NonGUITimeModule;
import ServerParser.ServerParserModule;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Filter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;


import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SpringLayout.Constraints;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class FaultDetectionModule extends JFrame{

	static NonGUIMemoryModule ThreadMemorydo = new NonGUIMemoryModule();
	static NonGUICPUModule ThreadCPUdo = new NonGUICPUModule();
	static NonGUIDiskModule ThreadDiskdo = new NonGUIDiskModule();
	static NonGUITimeModule ThreadTimedo = new NonGUITimeModule();
	
	static LinuxCPUModule LinuxThreadCPUdo = new LinuxCPUModule();
	static LinuxMemoryModule LinuxThreadMemorydo = new LinuxMemoryModule();
	static LinuxDiskSpeedModule LinuxThreadDiskdo= new LinuxDiskSpeedModule();

	static MACCPUModule MACThreadCPUdo = new MACCPUModule();
	static MACMemoryModule MACThreadMemorydo = new MACMemoryModule();
	static MACDiskSpeedModule MACThreadDiskdo= new MACDiskSpeedModule();
	
    private JButton buttonOK = new JButton("Execute the Tool");

    //private JButton optionAPI = new JButton("Choose Your File");
    private JRadioButton optionServerInfo = new JRadioButton("Server Information");
    private JRadioButton optionMemory = new JRadioButton("Memory Usage");
    private JRadioButton optionCPU = new JRadioButton("CPU Usage");
    private JRadioButton optionDisk = new JRadioButton("Disk Usage");
    private JRadioButton optionTime = new JRadioButton("Server Time Compare");


    public FaultDetectionModule() {
        super("Server Monitoring Tool");
    	new JFrame("Server Monitoring Tools");

    	
        JLabel label2 = new JLabel("<html>********************Choose an option******************<br></html>", SwingConstants.CENTER);

        add(label2);
        

        
        final ButtonGroup group = new ButtonGroup();
        optionServerInfo.setActionCommand ( "1" );
        group.add(optionServerInfo);
        optionMemory.setActionCommand ( "2" );
        group.add(optionMemory);
        optionCPU.setActionCommand ( "3" );
        group.add(optionCPU);
        optionDisk.setActionCommand ( "4" );
        group.add(optionDisk);
        optionTime.setActionCommand ( "5" );
        group.add(optionTime);

        
        optionServerInfo.setSelected(true); //Default Selection
        

        setLayout(new GridBagLayout());
        
        GridBagConstraints constraints = new GridBagConstraints();
        
        constraints.anchor = GridBagConstraints.BASELINE_LEADING;
        constraints.insets = new Insets(5, 90, 5, 5);
        
        
        constraints.gridy = 0;
        
        constraints.gridy = 1;
        add(optionServerInfo, constraints);
        constraints.gridy = 2;
        add(optionMemory, constraints);
        constraints.gridy = 3;
        add(optionCPU, constraints);
        constraints.gridy = 4;
        add(optionDisk, constraints);
        constraints.gridy = 5;
        add(optionTime, constraints);

        
        constraints.gridy = 10;
        add(buttonOK, constraints);

        
        
        
        buttonOK.addActionListener(new ActionListener() {

        	
            public void actionPerformed(ActionEvent event) {
                //Get "ID"
            	optionServerInfo.setForeground(Color.BLACK);
            	optionMemory.setForeground(Color.BLACK);
            	optionCPU.setForeground(Color.BLACK);
            	optionDisk.setForeground(Color.BLACK);
            	optionTime.setForeground(Color.BLACK);

            	
            String selectedOption = group.getSelection ().getActionCommand ();
            //System.out.println("Selected option " + selectedOption);
                //Switch on "IDS"

    	    
            //statusLabel.setText(data);
                switch(selectedOption) {
                    case "1":

                    	optionServerInfo.setForeground(Color.GREEN);

                    		ServerParserModule.CommandExecModule("systeminfo", 1000,3);
                    	
                    	break;
                    
                    case "2":

                    	optionMemory.setForeground(Color.GREEN);
                    	
	                    	String memoryusage="typeperf -sc "+VariableModule.Pcapduration+" \"" + "\\Memory\\Available MBytes" + "\"";
	                    	ServerParserModule.CommandExecModule(memoryusage, 1000,1);
                        
                    	break;
                    	
                    case "3":
                    	
                    	optionCPU.setForeground(Color.GREEN);
                    	
	                    	String cpuinfo="typeperf -sc "+VariableModule.Pcapduration+" \"\\processor(_total)\\% processor time\"";
	                    	ServerParserModule.CommandExecModule(cpuinfo, 5000,6);
	                    	

                	    break;
                        
                    case "4":
                    	
                    	optionDisk.setForeground(Color.GREEN);

	                    	ServerParserModule.DiskParser("fsutil volume diskfree c:",3);
	                    	ServerParserModule.DiskParser("winsat disk -seq -read -drive c",1);
	                    	ServerParserModule.DiskParser("winsat disk -ran -write -drive c",2);
                    	
                	    break;
                	    
                    case "5":
                    	
                    		optionTime.setForeground(Color.GREEN);
                    		ActionModule.ExecWebRequest(2);
                    		//CommonOSModule.SMSwithPlivo("স্বাগতম");

/*                      	  try {
                    	      CommonOSModule.Mail(CommonOSModule.mailhost, CommonOSModule.mailport, CommonOSModule.mailFrom, CommonOSModule.mailpassword, CommonOSModule.mailTo,CommonOSModule.mailsubject, CommonOSModule.mailmessage, CommonOSModule.attachFiles);
                    	      System.out.println("Email sent.");
                    	  } catch (Exception ex) {
                    	      System.out.println("Could not send email.");
                    	      ex.printStackTrace();
                    	  }*/
                      	  
                	    break;
                	    
                } // end of switch
            }

        });
        //setForeground(Color.GREEN);
        setPreferredSize(new Dimension(500, 500));
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        setLocationRelativeTo(null);
    }
    
    public static void main(String[] args) {
    	
/*    	DateFormat dateFormat= new SimpleDateFormat("dd/MM/yyyy");

    	Calendar c = Calendar.getInstance();    
    	c.add(Calendar.DATE, 7);
    	
    	String date = dateFormat.format(c.getTime());
    	Date date1 = null;
		try {
			date1 = dateFormat.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	System.out.println(date1);
    	long unixTime = (long)date1.getTime();
    	System.out.println(unixTime);
    	long unixTime2 = System.currentTimeMillis();
    	System.out.println(unixTime2);*/
    	
    	

/*    	Calendar date = Calendar.getInstance();
    	long t= date.getTimeInMillis();
    	Date afterAddingTenMins=new Date(t + (3 * 60000));
    	long unixTime = (long)afterAddingTenMins.getTime();
    	System.out.println("After Date: "+unixTime);*/
    	
    	//System.out.println("Decimal IP: "+CommonOSModule.IPtolong("192.168.16.234"));
    	//CommonOSModule.SerialKey();
    	//System.out.println(CommonOSModule.RandomOTP());

    	
    	CommonOSModule.StartModule();
    	
    }
	
}

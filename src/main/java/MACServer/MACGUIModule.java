package MACServer;


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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
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

public class MACGUIModule extends JFrame{

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


    public MACGUIModule() {
        super("Server Fault Detection Tools");
    	new JFrame("Server Fault Detection Tools");

    	
        JLabel label2 = new JLabel("<html>********************Choose Your SFD Tool Menu******************<br></html>", SwingConstants.CENTER);

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
        constraints.insets = new Insets(5, 115, 5, 5);
        
        
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

                    		//ServerParserModule.CommandExecModule("systeminfo", 1000,3);
                    	
                    	break;
                    
                    case "2":

                    	optionMemory.setForeground(Color.GREEN);
                    	
        				String memcommand="top -l 1";
        				MACAction.CommandExecModule(memcommand, 5000,3);
                        
                    	break;
                    	
                    case "3":
                    	
                    	optionCPU.setForeground(Color.GREEN);
                    	
        				String cpucommand="top -l 1";
        				MACAction.CommandExecModule(cpucommand, 5000,1);
	                    	

                	    break;
                        
                    case "4":
                    	
                    	optionDisk.setForeground(Color.GREEN);

        				String diskscommand="df -H";
        				MACAction.CommandExecModule(diskscommand, 5000,6);
        				
                	    break;
                	    
                    case "5":
                    	
                    	optionTime.setForeground(Color.GREEN);


                      	  
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
    
	
}

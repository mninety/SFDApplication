package MyGraph;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import CommonPlatform.CommonOSModule;
import MyAction.ActionModule;

public class LineGraphDiskReadModule extends JFrame {
	   
	   public LineGraphDiskReadModule( String applicationTitle , String chartTitle ) {
		      super(applicationTitle);
		      JFreeChart lineChart = ChartFactory.createLineChart(
		         chartTitle,
		         "Hour","Disk Read Speed",
		         MysqlConnectionAction(),
		         PlotOrientation.VERTICAL,
		         true,true,false);
		         
		      ChartPanel chartPanel = new ChartPanel( lineChart );
		      chartPanel.setPreferredSize( new java.awt.Dimension( 560 , 367 ) );
		      setContentPane( chartPanel );
		      setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		   }
	   
	   
		public static DefaultCategoryDataset MysqlConnectionAction()
		{
			DefaultCategoryDataset dataset = new DefaultCategoryDataset( );
		    Connection connection = null;
		    java.sql.Statement stmt = null;
		    
		    try {
		    	connection=CommonOSModule.MysqlConnection();
		    	stmt = connection.createStatement();
		    	
		    	
		        Statement statement = connection.createStatement( );
		        ResultSet resultSet = statement.executeQuery("select ddValue,ddTime from diskdata where ddDayID='"+ActionModule.getDayID()+"' and ddType=1;" );
		        
		        String test=null;
		        String test1=null;
		        int hr=1;
/*		        int i=0;
		        while( resultSet.next( ) ) {
		        	test=resultSet.getString( "mdValue" );
		        	
		           dataset.addValue(Double.parseDouble(test) , "schools" , );
		        i++;
		        }*/
		        while( resultSet.next( ) ) {
		        	test=resultSet.getString( "ddValue" );
		        	test1=resultSet.getString( "ddTime" );
		        	String[] time = test1.split("\\s");
		           dataset.addValue(Double.parseDouble(test) , "Usage" , time[1]);
		        hr++;
		        }
		        
		        
			    stmt.close();
			    connection.close();
			} catch (SQLException e) {
			    throw new IllegalStateException("Cannot connect the database!", e);
			}
			return dataset;
		}

	   public static void LineGraphCreator()
	   {
		   LineGraphDiskReadModule chart = new LineGraphDiskReadModule(
		    	         "Disk Read Speed vs Today's Hour" ,
		    	         "Disk Read Speed vs Hour");

		    	      chart.pack( );
		    	      RefineryUtilities.centerFrameOnScreen( chart );
		    	      chart.setVisible( true );
	   }
	}

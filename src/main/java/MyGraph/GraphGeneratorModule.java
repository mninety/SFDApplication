package MyGraph;

/*public class GraphGeneratorModule {

}*/


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;


public class GraphGeneratorModule extends ApplicationFrame {
	   
	   public GraphGeneratorModule( String title ) {
	      super( title ); 
	      setContentPane(createDemoPanel( ));
	   }
	   
	   public static Connection MysqlConnection()
	   {
		   
		    Connection conn = null;
		    try {
		        // STEP 2: Register JDBC driver
		        Class.forName("com.mysql.jdbc.Driver");

		        // STEP 3: Open a connection
		        //System.out.print("\nConnecting to database...");
		        conn = DriverManager.getConnection(
		                "jdbc:mysql://localhost:3306/serverfault" ,     
		                "root",     
		                "Ninety02#");
		        System.out.println(" Database Connected!\n");
		    }catch(SQLException se) {
		        se.printStackTrace();
		    } catch(Exception e) {
		        e.printStackTrace();
		    }
			return conn;
		      
	   }
	   
	   
		public static DefaultPieDataset MysqlConnectionAction()
		{

		    Connection connection = null;
		    java.sql.Statement stmt = null;
		    DefaultPieDataset dataset = new DefaultPieDataset( );
		    try {
		    	connection=MysqlConnection();
		    	stmt = connection.createStatement();
		    	
		    	
		        Statement statement = connection.createStatement( );
		        ResultSet resultSet = statement.executeQuery("select atServiceID,count(*) from alarmtracker where atDayID='27082017' group by atServiceID;" );
		        
		        String test=null;
		        while( resultSet.next( ) ) {
		        	test=resultSet.getString( "atServiceID" );
		        	if(test.equals("1"))
		        	{
		        		test="Memory";
		        	}
		        	else if(test.equals("2"))
		        	{
		        		test="CPU";
		        	}
		        	else if(test.equals("3"))
		        	{
		        		test="Disk Speed";
		        	}
		        	else if(test.equals("4"))
		        	{
		        		test="Disk Space";
		        	}
		        	
		           dataset.setValue( 
		           test ,
		           Double.parseDouble( resultSet.getString( "count(*)" )));
		        
		        }
		        
		        
			    stmt.close();
			    connection.close();
			} catch (SQLException e) {
			    throw new IllegalStateException("Cannot connect the database!", e);
			}
			return dataset;
		}
		
		
/*	   private static PieDataset createDataset( ) {
	      DefaultPieDataset dataset = new DefaultPieDataset( );
	      dataset.setValue( "IPhone 5s" , new Double( 20 ) );  
	      dataset.setValue( "SamSung Grand" , new Double( 20 ) );   
	      dataset.setValue( "MotoG" , new Double( 40 ) );    
	      dataset.setValue( "Nokia Lumia" , new Double( 10 ) );  
	      return dataset;         
	   }*/
	   
	   private static JFreeChart createChart( PieDataset dataset ) {
	      JFreeChart chart = ChartFactory.createPieChart(      
	         "Today's Server Performace", // chart title 
	         dataset,          // data    
	         true,             // include legend   
	         true, 
	         false);

	      return chart;
	   }
	   
	   public static JPanel createDemoPanel( ) {
	      JFreeChart chart = createChart(MysqlConnectionAction() );  
	      return new ChartPanel( chart ); 
	   }

	   public static void GraphCreator()
	   {
		  GraphGeneratorModule demo = new GraphGeneratorModule( "Mobile Sales" );  
	      demo.setSize( 560 , 367 );    
	      RefineryUtilities.centerFrameOnScreen( demo );    
	      demo.setVisible( true ); 
	   }
	}
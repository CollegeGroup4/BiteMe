package biteme.server;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart; 
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset; 
import org.jfree.data.category.DefaultCategoryDataset; 

public class mainfortesting {
   
   public mainfortesting( String applicationTitle , String chartTitle ) {    
      JFreeChart barChart = ChartFactory.createBarChart(
         chartTitle,           
         "Category",            
         "Score",            
         createDataset(),          
         PlotOrientation.VERTICAL,           
         true, true, false);
         
      //ChartPanel chartPanel = new ChartPanel( barChart );   
      try {
		ChartUtils.saveChartAsPNG(new File("C://Users/talye/Desktop/histogram.png"), barChart, 600, 400);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
      String time = "2021-12-21 12:30:14", str;
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
      DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
      LocalDateTime test = LocalDateTime.parse(time, formatter);
     LocalDateTime test2 = LocalDateTime.parse(str, formatter2);
      System.out.println(test2);
   }



   
   
   private CategoryDataset createDataset( ) {      
     
      final DefaultCategoryDataset dataset = 
      new DefaultCategoryDataset( );  

      dataset.addValue( 1000 , "McDonalds" , "1" );        
         

      dataset.addValue( 5430 , "Rimini" , "2" );        


      dataset.addValue( 1305 , "Greg" , "3" ); 
      
      dataset.addValue( 1020 , "McDo" , "4" );        
      

      dataset.addValue( 7430 , "Rimi" , "Restaurants" );        


      dataset.addValue( 6305 , "Gr" , "Restaurants" );   
             

      return dataset; 
   }
   
   public static void main( String[ ] args ) {
	   mainfortesting a = new mainfortesting("t", "b");
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM");
		LocalDateTime now = LocalDateTime.now();
	   System.out.println(now.format(dtf));
   }
}
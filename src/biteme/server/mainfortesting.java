package biteme.server;

import java.io.File;
import java.io.IOException;

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
   }



   
   
   private CategoryDataset createDataset( ) {      
     
      final DefaultCategoryDataset dataset = 
      new DefaultCategoryDataset( );  

      dataset.addValue( 1000 , "McDonalds" , "Restaurants" );        
         

      dataset.addValue( 5430 , "Rimini" , "Restaurants" );        


      dataset.addValue( 1305 , "Greg" , "Restaurants" ); 
      
      dataset.addValue( 1020 , "McDo" , "Restaurants" );        
      

      dataset.addValue( 7430 , "Rimi" , "Restaurants" );        


      dataset.addValue( 6305 , "Gr" , "Restaurants" );   
             

      return dataset; 
   }
   
   public static void main( String[ ] args ) {
	   mainfortesting a = new mainfortesting("t", "b");
   }
}
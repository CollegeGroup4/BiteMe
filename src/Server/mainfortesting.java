package Server;

import java.io.File;
import java.io.FileNotFoundException;
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

import com.aspose.pdf.Document;
import com.aspose.pdf.Image;
import com.aspose.pdf.Page;
import com.aspose.words.Paragraph;
import com.aspose.words.ReplaceAction;
import com.aspose.words.ReplacingArgs;
import com.aspose.words.Run;


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
      String time = "2021-12-21 12:30:14";
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
      DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
      LocalDateTime test = LocalDateTime.parse(time, formatter);
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
//	   mainfortesting a = new mainfortesting("t", "b");
//		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM");
//		LocalDateTime now = LocalDateTime.now();
//	   System.out.println(now.format(dtf));
	   
	   
	   
	// Instantiate Document Object
	   Document doc = new Document();

	   // Access image files in the folder
	   String imageDir = "C:\\MosheP\\";
	   File file = new File(imageDir);
	   file.mkdir();
	   String[] fileList = file.list();

	   for (String fileName : fileList) {
	   	// Add a page to pages collection of document
	   	Page page = doc.getPages().add();

	   	// Load the source image file to Stream object
	   	java.io.FileInputStream fs = null;
		try {
			fs = new java.io.FileInputStream(imageDir + fileName);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	   	// Set margins so image will fit, etc.
	   	page.getPageInfo().getMargin().setBottom(0);
	   	page.getPageInfo().getMargin().setTop(0);
	   	page.getPageInfo().getMargin().setLeft(0);
	   	page.getPageInfo().getMargin().setRight(0);
	   	page.setCropBox(new com.aspose.pdf.Rectangle(0, 0, 800, 600));

	   	// Create an image object
	   	Image image1 = new Image();

	   	// Add the image into paragraphs collection of the section
	   	page.getParagraphs().add(image1);

	   	// Set the image file stream
	   	image1.setImageStream(fs);
	   }

	   // Save resultant PDF file
	   doc.save("document.pdf");
	   
	   
	   
   }
   
   
}
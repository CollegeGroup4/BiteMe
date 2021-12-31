import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;

import javafx.scene.image.Image;

public class image {
	// D:\\projectImages\\794037.png
	public static void main(String[] args) {
		int width = 963;    //width of the image
        int height = 640;   //height of the image
        File f = null;
        
        //read image file
        try{
            f = new File("D:\\projectImages\\794037.png"); //image file path
            //image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
          //  image = ImageIO.read(f);
            ImageInputStream k = ImageIO.createImageInputStream(f);
            InputStream is = new BufferedInputStream(new FileInputStream("source.gif"));
            BufferedImage image = ImageIO.read(is);
            Image a = (Image) image;
        }catch(IOException e){
            System.out.println("Error: "+e);
        }
        
        //write image
        try{
            f = new File("D:\\Image\\Output.jpg");  //output file path
            ImageIO.write(image, "jpg", f);
            System.out.println("Writing complete.");
        }catch(IOException e){
            System.out.println("Error: "+e);
        }
	}
}

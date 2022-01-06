package ClassForTests;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.aspose.pdf.Image;

import Server.QueryConsts;

public class ImageManager implements iImageManager {
	public Image makeNSetImage(java.io.FileInputStream fs) {
		// Create an image object
		Image image1 = new Image();
		image1.setImageStream(fs);
		return image1;
	}

}

package ClassForTests;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class FileManager implements iFileManager{

	public String[] getFileListFromDir(String imageDir) {
		File file = new File(imageDir);
		file.mkdir();
		String[] fileList = file.list();
		return fileList;
	}
	
}

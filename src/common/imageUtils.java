package common;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class imageUtils {

	public static void receiver(MyPhoto myphoto, String saveImagePath) {
		File dir;
		FileOutputStream f;
		BufferedOutputStream o = null;
		String[] path;
		StringBuilder temp = new StringBuilder();
		try {
			path = saveImagePath.split("\\\\");
			for (int i = 0; i < path.length; i++) {
				for (int j = 0; j <= i; j++)
					temp.append(path[j] + "\\");
				dir = new File(temp.toString());
				dir.mkdir();
				temp = new StringBuilder();
			}

			f = new FileOutputStream(new File(saveImagePath + myphoto.getFileName()));
			o = new BufferedOutputStream(f);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			o.write(myphoto.getMybytearray(), 0, myphoto.getSize());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void sender(MyPhoto myphoto, String savedImagePath) {
//		String[] path;
//		int index;
		try {
//			path = myphoto.getFileName().split("\\");
//			index = path.length-1;
			File newFile = new File(myphoto.getFileName());
			byte[] mybytearray = new byte[(int) newFile.length()];
			FileInputStream fis = new FileInputStream(newFile);
			BufferedInputStream bis = new BufferedInputStream(fis);
			myphoto.initArray(mybytearray.length);
			myphoto.setSize(mybytearray.length);
			bis.read(myphoto.getMybytearray(), 0, mybytearray.length);
		} catch (Exception e) {
			System.out.println("Error send (Files)msg) to Server");
		}
	}

}

package common;

import java.io.Serializable;

public class MyPhoto implements Serializable {

	private String Description = null;
	private String fileName = null;
	private int size = 0;
	public byte[] myByteArray;

	public void initArray(int size) {
		myByteArray = new byte[size];
	}

	public MyPhoto(String fileName) {
		this.fileName = fileName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public byte[] getMybytearray() {
		return myByteArray;
	}

	public byte getMybytearray(int i) {
		return myByteArray[i];
	}

	public void setMybytearray(byte[] mybytearray) {

		for (int i = 0; i < mybytearray.length; i++)
			this.myByteArray[i] = mybytearray[i];
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}
}

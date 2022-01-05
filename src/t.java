import java.io.File;
import java.util.Arrays;

import Server.QueryConsts;

public class t {
	public static void main(String[] args) {
		String[] split = "C:\\Users\\talye\\git\\BiteMeFinal\\Images\\pizza.jpg".split("\\.");
		String sufix = split[split.length-1];
		System.out.println(sufix);
	}
}

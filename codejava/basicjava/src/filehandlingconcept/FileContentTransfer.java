package filehandlingconcept;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileContentTransfer {

	public static void main(String[] args)
			throws IOException
	{
		FileInputStream fis = null;
		FileOutputStream fos = null;

		try {
			fis = new FileInputStream("G:\\Code\\BooleanCheck.java");
			fos = new FileOutputStream("G:\\temp\\New Text Document.java");
			int c;
			while ((c=fis.read())!= -1) {
				fos.write(c);
			}
		}
		finally {
			System.out.println("copied the file successfully");
			fis.close();
			fos.close();
		}
	}
}
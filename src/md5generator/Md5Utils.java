package md5generator;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;

/**
 * Class to work with MD5 hashes
 * 
 * @author pedro.toledano
 */
public class Md5Utils
{
	private File file = null;
	
	/**
	 * Constructor.
	 * 
	 * @param filePath Path of a file
	 */
	public Md5Utils(String filePath)
	{
		//this.file = new File(filePath);
		setFile(filePath);
	}
	
	/**
	 * Constructor.
	 * 
	 * @param file a File object
	 */
	public Md5Utils(File file)
	{
		//this.file = file;
		setFile(file);
	}
	
	/**
	 * Sets the File object copying another given File object.
	 * 
	 * @param file	a File object
	 */
	public void setFile(File file)
	{
		this.file = file;
	}
	
	/**
	 * Sets the File object by means of a file path.
	 * 
	 * @param filePath	path of the file
	 */
	public void setFile(String filePath)
	{
		this.file = new File(filePath);
	}
	
	/**
	 * Gets the md5 checksum from the file defined by the {@link #setFile(File) setFile(File)} method,
	 * the {@link #setFile(String, String) setFile(String, String)} method or by the constructor.
	 * 
	 * @return md5 checksum of the source file
	 */
	public String md5FromFile()
	{
		//file = new File("C:\\Users\\pedro.toledano\\Desktop\\facts\\MainDocument.xml");
		String md5Checksum = null;

		FileInputStream fileInputStream = null;

		try
		{
			// Opens the file to read
			fileInputStream = new FileInputStream(file);

			// Calculates the MD5 checksum
			md5Checksum = DigestUtils.md5Hex( IOUtils.toByteArray(fileInputStream) );

			// Closes file
			fileInputStream.close();

		}
		catch (IOException e)
		{
			e.printStackTrace();            
		}

		return md5Checksum;
	}
}
package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertyUtil 
{
	public static String getPropertyString(String filename) 
	{
//		String constring=null;
		String url = null;
		try {
			Properties prop = new Properties();
			FileInputStream fis = new FileInputStream(filename);
			prop.load(fis);
			url = prop.getProperty("url")+"/"+prop.getProperty("database")+"?"+"user="+prop.getProperty("user")+"&password="+prop.getProperty("password");
		}
		catch(IOException ex)
		{
			ex.printStackTrace();
		}
		
//		System.out.print(url);
		
		return url;
	}
}
package tests;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class BaseTest {

	public static String giveProperty(String property) throws IOException {

		String propPath = "C:\\Users\\zikaz\\OneDrive\\Desktop\\projects\\PaypalApi\\src\\test\\java\\Keys.properties";
		FileReader reader = new FileReader(propPath);
		Properties prop = new Properties();
		prop.load(reader);
		return prop.getProperty(property);
	}

}

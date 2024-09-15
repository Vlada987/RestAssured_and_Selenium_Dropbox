package frontend;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class DriverSetup {

	public static FirefoxOptions getOptions() {

		FirefoxOptions option = new FirefoxOptions();
		option.addArguments("-private");
		option.addArguments("-headless");
		return option;
	}

	public static WebDriver getDriver() { 

		WebDriver driver = new FirefoxDriver(getOptions());
		System.setProperty("webdriver.gecko.driver",
				"C:\\Users\\zikaz\\OneDrive\\Desktop\\projects\\PaypalApi\\geckodriver.exe");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(45));
		return driver;
	}

}

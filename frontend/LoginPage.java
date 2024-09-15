package frontend;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {

	static WebDriver driver = DriverSetup.getDriver();
	static WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(25));

	public static String get_code(String client_id, String red_url, String email, String password) {

		String url = reformat_url(client_id, red_url);
		openPage(url);
		login(email, password);
		wait.until(ExpectedConditions.urlContains("google.com/?code="));

		return parseCode(driver.getCurrentUrl());

	}

	public static void login(String email, String password) {

		try {
			wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.xpath("//iframe[@id='ccpa-iframe']")));
			driver.findElement(By.xpath("//button[@aria-label='Close']")).click();
			driver.switchTo().defaultContent();
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(
					By.xpath("//iframe[@title='Дијалог Пријављивање помоћу Google-а']")));
			driver.findElement(By.xpath("//div[@id='close']")).click();
			driver.switchTo().defaultContent();
		} catch (Exception e) {
			e.printStackTrace();
		}

		driver.findElement(By.xpath("//input[@type='email']")).sendKeys(email);
		driver.findElement(By.xpath("//button[@type='submit']")).click();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='login_password']")))
				.sendKeys(password);
		driver.findElement(By.xpath("//button[@type='submit']")).click();

	}

	public static String parseCode(String url) {

		String[] parsedUrl = url.split("=");
		return parsedUrl[1];
	}

	public static String reformat_url(String client_id, String red_url) {

		String url = "https://www.dropbox.com/oauth2/authorize?client_id=" + client_id + "&redirect_uri=" + red_url
				+ "&response_type=code";
		return url;
	}

	public static void openPage(String url) {

		driver.get(url);
	}

}

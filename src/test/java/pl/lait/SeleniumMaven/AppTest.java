package pl.lait.SeleniumMaven;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.concurrent.TimeUnit;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runners.MethodSorters;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;


public class AppTest {

	static FirefoxDriver driver;
	String folder = "allScreens";

	
	public static FirefoxDriver getDriver() {

		if (driver == null) {
			System.out.println("obiekt DRIVERa == NULL - wykonuję new FirefoxDriver()");
			driver = new FirefoxDriver();
			driver.get("http://newtours.demoaut.com");
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			driver.manage().window().maximize();
			return driver;
		} else {
			System.out.println("obiekt DRIVERa != NULL - zwracam obiekt");
			return driver;
		}

	}


	
	// -------------------------------------------------------------------------
	// BRAK ADNOTACJI @TEST BO TO JEST reużywalna MEGTODA do innych testów
	public void loginAs(String user, String pass) {
		if (isElementPresent(By.linkText("SIGN-OFF"))) {
			
		} else {
			
			linkClick("SIGN-ON");
			driver.findElement(By.name("userName")).sendKeys(user);
			driver.findElement(By.name("password")).sendKeys(pass);
			driver.findElement(By.name("login")).click();
			
		}
	}
	
	//alternatywa dla loginAs z użyciem operatora negacji "!"
	public void loginAs2(String user, String pass) {
		if (!isElementPresent(By.linkText("SIGN-OFF"))) {
			
			linkClick("SIGN-ON");
			driver.findElement(By.name("userName")).sendKeys(user);
			driver.findElement(By.name("password")).sendKeys(pass);
			driver.findElement(By.name("login")).click();
			
		} 
	}

	

	public boolean isElementPresent(By locatorKey) {
		try {
			driver.findElement(locatorKey);
			return true;
		} catch (org.openqa.selenium.NoSuchElementException e) {
			return false;
		}
	}

	public void linkClick(String linkText) {
		try {
			driver.findElement(By.linkText(linkText)).click();
		} catch (Exception e) {
			System.out.println("Wystąpił wyjątek - nie znaleziono elementu: " + linkText);
			System.out.print("Wykonuję zrzut ekranu: ");
			printScr();
			Assert.assertFalse("Nie znaleziono elementu: " + linkText, true);
		}
	}

	public void printScr() {

		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		Long milis = timestamp.getTime();
		File srcFile = driver.getScreenshotAs(OutputType.FILE);
		System.out.println("Generating screenshot: " + milis);
		try {
			FileUtils.copyFile(srcFile, new File("target\\" + folder + "\\screenshot-" + milis + ".png"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	

}

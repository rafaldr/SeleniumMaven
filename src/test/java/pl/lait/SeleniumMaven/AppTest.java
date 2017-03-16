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

	@BeforeClass
	public static void openBrowser() {
		driver = new FirefoxDriver();
		driver.get("http://newtours.demoaut.com");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

	// http://newtours.demoaut.com
	//@Ignore
	@Test
	public void test2_mainMenuTest() {
		System.out.println("2");
		// driver.get("http://newtours.demoaut.com");
		linkClick("SIGN-ON");
		linkClick("SUPPORT");
		System.out.println("klikam w CONTACT");
		linkClick("CONTACT");
		printScr();
		linkClick("REGISTER");
	}

	//@Ignore
	@Test
	public void test1_register() {
		System.out.println("1");
		folder = new Object() {
		}.getClass().getEnclosingMethod().getName();

		linkClick("REGISTER");
		driver.findElement(By.id("email")).clear();
		driver.findElement(By.id("email")).sendKeys("adam_qwe123");
		driver.findElement(By.name("password")).sendKeys("qwe123");
		driver.findElement(By.name("confirmPassword")).sendKeys("qwe123");
		printScr();
		driver.findElement(By.name("register")).click();
		printScr();
		linkClick("SIGN-OFF");
	}

	@Test
	public void test3_reservation() {
		System.out.println("3");
		folder = new Object() {		}.getClass().getEnclosingMethod().getName();
		// sprawdzi czy zalogowany, jesli nie - zaloguje, jesli tak - idzie dalej
		loginAs("adam_qwe123", "qwe123"); 
		// tu będzie faktyczny test:
		driver.findElement(By.xpath("/html/body/div/table/tbody/tr/td[2]/"
				+ "table/tbody/tr[4]/td/table/tbody/tr/td[2]/"
				+ "table/tbody/tr[5]/td/form/table/tbody/tr[2]"
				+ "/td[2]/b/font/input[2]")).click();
		/*
		<select name="passCount">
        <option value="1">1 </option>
        <option value="2">2 </option>
        <option value="3">3 </option>
        <option value="4">4 </option>
      	</select>
      */
		Select passengers = new Select(driver.findElement(By.name("passCount")));
		passengers.selectByVisibleText("2");
		printScr();
		
		
	}
//-------------------------------------------------------------------------
	// BRAK ADNOTACJI @TEST BO TO JEST reużywalna MEGTODA do innych testów
	public void loginAs(String user, String pass) {
		if (isElementPresent(By.linkText("SIGN-OFF"))) {
			System.out.println("jestem zalogowany, testuję dalej");
		} else {
			System.out.println("NIE jestem zalogowany, loguje się");
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

	@AfterClass
	public static void closeBrowser() {
		driver.close();
		driver.quit();
	}

}

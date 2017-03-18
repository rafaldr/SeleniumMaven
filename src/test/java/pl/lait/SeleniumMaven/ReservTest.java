package pl.lait.SeleniumMaven;

import org.junit.*;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ReservTest extends AppTest{


	static FirefoxDriver driver;
	
	@BeforeClass
	public static void beforeClass(){
		System.out.println("--Before CLASS");
	}

	@Before
	public void before(){
		System.out.println("---Before");
		driver = getDriver();
	}
	
	// http://newtours.demoaut.com
	// @Ignore
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
	
	// @Ignore
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
			folder = new Object() {
			}.getClass().getEnclosingMethod().getName();
			// sprawdzi czy zalogowany, jesli nie - zaloguje, jesli tak - idzie
			// dalej
			loginAs("adam_qwe123", "qwe123");
			// tu będzie faktyczny test:
			driver.findElement(
					By.xpath("/html/body/div/table/tbody/tr/td[2]/" + "table/tbody/tr[4]/td/table/tbody/tr/td[2]/"
							+ "table/tbody/tr[5]/td/form/table/tbody/tr[2]" + "/td[2]/b/font/input[2]"))
					.click();
			/*
			 * <select name="passCount"> <option value="1">1 </option> <option
			 * value="2">2 </option> <option value="3">3 </option> <option
			 * value="4">4 </option> </select>
			 */
			Select passengers = new Select(driver.findElement(By.name("passCount")));
			passengers.selectByVisibleText("2");
			printScr();

		}

	

	@After
	public void tearDown(){
		System.out.println("driver.close, driver.quit");
		System.out.println(println("aa", "bb"));		
		driver.close();
		driver.quit();
		AppTest.driver = null;
	}
	
	public String println(String a, String b){
		return a+b;
	}
	
}

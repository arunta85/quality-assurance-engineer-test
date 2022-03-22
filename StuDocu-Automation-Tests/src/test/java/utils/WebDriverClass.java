package utils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

public class WebDriverClass {
	// initialize variables
	private static WebDriverClass WebDriverClassInstance = null;
	private WebDriver driver = null;
	private SoftAssert softassert = new SoftAssert();

	/**
	 * Constructor of the class. It sets the Chromdriver and Config.properties file
	 * path.
	 * 
	 */
	private WebDriverClass() {
		// Find the Chromdriver.exe path
		String chromedriverpath = System.getProperty("user.dir") + "\\src\\test\\resources\\chromedriver.exe";
		FileReader reader = null;
		// Creates file reader object with config.properties path
		try {
			reader = new FileReader(System.getProperty("user.dir") + "\\src\\test\\resources\\config.properties");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Properties p = new Properties();
		try {
			p.load(reader);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.setProperty("webdriver.chrome.driver", chromedriverpath);
		// Creating an object of ChromeDriver
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		// Gets the URL from config.properties and open the url
		String url = p.getProperty("url");
		driver.get(url);
	}

	/**
	 * This method calls constructor and creates singleton class Singleton class is
	 * a class that can have only one object (an instance of the class) at a time
	 * 
	 * @return WebDriverClass instance
	 */
	public static WebDriverClass getWebDriverInstance() {
		if (WebDriverClassInstance == null)
			WebDriverClassInstance = new WebDriverClass();
		return WebDriverClassInstance;

	}

	/**
	 * returns WebDriver instance
	 */
	public WebDriver getWebDriver() {
		return driver;
	}
	
	/**
	 * @param xpath
	 * @return webelement corresponding to the xpath
	 */
	public WebElement getWebElement(String xpath) {
		WebElement webElement = driver.findElement(By.xpath(xpath));
		return webElement;
	}
	
	/** This method gets the text corresponding to xpath
	 * @param xpath 
	 * @return text
	 */
	public String getTextFromWebElement(String xpath) {
		String text = driver.findElement(By.xpath(xpath)).getText();
		return text;
	}
	/**
	 * Gets the text corresponding to the xpath and verify with the expectedText
	 * @param xpath
	 * @param expectedText
	 * Assert the actual and expected text
	 */
	public void verifyTextIntheWebElement(String xpath, String expectedText) {
		String actual = driver.findElement(By.xpath(xpath)).getText();
		softassert.assertEquals(actual, expectedText);
	}
	
	/**
	 * Invoke sendKeys method for the id and write the input text in the element
	 * @param id
	 * @param input
	 */
	public void sendKeysOnID(By id, String input) {
		driver.findElement(id).clear();
		driver.findElement(id).sendKeys(input);
	}
	/**
	 * Click on the given xpath webelement
	 * @param xpath
	 */

	public void clickOnWebElement(String xpath) {
		driver.findElement(By.xpath(xpath)).click();
	}

	/**
	 * Getter method to get the SoftAssert instance
	 * @return
	 */
	public SoftAssert getSoftAssert() {
		return softassert;
	}
	/**
	 * Invokes hardAssert method AssertTrue with the user friend message
	 * @param result
	 * @param message
	 */
	public void assertTrue(boolean result, String message) {
		System.out.println(message);
		Assert.assertTrue(result);
	}

}

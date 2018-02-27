package pages;

import static org.testng.Assert.fail;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;



public class LoginPage {

	WebDriver driver = null;
	WebDriverWait exwait = null;

	protected URL url;



	public WebDriver launch_Browser(String browser)
	{	


		if(browser.equals("Firefox"))
		{
			System.setProperty("webdriver.gecko.driver", "driver//geckodriver.exe");
			driver = new FirefoxDriver();
		}
		else if(browser.equals("Chrome"))
		{
			System.setProperty("webdriver.chrome.driver", "driver//chromedriver.exe");
			driver = new ChromeDriver();	
		}
		else if(browser.equals("IE"))
		{
			System.setProperty("webdriver.ie.driver", "driver//IEDriverServer.exe");
			driver = new InternetExplorerDriver();	
		}		

		exwait = new WebDriverWait(driver, 40);
		PageFactory.initElements(driver, this);

		return driver;
	}


	public HomePage login(WebDriver driver, Properties prop) throws Exception
	{
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		driver.get(prop.getProperty("URL"));
		driver.manage().window().maximize();
		Thread.sleep(2000);
		driver.switchTo().frame(0);
		exwait.until(ExpectedConditions.visibilityOf(custID)).click();
		custID.sendKeys(prop.getProperty("CustomerID"));
		exwait.until(ExpectedConditions.visibilityOf(continueBtn)).click();
		Thread.sleep(4000);
		exwait.until(ExpectedConditions.visibilityOf(password)).sendKeys(prop.getProperty("Password"));
		exwait.until(ExpectedConditions.visibilityOf(imgcheckBox)).click();
		exwait.until(ExpectedConditions.visibilityOf(loginBtn)).click();
		driver.switchTo().defaultContent();
		
		return new HomePage(driver);	
	}
	
	public void close(WebDriver driver) throws InterruptedException {
		Thread.sleep(2000);
		driver.switchTo().defaultContent();
		Thread.sleep(2000);
		driver.switchTo().frame(logoutframe);
		logout.click();
		Thread.sleep(5000);
		driver.close();
	}
	
	
	
	@FindBy(xpath = "//input[@class='input_password']")
	public WebElement custID;

	@FindBy(xpath = "(//span[@class='pwd_field']/input)[2]")
	public WebElement password;

	@FindBy(xpath = "//img[@alt='continue']")
	public WebElement continueBtn;

	@FindBy(xpath = "//input[@id='chkrsastu']")
	public WebElement imgcheckBox;
	
	@FindBy(xpath = "//img[@alt='Login']")
	public WebElement loginBtn;
	
	@FindBy(xpath = "//img[@alt='Log Out']")
	public WebElement logout;
	
	@FindBy(xpath = "/html/frameset/frame")
	public WebElement logoutframe;
	

}

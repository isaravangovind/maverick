package tests;

import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import pages.FundTransferPage;
import pages.HomePage;
import pages.LoginPage;
import utils.ExcelUtils;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.BeforeClass;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;

public class HomeTest {

	WebDriver driver = null;
	private LoginPage login;
	ExcelUtils excelread;
	private FundTransferPage hdfcpage;
	private HomePage homepage;
	private Properties properties;
	private final String propertyFilePath= "config//Config.properties";
	public ExtentReports extent;
	public ExtentTest test;

	@BeforeSuite
	public void deletefiles() throws IOException {

		FileUtils.deleteDirectory(new File("Screenshots"));
		extent = new ExtentReports(System.getProperty("user.dir") + "/test-output/HDFCWebsiteTestReport.html", true);
		extent.loadConfig(new File("config//extent.xml"));

		extent.addSystemInfo("User Name", "Govind");
		extent.loadConfig(new File(System.getProperty("user.dir")+"\\extent-config.xml"));
	}


	@BeforeTest
	public void beforeTest() {

		excelread = new ExcelUtils();
		excelread.setExcel("DataSheet");

	}

	@BeforeClass
	public void beforeClass() {

		BufferedReader reader;
		try {

			reader = new BufferedReader(new FileReader(propertyFilePath));
			properties = new Properties();
			try {
				properties.load(reader);
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException("Configuration.properties not found at " + propertyFilePath);
		}		



	}


	@DataProvider
	public Object[][] Balance() {

		try {

			return excelread.getTableArray("Balance");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@BeforeMethod
	public void beforeMethod() {

		try {
			login = new LoginPage();
			driver = login.launch_Browser(properties.getProperty("Browser"));
			homepage = login.login(driver, properties);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}



	@Test(dataProvider = "Balance")
	public void checkBalance(String TestCaseName, String balance) throws InterruptedException {
		
		Thread.sleep(5000);
		homepage.balanceCheck(driver, balance);
		
	}





	@AfterMethod
	public void afterMethod(ITestResult result) throws InterruptedException {


		if(ITestResult.FAILURE==result.getStatus())
		{
			try 
			{
				TakesScreenshot ts=(TakesScreenshot)driver;
				File source=ts.getScreenshotAs(OutputType.FILE);
				FileUtils.copyFile(source, new File("./Screenshots/"+result.getName()+".png"));
				


			} 
			catch (Exception e)
			{

				System.out.println("Exception while taking screenshot "+e.getMessage());
			} 



		}
		
		try {
			
			login.close(driver);
			
			
		} catch (Exception e) {

			e.printStackTrace();
		}

	}

}

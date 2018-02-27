package tests;

import java.awt.AWTException;
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
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

import pages.FundTransferPage;
import pages.HomePage;
import pages.LoginPage;
import utils.ExcelUtils;

public class FundTransferTest {
	WebDriver driver = null;
	private LoginPage login;
	ExcelUtils excelread;
	private FundTransferPage hdfcpage;
	private HomePage homepage;
	private FundTransferPage fundtransfer;
	private Properties properties;
	private final String propertyFilePath= "config//Config.properties";
	ExtentReports extent;
    ExtentTest test;
	
	@BeforeSuite
	public void deletefiles() throws IOException {
		
		FileUtils.deleteDirectory(new File("Screenshots"));
	}
	
	@BeforeTest
	public void beforeTest() {
		extent = new ExtentReports(System.getProperty("user.dir") + "/test-output/HDFCWebsiteTestReport.html", true);
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
	public Object[][] CheckBeneficiary() {
		
		try {
			return excelread.getTableArray("CheckBeneficiary");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@DataProvider
	public Object[][] AddBeneficiary() {
		
		try {
			return excelread.getTableArray("AddBeneficiary");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@BeforeMethod
	public void beforeMethod() {

		try {
			test = extent.startTest("HDFC WebSite Testing");
			login = new LoginPage();
			driver = login.launch_Browser(properties.getProperty("Browser"));
			homepage = login.login(driver, properties);
			fundtransfer = homepage.navigateTo(driver, "Fund Transfer");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

//	@Test(dataProvider = "AddBeneficiary")
	public void AddBeneficiary(String TestCaseName, String AccNo, String NickName, String Email, String Alert) throws InterruptedException {
		Thread.sleep(5000);
		fundtransfer.AddBene(driver, AccNo, NickName, Email, Alert);
	}
	

	@Test(dataProvider = "CheckBeneficiary")
	public void CheckBeneficiary(String TestCaseName, String name) throws InterruptedException, AWTException {
		Thread.sleep(5000);
		fundtransfer.CheckBene(driver, name);
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

	@AfterClass
	public void afterClass() {
	}
}

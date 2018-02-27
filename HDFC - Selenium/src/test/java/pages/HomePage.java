package pages;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.expectThrows;

import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import tests.HomeTest;

public class HomePage extends HomeTest{
	
	ExtentTest test;

	WebDriverWait exwait;

	public HomePage(WebDriver driver) {
		exwait = new WebDriverWait(driver, 30);
		PageFactory.initElements(new AjaxElementLocatorFactory(driver, 30), this);
	}


	@SuppressWarnings("unchecked")
	public <T> T navigateTo(WebDriver driver, String Menu_item) throws InterruptedException {
		
		driver.switchTo().frame(logoutframe);

		if (Menu_item.equalsIgnoreCase("Fund Transfer"))
		{
			
			fundTransfer.click();
			Thread.sleep(5000);
			driver.switchTo().defaultContent();
			return ((T) new FundTransferPage(driver));

		} 


		return null;

	}

	public void balanceCheck(WebDriver driver, String balance) throws InterruptedException {
		try {
			Thread.sleep(5000);
			driver.switchTo().frame(framemainpart);

			String actualbal = exwait.until(ExpectedConditions.visibilityOf(balanceTxt)).getText().substring(29).replaceAll(",", "");	
			Assert.assertEquals(actualbal, balance, "Balance Mismatch");
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}


	}
	

	@FindBy(xpath = "//a[@title='Funds Transfer']/img")
	WebElement fundTransfer;

	@FindBy(xpath = "//span[@id='SavingTotalSummary']")
	WebElement balanceTxt;

	@FindBy(xpath = "/html/frameset/frameset/frameset/frame[1]")
	WebElement framemainpart;
	
	@FindBy(xpath = "/html/frameset/frame")
	public WebElement logoutframe;



}

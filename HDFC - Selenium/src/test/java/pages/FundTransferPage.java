package pages;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class FundTransferPage {


	WebDriver driver;
	WebDriverWait exwait;
	Robot robot;
	public FundTransferPage(WebDriver driver) {

		try {
			this.driver = driver;
			exwait = new WebDriverWait(driver, 40);
			PageFactory.initElements(new AjaxElementLocatorFactory(driver, 30), this);

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	public void AddBene(WebDriver driver, String AccNumber, String NickName, String Email, String Alert) throws InterruptedException {

		driver.switchTo().frame(sidemenuFrame);
		request.click();
		addABene.click();
		driver.switchTo().defaultContent();
		driver.switchTo().frame(mainFrame);
		Thread.sleep(3000);
		exwait.until(ExpectedConditions.visibilityOf(goBtn));
		goBtn.click();
		Thread.sleep(3000);
		exwait.until(ExpectedConditions.visibilityOf(beneAccNo));
		beneAccNo.sendKeys(AccNumber);
		reTypeAccNo.sendKeys(AccNumber);
		nickName.sendKeys(NickName);
		email.sendKeys(Email);
		addbtn.click();
		Thread.sleep(3000);
		Alert al = driver.switchTo().alert();
		Assert.assertEquals(al.getText(), Alert, "Alert message mismatch");
		al.dismiss();
		
	}


	public void CheckBene(WebDriver driver, String name) throws InterruptedException, AWTException {


		driver.switchTo().frame(sidemenuFrame);
		enquireLink.click();
		listofBene.click();
		driver.switchTo().defaultContent();
		driver.switchTo().frame(mainFrame);
		Thread.sleep(5000);
		viewBtn.click();
		Thread.sleep(5000);
		scroll();






		List<WebElement> lst = driver.findElements(By.xpath("//table[@class='datatable']//tr/td[3]"));

		List<String> strings = new ArrayList<String>();

		for(WebElement e : lst){
			strings.add(e.getText());
		}

		Assert.assertEquals(strings.contains(name), true, "The provided Beneficiary is Not Present");
	}

	public void scroll() throws AWTException {

		robot = new Robot();
		robot.keyPress(KeyEvent.VK_PAGE_DOWN);
		robot.keyRelease(KeyEvent.VK_PAGE_DOWN);

	}


	@FindBy(xpath = "/html/frameset/frameset/frame")
	public WebElement sidemenuFrame;

	@FindBy(xpath = "//a/strong[text()='Enquire']")
	public WebElement enquireLink;

	@FindBy(xpath = "//a/span[contains(text(),'List of Bene')]")
	public WebElement listofBene;

	@FindBy(xpath = "/html/frameset/frameset/frameset/frame[1]")
	public WebElement mainFrame;

	@FindBy(xpath = "(//table[@class='selectTPT']//tr[1]/td/table)[1]//tr[3]//a")
	public WebElement viewBtn;

	@FindBy(xpath = "//a/strong[text()='Request']")
	public WebElement request;

	@FindBy(xpath = "//a/span[contains(text(),'Add a Bene')]")
	public WebElement addABene;

	@FindBy(xpath = "(//table[@class='selectTPT']//tr[1]/td/table)[1]//tr[3]//a/img")
	public WebElement goBtn;

	@FindBy(xpath = "//input[@name='fldAcctNo']")
	public WebElement beneAccNo;

	@FindBy(xpath = "//input[@name='fldAcctNo2']")
	public WebElement reTypeAccNo;

	@FindBy(xpath = "//input[@name='fldNamBenef']")
	public WebElement nickName;
	
	@FindBy(xpath = "//input[@name='fldEmail']")
	public WebElement email;
	
	@FindBy(xpath = "//img[@alt='Add']")
	public WebElement addbtn;

	
	
	

}

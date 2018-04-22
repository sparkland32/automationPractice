package com.automation.dependencies;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class BaseWebDriver {
	
	public WebDriver driver;
	
	public WebDriver GetDriver(Browser browserType) throws IOException
	{
		String ffMacDriverLocation = PropertiesFileManager.GetProperty("mac_firefox_driver_location");
		String ffWinDriverLocation = PropertiesFileManager.GetProperty("win_firefox_driver_location");
		
		if (browserType.equals(Browser.Chrome))
		{
			this.driver = new ChromeDriver();
		}
		else if (browserType.equals(Browser.Firefox))
		{
			if (System.getProperty("os.name").startsWith("Windows"))
				System.setProperty("webdriver.gecko.driver", ffWinDriverLocation);
			else
				System.setProperty("webdriver.gecko.driver", ffMacDriverLocation);
			this.driver = new FirefoxDriver();
		}	
		
		return this.driver;
	}
	
	public static void MouseOverClick(WebDriver driver, WebElement element) {
		Actions action = new Actions(driver);
		action.moveToElement(element).click().build().perform();
	}
	
	public static void MouseOverClickWithCoords(WebDriver driver, WebElement element, int centreWidth, int centreHeight) {
		Actions action = new Actions(driver);
		action.moveToElement(element, centreHeight, centreWidth).perform();
		action.click(element).perform();
	}	
	
    public String GetCurrentUrl() {
    		return this.driver.getCurrentUrl();
    }	
	
	public void SetUrl(String url) {
		this.driver.navigate().to(url);
	}
	
	public static void ScrollToElement(WebDriver driver, WebElement element) {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
	}
	
	public void WaitUntilPageReady(WebDriverWait wait) {
		wait.until(driver -> ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete"));
	}
	
	public byte[] PerformScreenCapture(String scenario) {
		try {
			TakesScreenshot screenShotFromDriver = (TakesScreenshot)driver;
			File sourceImage = screenShotFromDriver.getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(sourceImage, new File(PropertiesFileManager.GetProperty("screenshot_output_directory") + scenario + ".png"));
			return screenShotFromDriver.getScreenshotAs(OutputType.BYTES);
		}
		catch (IOException e) {
			System.out.println("Error: Failed to save screenshot");
		}
		
		return null;
	}
	
	public void CloseDriver() {
		this.driver.quit();
	}
}

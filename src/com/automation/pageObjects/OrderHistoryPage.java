package com.automation.pageObjects;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.automation.dependencies.BaseWebDriver;

public class OrderHistoryPage extends BasePage {

	public static String pageUrl = GetSiteUrl() + "/index.php?controller=history";
	
    @FindBy(id = "order-list")
    public WebElement tableOfOrders;
    
    @FindBy(className = "info-order")
    public WebElement orderInformationBox;
    
    @FindBy(id = "submitReorder")
    public WebElement submitReorder;
    
    @FindBy(name = "msgText")
    public WebElement messageText;
    
    @FindBy(name = "submitMessage")
    public List<WebElement> submitMessageButton;
    
    @FindBy(className = "alert-success")
    public WebElement messageAlert;
    
    @FindBy(className = "detail_step_by_step")
    public List<WebElement> stepByStepTables;
    
    @FindBy(id = "order-detail-content")
    public WebElement orderDetails;
    
    public OrderHistoryPage(WebDriver driver) {
    		PageFactory.initElements(driver, this);
    }  
    
    public void SelectOrderFromTableByDateAndTime(WebDriver driver, String date, String time) {
    		List<WebElement> tableTr = tableOfOrders.findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
    		
    		String[] dateDataValues = date.split("/");
    		String[] timeDataValues = time.split(":");
    		
    		String dateTimeDataValue = "";
    		
    		dateTimeDataValue += dateDataValues[2]; // Set year
    		dateTimeDataValue += dateDataValues[0]; // Set month
    		dateTimeDataValue += dateDataValues[1]; // Set day
    		
    		for (String value : timeDataValues) {
    			dateTimeDataValue += value;
    		}
    		
    		for (WebElement element : tableTr) {
    			WebElement td = element.findElement(By.className("history_date"));
    			if (td.getText().equals(date) && td.getAttribute("data-value").equals(dateTimeDataValue)) {
    				element.findElement(By.className("color-myaccount")).click();
    			}
    		}
    }
    
    public void AddAMessage(WebDriver driver, String message) throws InterruptedException {
    		BaseWebDriver.ScrollToElement(driver, messageText);
    		messageText.sendKeys(message);
    		for (WebElement element : submitMessageButton) {
    			if (element.getTagName().equals("button")) {
    	    			BaseWebDriver.ScrollToElement(driver, element);
    	    			element.click();
    			}
    		}
    }
    
    public boolean ConfirmMessagePresentForDate(WebDriver driver, String message, String date) {
		Format dateFormatter;
		String useDate = "";
		
		WebElement messageTable = stepByStepTables.get(1);
    	
    		if (date.equals("today")) {
    			dateFormatter = new SimpleDateFormat("MM/dd/yyyy");
    			useDate = dateFormatter.format(new Date());
    		}
    		
    		List<WebElement> tableTr = messageTable.findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
    		
    		for (WebElement element : tableTr) {
    			List<WebElement> innerElements = element.findElements(By.tagName("td"));   			
    			if (innerElements.get(0).getText().contains(useDate)) {
    				if (innerElements.get(1).getText().equals(message)) {
    					return true;
    				}
    			}
    		}
    		return false;
    }
    
	public boolean ConfirmExpectedItemColor(WebDriver driver, String item, String color) {
		List<WebElement> orderedItems = orderDetails.findElement(By.tagName("tbody")).findElements(By.className("item"));
		
		for (WebElement element : orderedItems) {
			WebElement itemElement = element.findElement(By.className("bold"));
			BaseWebDriver.ScrollToElement(driver, itemElement);
			if (itemElement.getText().contains(item)) {
				if (itemElement.getText().contains(color)) 
					return true;
			}
		}
		return false;
	}     
}

package com.automation.pageObjects;

import com.automation.dependencies.BaseWebDriver;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class MyAccountPage extends BasePage {

	public static String pageUrl = GetSiteUrl() + "/index.php?controller=my-account";
	
    @FindBy(className = "logout")
    public WebElement signOutButton;
    
    @FindBy(className = "sf-menu")
    public WebElement categoryMenu;
    
    @FindBy(linkText = "Order history and details")
    public WebElement orderHistoryButton;
    
    public MyAccountPage(WebDriver driver) {
    		PageFactory.initElements(driver, this);
    }  
    
    public CategoryPage ClickMenuItem(WebDriver driver, String item) {
    		
    		List<WebElement> menuItemList = categoryMenu.findElements(By.tagName("li"));
    	
    		for (WebElement element : menuItemList) {
    			if (element.getText().toLowerCase().equals(item.toLowerCase())) {
    				BaseWebDriver.MouseOverClick(driver, element);
    			}
    		}
    		
    		return new CategoryPage(driver);
    }  
    
    public OrderHistoryPage ClickOrderHistoryButton(WebDriver driver) {
    		orderHistoryButton.click();
    		
    		return new OrderHistoryPage(driver);
    }
}

package com.automation.pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage extends BasePage {
	
    public static String pageUrl = GetSiteUrl() + "/index.php?";
	
    @FindBy(className = "login")
    public WebElement signinButton;
    
    public HomePage(WebDriver driver) {
    		PageFactory.initElements(driver, this);
    }

    public SigninPage ClickSignin(WebDriver driver) {
    		signinButton.click();
        return new SigninPage(driver);
    }
}

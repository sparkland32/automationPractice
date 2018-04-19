package com.automation.pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SigninPage extends BasePage {

	public static String pageUrl = GetSiteUrl() + "/index.php?controller=authentication&back=my-account";
	
    @FindBy(id = "email")
    public WebElement emailField;
    
    @FindBy(id = "passwd")
    public WebElement passwordField;
    
    @FindBy(id = "SubmitLogin")
    private WebElement signinButton;
    
    public SigninPage(WebDriver driver) {
    		PageFactory.initElements(driver, this);
    }    
    
    public MyAccountPage SignIn(WebDriver driver, String emailAddress, String password) {
        emailField.sendKeys(emailAddress);
        passwordField.sendKeys(password);
        signinButton.click();
        return new MyAccountPage(driver);
    }
}

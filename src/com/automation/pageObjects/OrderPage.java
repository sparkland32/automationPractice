package com.automation.pageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.automation.dependencies.BaseWebDriver;

public class OrderPage extends BasePage {

	public static String pageUrl = GetSiteUrl() + "/index.php?controller=order";
	public static String orderSummaryUrl = GetSiteUrl() + "/index.php?controller=order&step=0";
	public static String orderAddressUrl = GetSiteUrl() + "/index.php?controller=order&step=1";	
	public static String partialOrderWirePaymentUrl = "module=bankwire&controller=payment";
	
	@FindBy(id = "cart_title")
	public WebElement cartPageTitle;
	
    @FindBy(className = "cart_description")
    public List<WebElement> cartItemDescriptions;
    
    @FindBy(className = "cart_item")
    public List<WebElement> cartItems;
    
    @FindBy(id = "total_price")
    public WebElement totalPrice;
    
    @FindBy(id = "total_product")
    public WebElement totalProduct;
    
    @FindBy(id = "total_shipping")
    public WebElement totalShipping;   
    
    @FindBy(className = "standard-checkout")
    public WebElement summaryCheckoutButton; 
    
    @FindBy(name = "processCarrier")
    public WebElement shippingCheckoutButton;     
    
    @FindBy(name = "processAddress")
    public WebElement addressCheckoutButton;     
    
    @FindBy(id = "uniform-cgv")
    public WebElement agreeTermsArea;
    
    @FindBy(className = "bankwire")
    public WebElement payByBankwireButton;
    
    @FindBy(className = "button-medium")
    public List<WebElement> confirmOrderButtons;
    
    @FindBy(className = "page-heading")
    public WebElement pageHeading;
    
    @FindBy(linkText = "Back to orders")
    public WebElement backToOrdersButton;
    
    public OrderPage(WebDriver driver) {
    		PageFactory.initElements(driver, this);
    }

	public boolean ConfirmExpectedItemSize(WebDriver driver, String item, String size) {

		for (WebElement element : cartItemDescriptions) {
			if (!element.getText().equals("Description") && element.findElement(By.className("product-name")).getText().toLowerCase().equals(item.toLowerCase()))
			{
				List<WebElement> sizeElements = element.findElements(By.tagName("small"));
					
				if (sizeElements.get(1).findElement(By.tagName("a")).getText().contains("Size : " + size))
					return true;
			}
		}
		return false;
	} 
	
	public boolean ConfirmExpectedItemPrice(WebDriver driver, String item, String price) {

		for (WebElement element : cartItems) {
			if (element.findElement(By.className("product-name")).getText().toLowerCase().equals(item.toLowerCase()))
			{
				if (element.findElement(By.className("cart_unit")).findElement(By.className("price")).getText().equals(price))
					return true;
			}
		}
		return false;
	} 	
	
	public boolean ConfirmTotalIsProductsPlusShipping(WebDriver driver) {

		String totalShippingCost = totalShipping.getText().substring(1);
		String totalProductCost = totalProduct.getText().substring(1);
		String totalPriceCost = totalPrice.getText().substring(1);
		
		float totalShippingCostValue = Float.parseFloat(totalShippingCost);
		float totalProductCostValue = Float.parseFloat(totalProductCost);
		float totalPriceCostValue = Float.parseFloat(totalPriceCost);
		
		if (totalShippingCostValue + totalProductCostValue == totalPriceCostValue) {
			return true;
		}
		
		return false;
	} 
	
    public void ClickSummaryCheckoutButton(WebDriver driver) {
		ClickButton(driver, summaryCheckoutButton);
    }
    
    public void ClickAddressCheckoutButton(WebDriver driver) {
		ClickButton(driver, addressCheckoutButton);
    }   
    
    public void ClickShippingCheckoutButton(WebDriver driver) {
		ClickButton(driver, shippingCheckoutButton);
    }    

	public void ClickAgreeTermsCheckbox(WebDriver driver) {
		WebElement checkbox = agreeTermsArea.findElement(By.tagName("input"));
		
		if (!checkbox.isSelected() )
		{
		     checkbox.click();
		}
	}	
	
	public void ClickPayByWireButton(WebDriver driver) {
		ClickButton(driver, payByBankwireButton);
	}
	
	public void ClickConfirmOrderButton(WebDriver driver) {
		for (WebElement element : confirmOrderButtons) {
			if (element.getText().equals("I confirm my order"))
			{
				ClickButton(driver, element);
			}
		}
	}	
	
	private void ClickButton(WebDriver driver, WebElement element) {
		try {
			BaseWebDriver.ScrollToElement(driver, element);
			element.click();
			Thread.sleep(2000);
		}
		catch (ElementNotInteractableException | InterruptedException e) {
			// Mark - Retry if load too quick
			BaseWebDriver.ScrollToElement(driver, element);
			element.click();
		}
	}
    
}

package com.automation.pageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.automation.dependencies.BaseWebDriver;

public class CategoryPage extends BasePage {

	public static String partialPageUrl = "controller=category";
	
    @FindBy(className = "logout")
    public WebElement signOutButton;
    
    @FindBy(className = "category-name")
    public WebElement categoryName;
    
    @FindBy(className = "subcategory-name")
    public List<WebElement> subcategoryNames;
    
    @FindBy(className = "product-container")
    public List<WebElement> productContainers;
    
    @FindBy(className = "fancybox-overlay")
    public WebElement lightBoxOverlay;
    
    @FindBy(className = "fancybox-iframe")
    public WebElement lightBox;
    
    @FindBy(className = "attribute_list")
    public WebElement selectSize;
    
    @FindBy(id = "add_to_cart")
    public WebElement addToCart;
    
    @FindBy(className = "layer_cart_product")
    public WebElement productInCart;
    
    @FindBy(id = "layer_cart")
    public WebElement cartLayer;
    
    @FindBy(className = "header-container")
    public WebElement topOfPage;
    
    @FindBy(className = "sf-menu")
    public WebElement categoryMenu;  
    
    @FindBy(className = "continue")
    public WebElement continueButton;    
    
    @FindBy(linkText = "Proceed to checkout")
    public WebElement checkoutButton;
    
    public CategoryPage(WebDriver driver) {
    		PageFactory.initElements(driver, this);
    }  
    
    public void ClickCategory(WebDriver driver, String item) {		
		for (WebElement element : subcategoryNames) {
			if (element.getText().toLowerCase().equals(item.toLowerCase())) {
				element.click();
				break;
			}
		}
    }
    
    public void QuickViewItem(WebDriver driver, WebDriverWait wait, String item) {
    		for (WebElement element : productContainers) {
    			WebElement elementTitle = element.findElement(By.className("product-name"));
    			String itemTitle = elementTitle.getText();
    			if (itemTitle.toLowerCase().equals(item.toLowerCase())) {
    				BaseWebDriver.ScrollToElement(driver, element);
    				wait.until(ExpectedConditions.visibilityOf(elementTitle));
    				WebElement quickViewElement = element.findElement(By.className("img-responsive"));
    				int centreWidth = quickViewElement.getSize().width / 2;
    				int centreHeight = quickViewElement.getSize().height / 2;
    				BaseWebDriver.MouseOverClickWithCoords(driver, quickViewElement, centreWidth, centreHeight);
    			}
    		}
    }
    
    public void SetSize(WebDriver driver, String size) {
		driver.switchTo().frame(lightBox);
    		WebElement selectElement = selectSize.findElement(By.tagName("select"));
    		Select dropdownSelect = new Select(selectElement);
    		dropdownSelect.selectByVisibleText(size);
    		driver.switchTo().defaultContent();
    }
    
    public String GetSize(WebDriver driver) {
    		driver.switchTo().frame(lightBox);
		WebElement selectElement = selectSize.findElement(By.tagName("select"));    	
    		Select dropdownSelect = new Select(selectElement);
    		String selectedOption = dropdownSelect.getFirstSelectedOption().getText();
    		driver.switchTo().defaultContent();
    		return selectedOption;
    }
    
    public void AddItemToCart(WebDriver driver) {
		driver.switchTo().frame(lightBox);
    		addToCart.findElement(By.tagName("button")).click();
    		driver.switchTo().defaultContent();
    }
    
    public String GetCartMessage(WebDriver driver) {
		return productInCart.findElement(By.tagName("h2")).getText();
    }
    
    public void ClickMenuItem(WebDriver driver, String item) {    	
		List<WebElement> menuItemList = categoryMenu.findElements(By.tagName("li"));    	
		
		for (WebElement element : menuItemList) {
			if (element.getText().toLowerCase().equals(item.toLowerCase())) {
				BaseWebDriver.MouseOverClick(driver, element);
			}
		}	
    }
    
    public void ClickContinueButton(WebDriver driver) {
    		continueButton.click();
    }
    
    public OrderPage ClickCheckoutButton(WebDriver driver) {
		checkoutButton.click();
		return new OrderPage(driver);
    }
    
    public void ScrollToTop(WebDriver driver) {
		BaseWebDriver.ScrollToElement(driver, topOfPage);
    }
}



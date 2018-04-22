package com.automation.stepDefinitions.guiSteps;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.automation.dependencies.BaseWebDriver;
import com.automation.dependencies.Browser;
import com.automation.pageObjects.CategoryPage;
import com.automation.pageObjects.HomePage;
import com.automation.pageObjects.MyAccountPage;
import com.automation.pageObjects.OrderHistoryPage;
import com.automation.pageObjects.OrderPage;
import com.automation.pageObjects.SigninPage;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import com.automation.dependencies.PropertiesFileManager;

public class GuiSteps extends BaseWebDriver {
	
	private WebDriverWait wait;
	
	private HomePage homePage;
	private SigninPage signinPage;
	private MyAccountPage myAccountPage;
	private CategoryPage categoryPage;
	private OrderPage orderPage;
	private OrderHistoryPage orderHistoryPage;
	
	private Scenario runningScenario;
	
	@Before
	public void before(Scenario scenario) throws IOException {
		runningScenario = scenario;
		AssignWebDriver();
	}
	
	@Given("^the User is logged in with username \"(.*?)\" and password \"(.*?)\"$")
	public void the_User_is_logged_in_with_username_and_password(String emailAddress, String password) throws Throwable {
		try {
			assertEquals(HomePage.pageUrl, GetCurrentUrl());
			homePage = new HomePage(driver);
		    wait.until(ExpectedConditions.visibilityOf(homePage.signinButton));
		    signinPage = homePage.ClickSignin(driver);
		    wait.until(ExpectedConditions.visibilityOf(signinPage.emailField));
		    assertEquals(SigninPage.pageUrl, GetCurrentUrl());
		    myAccountPage = signinPage.SignIn(driver, emailAddress, password);
		    wait.until(ExpectedConditions.visibilityOf(myAccountPage.signOutButton));
		    assertEquals(MyAccountPage.pageUrl, GetCurrentUrl()); 
		}
		catch (Throwable t) {
			stepFailureHandler(t);
		}
	}

	@Given("^the User selects menu option \"(.*?)\"$")
	public void the_User_selects_menu_option(String menuItem) throws Throwable {
		try {
			if (GetCurrentUrl().equals(MyAccountPage.pageUrl)) {
				categoryPage = myAccountPage.ClickMenuItem(driver, menuItem);	
			}
			else {
				categoryPage.ClickMenuItem(driver, menuItem);
			}
		    wait.until(ExpectedConditions.visibilityOf(categoryPage.categoryName));
		    assertTrue(GetCurrentUrl().contains(CategoryPage.partialPageUrl));
		}
		catch (Throwable t) {
			stepFailureHandler(t);
		}
	}
	
	@Given("^the User selects category option \"(.*?)\"$")
	public void the_User_selects_category_option(String category) throws Throwable {
		try {
		    categoryPage.ClickCategory(driver, category);
		    wait.until(ExpectedConditions.visibilityOf(categoryPage.categoryName));
		    assertTrue(categoryPage.categoryName.getText().toLowerCase().equals(category.toLowerCase()));
		}
		catch (Throwable t) {
			stepFailureHandler(t);
		}		
	}	
	
	@Given("^the User is on the category \"(.*?)\"$")
	public void the_User_is_on_the_category(String category) throws Throwable {
		try {
			// Mark - To account for page refresh
			Thread.sleep(3000);
		    wait.until(ExpectedConditions.visibilityOf(categoryPage.categoryName));
		    assertTrue(categoryPage.categoryName.getText().toLowerCase().equals(category.toLowerCase()));
		}
		catch (Throwable t) {
			stepFailureHandler(t);
		}
	}		

	@Given("^the User selects to Quick view the item \"(.*?)\"$")
	public void the_User_selects_to_Quick_view_the_item(String item) throws Throwable {
		try {
			categoryPage.QuickViewItem(driver, wait, item);
		    wait.until(ExpectedConditions.visibilityOf(categoryPage.lightBoxOverlay));
		    // Mark - Account for the load time of the light box
		    Thread.sleep(5000);
		    assertTrue(categoryPage.lightBox.isDisplayed());
		}
		catch (Throwable t) {
			stepFailureHandler(t);
		}
	}

	@Given("^the User changes the Size to \"(.*?)\"$") 
	public void the_User_changes_the_Size_to(String itemSize) throws Throwable {
		try {
		    categoryPage.SetSize(driver, itemSize);
		    assertEquals(itemSize, categoryPage.GetSize(driver));
		}
		catch (Throwable t) {
			stepFailureHandler(t);
		}
	}

	@Given("^the User adds the item to their basket$")
	public void the_User_adds_the_item_to_their_basket() throws Throwable {
		try {
		    categoryPage.AddItemToCart(driver);
		    wait.until(ExpectedConditions.visibilityOf(categoryPage.cartLayer));
		    assertEquals("Product successfully added to your shopping cart", categoryPage.GetCartMessage(driver));
		    // Mark - Would add further tests around this being the correct product here
		}
		catch (Throwable t) {
			stepFailureHandler(t);
		}
	}	
	
	@Given("^the User continues shopping$")
	public void the_User_continues_shopping() throws Throwable {
		try {
		    // Mark - Account for the load time of the light box
		    Thread.sleep(5000);
		    categoryPage.ClickContinueButton(driver);
		    wait.until(ExpectedConditions.invisibilityOf(categoryPage.continueButton));
		    categoryPage.ScrollToTop(driver);
		}
		catch (Throwable t) {
			stepFailureHandler(t);
		}
	}
	
	@Given("^the User views Order History$")
	public void the_User_views_Order_Hisory() throws Throwable {
		try {
			orderHistoryPage = myAccountPage.ClickOrderHistoryButton(driver);
			wait.until(ExpectedConditions.visibilityOf(orderHistoryPage.tableOfOrders));
			assertTrue(GetCurrentUrl().equals(OrderHistoryPage.pageUrl));
		}
		catch (Throwable t) {
			stepFailureHandler(t);
		}
	}

	@When("^the User views the basket$")
	public void the_User_views_the_basket() throws Throwable {
		try {
		    // Mark - Account for the load time of the light box
		    Thread.sleep(5000);		
		    orderPage = categoryPage.ClickCheckoutButton(driver);
		}
		catch (Throwable t) {
			stepFailureHandler(t);
		}
	}

	@When("^confirm the Size of item \"(.*?)\" is \"(.*?)\"$")
	public void confirm_the_Size_of_each_item_is(String item, String size) throws Throwable {
		try {
		    wait.until(ExpectedConditions.visibilityOf(orderPage.cartPageTitle));
		    assertEquals(OrderPage.pageUrl, GetCurrentUrl());	
		    assertTrue(orderPage.ConfirmExpectedItemSize(driver, item, size));
		}
		catch (Throwable t) {
			stepFailureHandler(t);
		}
	}

	@When("^confirm the price of item \"(.*?)\" is \"(.*?)\"$")
	public void confirm_the_price_of_item_is(String item, String price) throws Throwable {
		try {
			wait.until(ExpectedConditions.visibilityOf(orderPage.cartPageTitle));
		    assertEquals(OrderPage.pageUrl, GetCurrentUrl());	
		    assertTrue(orderPage.ConfirmExpectedItemPrice(driver, item, price));
		}
		catch (Throwable t) {
			stepFailureHandler(t);
		}
	}

	@When("^confirm the total price is equal to the total products and total shipping$")
	public void confirm_the_total_price_is_equal_to_the_total_products_and_total_shipping() throws Throwable {
		try {
			wait.until(ExpectedConditions.visibilityOf(orderPage.cartPageTitle));
		    assertEquals(OrderPage.pageUrl, GetCurrentUrl());	
		    assertTrue(orderPage.ConfirmTotalIsProductsPlusShipping(driver));
		}
		catch (Throwable t) {
			stepFailureHandler(t);
		}
	}
	
	@When("^the User selects order with date \"(.*?)\" and time \"(.*?)\"$")
	public void the_User_selects_order_with_date_and_time(String date, String time) throws Throwable {
		try {
			orderHistoryPage.SelectOrderFromTableByDateAndTime(driver, date, time);
			wait.until(ExpectedConditions.visibilityOf(orderHistoryPage.orderInformationBox));
			assertTrue(orderHistoryPage.submitReorder.getText().contains(date));
		}
		catch (Throwable t) {
			stepFailureHandler(t);
		}	
	}	
	
	@When("^the User adds a message \"(.*?)\"$")
	public void the_User_adds_a_message(String messageText) throws Throwable {
		try {
		    orderHistoryPage.AddAMessage(driver, messageText);
		    wait.until(ExpectedConditions.visibilityOf(orderHistoryPage.messageAlert));
		    assertEquals("Message successfully sent", orderHistoryPage.messageAlert.getText());
		}
		catch (Throwable t) {
			stepFailureHandler(t);
		}
	}	

	@Then("^the User can complete payment by \"(.*?)\"$")
	public void the_User_can_complete_payment_by(String paymentMethod) throws Throwable {
		// Mark - this function is a candidate for refactor and splitting into separate functions
		try {
		    wait.until(ExpectedConditions.urlContains(OrderPage.pageUrl));
		    assertEquals(OrderPage.pageUrl, GetCurrentUrl());
		    orderPage.ClickSummaryCheckoutButton(driver);
		    wait.until(ExpectedConditions.urlContains(OrderPage.orderAddressUrl));
		    orderPage.ClickAddressCheckoutButton(driver);
		    wait.until(ExpectedConditions.visibilityOf(orderPage.agreeTermsArea));
		    orderPage.ClickAgreeTermsCheckbox(driver);
		    orderPage.ClickShippingCheckoutButton(driver);
		    wait.until(ExpectedConditions.visibilityOf(orderPage.payByBankwireButton));
		    if (paymentMethod.equals("wire")) {
		    		orderPage.ClickPayByWireButton(driver);
		    }
		    wait.until(ExpectedConditions.urlContains(OrderPage.partialOrderWirePaymentUrl));
		    orderPage.ClickConfirmOrderButton(driver);
		    wait.until(ExpectedConditions.visibilityOf(orderPage.backToOrdersButton));
		    assertEquals("order confirmation", orderPage.pageHeading.getText().toLowerCase());
		}
		catch (Throwable t) {
			stepFailureHandler(t);
		}
	}
	
	@Then("^the User can view the message \"(.*?)\" against todays date$")
	public void the_User_can_view_the_message(String messageText) throws Throwable {
		try {
			wait.until(ExpectedConditions.visibilityOf(orderHistoryPage.stepByStepTables.get(1)));
		    boolean messagePresent = orderHistoryPage.ConfirmMessagePresentForDate(driver, messageText, "today");
		    assertTrue(messagePresent);
		}
		catch (Throwable t) {
			stepFailureHandler(t);
		}
	}	
	
	@When("^confirm the Color of item \"(.*?)\" is \"(.*?)\"$")
	public void confirm_the_Color_of_each_item_is(String item, String color) throws Throwable {
		try {
		    wait.until(ExpectedConditions.visibilityOf(orderHistoryPage.orderDetails));
		    assertEquals(OrderHistoryPage.pageUrl, GetCurrentUrl());	
		    assertTrue(orderHistoryPage.ConfirmExpectedItemColor(driver, item, color));
		}
		catch (Throwable t) {
			stepFailureHandler(t);
		}		
	}	
	
	private void stepFailureHandler(Throwable t) throws Throwable {
		byte[] embeddedScreenCapture = PerformScreenCapture(runningScenario.getName());
		runningScenario.embed(embeddedScreenCapture, "image/png");
		CloseDriver();
		throw t;
	} 	
	
	@After
	public void AfterSteps(Scenario scenario) {
		CloseDriver();
	}	

	public Scenario getRunningScenario() {
		return runningScenario;
	}

	public void setRunningScenario(Scenario runningScenario) {
		this.runningScenario = runningScenario;
	}	
	
	private void AssignWebDriver() throws IOException {
		String browser = PropertiesFileManager.GetProperty("use_browser");
		driver = GetDriver(Browser.valueOf(browser));
		wait = new WebDriverWait(driver, 60);
		SetUrl(HomePage.pageUrl);
	}

}

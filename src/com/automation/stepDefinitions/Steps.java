package com.automation.stepDefinitions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.automation.dependencies.BaseWebDriver;
import com.automation.dependencies.Browser;
import com.automation.pageObjects.CategoryPage;
import com.automation.pageObjects.HomePage;
import com.automation.pageObjects.MyAccountPage;
import com.automation.pageObjects.OrderPage;
import com.automation.pageObjects.SigninPage;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static org.hamcrest.Matchers.equalTo;

import com.automation.dependencies.BaseApi;
import com.automation.dependencies.CucumberReport;

public class Steps extends BaseWebDriver {
	
	private WebDriverWait wait;
	private HomePage homePage;
	private SigninPage signinPage;
	private MyAccountPage myAccountPage;
	private CategoryPage categoryPage;
	private OrderPage orderPage;
	private Scenario runningScenario;
	
	private Response response;
	
	@Before
	public void before(Scenario scenario) {
		runningScenario = scenario;
		if (!scenario.getName().contains("API")) {
			AssignWebDriver();
		}
		RestAssured.baseURI = "https://reqres.in";
	}
	
	@Given("^the User is logged in with username \"(.*?)\" and password \"(.*?)\"$")
	public void the_User_is_logged_in_with_username_and_password(String emailAddress, String password) throws Throwable {
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

	@Given("^the User selects menu option \"(.*?)\"$")
	public void the_User_selects_menu_option(String menuItem) throws Throwable {
		if (GetCurrentUrl().equals(MyAccountPage.pageUrl)) {
			categoryPage = myAccountPage.ClickMenuItem(driver, menuItem);	
		}
		else {
			categoryPage.ClickMenuItem(driver, menuItem);
		}
	    wait.until(ExpectedConditions.visibilityOf(categoryPage.categoryName));
	    assertTrue(GetCurrentUrl().contains(CategoryPage.partialPageUrl));
	}
	
	@Given("^the User selects category option \"(.*?)\"$")
	public void the_User_selects_category_option(String category) throws Throwable {
	    categoryPage.ClickCategory(driver, category);
	    wait.until(ExpectedConditions.visibilityOf(categoryPage.categoryName));
	    assertTrue(categoryPage.categoryName.getText().toLowerCase().equals(category.toLowerCase()));
	}	
	
	@Given("^the User is on the category \"(.*?)\"$")
	public void the_User_is_on_the_category(String category) throws Throwable {
		// Mark - To account for page refresh
		Thread.sleep(3000);
	    wait.until(ExpectedConditions.visibilityOf(categoryPage.categoryName));
	    assertTrue(categoryPage.categoryName.getText().toLowerCase().equals(category.toLowerCase()));
	}		

	@Given("^the User selects to Quick view the item \"(.*?)\"$")
	public void the_User_selects_to_Quick_view_the_item(String item) throws Throwable {
		categoryPage.QuickViewItem(driver, wait, item);
	    wait.until(ExpectedConditions.visibilityOf(categoryPage.lightBoxOverlay));
	    // Mark - Account for the load time of the light box
	    Thread.sleep(5000);
	    assertTrue(categoryPage.lightBox.isDisplayed());
	}

	@Given("^the User changes the Size to \"(.*?)\"$")
	public void the_User_changes_the_Size_to(String itemSize) throws Throwable {
	    categoryPage.SetSize(driver, itemSize);
	    assertEquals(itemSize, categoryPage.GetSize(driver));
	}

	@Given("^the User adds the item to their basket$")
	public void the_User_adds_the_item_to_their_basket() throws Throwable {
	    categoryPage.AddItemToCart(driver);
	    wait.until(ExpectedConditions.visibilityOf(categoryPage.cartLayer));
	    assertEquals("Product successfully added to your shopping cart", categoryPage.GetCartMessage(driver));
	    // Mark - Would add further tests around this being the correct product here
	}	
	
	@Given("^the User continues shopping$")
	public void the_User_continues_shopping() throws Throwable {
	    // Mark - Account for the load time of the light box
	    Thread.sleep(5000);
	    categoryPage.ClickContinueButton(driver);
	    wait.until(ExpectedConditions.invisibilityOf(categoryPage.continueButton));
	    categoryPage.ScrollToTop(driver);
	}

	@When("^the User views the basket$")
	public void the_User_views_the_basket() throws Throwable {
	    // Mark - Account for the load time of the light box
	    Thread.sleep(5000);		
	    orderPage = categoryPage.ClickCheckoutButton(driver);
	}

	@When("^confirm the Size of item \"(.*?)\" is \"(.*?)\"$")
	public void confirm_the_Size_of_each_item_is(String item, String size) throws Throwable {
	    wait.until(ExpectedConditions.visibilityOf(orderPage.cartPageTitle));
	    assertEquals(OrderPage.pageUrl, GetCurrentUrl());	
	    assertTrue(orderPage.ConfirmExpectedItemSize(driver, item, size));
	}

	@When("^confirm the price of item \"(.*?)\" is \"(.*?)\"$")
	public void confirm_the_price_of_item_is(String item, String price) throws Throwable {
		wait.until(ExpectedConditions.visibilityOf(orderPage.cartPageTitle));
	    assertEquals(OrderPage.pageUrl, GetCurrentUrl());	
	    assertTrue(orderPage.ConfirmExpectedItemPrice(driver, item, price));
	}

	@When("^confirm the total price is equal to the total products and total shipping$")
	public void confirm_the_total_price_is_equal_to_the_total_products_and_total_shipping() throws Throwable {
		try {
			wait.until(ExpectedConditions.visibilityOf(orderPage.cartPageTitle));
		    assertEquals(OrderPage.pageUrl, GetCurrentUrl());	
		    assertFalse(orderPage.ConfirmTotalIsProductsPlusShipping(driver));
		}
		catch (Throwable t) {
			stepFailureHandler();
		}
	}

	@Then("^the User can complete payment by \"(.*?)\"$")
	public void the_User_can_complete_payment_by(String paymentMethod) throws Throwable {
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
	
	private void stepFailureHandler() throws Throwable {
		byte[] embeddedScreenCapture = PerformScreenCapture(runningScenario.getName());
		runningScenario.embed(embeddedScreenCapture, "image/png");
		CloseDriver();
		throw new Throwable("Failed in Scenario " + runningScenario.getName());
	}
	
	@Given("^the API receives \"(.*?)\"$")
	public void the_API_receives(String url) throws Throwable {
	    response = BaseApi.GetResponse(url);
	}

	@When("^the response is returned with an HTTP code of \"(.*?)\"$")
	public void the_response_is_returned_with_an_HTTP_code_of(String httpCode) throws Throwable {
		int statusCode = Integer.parseInt(httpCode);
	    response.then().statusCode(statusCode);
	}

	@When("^an object type of \"(.*?)\"$")
	public void an_object_type_of(ContentType objectType) throws Throwable {
		response.then().contentType(ContentType.JSON);
	}

	@Then("^the values are present under root \"(.*?)\"$")
	public void the_values_are_present_under_root(String rootValue, List<String> valuesToConfirm) throws Throwable {		
		if (valuesToConfirm.size() % 3 == 0) {
		
		    for (int i=3; i<valuesToConfirm.size(); i=i+3) {
		    	
		    		if (valuesToConfirm.get(i+2).toLowerCase().equals("int")) {
		    				response.then()
		    				.root(rootValue)
		    				.body(valuesToConfirm.get(i), equalTo(Integer.parseInt(valuesToConfirm.get(i+1))));
		    		} 	
		    		else {
						response.then()
					    .root(rootValue)
					    .body(valuesToConfirm.get(i), equalTo(valuesToConfirm.get(i+1)));
		    		}
		    }
	    }
		else {
			Assert.fail("Values to confirm did not conform to set Key, Value, Type");
		}
	}	
	
	@After
	public void AfterSteps(Scenario scenario) {
		if (!scenario.getName().contains("API")) {
			CloseDriver();
		}
		
		CucumberReport.BuildCucumberReport("./Report");
	}
	
	private void AssignWebDriver() {
		driver = GetDriver(Browser.Firefox);
		wait = new WebDriverWait(driver, 60);
		SetUrl(HomePage.pageUrl);
	}

}

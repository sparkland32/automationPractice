package com.automation.stepDefinitions.apiSteps;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.junit.Assert;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.notNullValue;

import com.automation.dependencies.BaseApi;
import com.automation.dependencies.PropertiesFileManager;

public class ApiSteps extends BaseApi {
	
	private Response response;
	private Scenario runningScenario = null;
	
	@Before
	public void before(Scenario scenario) throws IOException {
		setRunningScenario(scenario);
		RestAssured.baseURI = PropertiesFileManager.GetProperty("api_url");
	}
	
	@Given("^the API performs GET request on \"(.*?)\"$")
	public void the_API_receives_get(String url) throws Throwable {
		response = GetRequest(url);
	}	
	
	@Given("^the API performs POST request on \"(.*?)\" with values$")
	public void the_API_receives_post(String url, List<String> valuesToPost) throws Throwable {
		HashMap<String, String> values = MapValuesForRequest(valuesToPost);
		response = PostRequest(url, values);
	}
	
	@Given("^the API performs PUT request on \"(.*?)\" with values$")
	public void the_API_performs_PUT_action_on_with_values(String url, List<String> valuesToPost) throws Throwable {
		HashMap<String, String> values = MapValuesForRequest(valuesToPost);
		response = PutRequest(url, values);
	}	
	
	@Given("^the API performs PATCH request on \"(.*?)\" with values$")
	public void the_API_performs_PATCH_action_on_with_values(String url, List<String> valuesToPost) throws Throwable {
		HashMap<String, String> values = MapValuesForRequest(valuesToPost);
		response = PatchRequest(url, values);
	}	
	
	@Given("^the API performs DELETE request on \"(.*?)\"$")
	public void the_API_performs_DELETE_action_on(String url) throws Throwable {
		response = DeleteRequest(url);
	}		

	@When("^the response is returned with an HTTP code of \"(.*?)\"$")
	public void the_response_is_returned_with_an_HTTP_code_of(String httpCode) throws Throwable {
		int statusCode = Integer.parseInt(httpCode);
	    AssertStatusCode(response, statusCode);
	}

	@When("^an object type of \"(.*?)\"$")
	public void an_object_type_of(String objectType) throws Throwable {
		AssertContentType(response,  objectType);
	}
	
	@Then("^the response contains values equal to$")
	public void the_values_are_present_under_root(List<String> valuesToConfirm) throws Throwable {	
		int tableSize = 3;
		
		if (valuesToConfirm.size() % tableSize == 0) {
		
		    for (int i=tableSize; i<valuesToConfirm.size(); i=i+tableSize) {
		    	
		    		if (valuesToConfirm.get(i+2).toLowerCase().equals("int")) {
		    				response.then()
		    				.body(valuesToConfirm.get(i), equalTo(Integer.parseInt(valuesToConfirm.get(i+1))));
		    		} 	
		    		else {
						response.then()
					    .body(valuesToConfirm.get(i), equalTo(valuesToConfirm.get(i+1)));
		    		}
		    }
	    }
		else {
			Assert.fail("Values to confirm did not conform to table headers Key, Value, Type");
		}
	}

	@Then("^the response collection has items$")
	public void the_response_has_values(List<String> valuesToConfirm) throws Throwable {	
		int tableSize = 3;
		
		if (valuesToConfirm.size() % tableSize == 0) {
		
		    for (int i=tableSize; i<valuesToConfirm.size(); i=i+tableSize) {
		    	
	    			String[] hasValues = valuesToConfirm.get(i+1).split(",");
		    	
		    		if (valuesToConfirm.get(i+2).toLowerCase().equals("int")) {
		    			
		    			for (String value : hasValues) {
		    				response.then()
		    				.body(valuesToConfirm.get(i), hasItems(Integer.parseInt(value)));
		    			}
		    		} 	
		    		else {
		    			for (String value : hasValues) {
						response.then()
					    .body(valuesToConfirm.get(i), hasItems(value));
		    			}
		    		}
		    }
	    }
		else {
			Assert.fail("Values to confirm did not conform to table headers Key, Value, Type");
		}
	}	
	
	@Then("^the response contains key \"(.*?)\" equal to int \"(.*?)\"$")
	public void the_value_present_under_key_is_int(String key, int intValue) throws Throwable {
	    response.then()
	    .body(key, equalTo(intValue));
	}	
	
	@Then("^the response contains key \"(.*?)\" equal to string \"(.*?)\"$")
	public void the_value_present_under_key_is_string(String key, String value) throws Throwable {
	    response.then()
	    .body(key, equalTo(value));
	}		
	
	@Then("^the response contains key \"(.*?)\" not null$")
	public void the_response_contains_key_not_null(String key) throws Throwable {
	    response.then()
	    .body(key, notNullValue());
	}	
	
	@After
	public void AfterSteps(Scenario scenario) {
		
	}

	public Scenario getRunningScenario() {
		return runningScenario;
	}

	public void setRunningScenario(Scenario runningScenario) {
		this.runningScenario = runningScenario;
	}
}

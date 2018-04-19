package com.automation.tests;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.*;

import static org.hamcrest.Matchers.equalTo;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import io.restassured.path.json.*;
 
public class ApiTests {
	
    @BeforeClass
    public static void before() {
        String port = System.getProperty("server.port");
        if (port == null) {
            RestAssured.port = Integer.valueOf(8080);
        }
        else {
            RestAssured.port = Integer.valueOf(port);
        }

        String baseHost = System.getProperty("server.host");
        if(baseHost == null){
            baseHost = "https://reqres.in";
        }
        RestAssured.baseURI = baseHost;
    }
    
    @Test
    public void SingleUser()
    {	
    		GetResponse("/api/users/2", 200)
    		.root("data")
    		.body("id", equalTo(2))
    		.body("first_name", equalTo("Janet"))
    		.body("last_name", equalTo("Weaver"))
    		.body("avatar", equalTo("https://s3.amazonaws.com/uifaces/faces/twitter/josephstein/128.jpg"));
    }
    
    @Test
    public void SingleUserNotFound()
    {
    		GetResponse("/api/users/23", 404);
    } 
    
    @Test
    public void SingleResource()
    {
    		GetResponse("/api/unknown/2", 200)
		.root("data")
		.body("id", equalTo(2))
		.body("last_name", equalTo("fuchsia rose"))
    		.body("year", equalTo(2001))
    		.body("color", equalTo("#C74375"))
    		.body("pantone_value", equalTo("17-2031"));	
    }
	
	@Test
	public void ListUsers()
	{
		Response response = SendSingleRequest(Method.GET, "/api/users?page=2");
		String responseBody = response.getBody().asString();
		JsonPath pathEvaluation = response.jsonPath();
		EvaluateSuccessfulResponseData(response);
		assertEquals(2, (int) pathEvaluation.get("page"));
	}
	
	private Response SendSingleRequest(Method requestType, String requestString) {
		RequestSpecification httpRequest = RestAssured.given();
		Response response = httpRequest.request(requestType, requestString);
		return response;
	}
	
	private ValidatableResponse GetResponse(String requestString, int expectedStatusCode) {
		return RestAssured.given().when().get(requestString).then()
		.statusCode(expectedStatusCode)
		.contentType(ContentType.JSON);
	}
	
	private void EvaluateSuccessfulResponseData(Response response) {
		assertEquals(200, response.getStatusCode());
		assertTrue(response.contentType().contains("application/json"));
	}
 
}
package com.automation.dependencies;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class BaseApi {
	
	public static Response GetResponse(String requestString) {
		return RestAssured.given().when().get(requestString);
	}
}

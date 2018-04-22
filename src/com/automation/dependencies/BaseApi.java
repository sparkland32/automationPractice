package com.automation.dependencies;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public abstract class BaseApi {
	
	public static Response GetRequest(String requestString) {
		return RestAssured.given().when().get(requestString);
	}
	
	public static Response PostRequest(String requestString, HashMap<String, String> requestParams) {
		
		 Map<String, String> parameters = requestParams;	 
		
		return RestAssured.given()
				.accept(ContentType.JSON)
		        .contentType(ContentType.JSON)
				.body(parameters)
				.when().post(requestString);
	}
	
	public static Response PutRequest(String requestString, HashMap<String, String> requestParams) {
		
		 Map<String, String> parameters = requestParams;	 
		
		return RestAssured.given()
				.accept(ContentType.JSON)
		        .contentType(ContentType.JSON)
				.body(parameters)
				.when().put(requestString);
	}
	
	public static Response PatchRequest(String requestString, HashMap<String, String> requestParams) {
		
		 Map<String, String> parameters = requestParams;	 
		
		return RestAssured.given()
				.accept(ContentType.JSON)
		        .contentType(ContentType.JSON)
				.body(parameters)
				.when().patch(requestString);
	}	
	
	public static HashMap<String,String> MapValuesForRequest(List<String> valuesToPost) {
		int tableSize = 2;
		HashMap<String, String> values = new HashMap<String, String>();
		
		if (valuesToPost.size() % tableSize == 0) {
			
		    for (int i=tableSize; i<valuesToPost.size(); i=i+tableSize) {
		    		values.put(valuesToPost.get(i), valuesToPost.get(i+1));
		    }
		}
		
		return values;
	}
	
	public static Response DeleteRequest(String requestString) {
		
		return RestAssured.given()
				.when().delete(requestString);
	}	
	
	public static void AssertStatusCode(Response response, int statusCode) {
		response.then().statusCode(statusCode);
	}
	
	public static void AssertContentType(Response response, String objectType) {
		response.then().contentType(ContentType.valueOf(objectType));
	}
}

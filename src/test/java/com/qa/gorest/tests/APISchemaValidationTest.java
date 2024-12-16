package com.qa.gorest.tests;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.gorest.base.BaseTest;
import com.qa.gorest.client.RestClient;
import com.qa.gorest.constants.APIHttpStatus;

public class APISchemaValidationTest extends BaseTest{
	
	@BeforeMethod
	public void getUserSetup() {
		restClient = new RestClient(prop, baseURI);
	}
	
	@Test
	public void getUserAPISchemaTest() {
		
		
		
		restClient.get(GOREST_ENDPOINT,true, true)
		.then().log().all()
			.assertThat().statusCode(APIHttpStatus.OK_200.getCode())
					.and()
						.assertThat()
							.body(matchesJsonSchemaInClasspath("getuserschema.json")); // get converted schema from response json online using one of converter source 
					//== also classpath means the src/test/resources folder and data folder contains the schema json 

}

}

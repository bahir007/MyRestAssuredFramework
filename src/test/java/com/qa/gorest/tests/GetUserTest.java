package com.qa.gorest.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.qa.gorest.base.BaseTest;
import com.qa.gorest.client.RestClient;
import com.qa.gorest.constants.APIHttpStatus;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;

import java.util.HashMap;
import java.util.Map;

public class GetUserTest extends BaseTest {
	
	@BeforeMethod
	public void getUserSetup() {
		restClient = new RestClient(prop, baseURI);
	}
		
		
	//code smell: sonarQube detects duplicate codes so changed it in BaseTest
	@Test(enabled=false, priority = 3, description = "this test is in progress...")
	public void getAllUsersTest() {
		
		
		restClient.get(GOREST_ENDPOINT,true, true)
			.then().log().all()
				.assertThat().statusCode(APIHttpStatus.OK_200.getCode());
		
	}
	
	@Test(priority = 2)
	public void getUserTest() {
		
		
		restClient.get(GOREST_ENDPOINT+"/7580259",true, true)
			.then().log().all()
				.assertThat().statusCode(APIHttpStatus.OK_200.getCode())
					.and().body("id", equalTo(7580259));
		
	}
	
	@Test(priority = 1)
	public void getUserWithQuaryParamsTest() {
		

	
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("name", "Vedang Pillai");
		queryMap.put("status", "active");
		
		
		restClient.get(GOREST_ENDPOINT, queryMap,null, true, true)
			.then().log().all()
				.assertThat().statusCode(APIHttpStatus.OK_200.getCode());
		System.out.println("end test of quaryParams");
		
	}
	

}

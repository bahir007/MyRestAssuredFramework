package com.qa.gorest.tests;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.gorest.base.BaseTest;
import com.qa.gorest.client.RestClient;
import com.qa.gorest.constants.APIConstants;
import com.qa.gorest.constants.APIHttpStatus;
import com.qa.gorest.pojo.User;
import com.qa.gorest.utils.ExcelUtil;
import com.qa.gorest.utils.StringUtils;


public class CreateUserTest extends BaseTest{
	
	@BeforeMethod
	public void getUserSetup() {
		restClient = new RestClient(prop, baseURI);
	}
	
	//FYI: using dataProvider is used a lot now days on modern design frameworks than using an xl sheet as a source of data because lots of parsing is happening when working 
	// with Excel
	@DataProvider
	public Object [][] getUserTestData() {
		return new Object [][] {
			{"Tom", "male", "active"},
			{"David", "male", "inactive"},
			{"Sara", "female", "active"}
			
		};
	}
	
	@DataProvider
	public Object [][] getUserTestSheetData() {
		return ExcelUtil.getTestData(APIConstants.GOREST_USER_SHEET_NAME);
	}
	
	@Test(dataProvider = "getUserTestData")
	public void createUserWithPojoTest(String name, String gender, String status) {
		
	
		
		//1. post:
		User user = new User(name, StringUtils.getRandomEmailId(),gender, status);
		Integer userId = restClient.post(GOREST_ENDPOINT, "JSON", user,true, true)
					.then().log().all()
						.assertThat().statusCode(APIHttpStatus.CREATED_201.getCode())
							.extract().path("id");
		
		System.out.print("User id: "+ userId);
		
		
		
						
		//2. GET:
		
		restClient.get(GOREST_ENDPOINT+"/"+userId, true, true)
					.then().log().all()
						.assertThat()
							.statusCode(APIHttpStatus.OK_200.getCode());
		
	}

}

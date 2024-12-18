package com.qa.gorest.tests;

import java.util.List;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.qa.gorest.base.BaseTest;
import com.qa.gorest.client.RestClient;
import com.qa.gorest.constants.APIHttpStatus;
import com.qa.gorest.utils.JsonPathValidator;
import io.restassured.response.Response;

public class CircuitTest extends BaseTest{
	
	@BeforeMethod
	public void getUserSetup() {
		restClient = new RestClient(prop, baseURI);
	}
	
	@Test
	public void getCircuitTest() {
		
		
		Response circuitResponse=restClient.get(CIRCUIT_ENDPOINT+"/2017/circuits.json",false, false);
		circuitResponse
					.then()
						.assertThat()
							.statusCode(APIHttpStatus.OK_200.getCode());
		
		
		//FYI you can create a utility liket JsonPathValidator to use or use the normal bdd style option with RestAssure
		JsonPathValidator jsV= new JsonPathValidator();
		List<String>countryList=jsV.readList(circuitResponse, "$.MRData.CircuitTable.Circuits[?(@.circuitId == 'shanghai')].Location.country");
		System.out.println(countryList);
		Assert.assertTrue(countryList.contains("China"));			
		
		//.then().log().all()
		//		.assertThat().statusCode(APIHttpStatus.OK_200.getCode());
		}

}
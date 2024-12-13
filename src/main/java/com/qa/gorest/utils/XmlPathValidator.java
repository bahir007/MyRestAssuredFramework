package com.qa.gorest.utils;

import java.util.List;
import java.util.Map;

import com.jayway.jsonpath.PathNotFoundException;
import com.qa.gorest.frameworkexception.APIFrameworkException;

import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;

public class XmlPathValidator {
	
	
	private XmlPath getXmlPath(Response response) {
		String responseBody = response.body().asString();
		
		return new XmlPath(responseBody);
		
	}
	
	public <T> T read(Response response, String xmlPathExpression) {
		
		XmlPath xmlPath = getXmlPath(response);
		try {
		
		return xmlPath.get(xmlPathExpression);
		
		}catch (PathNotFoundException e) {
			e.printStackTrace();
			throw new APIFrameworkException( xmlPathExpression + "is not found...");
		}
		
	}
	
	public <T> List<T> readList(Response response, String jxmlPathExpression) {
		
		XmlPath xmlPath = getXmlPath(response);
		try {
		
		return xmlPath.getList(jxmlPathExpression);
		
		}catch (PathNotFoundException e) {
			e.printStackTrace();
			throw new APIFrameworkException(jxmlPathExpression + "is not found...");
		}
		
	}
	
	//FYI normally in the real world, we don't use that much this option and the two above is enough for xml validation
	public <T> List<Map<String, T>> readListOfMaps(Response response, String jxmlPathExpression) {
		
		XmlPath xmlPath = getXmlPath(response);
		try {
		
		return xmlPath.getList(jxmlPathExpression);
		
		}catch (PathNotFoundException e) {
			e.printStackTrace();
			throw new APIFrameworkException(jxmlPathExpression + "is not found...");
		}
		
	}
	
	

}

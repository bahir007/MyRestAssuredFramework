package com.qa.gorest.utils;

public class StringUtils {
	
	public static String getRandomEmailId() {
		return "api"+System.currentTimeMillis()+"@mail.com";
		//return "apiautomation"+UUID.randomUUID()+"@mail.com";
	}

}

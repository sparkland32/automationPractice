package com.automation.pageObjects;

public abstract class BasePage {
	
    private static String siteUrl = "http://automationpractice.com";
    
    public static String GetSiteUrl()
    {
    		return siteUrl;
    }
}

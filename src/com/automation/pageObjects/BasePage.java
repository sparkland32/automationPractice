package com.automation.pageObjects;

import java.io.IOException;

import com.automation.dependencies.PropertiesFileManager;

public abstract class BasePage {
	
    private static String siteUrl = "";
    
    public static void SetSiteUrlViaProperties() {
        try {
        	siteUrl = PropertiesFileManager.GetProperty("gui_url");
        }
        catch (IOException e)
        {
        	e.printStackTrace();
        }
    }
    
    public static String GetSiteUrl()
    {
    	if (siteUrl.isEmpty())
    		SetSiteUrlViaProperties();
    	return siteUrl;
    }
}

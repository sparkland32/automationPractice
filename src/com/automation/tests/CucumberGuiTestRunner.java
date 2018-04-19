package com.automation.tests;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(features = "Feature/GuiTest.feature",
		glue = {"com.automation.stepDefinitions"},
		format = {"json:automation-test-gui.json"})
public class CucumberGuiTestRunner
{
	
}
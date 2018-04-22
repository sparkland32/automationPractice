package com.automation.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.automation.tests.testrunners.CucumberApiTestRunner;
import com.automation.tests.testrunners.CucumberGuiTestRunner;
import com.automation.tests.testrunners.CucumberReportBuilderTestRunner;

@RunWith(Suite.class)
@SuiteClasses({
		CucumberGuiTestRunner.class,
	    CucumberApiTestRunner.class, 
        CucumberReportBuilderTestRunner.class})
public class TestRunner {

}
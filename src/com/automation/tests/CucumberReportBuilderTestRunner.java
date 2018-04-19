package com.automation.tests;

import static org.junit.Assert.assertTrue;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import org.junit.After;
import org.junit.Test;

import com.automation.dependencies.CucumberReport;

import net.masterthought.cucumber.Reportable;

public class CucumberReportBuilderTestRunner {

	@Test
	public void ReportOutputExists() {
		
		File jsonOutput = new File("./automation-test.json");
		assertTrue(jsonOutput.exists());
	}
	
	@After
	public void ReportBuilder() {
		Reportable result = CucumberReport.BuildCucumberReport("./Report");
		if (!result.equals(null)) {
			try {
				File htmlFile = new File("./Report/cucumber-html-reports/overview-features.html");
				Desktop.getDesktop().browse(htmlFile.toURI());
			} catch (IOException e) {
				System.out.println("Failed to open output Cucumber Report successfully.");
			}
		}
	}
}

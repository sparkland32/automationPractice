package com.automation.tests.testrunners;

import static org.junit.Assert.assertTrue;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import org.junit.AfterClass;
import org.junit.Test;

import com.automation.dependencies.CucumberReport;
import com.automation.dependencies.PropertiesFileManager;

import net.masterthought.cucumber.Reportable;

public class CucumberReportBuilderTestRunner {
	
	@Test
	public void GuiReportOutputExists() {
		
		File jsonOutput = new File("./automation-test-gui.json");
		assertTrue(jsonOutput.exists());
	}	
	
	@Test
	public void ApiReportOutputExists() {
		
		File jsonOutput = new File("./automation-test-api.json");
		assertTrue(jsonOutput.exists());
	}	
	
	@AfterClass
	public static void AfterClass() throws IOException {
		Reportable result = CucumberReport.BuildCucumberReport(PropertiesFileManager.GetProperty("report_output_directory"));
		if (!result.equals(null)) {
			try {
				File htmlFile = new File(PropertiesFileManager.GetProperty("report_output_directory") + "/cucumber-html-reports/overview-features.html");
				Desktop.getDesktop().browse(htmlFile.toURI());
			} catch (IOException e) {
				System.out.println("Failed to open output Cucumber Report successfully:\n\r " + e.getMessage());
			}
		}
	}
}

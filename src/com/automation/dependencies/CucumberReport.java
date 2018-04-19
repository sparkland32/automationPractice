package com.automation.dependencies;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;
import net.masterthought.cucumber.Reportable;

public class CucumberReport {
	
	public static Reportable BuildCucumberReport(String target) {
		File reportOutputDirectory = new File(target);
		List<String> jsonFiles = new ArrayList<>();
		jsonFiles.add("automation-test-gui.json");
		jsonFiles.add("automation-test-api.json");

		String buildNumber = "1";
		String projectName = "Automation Practice";
		boolean runWithJenkins = false;
		boolean parallelTesting = false;

		Configuration configuration = new Configuration(reportOutputDirectory, projectName);
		configuration.setParallelTesting(parallelTesting);
		configuration.setRunWithJenkins(runWithJenkins);
		configuration.setBuildNumber(buildNumber);
		configuration.addClassifications("Browser", "Firefox");
		configuration.addClassifications("Branch", "release/1.0");

		//List<String> classificationFiles = new ArrayList<>();
		//classificationFiles.add("properties-1.properties");
		//classificationFiles.add("properties-2.properties");
		//configuration.addClassificationFiles(classificationFiles);

		ReportBuilder reportBuilder = new ReportBuilder(jsonFiles, configuration);
		Reportable result = reportBuilder.generateReports();
		
		return result;
	}

}

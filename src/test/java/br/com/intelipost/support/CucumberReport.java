package br.com.intelipost.support;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;

/**
 * Gerador de report Utiliza o cam
 * 
 * @author duekita
 *
 */
public class CucumberReport {

	public static void generateReport() {
		File reportOutputDirectory = new File(CucumberRunner.getLastReportPath());
		List<String> jsonFiles = new ArrayList<>();

		boolean runWithJenkins = false;
		boolean parallelTesting = false;
		jsonFiles.add(CucumberRunner.getLastReportPath() + "/results.json");
		Configuration configuration = new Configuration(reportOutputDirectory,
				ConfigurationProperties.getProperty("projectName"));
		configuration.setParallelTesting(parallelTesting);
		configuration.setRunWithJenkins(runWithJenkins);
		new ReportBuilder(jsonFiles, configuration).generateReports();
	}

	public static void main(String[] args) {
		System.out.println(System.getProperty("os.name"));
	}

}

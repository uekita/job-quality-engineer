package br.com.intelipost.support;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CucumberRunner {

	private static String reportPath = "./Reports";
	private static String lastReportPath;

	public static void main(String[] args) {
		ConfigurationProperties.setProperties();
		verifyReportPath();
		runCucumber();
		CucumberReport.generateReport();
		openReportAfterTest();
	}

	/**
	 * Executa o Cucumber utilizando CLI Runner verifica a existência de tags no
	 * arquivo de execução
	 * 
	 * 
	 */
	public static void runCucumber() {
		try {
			String feature = ConfigurationProperties.getProperty("feature");
			String tag = ConfigurationProperties.getProperty("tag");
			String glue = ConfigurationProperties.getProperty("steps");
			String[] runnerArguments;
			String[] plugins = { "pretty", "json:" + lastReportPath + "/results.json" };
			if (tag.equals("")) {
				runnerArguments = new String[] { "-m", "-p", plugins[0], "-p", plugins[1], "-g", glue, feature };
			} else {
				runnerArguments = new String[] { "-m", "-p", plugins[0], "-p", plugins[1], "-g", glue, feature, "-t",
						tag };
			}
			cucumber.api.cli.Main.run(runnerArguments, Thread.currentThread().getContextClassLoader());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String getReportPath() {
		return reportPath;
	}

	public static void verifyReportPath() {
		if (!ConfigurationProperties.getProperty("reportPath").equals("")) {
			System.out.println("REPORT PATH VAZIO");
			setReportPath(ConfigurationProperties.getProperty("reportPath"));
		}
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
		Date date = new Date();
		lastReportPath = reportPath + "/Report_" + sdf.format(date);
	}

	public static void setReportPath(String reportPath) {
		CucumberRunner.reportPath = reportPath;
	}

	/**
	 * Abre o report após a execução do teste, utilizando o navegador padrão do
	 * windows
	 */
	public static void openReportAfterTest() {
		if (ConfigurationProperties.getProperty("openReportAfterTest").equalsIgnoreCase("true")) {
			String os = System.getProperty("os.name").toLowerCase();
			try {
				if (os.contains("windows")) {
					Runtime rt = Runtime.getRuntime();
					File file = new File(getLastReportPath() + "/cucumber-html-reports/overview-features.html");
					String AbsoluteFilePath = file.getAbsolutePath();
					rt.exec("rundll32 url.dll,FileProtocolHandler " + "file:///" + AbsoluteFilePath);
				} else {
					// TODO implementar para linux
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static String getLastReportPath() {
		return lastReportPath;
	}

	public static void setLastReportPath(String lastReportPath) {
		CucumberRunner.lastReportPath = lastReportPath;
	}
}

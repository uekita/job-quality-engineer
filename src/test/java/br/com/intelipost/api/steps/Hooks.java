package br.com.intelipost.api.steps;

import cucumber.api.Scenario;
import cucumber.api.java.Before;

/**
 * @author duekita
 *
 */

public class Hooks {

	private static Scenario scenario;

	/**
	 * Sem este método não é possível utilizar o objeto Scenario
	 * 
	 * @param scenario
	 */
	@Before
	public void keepScenarion(Scenario scenario) {
		Hooks.scenario = scenario;
	}

	/**
	 * Escreve no relatório de execuçãoo do cucumber
	 * 
	 * @param text
	 */
	public static void write(String text) {
		scenario.write(text);
	}
}

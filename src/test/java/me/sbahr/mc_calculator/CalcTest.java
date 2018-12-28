package me.sbahr.mc_calculator;

import java.util.UUID;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import me.sbahr.mc_calculator.manager.CalculatorCache;
import me.sbahr.mc_calculator.manager.Operation;

/**
 * Unit test for the calculator.
 */
public class CalcTest extends TestCase {
	
	/**
	 * Create the test case.
	 *
	 * @param testName name of the test case
	 */
	public CalcTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(CalcTest.class);
	}

	/**
	 * Rigourous Test :-)
	 */
	public void testAdd() {
		
		UUID randomUUID = UUID.randomUUID();
		
		CalculatorCache cache = new CalculatorCache(randomUUID);
		cache.setInputLeft("2");
		cache.setInputRight("3");
		cache.setOperation(Operation.ADD);
		
		int result = (int) cache.calculate();
		assertTrue(result == 5);
	}
}

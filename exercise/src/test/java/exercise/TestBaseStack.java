package exercise;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import exercise.exception.InsufficientElementsException;

public class TestBaseStack {

	public static Calculator calculator;
	
	/**
	 * 
	 * Test class: TODO
	 */
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {	
		calculator = new Calculator(DataType.DOUBLE);
	}

	@Test
	public void testExerciseValues() {
		assertEquals("Case 1: ", calculator.process("2 1 +"), true);
	}


	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
}

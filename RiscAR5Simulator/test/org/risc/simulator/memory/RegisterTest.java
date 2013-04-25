package org.risc.simulator.memory;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class RegisterTest {

	private Register r = null;

	@Before
	public void setUp() throws Exception {
		r = new Register("test", 10, 4);
	}

	@After
	public void tearDown() throws Exception {
		r = null;
	}

	@Test
	public void testGetData() {
		assertTrue(10 == r.getData());
	}

	@Test
	public void testSetData() {
		int expected = -8;
		r.setData(expected);
		assertTrue(expected == r.getData());
	}

	@Test(expected = NumberFormatException.class)
	public void testNumberThatDoesNotFit() {
		r.setData(-9);
	}
	
	@Test(expected = NumberFormatException.class)
	public void testUpperNumberThatDoesNotFit() {
		r.setData(16);
	}

}
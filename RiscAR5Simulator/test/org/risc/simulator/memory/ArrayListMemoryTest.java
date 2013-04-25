package org.risc.simulator.memory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ArrayListMemoryTest {

	private ArrayListMemory m;
	private static final int LEN = 4, CELL = 8;

	@Before
	public void setUp() throws Exception {
		m = new ArrayListMemory(LEN, CELL);
	}

	@After
	public void tearDown() throws Exception {
		m = null;
	}

	@Test
	public void testGetDataAtInt() {
		assertTrue(0 == m.getDataAt(0));

	}

	@Test
	public void testSetAndGetDataAtWithNumBits() {
		m.setDataAt(0, -258, 10);
		assertTrue(-258 == m.getDataAt(0, 2));
	}

	@Test
	public void testSetWithNumBitsLessThanCellSize() {
		int expected = 4;
		m.setDataAt(0, expected, 4);
		assertTrue(expected == m.getDataAt(0));
	}

	@Test
	public void testSetWithNumBitsLessThanNumber() {
		int expected = 4;
		m.setDataAt(0, expected, 4);
		assertTrue(expected == m.getDataAt(0));
	}

	@Test
	public void testSetDataAt() {
		int expected = 3;
		int test = 1 << CELL;
		test += 3;
		m.setDataAt(1, test);
		assertTrue(expected == m.getDataAt(1));
	}
	
	@Test
	public void testSetWithNumberOfBitsGreaterThanCell(){
		int test = 1 << CELL;
		m.setDataAt(2, test);
		assertEquals(0, m.getDataAt(2));
	}

}
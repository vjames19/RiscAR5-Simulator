package org.risc.simulator.util;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.risc.simulator.util.NumberUtils.*;

public class NumberUtilsTest {


	@Test
	public void testIntToTwosComplementString() {
		String expected = "00011";
		assertEquals(expected, intToTwosComplementString(3, 5));

		expected = "111101";
		assertEquals(expected, intToTwosComplementString(-3, 6));

		expected = "10";
		assertEquals(expected, intToTwosComplementString(10, 2));
	}

	@Test
	public void testFitsInBits() {
		int bits = 3;
		assertTrue(fitsInBits(-4, bits));
		assertTrue(fitsInBits(3, bits));
		assertFalse(fitsInBits(-5, bits));
		assertFalse(fitsInBits(8, bits));

	}

	@Test(expected = NumberFormatException.class)
	public void testCheckFitsInBits() {
		checkFitsInBits(-5, 2);
	}

	@Test
	public void testGetUnsignedValueOf() {
		int expected = 14;
		int test = -258;
		assertTrue(expected == getUnsignedValueOf(test, 0, 3, 4));
		assertTrue(15 == getUnsignedValueOf(test, 0, 3, 8));
		assertTrue(expected == getUnsignedValueOf(test, 8, 11, 12));
	}

	@Test
	public void testGetSignedValueOf() {
		int expected = -1;
		int test = 0xaf;
		assertTrue(expected == getSignedValueOf(test, 0, 2, 3));
	}

	@Test
	public void testGetNumberOfBitsNeeded() {
		int expected = 3, test = -4;
		assertTrue(expected == getNumberOfBitsNeededInTwosComplement(test));
		test = 3;
		assertTrue(expected == getNumberOfBitsNeededInTwosComplement(test));
	}

	@Test
	public void testIntToHex() {
		String expected = "102";
		assertEquals(expected, intToHexString(258, 3));

		expected = "0102";
		assertEquals(expected, intToHexString(258, 4));

		expected = "FEFE";
		assertEquals(expected, intToHexString(-258, 4));

		expected = "FFEFE";
		assertEquals(expected, intToHexString(-258, 5));
	}
	
	@Test
	public void testGetMsb(){
		int number = 10;
		assertEquals(0, NumberUtils.getMSB(number));
		assertEquals(1, NumberUtils.getMSB(-10));
	}
	
	public void testGetLsb(){
		assertEquals(1, NumberUtils.getLSB(11));
		assertEquals(1, NumberUtils.getLSB(1));
		
		assertEquals(0, NumberUtils.getLSB(10));
		assertEquals(0, NumberUtils.getLSB(-5));
		
	}

}
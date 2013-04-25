package org.risc.simulator.instructionSet;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.risc.simulator.memory.Register;
import org.risc.simulator.memory.StatusRegister;
import org.risc.simulator.processor.Processor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ArithmeticLogicTest {

	Register acc = null;
	Register op = null;
	StatusRegister sr = null;

	@Before
	public void setUp() throws Exception {
		acc = new Register(Processor.ACC, 0, Processor.REGISTER_WIDTH);
		op = new Register(Processor.GPR_PREFIX + "1", 0, Processor.REGISTER_WIDTH);
		sr = new StatusRegister(Processor.SR);
	}

	@After
	public void tearDown() throws Exception {
		acc = null;
		op = null;
		sr = null;
	}

	@Test
	public void testAnd() throws Exception {
		acc.setData(5);
		op.setData(5);
		ArithmeticLogic.and(acc, op, sr);
		assertTrue(acc.getData() == 5);

		acc.setData(5);
		op.setData(0);
		ArithmeticLogic.and(acc, op, sr);
		assertTrue(acc.getData() == 0);

		acc.setData(-5);
		op.setData(5);
		ArithmeticLogic.and(acc, op, sr);
		assertTrue(acc.getData() == 1);
	}

	@Test
	public void testOr() throws Exception {
		acc.setData(5);
		op.setData(5);
		ArithmeticLogic.or(acc, op, sr);
		assertTrue(acc.getData() == 5);

		acc.setData(5);
		op.setData(7);
		ArithmeticLogic.or(acc, op, sr);
		assertTrue(acc.getData() == 7);
	}

	@Test
	public void testAddC() throws Exception {
		acc.setData(5);
		op.setData(5);
		sr.setFlagValue(StatusRegister.Flag.CARRY, true);
		ArithmeticLogic.addC(acc, op, sr);
		assertTrue(acc.getData() == 11);

		acc.setData(5);
		op.setData(5);
		sr.setFlagValue(StatusRegister.Flag.CARRY, false);
		ArithmeticLogic.addC(acc, op, sr);
		assertTrue(acc.getData() == 10);
	}

	@Test
	public void testSub() throws Exception {
		acc.setData(5);
		op.setData(5);
		ArithmeticLogic.sub(acc, op, sr);
		assertTrue(acc.getData() == 0);

		acc.setData(5);
		op.setData(10);
		ArithmeticLogic.sub(acc, op, sr);
		assertTrue(acc.getData() == -5);
	}

	@Test
	public void testMul() throws Exception {
		acc.setData(5); // 00000101
		op.setData(5); // 00000101
		ArithmeticLogic.mul(acc, op, sr);
		assertTrue(acc.getData() == 25); // 00011001
		acc.setData(5); // 00000101
		op.setData(10); // 00001010
		ArithmeticLogic.mul(acc, op, sr);
		assertTrue(acc.getData() == 50); // 00110010 // unsigned
//		assertTrue(acc.getData() == -30); // signed

		acc.setData(-1); // 11111111
		op.setData(-1); // 11111111
		ArithmeticLogic.mul(acc, op, sr);
		assertEquals(acc.getData(), -31); // unsigned
//		assertEquals(acc.getData(), 1); // signed
	}

	@Test
	public void testNot() throws Exception {
		acc.setData(5); // 00000101;
		ArithmeticLogic.not(acc, sr); // 11111010;
		assertTrue(acc.getData() == -6);

		acc.setData(-10); // 11110110
		ArithmeticLogic.not(acc, sr); // 00001001
		assertTrue(acc.getData() == 9);

		acc.setData(127); // 01111111
		ArithmeticLogic.not(acc, sr); // 10000000
		assertTrue(acc.getData() == -128);

		acc.setData(-128); // 10000000
		ArithmeticLogic.not(acc, sr); // 01111111
		assertTrue(acc.getData() == 127);
	}

	@Test
	public void testNeg() throws Exception {
		acc.setData(0);
		ArithmeticLogic.neg(acc, sr);
		assertEquals(acc.getData(), 0);

		acc.setData(-128);
		ArithmeticLogic.neg(acc, sr);
		assertEquals(acc.getData(), -128);

		acc.setData(127);
		ArithmeticLogic.neg(acc, sr);
		assertEquals(acc.getData(), -127);

		acc.setData(-1);
		ArithmeticLogic.neg(acc, sr);
		assertEquals(acc.getData(), 1);
	}

	@Test
	public void testRrc() throws Exception {
		acc.setData(127);
		sr.setFlagValue(StatusRegister.Flag.CARRY, false);
		ArithmeticLogic.rrc(acc, sr);
		assertTrue(acc.getData() == 63);
		acc.setData(127);
		sr.setFlagValue(StatusRegister.Flag.CARRY, true);
		ArithmeticLogic.rrc(acc, sr);
		assertTrue(acc.getData() == -65);

		acc.setData(-128);
		sr.setFlagValue(StatusRegister.Flag.CARRY, false);
		ArithmeticLogic.rrc(acc, sr);
		assertTrue(acc.getData() == 64);
		acc.setData(-128);
		sr.setFlagValue(StatusRegister.Flag.CARRY, true);
		ArithmeticLogic.rrc(acc, sr);
		assertTrue(acc.getData() == -64);

		acc.setData(-1);
		sr.setFlagValue(StatusRegister.Flag.CARRY, false);
		ArithmeticLogic.rrc(acc, sr);
		assertTrue(acc.getData() == 127);
		acc.setData(-1);
		sr.setFlagValue(StatusRegister.Flag.CARRY, true);
		ArithmeticLogic.rrc(acc, sr);
		assertTrue(acc.getData() == -1);

		acc.setData(0);
		sr.setFlagValue(StatusRegister.Flag.CARRY, false);
		ArithmeticLogic.rrc(acc, sr);
		assertTrue(acc.getData() == 0);
		acc.setData(0);
		sr.setFlagValue(StatusRegister.Flag.CARRY, true);
		ArithmeticLogic.rrc(acc, sr);
		assertTrue(acc.getData() == -128);
	}

	@Test
	public void testRlc() throws Exception {
		acc.setData(0);
		sr.setFlagValue(StatusRegister.Flag.CARRY, false);
		ArithmeticLogic.rlc(acc, sr);
		assertTrue(acc.getData() == 0);

		acc.setData(10);
		sr.setFlagValue(StatusRegister.Flag.CARRY, true);
		ArithmeticLogic.rlc(acc, sr);
		assertTrue(acc.getData() == 21);

		acc.setData(10);
		sr.setFlagValue(StatusRegister.Flag.CARRY, false);
		ArithmeticLogic.rlc(acc, sr);
		assertTrue(acc.getData() == 20);

		acc.setData(-127);
		sr.setFlagValue(StatusRegister.Flag.CARRY, true);
		ArithmeticLogic.rlc(acc, sr);
		assertTrue(acc.getData() == 3);

		acc.setData(128);
		sr.setFlagValue(StatusRegister.Flag.CARRY, false);
		ArithmeticLogic.rlc(acc, sr);
		assertTrue(acc.getData() == 0);

		acc.setData(-128);
		sr.setFlagValue(StatusRegister.Flag.CARRY, false);
		ArithmeticLogic.rlc(acc, sr);
		assertTrue(acc.getData() == 0);
		acc.setData(-128);
		sr.setFlagValue(StatusRegister.Flag.CARRY, true);
		ArithmeticLogic.rlc(acc, sr);
		assertTrue(acc.getData() == 1);

		acc.setData(127);
		sr.setFlagValue(StatusRegister.Flag.CARRY, false);
		ArithmeticLogic.rlc(acc, sr);
		assertTrue(acc.getData() == -2);
		acc.setData(127);
		sr.setFlagValue(StatusRegister.Flag.CARRY, true);
		ArithmeticLogic.rlc(acc, sr);
		assertTrue(acc.getData() == -1);
	}

}
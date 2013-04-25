package org.risc.simulator.instructionSet;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.risc.simulator.memory.Register;
import org.risc.simulator.memory.StatusRegister;
import org.risc.simulator.memory.StatusRegister.Flag;
import org.risc.simulator.processor.Processor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class FlagManagementTest {

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
	public void testAndFlags() throws Exception {
		acc.setData(5);
		op.setData(5);
		ArithmeticLogic.and(acc, op, sr);
		assertTrue(acc.getData() == 5);
		assertTrue(sr.getFlagValue(StatusRegister.Flag.NEGATIVE) == 0);
		assertTrue(sr.getFlagValue(StatusRegister.Flag.ZERO) == 0);

		acc.setData(5);
		op.setData(0);
		ArithmeticLogic.and(acc, op, sr);
		assertTrue(acc.getData() == 0);
		assertTrue(sr.getFlagValue(StatusRegister.Flag.NEGATIVE) == 0);
		assertTrue(sr.getFlagValue(StatusRegister.Flag.ZERO) == 1);

		acc.setData(-5);
		op.setData(5);
		ArithmeticLogic.and(acc, op, sr);
		assertTrue(acc.getData() == 1);
		assertTrue(sr.getFlagValue(StatusRegister.Flag.NEGATIVE) == 0);
		assertTrue(sr.getFlagValue(StatusRegister.Flag.ZERO) == 0);

		acc.setData(-127);
		op.setData(-127);
		ArithmeticLogic.and(acc, op, sr);
		assertTrue(acc.getData() == -127);
		assertTrue(sr.getFlagValue(StatusRegister.Flag.NEGATIVE) == 1);
		assertTrue(sr.getFlagValue(StatusRegister.Flag.ZERO) == 0);
	}

	@Test
	public void testOrFlags() throws Exception {
		acc.setData(5);
		op.setData(5);
		ArithmeticLogic.or(acc, op, sr);
		assertTrue(acc.getData() == 5);
		assertTrue(sr.getFlagValue(StatusRegister.Flag.NEGATIVE) == 0);
		assertTrue(sr.getFlagValue(StatusRegister.Flag.ZERO) == 0);

		acc.setData(5);
		op.setData(7);
		ArithmeticLogic.or(acc, op, sr);
		assertTrue(acc.getData() == 7);
		assertTrue(sr.getFlagValue(StatusRegister.Flag.NEGATIVE) == 0);
		assertTrue(sr.getFlagValue(StatusRegister.Flag.ZERO) == 0);

		acc.setData(5);
		op.setData(0);
		ArithmeticLogic.or(acc, op, sr);
		assertTrue(acc.getData() == 5);
		assertTrue(sr.getFlagValue(StatusRegister.Flag.NEGATIVE) == 0);
		assertTrue(sr.getFlagValue(StatusRegister.Flag.ZERO) == 0);

		acc.setData(-128);
		op.setData(-127);
		ArithmeticLogic.or(acc, op, sr);
		assertTrue(acc.getData() == -127);
		assertTrue(sr.getFlagValue(StatusRegister.Flag.NEGATIVE) == 1);
		assertTrue(sr.getFlagValue(StatusRegister.Flag.ZERO) == 0);

	}

	@Test
	public void testAddCFlags() throws Exception {
		acc.setData(5);
		op.setData(5);
		sr.setFlagValue(StatusRegister.Flag.CARRY, true);
		ArithmeticLogic.addC(acc, op, sr);
		assertTrue(acc.getData() == 11);
		assertTrue(sr.getFlagValue(StatusRegister.Flag.NEGATIVE) == 0);
		assertTrue(sr.getFlagValue(StatusRegister.Flag.ZERO) == 0);
		assertTrue(sr.getFlagValue(StatusRegister.Flag.CARRY) == 0);
		assertTrue(sr.getFlagValue(StatusRegister.Flag.OVERFLOW) == 0);

		acc.setData(-10);
		op.setData(5);
		sr.setFlagValue(StatusRegister.Flag.CARRY, false);
		ArithmeticLogic.addC(acc, op, sr);
		assertTrue(acc.getData() == -5);
		assertTrue(sr.getFlagValue(StatusRegister.Flag.NEGATIVE) == 1);
		assertTrue(sr.getFlagValue(StatusRegister.Flag.ZERO) == 0);
		assertTrue(sr.getFlagValue(StatusRegister.Flag.CARRY) == 0);
		assertTrue(sr.getFlagValue(StatusRegister.Flag.OVERFLOW) == 0);

		acc.setData(127);
		op.setData(120);
		sr.setFlagValue(StatusRegister.Flag.CARRY, true);
		ArithmeticLogic.addC(acc, op, sr);
		assertTrue(acc.getData() == -8);
		assertTrue(sr.getFlagValue(StatusRegister.Flag.NEGATIVE) == 1);
		assertTrue(sr.getFlagValue(StatusRegister.Flag.ZERO) == 0);
		assertTrue(sr.getFlagValue(StatusRegister.Flag.CARRY) == 0);
		assertTrue(sr.getFlagValue(StatusRegister.Flag.OVERFLOW) == 1);

		acc.setData(-127);
		op.setData(127);
		sr.setFlagValue(StatusRegister.Flag.CARRY, false);
		ArithmeticLogic.addC(acc, op, sr);
		assertTrue(acc.getData() == 0);
		assertTrue(sr.getFlagValue(StatusRegister.Flag.NEGATIVE) == 0);
		assertTrue(sr.getFlagValue(StatusRegister.Flag.ZERO) == 1);
		assertTrue(sr.getFlagValue(StatusRegister.Flag.CARRY) == 1);
		assertTrue(sr.getFlagValue(StatusRegister.Flag.OVERFLOW) == 0);

		acc.setData(127);
		op.setData(-128);
		sr.setFlagValue(StatusRegister.Flag.CARRY, false);
		ArithmeticLogic.addC(acc, op, sr);
		assertTrue(acc.getData() == -1);
		assertTrue(sr.getFlagValue(StatusRegister.Flag.NEGATIVE) == 1);
		assertTrue(sr.getFlagValue(StatusRegister.Flag.ZERO) == 0);
		assertTrue(sr.getFlagValue(StatusRegister.Flag.CARRY) == 0);
		assertTrue(sr.getFlagValue(StatusRegister.Flag.OVERFLOW) == 0);

		acc.setData(128);
		op.setData(127);
		sr.setFlagValue(StatusRegister.Flag.CARRY, true);
		ArithmeticLogic.addC(acc, op, sr);
		assertTrue(acc.getData() == 0);
		assertTrue(sr.getFlagValue(StatusRegister.Flag.NEGATIVE) == 0);
		assertTrue(sr.getFlagValue(StatusRegister.Flag.ZERO) == 0);
		assertTrue(sr.getFlagValue(StatusRegister.Flag.CARRY) == 1);
		assertTrue(sr.getFlagValue(StatusRegister.Flag.OVERFLOW) == 0);

		acc.setData(-114);
		op.setData(-114);
		sr.setFlagValue(StatusRegister.Flag.CARRY, true);
		ArithmeticLogic.addC(acc, op, sr);
		assertTrue(acc.getData() == 29);
		assertTrue(sr.getFlagValue(StatusRegister.Flag.NEGATIVE) == 0);
		assertTrue(sr.getFlagValue(StatusRegister.Flag.ZERO) == 0);
		assertTrue(sr.getFlagValue(StatusRegister.Flag.CARRY) == 1);
		assertTrue(sr.getFlagValue(StatusRegister.Flag.OVERFLOW) == 1);
	}

	@Test
	public void testSubFlags() throws Exception {
		acc.setData(5);
		op.setData(5);
		sr.setFlagValue(Flag.CARRY, true);
		ArithmeticLogic.sub(acc, op, sr);
		assertTrue(acc.getData() == 0);
		assertTrue(sr.getFlagValue(StatusRegister.Flag.NEGATIVE) == 0);
		assertTrue(sr.getFlagValue(StatusRegister.Flag.ZERO) == 1);
		assertTrue(sr.getFlagValue(StatusRegister.Flag.CARRY) == 0);
		assertTrue(sr.getFlagValue(StatusRegister.Flag.OVERFLOW) == 0);

		acc.setData(5);
		op.setData(10);
		sr.setFlagValue(Flag.CARRY, false);
		ArithmeticLogic.sub(acc, op, sr);
		assertTrue(acc.getData() == -5);
		assertTrue(sr.getFlagValue(StatusRegister.Flag.NEGATIVE) == 1);
		assertTrue(sr.getFlagValue(StatusRegister.Flag.ZERO) == 0);
		assertTrue(sr.getFlagValue(StatusRegister.Flag.CARRY) == 1);
		assertTrue(sr.getFlagValue(StatusRegister.Flag.OVERFLOW) == 1);

		acc.setData(-128);
		op.setData(-1);
		ArithmeticLogic.sub(acc, op, sr);
		assertTrue(acc.getData() == -127);
		assertTrue(sr.getFlagValue(StatusRegister.Flag.NEGATIVE) == 1);
		assertTrue(sr.getFlagValue(StatusRegister.Flag.ZERO) == 0);
		assertTrue(sr.getFlagValue(StatusRegister.Flag.CARRY) == 1);
		assertTrue(sr.getFlagValue(StatusRegister.Flag.OVERFLOW) == 0);

		acc.setData(-12);
		op.setData(-15);
		sr.setFlagValue(Flag.CARRY, true);
		ArithmeticLogic.sub(acc, op, sr);
		assertTrue(acc.getData() == 3);
		assertTrue(sr.getFlagValue(StatusRegister.Flag.NEGATIVE) == 0);
		assertTrue(sr.getFlagValue(StatusRegister.Flag.ZERO) == 0);
		assertTrue(sr.getFlagValue(StatusRegister.Flag.CARRY) == 0);
		assertTrue(sr.getFlagValue(StatusRegister.Flag.OVERFLOW) == 1);

		acc.setData(127);
		op.setData(-128);
		sr.setFlagValue(Flag.CARRY, true);
		ArithmeticLogic.sub(acc, op, sr);
		assertTrue(acc.getData() == -1);
		assertTrue(sr.getFlagValue(StatusRegister.Flag.NEGATIVE) == 1);
		assertTrue(sr.getFlagValue(StatusRegister.Flag.ZERO) == 0);
		assertTrue(sr.getFlagValue(StatusRegister.Flag.CARRY) == 0);
		assertTrue(sr.getFlagValue(StatusRegister.Flag.OVERFLOW) == 0);
		
		acc.setData(1);
		op.setData(5);
		sr.setFlagValue(Flag.CARRY, false);
		sr.setFlagValue(Flag.OVERFLOW, false);
		ArithmeticLogic.sub(acc, op, sr);
		assertTrue(acc.getData() == -4);
		assertTrue(sr.getFlagValue(StatusRegister.Flag.NEGATIVE) == 1);
		assertTrue(sr.getFlagValue(StatusRegister.Flag.ZERO) == 0);
		assertTrue(sr.getFlagValue(StatusRegister.Flag.CARRY) == 1);
		assertTrue(sr.getFlagValue(StatusRegister.Flag.OVERFLOW) == 1);


	}

	@Test
	public void testMulFlags() throws Exception {
		acc.setData(5); // 00000101
		op.setData(5); // 00000101
		ArithmeticLogic.mul(acc, op, sr);
		assertTrue(acc.getData() == 25); // 00011001
		assertTrue(sr.getFlagValue(StatusRegister.Flag.NEGATIVE) == 0);
		assertTrue(sr.getFlagValue(StatusRegister.Flag.ZERO) == 0);
		assertTrue(sr.getFlagValue(StatusRegister.Flag.CARRY) == 0);
		assertTrue(sr.getFlagValue(StatusRegister.Flag.OVERFLOW) == 0);

		acc.setData(5); // 00000101
		op.setData(10); // 00001010
		ArithmeticLogic.mul(acc, op, sr);
//		assertTrue(acc.getData() == -30); // 00110010 -> Signed
		assertTrue(acc.getData() == 50); // 00110010 -> Unsigned
//		assertTrue(sr.getFlagValue(StatusRegister.Flag.NEGATIVE) == 1); // Signed
		assertTrue(sr.getFlagValue(StatusRegister.Flag.NEGATIVE) == 0); // Unsigned
		assertTrue(sr.getFlagValue(StatusRegister.Flag.ZERO) == 0);
		assertTrue(sr.getFlagValue(StatusRegister.Flag.CARRY) == 0);
		assertTrue(sr.getFlagValue(StatusRegister.Flag.OVERFLOW) == 0);

		acc.setData(-1); // 11111111
		op.setData(-1); // 11111111
		ArithmeticLogic.mul(acc, op, sr);
		assertTrue(acc.getData() == -31); // 00110010 Unsigned
//		assertTrue(acc.getData() == 1); // Signed
		assertTrue(sr.getFlagValue(StatusRegister.Flag.NEGATIVE) == 1); // Unsigned
//		assertTrue(sr.getFlagValue(StatusRegister.Flag.NEGATIVE) == 0); // Signed
		assertTrue(sr.getFlagValue(StatusRegister.Flag.ZERO) == 0);
		assertTrue(sr.getFlagValue(StatusRegister.Flag.CARRY) == 0);
		assertTrue(sr.getFlagValue(StatusRegister.Flag.OVERFLOW) == 0);

		acc.setData(127); // 1000000
		op.setData(3); // 10000000
		ArithmeticLogic.mul(acc, op, sr);
		assertTrue(acc.getData() == 45); // unsigned
//		assertTrue(acc.getData() == -3); // signed
		assertTrue(sr.getFlagValue(StatusRegister.Flag.NEGATIVE) == 0); // unsigned
//		assertTrue(sr.getFlagValue(StatusRegister.Flag.NEGATIVE) == 1); // signed
		assertTrue(sr.getFlagValue(StatusRegister.Flag.ZERO) == 0);
		assertTrue(sr.getFlagValue(StatusRegister.Flag.CARRY) == 0);
		assertTrue(sr.getFlagValue(StatusRegister.Flag.OVERFLOW) == 0);

	}


	@Test
	public void testNotFlags() throws Exception {
		acc.setData(5); // 00000101;
		ArithmeticLogic.not(acc, sr); // 11111010;
		assertTrue(acc.getData() == -6);
		assertTrue(sr.getFlagValue(StatusRegister.Flag.NEGATIVE) == 1);
		assertTrue(sr.getFlagValue(StatusRegister.Flag.ZERO) == 0);

		acc.setData(-10); // 11110110
		ArithmeticLogic.not(acc, sr); // 00001001
		assertTrue(acc.getData() == 9);
		assertTrue(sr.getFlagValue(StatusRegister.Flag.NEGATIVE) == 0);
		assertTrue(sr.getFlagValue(StatusRegister.Flag.ZERO) == 0);

		acc.setData(127); // 01111111
		ArithmeticLogic.not(acc, sr); // 10000000
		assertTrue(acc.getData() == -128);
		assertTrue(sr.getFlagValue(StatusRegister.Flag.NEGATIVE) == 1);
		assertTrue(sr.getFlagValue(StatusRegister.Flag.ZERO) == 0);

		acc.setData(-128); // 10000000
		ArithmeticLogic.not(acc, sr); // 01111111
		assertTrue(acc.getData() == 127);
		assertTrue(sr.getFlagValue(StatusRegister.Flag.NEGATIVE) == 0);
		assertTrue(sr.getFlagValue(StatusRegister.Flag.ZERO) == 0);

		acc.setData(1); // 00000001
		ArithmeticLogic.not(acc, sr); // 11111111
		assertTrue(acc.getData() == -2);
		assertTrue(sr.getFlagValue(StatusRegister.Flag.NEGATIVE) == 1);
		assertTrue(sr.getFlagValue(StatusRegister.Flag.ZERO) == 0);
	}

	@Test
	public void testNegFlags() throws Exception {
		acc.setData(0);
		ArithmeticLogic.neg(acc, sr);
		assertTrue(acc.getData() == 0);
		assertTrue(sr.getFlagValue(StatusRegister.Flag.NEGATIVE) == 0);
		assertTrue(sr.getFlagValue(StatusRegister.Flag.ZERO) == 1);

		acc.setData(-128);
		ArithmeticLogic.neg(acc, sr);
		assertTrue(acc.getData() == -128);
		assertTrue(sr.getFlagValue(StatusRegister.Flag.NEGATIVE) == 1);
		assertTrue(sr.getFlagValue(StatusRegister.Flag.ZERO) == 0);

		acc.setData(127);
		ArithmeticLogic.neg(acc, sr);
		assertTrue(acc.getData() == -127);
		assertTrue(sr.getFlagValue(StatusRegister.Flag.NEGATIVE) == 1);
		assertTrue(sr.getFlagValue(StatusRegister.Flag.ZERO) == 0);

		acc.setData(-1);
		ArithmeticLogic.neg(acc, sr);
		assertTrue(acc.getData() == 1);
		assertTrue(sr.getFlagValue(StatusRegister.Flag.NEGATIVE) == 0);
		assertTrue(sr.getFlagValue(StatusRegister.Flag.ZERO) == 0);
	}

	@Test
	public void testRrcFlags() throws Exception {
		acc.setData(127);
		sr.setFlagValue(StatusRegister.Flag.CARRY, false);
		ArithmeticLogic.rrc(acc, sr);
		assertTrue(acc.getData() == 63);
		assertTrue(sr.getFlagValue(StatusRegister.Flag.NEGATIVE) == 0);
		assertTrue(sr.getFlagValue(StatusRegister.Flag.ZERO) == 0);
		assertTrue(sr.getFlagValue(StatusRegister.Flag.CARRY) == 1);
		assertTrue(sr.getFlagValue(StatusRegister.Flag.OVERFLOW) == 0);


		acc.setData(127);
		sr.setFlagValue(StatusRegister.Flag.CARRY, true);
		ArithmeticLogic.rrc(acc, sr);
		assertTrue(acc.getData() == -65);
		assertTrue(sr.getFlagValue(StatusRegister.Flag.NEGATIVE) == 1);
		assertTrue(sr.getFlagValue(StatusRegister.Flag.ZERO) == 0);
		assertTrue(sr.getFlagValue(StatusRegister.Flag.CARRY) == 1);
		assertTrue(sr.getFlagValue(StatusRegister.Flag.OVERFLOW) == 0);

		acc.setData(-128);
		sr.setFlagValue(StatusRegister.Flag.CARRY, false);
		ArithmeticLogic.rrc(acc, sr);
		assertTrue(acc.getData() == 64);
		assertTrue(sr.getFlagValue(StatusRegister.Flag.NEGATIVE) == 0);
		assertTrue(sr.getFlagValue(StatusRegister.Flag.ZERO) == 0);
		assertTrue(sr.getFlagValue(StatusRegister.Flag.CARRY) == 0);
		assertTrue(sr.getFlagValue(StatusRegister.Flag.OVERFLOW) == 0);

		acc.setData(-128);
		sr.setFlagValue(StatusRegister.Flag.CARRY, true);
		ArithmeticLogic.rrc(acc, sr);
		assertTrue(acc.getData() == -64);
		assertTrue(sr.getFlagValue(StatusRegister.Flag.NEGATIVE) == 1);
		assertTrue(sr.getFlagValue(StatusRegister.Flag.ZERO) == 0);
		assertTrue(sr.getFlagValue(StatusRegister.Flag.CARRY) == 0);
		assertTrue(sr.getFlagValue(StatusRegister.Flag.OVERFLOW) == 0);

		acc.setData(-1);
		sr.setFlagValue(StatusRegister.Flag.CARRY, false);
		ArithmeticLogic.rrc(acc, sr);
		assertTrue(acc.getData() == 127);
		assertTrue(sr.getFlagValue(StatusRegister.Flag.NEGATIVE) == 0);
		assertTrue(sr.getFlagValue(StatusRegister.Flag.ZERO) == 0);
		assertTrue(sr.getFlagValue(StatusRegister.Flag.CARRY) == 1);
		assertTrue(sr.getFlagValue(StatusRegister.Flag.OVERFLOW) == 0);

		acc.setData(-1);
		sr.setFlagValue(StatusRegister.Flag.CARRY, true);
		ArithmeticLogic.rrc(acc, sr);
		assertTrue(acc.getData() == -1);
		assertTrue(sr.getFlagValue(StatusRegister.Flag.NEGATIVE) == 1);
		assertTrue(sr.getFlagValue(StatusRegister.Flag.ZERO) == 0);
		assertTrue(sr.getFlagValue(StatusRegister.Flag.CARRY) == 1);
		assertTrue(sr.getFlagValue(StatusRegister.Flag.OVERFLOW) == 0);

		acc.setData(0);
		sr.setFlagValue(StatusRegister.Flag.CARRY, false);
		ArithmeticLogic.rrc(acc, sr);
		assertTrue(acc.getData() == 0);
		assertTrue(sr.getFlagValue(StatusRegister.Flag.NEGATIVE) == 0);
		assertTrue(sr.getFlagValue(StatusRegister.Flag.ZERO) == 1);
		assertTrue(sr.getFlagValue(StatusRegister.Flag.CARRY) == 0);
		assertTrue(sr.getFlagValue(StatusRegister.Flag.OVERFLOW) == 0);

		acc.setData(0);
		sr.setFlagValue(StatusRegister.Flag.CARRY, true);
		ArithmeticLogic.rrc(acc, sr);
		assertTrue(acc.getData() == -128);
		assertTrue(sr.getFlagValue(StatusRegister.Flag.NEGATIVE) == 1);
		assertTrue(sr.getFlagValue(StatusRegister.Flag.ZERO) == 0);
		assertTrue(sr.getFlagValue(StatusRegister.Flag.CARRY) == 0);
		assertTrue(sr.getFlagValue(StatusRegister.Flag.OVERFLOW) == 0);
	}

	@Test
	public void testRlcFlags() throws Exception {
		acc.setData(0);
		sr.setFlagValue(StatusRegister.Flag.CARRY, false);
		ArithmeticLogic.rlc(acc, sr);
		assertTrue(acc.getData() == 0);
		assertTrue(sr.getFlagValue(StatusRegister.Flag.NEGATIVE) == 0);
		assertTrue(sr.getFlagValue(StatusRegister.Flag.ZERO) == 1);
		assertTrue(sr.getFlagValue(StatusRegister.Flag.CARRY) == 0);
		assertTrue(sr.getFlagValue(StatusRegister.Flag.OVERFLOW) == 0);

		acc.setData(10);
		sr.setFlagValue(StatusRegister.Flag.CARRY, true);
		ArithmeticLogic.rlc(acc, sr);
		assertTrue(acc.getData() == 21);
		assertTrue(sr.getFlagValue(StatusRegister.Flag.NEGATIVE) == 0);
		assertTrue(sr.getFlagValue(StatusRegister.Flag.ZERO) == 0);
		assertTrue(sr.getFlagValue(StatusRegister.Flag.CARRY) == 0);
		assertTrue(sr.getFlagValue(StatusRegister.Flag.OVERFLOW) == 0);

		acc.setData(10);
		sr.setFlagValue(StatusRegister.Flag.CARRY, false);
		ArithmeticLogic.rlc(acc, sr);
		assertTrue(acc.getData() == 20);
		assertTrue(sr.getFlagValue(StatusRegister.Flag.NEGATIVE) == 0);
		assertTrue(sr.getFlagValue(StatusRegister.Flag.ZERO) == 0);
		assertTrue(sr.getFlagValue(StatusRegister.Flag.CARRY) == 0);
		assertTrue(sr.getFlagValue(StatusRegister.Flag.OVERFLOW) == 0);

		acc.setData(-127);
		sr.setFlagValue(StatusRegister.Flag.CARRY, true);
		ArithmeticLogic.rlc(acc, sr);
		assertTrue(acc.getData() == 3);
		assertTrue(sr.getFlagValue(StatusRegister.Flag.NEGATIVE) == 0);
		assertTrue(sr.getFlagValue(StatusRegister.Flag.ZERO) == 0);
		assertTrue(sr.getFlagValue(StatusRegister.Flag.CARRY) == 1);
		assertTrue(sr.getFlagValue(StatusRegister.Flag.OVERFLOW) == 0);

		acc.setData(128);
		sr.setFlagValue(StatusRegister.Flag.CARRY, false);
		ArithmeticLogic.rlc(acc, sr);
		assertTrue(acc.getData() == 0);
		assertTrue(sr.getFlagValue(StatusRegister.Flag.NEGATIVE) == 0);
		assertTrue(sr.getFlagValue(StatusRegister.Flag.ZERO) == 1);
		assertTrue(sr.getFlagValue(StatusRegister.Flag.CARRY) == 1);
		assertTrue(sr.getFlagValue(StatusRegister.Flag.OVERFLOW) == 0);

		acc.setData(-128);
		sr.setFlagValue(StatusRegister.Flag.CARRY, false);
		ArithmeticLogic.rlc(acc, sr);
		assertTrue(acc.getData() == 0);
		assertTrue(sr.getFlagValue(StatusRegister.Flag.NEGATIVE) == 0);
		assertTrue(sr.getFlagValue(StatusRegister.Flag.ZERO) == 1);
		assertTrue(sr.getFlagValue(StatusRegister.Flag.CARRY) == 1);
		assertTrue(sr.getFlagValue(StatusRegister.Flag.OVERFLOW) == 0);


		acc.setData(127);
		sr.setFlagValue(StatusRegister.Flag.CARRY, false);
		ArithmeticLogic.rlc(acc, sr);
		assertTrue(acc.getData() == -2);
		assertTrue(sr.getFlagValue(StatusRegister.Flag.NEGATIVE) == 1);
		assertTrue(sr.getFlagValue(StatusRegister.Flag.ZERO) == 0);
		assertTrue(sr.getFlagValue(StatusRegister.Flag.CARRY) == 0);
		assertTrue(sr.getFlagValue(StatusRegister.Flag.OVERFLOW) == 0);

		acc.setData(127);
		sr.setFlagValue(StatusRegister.Flag.CARRY, true);
		ArithmeticLogic.rlc(acc, sr);
		assertTrue(acc.getData() == -1);
		assertTrue(sr.getFlagValue(StatusRegister.Flag.NEGATIVE) == 1);
		assertTrue(sr.getFlagValue(StatusRegister.Flag.ZERO) == 0);
		assertTrue(sr.getFlagValue(StatusRegister.Flag.CARRY) == 0);
		assertTrue(sr.getFlagValue(StatusRegister.Flag.OVERFLOW) == 0);
	}

	@Test
	public void testCarryFlagBoundaries() {
		Flag c = Flag.CARRY;
		acc.setData(0);
		op.setData(10);
		ArithmeticLogic.sub(acc, op, sr);
		assertEquals(-10, acc.getData());
		assertTrue(sr.isFlagSet(c));

		acc.setData(2);
		op.setData(3);
		ArithmeticLogic.sub(acc, op, sr);
		assertEquals(-1, acc.getData());
		assertTrue(sr.isFlagSet(c));
		
		acc.setData(-1);
		op.setData(1);
		sr.clearAllFlags();
		ArithmeticLogic.addC(acc, op, sr);
		assertEquals(0, acc.getData());
		assertTrue(sr.isFlagSet(c));
		
		acc.setData(-10);
		op.setData(50);
		sr.clearAllFlags();
		ArithmeticLogic.addC(acc, op, sr);
		assertEquals(40, acc.getData());
		
	}

	@Test
	public void testOverflowFlagsBoundaries() {
		Flag o = Flag.OVERFLOW;
		acc.setData(-128);
		op.setData(-128);
		ArithmeticLogic.addC(acc, op, sr);
		assertEquals(0, acc.getData());
		assertTrue(sr.isFlagSet(o));

		acc.setData(127);
		op.setData(1);
		ArithmeticLogic.addC(acc, op, sr);
		System.out.println(acc.getData());
		assertTrue(sr.isFlagSet(o));


	}


}
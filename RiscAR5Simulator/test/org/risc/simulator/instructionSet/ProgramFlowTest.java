package org.risc.simulator.instructionSet;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.risc.simulator.memory.Register;
import org.risc.simulator.memory.StatusRegister;
import org.risc.simulator.processor.Processor;
import org.risc.simulator.processor.RISCAR5Processor;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ProgramFlowTest {

	Register pc;
	Register r7;
	StatusRegister sr;
	Processor process;

	@Before
	public void setUp() throws Exception {
		pc = new Register(Processor.PC, Processor.REGISTER_WIDTH);
		r7 = new Register(Processor.GPR_PREFIX + "7", Processor.REGISTER_WIDTH);
		sr = new StatusRegister(Processor.SR);
		process = new RISCAR5Processor();
	}

	@After
	public void tearDown() throws Exception {
		pc = null;
		r7 = null;
		sr = null;
		process = null;
	}

	@Test
	public void testBrz() throws Exception {
		assertTrue(pc.getData() == 0);
		assertTrue(r7.getData() == 0);
		assertTrue(sr.getData() == 0);

		r7.setData(10);
		sr.setFlagValue(StatusRegister.Flag.ZERO, false);
		ProgramFlow.brz(pc, r7, sr);
		assertTrue(pc.getData() == 0);
		sr.setFlagValue(StatusRegister.Flag.ZERO, true);
		ProgramFlow.brz(pc, r7, sr);
		assertTrue(pc.getData() == 10);

		r7.setData(-20);
		sr.setFlagValue(StatusRegister.Flag.ZERO, false);
		ProgramFlow.brz(pc, r7, sr);
		assertTrue(pc.getData() == 10);
		sr.setFlagValue(StatusRegister.Flag.ZERO, true);
		ProgramFlow.brz(pc, r7, sr);
		assertTrue(pc.getData() == -20);
	}

	@Test
	public void testBrc() throws Exception {
		assertTrue(pc.getData() == 0);
		assertTrue(r7.getData() == 0);
		assertTrue(sr.getData() == 0);

		r7.setData(10);
		sr.setFlagValue(StatusRegister.Flag.CARRY, false);
		ProgramFlow.brc(pc, r7, sr);
		assertTrue(pc.getData() == 0);
		sr.setFlagValue(StatusRegister.Flag.CARRY, true);
		ProgramFlow.brc(pc, r7, sr);
		assertTrue(pc.getData() == 10);

		r7.setData(-20);
		sr.setFlagValue(StatusRegister.Flag.CARRY, false);
		ProgramFlow.brc(pc, r7, sr);
		assertTrue(pc.getData() == 10);
		sr.setFlagValue(StatusRegister.Flag.CARRY, true);
		ProgramFlow.brc(pc, r7, sr);
		assertTrue(pc.getData() == -20);
	}

	@Test
	public void testBrn() throws Exception {
		assertTrue(pc.getData() == 0);
		assertTrue(r7.getData() == 0);
		assertTrue(sr.getData() == 0);

		r7.setData(10);
		sr.setFlagValue(StatusRegister.Flag.NEGATIVE, false);
		ProgramFlow.brn(pc, r7, sr);
		assertTrue(pc.getData() == 0);
		sr.setFlagValue(StatusRegister.Flag.NEGATIVE, true);
		ProgramFlow.brn(pc, r7, sr);
		assertTrue(pc.getData() == 10);

		r7.setData(-20);
		sr.setFlagValue(StatusRegister.Flag.NEGATIVE, false);
		ProgramFlow.brn(pc, r7, sr);
		assertTrue(pc.getData() == 10);
		sr.setFlagValue(StatusRegister.Flag.NEGATIVE, true);
		ProgramFlow.brn(pc, r7, sr);
		assertTrue(pc.getData() == -20);
	}

	@Test
	public void testBro() throws Exception {
		assertTrue(pc.getData() == 0);
		assertTrue(r7.getData() == 0);
		assertTrue(sr.getData() == 0);

		r7.setData(10);
		sr.setFlagValue(StatusRegister.Flag.OVERFLOW, false);
		ProgramFlow.bro(pc, r7, sr);
		assertTrue(pc.getData() == 0);
		sr.setFlagValue(StatusRegister.Flag.OVERFLOW, true);
		ProgramFlow.bro(pc, r7, sr);
		assertTrue(pc.getData() == 10);

		r7.setData(-20);
		sr.setFlagValue(StatusRegister.Flag.OVERFLOW, false);
		ProgramFlow.bro(pc, r7, sr);
		assertTrue(pc.getData() == 10);
		sr.setFlagValue(StatusRegister.Flag.OVERFLOW, true);
		ProgramFlow.bro(pc, r7, sr);
		assertTrue(pc.getData() == -20);
	}

	@Test
	public void testNop() throws Exception {
		ProgramFlow.nop();
	}

	@Test
	public void testStop() throws Exception {
		assertTrue(process.isRunning());
		ProgramFlow.stop(process);
		assertFalse(process.isRunning());
	}

}
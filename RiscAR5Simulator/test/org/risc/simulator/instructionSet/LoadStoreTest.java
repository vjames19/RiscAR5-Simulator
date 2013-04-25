package org.risc.simulator.instructionSet;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.risc.simulator.memory.ArrayListMemory;
import org.risc.simulator.memory.Memory;
import org.risc.simulator.memory.Register;
import org.risc.simulator.memory.StatusRegister;
import org.risc.simulator.processor.Processor;

import static org.junit.Assert.assertTrue;

public class LoadStoreTest {

	Register acc = null;
	Register op = null;
	StatusRegister sr = null;
	Memory mem = null;

	@Before
	public void setUp() throws Exception {
		acc = new Register(Processor.ACC, 0, Processor.REGISTER_WIDTH);
		op = new Register(Processor.GPR_PREFIX + "1", 0, Processor.REGISTER_WIDTH);
		sr = new StatusRegister(Processor.SR);
		mem = new ArrayListMemory(Processor.MEMORY_SIZE, Processor.MEMORY_CELL_SIZE);
	}

	@After
	public void tearDown() throws Exception {
		acc = null;
		op = null;
		sr = null;
		mem = null;
	}

	@Test
	public void testLdaR() throws Exception {
		LoadStore.ldaR(acc, op, sr);
		assertTrue(acc.getData() == 0);
		op.setData(5);
		LoadStore.ldaR(acc, op, sr);
		assertTrue(acc.getData() == 5);
	}

	@Test
	public void testLdaA() throws Exception {
		LoadStore.ldaA(acc, mem, 0xFF, sr);
		assertTrue(acc.getData() == 0);
		mem.setDataAt(0xFF, 10);
		mem.setDataAt(0xF5, 20);
		LoadStore.ldaA(acc, mem, 0xFF, sr);
		assertTrue(acc.getData() == 10);
		LoadStore.ldaA(acc, mem, 0xF5, sr);
		assertTrue(acc.getData() == 20);
		LoadStore.ldaA(acc, mem, 0xFF, sr);
		assertTrue(acc.getData() == 10);
		LoadStore.ldaA(acc, mem, 0x00, sr);
		assertTrue(acc.getData() == 0);
	}

	@Test
	public void testLdaI() throws Exception {
		assertTrue(acc.getData() == 0);
		LoadStore.ldaI(acc, 255, sr);
		assertTrue(acc.getData() == -1);
		LoadStore.ldaI(acc, 0, sr);
		assertTrue(acc.getData() == 0);
		LoadStore.ldaI(acc, 128, sr);
		assertTrue(acc.getData() == -128);
		LoadStore.ldaI(acc, 127, sr);
		assertTrue(acc.getData() == 127);
	}

	@Test
	public void testStaR() throws Exception {
		assertTrue(op.getData() == 0);
		LoadStore.staR(acc, op);
		assertTrue(op.getData() == 0);
		acc.setData(5);
		LoadStore.staR(acc, op);
		assertTrue(op.getData() == 5);
		acc.setData(127);
		LoadStore.staR(acc, op);
		assertTrue(op.getData() == 127);
		acc.setData(-128);
		LoadStore.staR(acc, op);
		assertTrue(op.getData() == -128);
	}

	@Test
	public void testStaA() throws Exception {
		assertTrue(mem.getDataAt(0) == 0);
		acc.setData(10);
		LoadStore.staA(acc, mem, 0x10);
		assertTrue(mem.getDataAt(0x10) == 10);
		acc.setData(20);
		LoadStore.staA(acc, mem, 0x10);
		assertTrue(mem.getDataAt(0x10) == 20);
	}

}
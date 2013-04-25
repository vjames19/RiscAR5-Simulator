package org.risc.simulator.processor;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.risc.simulator.assembler.Assembler;
import org.risc.simulator.io.IOChannel;
import org.risc.simulator.memory.Memory;
import org.risc.simulator.memory.Register;

import java.io.File;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class RISCAR5ProcessorTest {

	private Processor p;
	private Assembler a;
	private Register acc, pc;
	private static String RESOURCE_PATH = "../io/TextFiles/processorCommands/";

	@Before
	public void setUp() throws Exception {
		p = new RISCAR5Processor();
		acc = p.getRegister(Processor.ACC);
		pc = p.getRegister(Processor.PC);
	}

	@After
	public void tearDown() throws Exception {
		p = null;
		a = null;
	}

	@Test
	public void testInfinite() throws Exception {
		assemble("infinite.asm");
		p.step();
		assertTrue(pc.getData() == 2);
		p.step();
		assertTrue(pc.getData() == 4);
		p.step();
		assertTrue(pc.getData() == 6);
		p.step();
		assertTrue(pc.getData() == 8);
		p.step();
		assertTrue(pc.getData() == 10);
		p.step();
		assertTrue(pc.getData() == 0);
		p.step();
		assertTrue(pc.getData() == 2);
		p.step();
		assertTrue(pc.getData() == 4);
		p.step();
		assertTrue(pc.getData() == 6);
		p.step();
		assertTrue(pc.getData() == 8);
		p.step();
		assertTrue(pc.getData() == 10);
		p.step();
		assertTrue(pc.getData() == 0);
	}

	@Test
	public void testStop() throws Exception {
		int i= assemble("stop.asm");
		p.run();
		testPCAndStop(i);
	}

	@Test
	public void testAdd() throws Exception {
		int i =assemble("add.asm");
		p.run();
		assertTrue(!p.isRunning());
		assertEquals(20, acc.getData());
		testPCAndStop(i);
	}

	@Test
	public void testLoopUntilPositive() throws Exception {
		int i=assemble("loopUntilPositive.asm");
		p.run();
		System.out.println(p.getRegisters());
		assertTrue("Acc must be positive after stopping", acc.getData() >=0);
		testPCAndStop(i);

	}

	@Test
	public void testGetRegisters() throws Exception {
		int i=assemble("load20toEachRegister.asm");
		p.run();
		for (Register r : p.getRegisters()) {
			if (r.getName().startsWith(Processor.GPR_PREFIX)) {
				assertEquals("Each register is supposed to have 20", 20, r.getData());
			}
		}
		testPCAndStop(i);
	}

	@Test
	public void testGetMemory() throws Exception {
		int i = assemble("manipulateMemory.asm");
		p.run();
		assertEquals(50, acc.getData());
		assertEquals(50, p.getRegister("R" + 0).getData());
		testPCAndStop(i);

	}

	@Test
	public void testStep() throws Exception {
		int i = assemble("add.asm");
		while (p.isRunning()) {
			p.step();
		}
		assertTrue(!p.isRunning());
		assertEquals(20, acc.getData());
		testPCAndStop(i);
	}

	@Test
	public void testInputPort() throws Exception {
		IOChannel ch = p.getInputChannel();
		ch.write("AB");
		int i =assemble("inputReader.asm");
		p.run();
		assertEquals(65, p.getRegister(0).getData());
		assertEquals(66, p.getRegister(1).getData());
		testPCAndStop(i);

	}

	@Test
	public void testOutputPort() throws Exception {
		IOChannel ch = p.getOutputChannel();
		int inst= assemble("outputWriter.asm");
		p.run();
		Memory m = p.getMemory();
		int code = 65;
		for (int i = 252; i < 256; i++) {
			assertEquals(code++, m.getDataAt(i));
		}

		assertEquals("ABCD", ch.readString());
		testPCAndStop(inst);
	}
	
	@Test
	public void naydaTest() throws Exception{
		System.out.println("Nayda test");
		int inst = assemble("naydaTest.asm");
		while(p.isRunning()){
			p.step();
			System.out.println(p.getRegisters());
		}
		testPCAndStop(inst);
	}

	/**
	 * Checks if the PC value after stopping is the one after the last instruction.
	 * And verifies that the processor is not running
	 * @param numberOfInstructions number of instructions passed to the processor
	 */
	private void testPCAndStop(int numberOfInstructions) {
		assertEquals("PC final state is not the right one", numberOfInstructions * Processor.PC_INCREMENT, pc.getData());
		assertTrue("Processor shouldn't be running", !p.isRunning());
	}


	
	/**
	 * Assembles the code and initializes the processor with the instructions
	 * @param name name of the file to assemble
	 * @throws Exception
	 * @returns the numer of instructions assembled
	 */
	private int assemble(String name) throws Exception {
		File f = getFile(name);
		a = new Assembler(f);
		List<Instruction> inst = a.assemble();
		p.init(inst);
		return inst.size();
	}

	/**
	 * Loads a resource into a file
	 * @param name name the resource to load
	 * @return the file loaded
	 */
	private File getFile(String name) {
		return new File(this.getClass().getResource(RESOURCE_PATH + name).getFile());
	}

}
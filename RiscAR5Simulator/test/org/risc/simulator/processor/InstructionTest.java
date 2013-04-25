package org.risc.simulator.processor;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class InstructionTest {

	List<Instruction> instruction;

	@Before
	public void setUp() throws Exception {
		instruction = new ArrayList<Instruction>();
		instruction.add(new Instruction("2B00"));
		instruction.add(new Instruction(11264));
		instruction.add(new Instruction("1B82"));
		instruction.add(new Instruction("6270"));
		instruction.add(new Instruction(63488));
		instruction.add(new Instruction("12FF"));
		instruction.add(new Instruction("B786"));
		instruction.add(new Instruction("*86A0"));
	}

	@After
	public void tearDown() throws Exception {
		instruction.clear();
		instruction = null;
	}

	@Test
	public void testGetOpCode() throws Exception {
		assertTrue(instruction.get(0).getOpCode() == 5);
		assertTrue(instruction.get(1).getOpCode() == 5);
		assertTrue(instruction.get(2).getOpCode() == 3);
		assertTrue(instruction.get(3).getOpCode() == 12);
		assertTrue(instruction.get(4).getOpCode() == 31);
		assertTrue(instruction.get(5).getOpCode() == 2);
		assertTrue(instruction.get(6).getOpCode() == 22);
		assertTrue(instruction.get(7).getOpCode() == 0);
	}

	@Test
	public void testGetInstruction() throws Exception {
		assertTrue(instruction.get(0).getInstruction() == 11008);
		assertTrue(instruction.get(1).getInstruction() == 11264);
		assertTrue(instruction.get(2).getInstruction() == 7042);
		assertTrue(instruction.get(3).getInstruction() == 25200);
		assertTrue(instruction.get(4).getInstruction() == 63488);
		assertTrue(instruction.get(5).getInstruction() == 4863);
		assertTrue(instruction.get(6).getInstruction() == 46982);
		assertTrue(instruction.get(7).getInstruction() == 0);
	}

	@Test
	public void testGetOperand() throws Exception {
		assertTrue(instruction.get(0).getOperand(8, 15) == 0);
		assertTrue(instruction.get(1).getOperand(8, 15) == 0);
		assertTrue(instruction.get(2).getOperand(8, 15) == -126);
		assertTrue(instruction.get(3).getOperand(8, 15) == 112);
		assertTrue(instruction.get(4).getOperand(8, 15) == 0);
		assertTrue(instruction.get(5).getOperand(8, 15) == -1);
		assertTrue(instruction.get(6).getOperand(8, 15) == -122);
		assertTrue(instruction.get(7).getOperand(8, 15) == 0);
	}

	@Test
	public void testGetInstructionInHex() throws Exception {
		assertTrue(instruction.get(0).getInstructionInHex().equals("2B00"));
		assertTrue(instruction.get(1).getInstructionInHex().equals("2C00"));
		assertTrue(instruction.get(2).getInstructionInHex().equals("1B82"));
		assertTrue(instruction.get(3).getInstructionInHex().equals("6270"));
		assertTrue(instruction.get(4).getInstructionInHex().equals("F800"));
		assertTrue(instruction.get(5).getInstructionInHex().equals("12FF"));
		assertTrue(instruction.get(6).getInstructionInHex().equals("B786"));
		assertTrue(instruction.get(7).getInstructionInHex().equals("0000"));
	}

	@Test
	public void testIsStopInstruction() throws Exception {
		assertFalse(instruction.get(0).isStopInstruction());
		assertFalse(instruction.get(1).isStopInstruction());
		assertFalse(instruction.get(2).isStopInstruction());
		assertFalse(instruction.get(3).isStopInstruction());
		assertTrue(instruction.get(4).isStopInstruction());
		assertFalse(instruction.get(5).isStopInstruction());
		assertFalse(instruction.get(6).isStopInstruction());
		assertFalse(instruction.get(7).isStopInstruction());
	}

	@Test
	public void testIsValid() throws Exception {
		assertTrue(instruction.get(0).isValid());
		assertTrue(instruction.get(1).isValid());
		assertTrue(instruction.get(2).isValid());
		assertTrue(instruction.get(3).isValid());
		assertTrue(instruction.get(4).isValid());
		assertFalse(instruction.get(5).isValid());
		assertFalse(instruction.get(6).isValid());
		assertFalse(instruction.get(7).isValid());
	}

	@Test
	public void testGetOriginalInstruction() throws Exception {
		assertTrue(instruction.get(0).getOriginalInstruction().equals("2B00"));
		assertTrue(instruction.get(1).getOriginalInstruction().equals("11264"));
		assertTrue(instruction.get(2).getOriginalInstruction().equals("1B82"));
		assertTrue(instruction.get(3).getOriginalInstruction().equals("6270"));
		assertTrue(instruction.get(4).getOriginalInstruction().equals("63488"));
		assertTrue(instruction.get(5).getOriginalInstruction().equals("12FF"));
		assertTrue(instruction.get(6).getOriginalInstruction().equals("B786"));
		assertTrue(instruction.get(7).getOriginalInstruction().equals("*86A0"));
	}

}
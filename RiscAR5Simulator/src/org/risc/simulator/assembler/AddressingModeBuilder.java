package org.risc.simulator.assembler;

import org.risc.simulator.processor.Instruction;

/**
 * Builds an instruction based on the addressing mode
 * @author Victor J.
 *
 */
public interface AddressingModeBuilder {
	
	public Instruction getInstruction(int opcode,String[] args) throws AssemblerException;

}

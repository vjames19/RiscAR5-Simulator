package org.risc.simulator.assembler.addressingmode;

import org.risc.simulator.processor.Instruction;

public class ImplicitAddressing extends AbstractAddressingModeBuilder {

	@Override
	public Instruction getInstruction(int opcode, String[] args) {
		int inst= shiftOpcode(opcode);
		return buildInstruction(inst);
	}

}

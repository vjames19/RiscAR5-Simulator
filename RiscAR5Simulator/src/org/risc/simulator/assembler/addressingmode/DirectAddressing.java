package org.risc.simulator.assembler.addressingmode;

import static org.risc.simulator.util.NumberUtils.fitsInBits;

import org.risc.simulator.assembler.AssemblerException;
import org.risc.simulator.processor.Instruction;

public class DirectAddressing extends AbstractAddressingModeBuilder {

	@Override
	public Instruction getInstruction(int opcode, String[] args) throws AssemblerException {
		int constant = getInt(args[1]);
		if(!fitsInBits(constant, 8)) {
			throw new AssemblerException("Constant doesn't fit in 8 bits " + getString(args));
		}
		constant = maskConstant(constant);
		int op = shiftOpcode(opcode);
		op |= constant;
		return buildInstruction(op);
	}

}

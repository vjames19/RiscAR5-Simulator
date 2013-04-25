package org.risc.simulator.assembler.addressingmode;

import org.risc.simulator.assembler.AssemblerException;
import org.risc.simulator.processor.Instruction;

public class RegisterDirectAddressing extends AbstractAddressingModeBuilder {

	@Override
	public Instruction getInstruction(int opcode, String[] args) throws AssemblerException {
		int op = shiftOpcode(opcode);
		int reg = getInt(args[1]);
		if(reg < 0 || reg > 7){
			throw new AssemblerException("Invalid register in " + getString(args));
		}
		reg= shiftandMaskRegister(reg);
		op |= reg;
		return buildInstruction(op);
	}

}

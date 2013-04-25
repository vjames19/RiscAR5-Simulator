package org.risc.simulator.assembler.addressingmode;

import java.util.Arrays;

import org.risc.simulator.assembler.AddressingModeBuilder;
import static org.risc.simulator.util.NumberUtils.fitsInBits;
import org.risc.simulator.processor.Instruction;
import org.risc.simulator.processor.Processor;
import org.risc.simulator.util.NumberUtils;

/**
 * 
 * @author Victor J.
 *
 */
public abstract class AbstractAddressingModeBuilder implements AddressingModeBuilder {

	protected final int byteMask = 0xFF;
	protected final int registerMask = 0x07;
	
	protected int getInt(String arg){
		return Integer.valueOf(arg);
	}
	
	protected int shiftOpcode(int opcode){
		return opcode << (Processor.IR_SIZE - Processor.OPCODE_SIZE);
	}
	
	protected int or(int... numbers){
		int result =0;
		for(int i: numbers){
			result |= i;
		}
		
		return result;
	}
	
	protected int shiftandMaskRegister(int number){
		number &= registerMask;
		return number << 8;
	
	}
	
	protected int maskConstant(int constant){
		return constant &= byteMask;
	}
	protected Instruction buildInstruction(int inst){
		String hex = NumberUtils.intToHexString(inst, 4);
		return new Instruction(hex);  
	}
	
	protected String getString(String[] args){
		return Arrays.toString(args);
	}
}

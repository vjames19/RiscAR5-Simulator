package org.risc.simulator.assembler;

import java.util.HashMap;
import java.util.Map;

import org.risc.simulator.assembler.addressingmode.DirectAddressing;
import org.risc.simulator.assembler.addressingmode.ImmediateAddressing;
import org.risc.simulator.assembler.addressingmode.ImplicitAddressing;
import org.risc.simulator.assembler.addressingmode.RegisterDirectAddressing;
import org.risc.simulator.processor.Processor;

public class InstructionSet {
	private final Map<String,AssemblerInstruction> instructions;
	private final AddressingModeBuilder direct,register,implicit, immediate;
	private static InstructionSet singleton;
	
	private InstructionSet(){
		instructions = new HashMap<String, AssemblerInstruction>(Processor.NUMBER_OF_INSTRUCTIONS);
		direct = new DirectAddressing();
		register = new RegisterDirectAddressing();
		implicit = new ImplicitAddressing();
		immediate = new ImmediateAddressing();
		build();
	}
	
	private void build() {

		add("and",0, register);
		add("or", 1, register);
		add("addc", 3, register);
		add("sub", 4, register);
		add("mul", 5, register);
		add("neg",6, implicit);
		add("not",7,implicit);
		add("rlc",8, implicit);
		add("rrc",9, implicit);
		add("ldar",10, register);
		add("star",11,register);
		add("lda",12,direct);
		add("sta",13, direct);
		add("ldi",14,immediate);
		add("brz",16,implicit);
		add("brc",17,implicit);
		add("brn",18,implicit);
		add("bro",19,implicit);
		add("stop",31,implicit);
		add("nop",24,implicit);
		
		
		
	}
	
	public static InstructionSet getInstance(){
		if(singleton == null){
			singleton = new InstructionSet();
		}
		
		return singleton;
	}
	public AssemblerInstruction get(String mnemonic){
		return instructions.get(mnemonic);
	}
	
	private void add(String mnemonic, int opcode, AddressingModeBuilder mode){
		if(instructions.put(mnemonic, new AssemblerInstruction(mnemonic,opcode,mode)) != null){
			System.out.println("A copy has made " + mnemonic);
		}
	}
	

}

package org.risc.simulator.assembler;

public class AssemblerInstruction {

	private final String mnemonic;
	private final int opcode;
	private final AddressingModeBuilder mode;


	public AssemblerInstruction(String mnemonic, int opcode, AddressingModeBuilder mode) {
		super();
		this.mnemonic = mnemonic;
		this.opcode = opcode;
		this.mode = mode;
	}
	
	
	public String getMnemonic() {
		return mnemonic;
	}

	public int getOpcode() {
		return opcode;
	}

	public AddressingModeBuilder getMode() {
		return mode;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((mnemonic == null) ? 0 : mnemonic.hashCode());
		result = prime * result + ((mode == null) ? 0 : mode.hashCode());
		result = prime * result + opcode;
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AssemblerInstruction other = (AssemblerInstruction) obj;
		if (mnemonic == null) {
			if (other.mnemonic != null)
				return false;
		} else if (!mnemonic.equals(other.mnemonic))
			return false;
		if (mode == null) {
			if (other.mode != null)
				return false;
		} else if (!mode.equals(other.mode))
			return false;
		if (opcode != other.opcode)
			return false;
		return true;
	}
	
}

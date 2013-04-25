package org.risc.simulator.processor;

import org.risc.simulator.util.NumberUtils;

/**
 * This class provides validation of the instruction itself.
 * It verifies if the opcode of the instruction is an existing opcode.
 * It also provides methods for gathering the operands of an instruction.
 */
public class Instruction {

	private int instruction;
	private static final int OPCODE_SIZE = 5;
	private static final int RADIX = 16;
	private boolean valid = false;
	private boolean stopInstruction = false;
	private String originalInstruction;

	/**
	 * Instantiates a new Instruction given as an integer value.
	 * @param instruction The instruction to be parsed and validated.
	 */
	public Instruction(int instruction) {
		this.originalInstruction = Integer.toString(instruction);
		this.setInstruction(instruction);
	}

	/**
	 * Instantiates a new Instruction given as a hex string.
	 * @param instruction The instruction to be parsed and validated.
	 */
	public Instruction(String instruction) {
		this.originalInstruction = instruction;
		if (NumberUtils.isValidNumberString(instruction, RADIX)) {
			int number = Integer.parseInt(instruction, RADIX);
			this.setInstruction(number);
		}
	}

	/**
	 * Private method. It's used to verify if the opcode of the instruction is valid.
	 * It also verifies if the given opcode belongs to the stop instruction.
	 * @param instruction The instruction to be parsed and validated.
	 */
	private void setInstruction(int instruction) {
		this.instruction = instruction;
		int opCode = this.getOpCode();
		valid = isOpCodeValid(opCode);
		if (valid) {
			stopInstruction = opCode == Processor.STOP_INSTRUCTION;
		}
	}

	/**
	 * Method used to verify if a given opcode is valid.
	 * @param opCode The opcode that will be validated.
	 * @return true if the opcode is valid, false otherwise.
	 */
	protected static boolean isOpCodeValid(int opCode) {
		return ((opCode >= 0 && opCode <= 31) && !(opCode == 2) && !(opCode == 15) && // valid ranges: 0-1, 3-14, 16-19, 24, 31
				!(opCode >= 20 && opCode <= 23) && !(opCode >= 25 && opCode <= 30)); // temporary measure for valid ranges
	}

	/**
	 * This method retrieves the opcode of a given instruction.
	 * @return The opcode value of the instruction as a unsigned integer.
	 */
	public int getOpCode() {
		return NumberUtils.getUnsignedValueOf(instruction, 0, OPCODE_SIZE - 1, Processor.IR_SIZE);
	}

	/**
	 * This method retrieves the entire instruction value.
	 * @return The instruction integer value.
	 */
	public int getInstruction() {
		return instruction;
	}

	/**
	 * This method retrieves the operand of an instruction given the boundaries of the operand.
	 * @param start The beginning bit of the operand value.
	 * @param end   The ending bit of the operand value.
	 * @return The value of the operand as a signed integer.
	 */
	public int getOperand(int start, int end) {
		return NumberUtils.getSignedValueOf(instruction, start, end, Processor.IR_SIZE);
	}

	/**
	 * This method converts the instruction into its hex representation.
	 * @return The instruction in hex representation.
	 */
	public String getInstructionInHex() {
		return NumberUtils.intToHexString(instruction, 4);
	}

	/**
	 * Gets if a given instruction is the stop instruction.
	 * @return true if its the stop instruction, false otherwise.
	 */
	public boolean isStopInstruction() {
		return stopInstruction;
	}

	/**
	 * Gets if a given instruction is valid or not.
	 * @return true if the instruction is valid, false otherwise.
	 */
	public boolean isValid() {
		return valid;
	}

	/**
	 * This getter returns the original representation of the given instruction.
	 * If it was original as an hex string it will return hex, else it will return an integer string.
	 * @return The original input instruction.
	 */
	public String getOriginalInstruction() {
		return originalInstruction;
	}

	@Override
	public String toString() {
		return "Instruction [instruction=" + instruction
				+ ", originalInstruction=" + originalInstruction + "]";
	}

}
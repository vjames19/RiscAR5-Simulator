package org.risc.simulator.processor;

import org.risc.simulator.io.IOChannel;
import org.risc.simulator.memory.Memory;
import org.risc.simulator.memory.Register;

import java.util.Collection;
import java.util.List;

/**
 * The interface Processor.
 * It defines the methods and constants pertaining to the RISC R5 processor.
 */
public interface Processor {

	/**
	 * This constant contains the size of 8-bit registers.
	 */
	public static final int REGISTER_WIDTH = 8;

	/**
	 * This constant contains the size of the instruction register which is sixteen bits.
	 */
	public static final int IR_SIZE = 16;

	/**
	 * This constant contains the amount of general purpose register available.
	 */
	public static final int NUMBER_OF_GPR = 8;

	/**
	 * This constant contains the size of the memory for the RISC R5 processor.
	 */
	public static final int MEMORY_SIZE = 256;

	/**
	 * This constant contains the size of the each memory cell.
	 */
	public static final int MEMORY_CELL_SIZE = 8;

	/**
	 * This constant contains the Program Counter increment amount for each RISC cycle.
	 */
	public static final int PC_INCREMENT = 2;

	/**
	 * This constant contains the number of supported instructions in the RISC R5.
	 */
	public static final int NUMBER_OF_INSTRUCTIONS = 20; //Total 20 Instructions

	/**
	 * This constant contains the opcode number for the stop instruction in the RISC R5.
	 */
	public static final int STOP_INSTRUCTION = 31;

	/**
	 * This constant provides the prefix used to denote the general purpose registers.
	 */
	public static final String GPR_PREFIX = "R";

	/**
	 * This constant provides the value used to denote the Program Counter.
	 */
	public static final String PC = "PC";

	/**
	 * This constant provides the value used to denote the Accumulator Register.
	 */
	public static final String ACC = "ACC";

	/**
	 * This constant provides the value used to denote the Instruction Register.
	 */
	public static final String IR = "IR";

	/**
	 * This constant provides the value used to denote the Status Register.
	 */
	public static final String SR = "SR";

	/**
	 * This constant contains the size of the opcode for an instruction.
	 */
	public static final int OPCODE_SIZE = 5;

	/**
	 * Method used to retrieve any register belonging to the RISC R5 processor.
	 * @param key The name of the register to retrieve. It can be a special or general purpose register.
	 * @return Returns the register requested, giving access to its data and other properties.
	 */
	public Register getRegister(String key);

	/**
	 * Method used to retrieve a general purpose registers belonging to the RISC R5 processor.
	 * @param i The value of the register to retrieve ranging from 0 to 7. Only general purpose can be retrieved.
	 * @return Returns the register requested, giving access to its data and other properties.
	 */
	public Register getRegister(int i);

	/**
	 * Method used to retrieve a collection of all the registers belonging to the RISC R5 processor.
	 * @return A collection of registers.
	 */
	public Collection<Register> getRegisters();

	/**
	 * Method used to retrieve the memory instance of the RISC R5 processor.
	 * @return The memory of the processor.
	 */
	public Memory getMemory();

	/**
	 * Method used to determine if the processor is running.
	 * @return true if the processor is running; false otherwise.
	 */
	public boolean isRunning();

	/**
	 * Method used to stop or start the RISC R5 processor instance.
	 * @param run false in order to stop the processor; true for the inverse.
	 */
	public void setRun(boolean run);

	/**
	 * Method used to step through a fetch, decode, execute cycle in the RISC R5 processor instance.
	 */
	public void step();

	/**
	 * Method used to fetch, decode, execute all the instructions given the processor instance.
	 */
	public void run();

	/**
	 * Get the values from memory ranges 250-251 in processor instance.
	 * @return The values corresponding to locations 250-251 in memory.
	 */
	public IOChannel getInputChannel();

	/**
	 * Get the values from memory ranges 252-255 in processor instance.
	 * @return The values corresponding to locations 252-255 in memory.
	 */
	public IOChannel getOutputChannel();

	/**
	 * This method initializes this processor with the list of instructions and loads them to
	 * memory.
	 * @param instructions The list containing all the instructions that will be executed.
	 */
	public void init(List<Instruction> instructions);

}
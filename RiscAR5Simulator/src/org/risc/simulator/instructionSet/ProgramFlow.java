package org.risc.simulator.instructionSet;

import org.risc.simulator.memory.Register;
import org.risc.simulator.memory.StatusRegister;
import org.risc.simulator.processor.Processor;

/**
 * This class contains all the methods used for Program Flow
 * operations used in the RISC R5 processor.
 */
public class ProgramFlow {

	/**
	 * Branch Operation, Branch if zero. Uses Implicit addressing mode.
	 * This method provides the functionality of the BRZ function.
	 * It works in the following manner: PC <- R7 if ZF = 1
	 * @param pc The program counter of the processor, denoted above as PC.
	 * @param r7 The general purpose register R7, denoted above as R7.
	 * @param sr The status register for verification of the Zero Flag denoted above as ZF.
	 */
	public static void brz(Register pc, Register r7, StatusRegister sr) {
		if (sr.getFlagValue(StatusRegister.Flag.ZERO) == 1) {
			pc.setData(r7.getData());
		}
	}

	/**
	 * Branch Operation, Branch if Carry. Uses Implicit addressing mode.
	 * This method provides the functionality of the BRC function.
	 * It works in the following manner: PC <- R7 if CF = 1
	 * @param pc The program counter of the processor, denoted above as PC.
	 * @param r7 The general purpose register R7, denoted above as R7.
	 * @param sr The status register for verification of the Carry Flag denoted above as CF.
	 */
	public static void brc(Register pc, Register r7, StatusRegister sr) {
		if (sr.getFlagValue(StatusRegister.Flag.CARRY) == 1) {
			pc.setData(r7.getData());
		}
	}

	/**
	 * Branch Operation, Branch if Negative. Uses Implicit addressing mode.
	 * This method provides the functionality of the BRN function.
	 * It works in the following manner: PC <- R7 if NF = 1
	 * @param pc The program counter of the processor, denoted above as PC.
	 * @param r7 The general purpose register R7, denoted above as R7.
	 * @param sr The status register for verification of the Negative Flag denoted above as NF.
	 */
	public static void brn(Register pc, Register r7, StatusRegister sr) {
		if (sr.getFlagValue(StatusRegister.Flag.NEGATIVE) == 1) {
			pc.setData(r7.getData());
		}
	}

	/**
	 * Branch Operation, Branch if Overflow. Uses Implicit addressing mode.
	 * This method provides the functionality of the BRO function.
	 * It works in the following manner: PC <- R7 if OF = 1
	 * @param pc The program counter of the processor, denoted above as PC.
	 * @param r7 The general purpose register R7, denoted above as R7.
	 * @param sr The status register for verification of the Overflow Flag denoted above as OF.
	 */
	public static void bro(Register pc, Register r7, StatusRegister sr) {
		if (sr.getFlagValue(StatusRegister.Flag.OVERFLOW) == 1) {
			pc.setData(r7.getData());
		}
	}

	/**
	 * Flow operation, simulates one clock cycle. Uses Implicit addressing mode.
	 */
	public static void nop() {
		// Simulating one cycle here...
	}

	/**
	 * Flow operation, stops the processor. Uses Implicit addressing mode.
	 * @param processor The processor that will be stopped.
	 */
	public static void stop(Processor processor) {
		if (processor.isRunning()) {
			processor.setRun(false);
		}
	}

}
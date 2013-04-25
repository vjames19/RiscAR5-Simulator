package org.risc.simulator.instructionSet;

import org.risc.simulator.memory.Memory;
import org.risc.simulator.memory.Register;
import org.risc.simulator.memory.StatusRegister;

/**
 * This class contains all the methods used for Load/Store
 * operations used in the RISC R5 processor.
 */
public class LoadStore {

	/**
	 * Load Operation, Loads Accumulator with Register content.
	 * This method provides the functionality of the LDA function using Register Direct Addressing mode.
	 * It works in the following manner: A <- RF
	 * @param acc The accumulator register denoted above as A.
	 * @param op  The register to be used denoted above as RF.
	 * @param sr  The status register for flag management functionality.
	 */
	public static void ldaR(Register acc, Register op, StatusRegister sr) {
		Integer value = op.getData();
		FlagManagement.setFlags(acc.getData(), value, 0, 0, sr, FlagManagement.Operation.LDAR);
		acc.setData(value.byteValue());
	}

	/**
	 * Load Operation, Loads Accumulator with Address content.
	 * This method provides the functionality of the LDA function using Direct Addressing mode.
	 * It works in the following manner: A <- [addr]
	 * @param acc     The accumulator register denoted above as A.
	 * @param mem     The memory from where the value will be retrieved.
	 * @param address The memory address where the value is located, denoted above as [addr].
	 * @param sr      The status register for flag management functionality.
	 */
	public static void ldaA(Register acc, Memory mem, int address, StatusRegister sr) {
		Integer value = mem.getDataAt(address);
		FlagManagement.setFlags(acc.getData(), value, 0, 0, sr, FlagManagement.Operation.LDAA);
		acc.setData(value.byteValue());
	}

	/**
	 * Load Operation, Loads Accumulator with an Immediate value.
	 * This method provides the functionality of the LDI function using Immediate Addressing mode.
	 * It works in the following manner: A <- Immediate
	 * @param acc   The accumulator register denoted above as A.
	 * @param value The value denoted above as Immediate.
	 * @param sr    The status register for flag management functionality.
	 */
	public static void ldaI(Register acc, int value, StatusRegister sr) {
		Integer val = value;
		FlagManagement.setFlags(acc.getData(), value, 0, 0, sr, FlagManagement.Operation.LDAI);
		acc.setData(val.byteValue());
	}

	/**
	 * Store Operation, Stores the Accumulator value into a General Purpose register.
	 * This method provides the functionality of the STA function using Register Direct Addressing mode.
	 * It works in the following manner: RF <- A
	 * @param acc The accumulator register denoted above as A.
	 * @param reg The general purpose register to store the value into, denoted above as RF.
	 */
	public static void staR(Register acc, Register reg) {
		reg.setData(acc.getData());
	}

	/**
	 * Store Operation, Stores the Accumulator value into memory.
	 * This method provides the functionality of the STA function using Direct Addressing mode.
	 * It works in the following manner: [addr] <- A
	 * @param acc     The accumulator register denoted above as A.
	 * @param mem     The memory where the value will be stored.
	 * @param address The memory address where the value will be stored, denoted above as [addr].
	 */
	public static void staA(Register acc, Memory mem, int address) {
		mem.setDataAt(address, acc.getData());
	}

}
package org.risc.simulator.instructionSet;

import org.risc.simulator.memory.StatusRegister;
import org.risc.simulator.memory.StatusRegister.Flag;
import org.risc.simulator.util.NumberUtils;

/**
 * This class provides management for flags based on the initial parameters and
 * result of an operation.
 */
public class FlagManagement {

	/**
	 * The enum that contains all the arithmetic and logic operations in which
	 * flags will be managed.
	 */
	public enum Operation {
		/**
		 * The LDAI operation.
		 */
		LDAI(11), /**
		 * The LDAA operation.
		 */
		LDAA(10), /**
		 * The LDAR operation.
		 */
		LDAR(9), /**
		 * The RLC operation.
		 */
		RLC(8), /**
		 * The RRC operation.
		 */
		RRC(7), /**
		 * The NEG operation.
		 */
		NEG(6), /**
		 * The NOT operation.
		 */
		NOT(5), /**
		 * The MUL operation.
		 */
		MUL(4), /**
		 * The SUB operation.
		 */
		SUB(3), /**
		 * The ADDC operation.
		 */
		ADDC(2), /**
		 * The OR operation.
		 */
		OR(1), /**
		 * The AND operation.
		 */
		AND(0);

		private int index;

		Operation(int value) {
			this.index = value;
		}
	}

	/**
	 * This method is used to set the different flags {carry, overflow,
	 * negative, zero} based on the operation being executed.
	 * @param acc       The Accumulator that contains the value used in the operation.
	 * @param op        The Register that contains the valued which will be used for
	 *                  the operation.
	 * @param c         The Carry Flag value if its used in the operation { only used
	 *                  in ADDC } .
	 * @param result    The Result of the operation.
	 * @param sr        The Status register used in the operation.
	 * @param operation The operation being executed, specified by the Operation enum.
	 */
	public static void setFlags(final int acc, final int op, final int c, final int result,
	                            StatusRegister sr, Operation operation) {
		final int accMsb = NumberUtils.getMSB(acc);
		final int opMsb = NumberUtils.getMSB(op);
		final int resMsb = NumberUtils.getMSB(result);
		
		switch (operation.index) {
			case 0: // AND
				sr.setFlagValue(StatusRegister.Flag.ZERO, result == 0);
				//&& NumberUtils.getMSB(result) == 0);
				sr.setFlagValue(StatusRegister.Flag.NEGATIVE, resMsb == 1);
				sr.setFlagValue(StatusRegister.Flag.CARRY, false);
				sr.setFlagValue(StatusRegister.Flag.OVERFLOW, false);
				break;

			case 1: // OR
				sr.setFlagValue(StatusRegister.Flag.ZERO, result == 0);
				//&& NumberUtils.getMSB(result) == 0);
				sr.setFlagValue(StatusRegister.Flag.NEGATIVE, resMsb == 1);
				sr.setFlagValue(StatusRegister.Flag.CARRY, false);
				sr.setFlagValue(StatusRegister.Flag.OVERFLOW, false);
				break;
			case 2: // ADDC
				sr.setFlagValue(StatusRegister.Flag.ZERO, result == 0);
				sr.setFlagValue(StatusRegister.Flag.NEGATIVE, resMsb == 1);
				
				int a = NumberUtils.getUnsignedValueOf(acc, 0, 7, 8);
				int o = NumberUtils.getUnsignedValueOf(op, 0, 7, 8);
				int r = a + o + c;
				sr.setFlagValue(Flag.CARRY, NumberUtils.getMSB(r,9) == 1);
				
				sr.setFlagValue(StatusRegister.Flag.OVERFLOW,
						(((accMsb == 1 && opMsb == 1) && resMsb == 0))
								|| ((accMsb == 0 && opMsb == 0) && resMsb == 1));
				break;
			case 3: // SUB
				sr.setFlagValue(StatusRegister.Flag.ZERO, result == 0);
				sr.setFlagValue(StatusRegister.Flag.NEGATIVE, resMsb == 1);
				sr.setFlagValue(StatusRegister.Flag.CARRY, acc < op);
				sr.setFlagValue(StatusRegister.Flag.OVERFLOW,
						(((accMsb == 1 && opMsb == 1) && resMsb == 0))
								|| ((accMsb == 0 && opMsb == 0) && resMsb == 1));
				break;
			case 4: // MUL
				sr.setFlagValue(StatusRegister.Flag.ZERO, result == 0);
				sr.setFlagValue(StatusRegister.Flag.NEGATIVE, resMsb == 1);
				sr.setFlagValue(StatusRegister.Flag.CARRY, false);
				sr.setFlagValue(StatusRegister.Flag.OVERFLOW, false);
				break;
			case 5: // NOT
				sr.setFlagValue(StatusRegister.Flag.ZERO, result == 0);
				sr.setFlagValue(StatusRegister.Flag.NEGATIVE, resMsb == 1);
				sr.setFlagValue(StatusRegister.Flag.CARRY, false);
				sr.setFlagValue(StatusRegister.Flag.OVERFLOW, false);
				break;
			case 6: // NEG
				sr.setFlagValue(StatusRegister.Flag.ZERO, result == 0);
				sr.setFlagValue(StatusRegister.Flag.NEGATIVE, resMsb == 1);
				sr.setFlagValue(StatusRegister.Flag.CARRY, false);
				sr.setFlagValue(StatusRegister.Flag.OVERFLOW, false);
				break;
			case 7: // RRC
				sr.setFlagValue(StatusRegister.Flag.ZERO, result == 0);
				sr.setFlagValue(StatusRegister.Flag.NEGATIVE, resMsb == 1);
				sr.setFlagValue(StatusRegister.Flag.CARRY,
						NumberUtils.getLSB(acc) == 1);
				sr.setFlagValue(StatusRegister.Flag.OVERFLOW, false);
				break;
			case 8: // RLC
				sr.setFlagValue(StatusRegister.Flag.ZERO, result == 0);
				sr.setFlagValue(StatusRegister.Flag.NEGATIVE, resMsb == 1);
				sr.setFlagValue(StatusRegister.Flag.CARRY, accMsb == 1);
				sr.setFlagValue(StatusRegister.Flag.OVERFLOW, false);
				break;

			case 9: // LDAR
				sr.setFlagValue(StatusRegister.Flag.ZERO, op == 0);
				sr.setFlagValue(StatusRegister.Flag.NEGATIVE, opMsb == 1);
//				sr.setFlagValue(StatusRegister.Flag.OVERFLOW, false);
//				sr.setFlagValue(StatusRegister.Flag.CARRY, false);
				break;
			case 10: // LDAA
				sr.setFlagValue(StatusRegister.Flag.ZERO, op == 0);
				sr.setFlagValue(StatusRegister.Flag.NEGATIVE, opMsb == 1);
//				sr.setFlagValue(StatusRegister.Flag.OVERFLOW, false);
//				sr.setFlagValue(StatusRegister.Flag.CARRY, false);

				break;
			case 11: // LDAI
				sr.setFlagValue(StatusRegister.Flag.ZERO, op == 0);
				sr.setFlagValue(StatusRegister.Flag.NEGATIVE, opMsb == 1);
//				sr.setFlagValue(StatusRegister.Flag.OVERFLOW, false);
//				sr.setFlagValue(StatusRegister.Flag.CARRY, false);

				break;
		}

	}

}

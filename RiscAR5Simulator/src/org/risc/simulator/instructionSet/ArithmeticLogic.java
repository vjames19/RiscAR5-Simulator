package org.risc.simulator.instructionSet;

import org.risc.simulator.memory.Register;
import org.risc.simulator.memory.StatusRegister;
import org.risc.simulator.processor.Processor;
import org.risc.simulator.util.NumberUtils;

/**
 * This class contains all the methods used for arithmetic and logic
 * operations used in the RISC R5 processor.
 */
public class ArithmeticLogic {

	/**
	 * Logic Operation, AND, using Register Direct Addressing Mode.
	 * This method provides the functionality of the AND function on 8-bit registers.
	 * It works in the following manner: A <- A and RF
	 * @param acc The accumulator register denoted above as A.
	 * @param op  The register to be used denoted above as RF.
	 * @param sr  The status register for flag management functionality.
	 */
	public static void and(Register acc, Register op, StatusRegister sr) {
		int value1 = acc.getData();
		int value2 = op.getData();
		Integer result = value1 & value2;
		FlagManagement.setFlags(value1, value2, 0, result, sr, FlagManagement.Operation.AND);
		acc.setData(result.byteValue());
	}

	/**
	 * Logic Operation, OR, using Register Direct Addressing Mode.
	 * This method provides the functionality of the OR function on 8-bit registers.
	 * It works in the following manner: A <- A or RF
	 * @param acc The accumulator register denoted above as A.
	 * @param op  The register to be used denoted above as RF.
	 * @param sr  The status register for flag management functionality.
	 */
	public static void or(Register acc, Register op, StatusRegister sr) {
		int value1 = acc.getData();
		int value2 = op.getData();
		Integer result = value1 | value2;
		FlagManagement.setFlags(value1, value2, 0, result, sr, FlagManagement.Operation.OR);
		acc.setData(result.byteValue());
	}

	/**
	 * Arithmetic Operation, Add with carry, using Register Direct Addressing Mode.
	 * This method provides the functionality of the ADDC function on 8-bit registers.
	 * It works in the following manner: A <- A + RF + CF
	 * @param acc The accumulator register denoted above as A.
	 * @param op  The register to be used denoted above as RF.
	 * @param sr  The status register for flag management functionality
	 *            and verification of the Carry flag denoted
	 *            above as CF.
	 */
	public static void addC(Register acc, Register op, StatusRegister sr) {
		int value1 = acc.getData();
		int value2 = op.getData();
		int flag = sr.getFlagValue(StatusRegister.Flag.CARRY);
		Integer result = value1 + value2 + flag;
		FlagManagement.setFlags(value1, value2, flag, result, sr, FlagManagement.Operation.ADDC);
		acc.setData(result.byteValue());
	}

	/**
	 * Arithmetic Operation, subtract, using Register Direct Addressing Mode.
	 * This method provides the functionality of the SUB function on 8-bit registers.
	 * It works in the following manner: A <- A - RF
	 * @param acc The accumulator register denoted above as A.
	 * @param op  The register to be used denoted above as RF.
	 * @param sr  The status register for flag management functionality.
	 */
	public static void sub(Register acc, Register op, StatusRegister sr) {
		int value1 = acc.getData();
		int value2 = op.getData();
		Integer result = value1 - value2;
		FlagManagement.setFlags(value1, value2, 0, result, sr, FlagManagement.Operation.SUB);
		acc.setData(result.byteValue());
	}

	/**
	 * Arithmetic Operation, multiply, using Register Direct Addressing Mode.
	 * This method provides the functionality of the MUL function on 8-bit registers.
	 * It works in the following manner: A <- A(Four LSB) * RF(Four LSB)
	 * @param acc The accumulator register denoted above as A.
	 *            Note: It will only use the four least significant bytes of the A register to perform the operation.
	 * @param op  The register to be used denoted above as RF.
	 *            Note: It will only use the four least significant bytes of the RF register to perform the operation.
	 * @param sr  The status register for flag management functionality.
	 */
	public static void mul(Register acc, Register op, StatusRegister sr) {
		int value1 = acc.getData();
		int value2 = op.getData();
		Integer result = (value1 & 0x0F) * (value2 & 0x0F); // unsigned
//		int signedValue1 = NumberUtils.getSignedValueOf(value1 , 0, 3, 4);
//		int signedValue2 = NumberUtils.getSignedValueOf(value2 , 0, 3, 4);
//		Integer result = signedValue1 * signedValue2; //signed
		FlagManagement.setFlags(value1, value2, 0, result, sr, FlagManagement.Operation.MUL);
		acc.setData(result.byteValue());
	}

	/**
	 * Logic Operation, invert, using Implicit Addressing Mode.
	 * This method provides the functionality of the NOT function on 8-bit registers.
	 * It works in the following manner: A <- not(A)
	 * Keep in mind that this method only takes the ones complement of the register A.
	 * @param acc The accumulator register denoted above as A.
	 * @param sr  The status register for flag management functionality.
	 */
	public static void not(Register acc, StatusRegister sr) {
		int value = acc.getData();
		Integer result = ~(value);
		FlagManagement.setFlags(value, 0, 0, result, sr, FlagManagement.Operation.NOT);
		acc.setData(result.byteValue());
	}

	/**
	 * Logic Operation, negate, using Implicit Addressing Mode.
	 * This method provides the functionality of the NEG function on 8-bit registers.
	 * It works in the following manner: A <- ^(A)
	 * Keep in mind that this method takes the twos complement of the register A.
	 * @param acc The accumulator register denoted above as A.
	 * @param sr  The status register for flag management functionality.
	 */
	public static void neg(Register acc, StatusRegister sr) {
//		String value = NumberUtils.intToTwosComplementString(acc.getData(), Processor.REGISTER_WIDTH);
//		Integer result = Integer.parseInt(value, 2);
		Integer result = ~acc.getData() + 0x01;
		FlagManagement.setFlags(acc.getData(), 0, 0, result, sr, FlagManagement.Operation.NEG);
		acc.setData(result.byteValue());
	}

	/**
	 * Arithmetic Operation, Rotate Right through Carry, using Implicit Addressing Mode.
	 * This method provides the functionality of the RRC function on 8-bit registers.
	 * It works in the following manner: A <- A7..A1 &, CF <- A0 where [7..0] are the bits within A.
	 * @param acc The accumulator register denoted above as A.
	 *            Note: If the carry flag is set, it will rotate with one, otherwise it will rotate with zero.
	 * @param sr  The status register for flag management functionality
	 *            and verification of the Carry flag denoted above as CF.
	 */
	public static void rrc(Register acc, StatusRegister sr) {
		int value = NumberUtils.getUnsignedValueOf(acc.getData(), 0, 7, Processor.REGISTER_WIDTH);
		int flag = sr.getFlagValue(StatusRegister.Flag.CARRY);
		Integer result = ((value >> 1) + (flag << 7)) & 0xFF;
		FlagManagement.setFlags(value, 0, flag, result, sr, FlagManagement.Operation.RRC);
		acc.setData(result.byteValue());
	}

	/**
	 * Arithmetic Operation, Rotate Left through Carry, using Implicit Addressing Mode.
	 * This method provides the functionality of the RLC function on 8-bit registers.
	 * It works in the following manner: A <- A6..A0 & CF, CF <- A7 where [7..0] are the bits within A.
	 * @param acc The accumulator register denoted above as A.
	 *            Note: If the carry flag is set, it will rotate with one, otherwise it will rotate with zero.
	 * @param sr  The status register for flag management functionality
	 *            and verification of the Carry flag denoted above as CF.
	 */
	public static void rlc(Register acc, StatusRegister sr) {
		int value = acc.getData();
		int flag = sr.getFlagValue(StatusRegister.Flag.CARRY);
		Integer result = ((value << 1) + (flag)) & 0xFF;
		FlagManagement.setFlags(value, 0, flag, result, sr, FlagManagement.Operation.RLC);
		acc.setData(result.byteValue());
	}

}
package org.risc.simulator.util;

import java.math.BigDecimal;

public class NumberUtils {

	private static final byte NUM_BITS = 32;

	/**
	 * Utilities class no instantiation
	 */
	private NumberUtils(){}
	
	/**
	 * Returns the twos complement representation in the specified numBits
	 */
	public static String intToTwosComplementString(int value, int numBits) {
		String number = Integer.toBinaryString(value);
		if (value >= 0) {// pad with zeroes
			int numberOfFills = numBits - number.length();
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < numberOfFills; i++) {
				sb.append('0');
			}
			sb.append(number);
			number = sb.toString();
			if (numBits < number.length()) {
				number = number.substring(number.length() - numBits);
			}
		} else {
			number = number.substring(NUM_BITS - numBits);
		}
		return number;
	}

	/**
	 * Returns this value in its hex representation. 
	 * This value will be masked to number of hex digits specified.
	 * @param value value to get the hex representation
	 * @param hexDigits number of hex digits wanted in the String
	 * @return the hex representation of the value masked to the number of
	 * hex digits
	 */
	public static String intToHexString(int value, int hexDigits) {
		int max = (1 << hexDigits * 4) - 1;
		String format = String.format("%%0%dX", hexDigits);
		return String.format(format, value & max).toUpperCase();
	}

	/**
	 * Returns true if the number is in the range [-(2^(numBits-1)) to 2^(numBits)]
	 * @param value
	 * @param numBits number of bits to use in this representation
	 * @return true if the number fits in the specified bits
	 */
	public static boolean fitsInBits(int value, int numBits) {
		return value >= -(1 << (numBits - 1)) && value < (1 << numBits);
	}

	/**
	 * Verifies if the value fits in the specified number of bits
	 * @throws NumberFormatException if the number doesn't fit
	 */
	public static void checkFitsInBits(int value, int numBits) {
		if (!fitsInBits(value, numBits)) {
			throw new NumberFormatException(String.format("The number %d doesn't fit in %d bits", value, numBits));
		}
	}

	/**
	 * Gets the unsigned value of the wanted bits. The most significant bit, starts with
	 * index 0
	 * <p>
	 * Indexes start and end are inclusive. They go from 0 through bitsToUse-1.
	 * <p>
	 * <code>
	 * if value is  12 and you are using a resolution of 4 bits, you can use indexes 0-3<br>
	 * getValueOf(12, 2,3, 4) = 2 <br>
	 * getValueOf(12,1,3) = 3 <br>
	 * </code>
	 * 
	 * 
	 * @param value     number to get the value of a certain bits
	 * @param start     starting bit
	 * @param end       ending bit
	 * @param bitsToUse resolution of the number to use, maximum is 32
	 * if its less than 32 it will take the least significant bits.
	 * @return the value of specified bits
	 */
	public static int getUnsignedValueOf(int value, int start, int end, int bitsToUse) {
		if (bitsToUse > NUM_BITS || start < 0 || end >= bitsToUse || start > end) {
			throw new IndexOutOfBoundsException(String.format("index %d start and %d end are out of bounds", start, end));
		}
		final int normalizedIndex = NUM_BITS - bitsToUse;
		start += normalizedIndex;
		end += normalizedIndex;
		int number = value << start >>> start;
		number >>>= NUM_BITS - end - 1;
		return number;
	}

	/**
	 * Gets the signed value of the wanted bits The most significant bit, starts with
	 * index 0
	 * <p>
	 * Indexes start and end are inclusive. They go from 0 through bitsToUse-1.
	 * <p>
	 * <code>
	 * if value is  12 and you are using a resolution of 4 bits, you can use indexes 0-3<br>
	 * getValueOf(12, 2,3, 4) = 2 <br>
	 * getValueOf(12,1,3) = 3 <br>
	 * </code>
	 * @param value     number to get the value of a certain bits
	 * @param start     starting bit
	 * @param end       ending bit
	 * @param bitsToUse resolution of the number to use, maximum is 32
	 * @return the signed value of specified bits
	 */
	public static int getSignedValueOf(int value, int start, int end, int bitsToUse) {
		if (bitsToUse > NUM_BITS || start < 0 || end >= bitsToUse || start > end) {
			throw new IndexOutOfBoundsException(String.format("index %d start and %d end are out of bounds", start, end));
		}
		final int normalizedIndex = NUM_BITS - bitsToUse;
		start += normalizedIndex;
		end += normalizedIndex;
		int number = value << start >> start;
		number >>= NUM_BITS - end - 1;
		return number;
	}

	public static String signExtendOrTrim(String number, int numberOfBitsToRepresent, boolean positive) {
		if (number.length() > numberOfBitsToRepresent) {
			return number.substring(number.length() - numberOfBitsToRepresent);
		}
		char sign = positive ? '0' : '1';
		int numberOfFills = numberOfBitsToRepresent - number.length();
		StringBuilder sb = new StringBuilder(numberOfBitsToRepresent);
		for (int i = 0; i < numberOfFills; i++) {
			sb.append(sign);
		}
		sb.append(number);
		return sb.toString();
	}

	/**
	 * Verifies if its a valid integer
	 */
	public static boolean isValidNumberString(String number, int radix) {
		try {
			Integer.parseInt(number, radix);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

	/**
	 * Gets the most significant bit in a byte
	 * @param x value to take the MSB
	 * @return MSB
	 */
	public static int getMSB(int x) {
		return getMSB(x,8);
	}
	
	public static int getMSB(int x, int bits){
		return getUnsignedValueOf(x,0,0,bits);
	}
	
	/**
	 * Gets the least significant bit in a byte
	 * @param x value to take the LSB
	 * @return LSB
	 */
	public static int getLSB(int x) {
		return getUnsignedValueOf(x, 7, 7, 8);
	}

	/**
	 * Return the number of bits needed to represent this value
	 */
	public static int getNumberOfBitsNeededInTwosComplement(int value) {
		BigDecimal b = new BigDecimal(value);
		return b.toBigInteger().bitCount() + 1;
	}
}
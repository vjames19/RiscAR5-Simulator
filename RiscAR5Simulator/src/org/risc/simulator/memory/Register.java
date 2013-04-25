package org.risc.simulator.memory;

import org.risc.simulator.util.NumberUtils;

/**
 * This register supports a maximum width of 32, since its backed by an int.
 * <br> If the data supplied is not in the range [-2^(width-1), 2^width]. It will
 * throw an exception.
 * @author Victor J.
 */
public class Register {

	/**
	 * Number of bits supported by this register
	 */
	private final int width;
	
	/**
	 * Identifier
	 */
	private String name;
	
	/**
	 * Data being stored
	 */
	private int data;

	/**
	 * Initializes this register to 0
	 * @param width number of bits this register supports
	 */
	public Register(String name, int width) {
		this(name, 0, width);
	}

	public Register(String name, int data, int width) {
		if(width > 32 || width < 0){
			throw new IllegalArgumentException("Width must be between 0 and 32");
		}
		this.width = width;
		setName(name);
		setData(data);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getWidth() {
		return width;
	}

	public int getData() {
		return data;
	}

	/**
	 * If the supplied data is not in Range[-2^(n-1), 2^n] it will throw
	 * a runtime exception
	 * @param data
	 * @throws RuntimeException if the data is not in range
	 */
	public void setData(int data) {
		NumberUtils.checkFitsInBits(data, width); // Range[-2^(n-1), 2^n]
		this.data = data;
	}

	@Override
	public String toString() {
		return "Register [width=" + width + ", name=" + name + ", data=" + data
				+ "]";
	}
	

}
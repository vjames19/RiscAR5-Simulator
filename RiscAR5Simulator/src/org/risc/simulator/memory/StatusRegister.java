package org.risc.simulator.memory;

import org.risc.simulator.util.NumberUtils;

/**
 * This class represents a StatusRegister and provides 
 * useful methods deals with flags.
 * <br> The flags supported are: Zero, Carry, Negative and Overflow.
 * @author Victor J.
 *
 */
public class StatusRegister extends Register {

	public enum Flag {
		ZERO(3), CARRY(2), NEGATIVE(1), OVERFLOW(0);

		private int index;

		Flag(int value) {
			this.index = value;
		}
	}

	private static final int WIDTH = 4;

	public StatusRegister(String name) {
		super(name, WIDTH);
		this.setData(0);
	}

	public int getFlagValue(Flag flag) {
		int index = WIDTH - flag.index - 1;
		return NumberUtils.getUnsignedValueOf(getData(), index, index, WIDTH);
	}

	public boolean isFlagSet(Flag flag) {
		return getFlagValue(flag) == 1;
	}

	public void setFlagValue(Flag flag, boolean set) {
		if (set) {
			set(flag);
		} else {
			clear(flag);
		}
	}
	
	public void setFlagValue(Flag flag, int bit){
		setFlagValue(flag, bit == 1);
	}

	private void clear(Flag flag) {
		int mask = ~(1 << flag.index);
		int flags = getData();
		flags &= mask;
		setData(flags);
	}

	private void set(Flag flag) {
		int mask = 1 << flag.index;
		int flags = getData();
		flags |= mask;
		setData(flags);
	}

	public void clearAllFlags() {
		setData(0);
	}

}
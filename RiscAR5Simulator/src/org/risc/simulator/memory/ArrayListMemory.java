package org.risc.simulator.memory;

import org.risc.simulator.util.NumberUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * ArrayList implementation of the Memory interface storing data in <b>BIG
 * ENDIAN</b>
 * <br> Any illegal access(i.e. index out bounds) will throw an exception.
 * @author Victor J.
 */
public class ArrayListMemory implements Memory {

	private final List<Integer> memory;
	private final int cellSize;
	private final int cellMask;

	/**
	 * Initializes this Memory with the number of cells and cell size specified. All cells
	 * are initialized to zero.
	 * @param numberOfCells
	 * @param cellSize
	 */
	public ArrayListMemory(int numberOfCells, int cellSize) {
		if (cellSize < 0 || cellSize > 32) {
			throw new IllegalArgumentException("cellSize is bigger than an int");
		}
		memory = new ArrayList<Integer>(numberOfCells);
		this.cellSize = cellSize;
		this.cellMask = (1 << cellSize) - 1;
		for (int i = 0; i < numberOfCells; i++) {
			memory.add(0);
		}
	}

	public int getCellSize() {
		return cellSize;
	}

	@Override
	public int getDataAt(int location) {
		return NumberUtils.getSignedValueOf(memory.get(location), 0,
				cellSize - 1, cellSize);
	}

	@Override
	public void setDataAt(int location, int data) {
		int value = cellMask & data;
		memory.set(location, value);
	}

	@Override
	public int getDataAt(int location, int fetchSize) {
		int nBits = fetchSize * cellSize;
		if (nBits > 32) {
			throw new IllegalArgumentException("number of bits is greater than an int");
		}

		int value = 0;
		for (int i = 0; i < fetchSize; i++) {
			value |= memory.get(location + i);
			if (i != fetchSize - 1) {
				value <<= cellSize;
			}
		}

		return NumberUtils.getSignedValueOf(value, 0, nBits - 1, nBits);
	}

	@Override
	public Iterator<Integer> iterator() {
		return memory.iterator();
	}

	@Override
	public void setDataAt(int location, int data, int numBits) {
		int numCells = (int) Math.ceil(numBits * 1.0 / cellSize);
		int bitsToUse = numCells * cellSize;
		for (int i = 0; i < numCells; i++) {
			int start = i * cellSize, end = (i + 1) * cellSize - 1;
			int value = NumberUtils.getSignedValueOf(data, start, end, bitsToUse);
			setDataAt(location + i, value);
		}
	}

	/**
	 * Returns the contents of this memory in the following format
	 * : [cellLocation in hex] value in twos complement
	 */
	@Override
	public String toString() {
		int size = memory.size();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < size; i++) {
			sb.append(String.format("%02X: %s\n", i, NumberUtils
					.intToTwosComplementString(memory.get(i), cellSize)));
		}
		return sb.toString();
	}

}
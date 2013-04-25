package org.risc.simulator.memory;

/**
 * A memory module. The implementation decides if its Big Endian or Little Endian.
 * @author Victor J.
 *
 */
public interface Memory extends Iterable<Integer> {

	/**
	 * Returns 1 cell of data
	 * @return data
	 */
	public int getDataAt(int location);

	/**
	 * The data returned will be a concatenation of the specified number of cells.
	 * The number of cells used in this fetch will be depicted by fetch size.
	 * @param location  where in memory
	 * @param fetchSize number of cells to use
	 */
	public int getDataAt(int location, int fetchSize);

	/**
	 * Stores the value in one cell. The value will trimmed to cell size
	 * @param data to store
	 */
	public void setDataAt(int location, int data);

	/**
	 * Stores the value, the number of cells used will be equal to ceil(numBits/cellSize)
	 */
	public void setDataAt(int location, int data, int numBits);

	/**
	 * @return Size of a cell
	 */
	public int getCellSize();

}
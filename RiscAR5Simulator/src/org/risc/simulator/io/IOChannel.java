package org.risc.simulator.io;

/**
 * Represents a port. The port is backed by a string
 * and its constructed using the number of characters supported by this port.
 * @author Victor J.
 *
 */
public class IOChannel {

	private String data;
	private int characters;

	/**
	 * Initializes this channel with the number of characters supported.
	 * @param characters number of characters that can be written to this channel
	 */
	public IOChannel(int characters) {
		this.characters = characters;
		data = "";
	}

	/**
	 * 
	 * @return the data stored in this port
	 */
	public String readString() {
		return data;
	}
	
	/**
	 * If data length is greater than the number of characters this channel 
	 * supports, the data will be trimmed to the number of characters supported.
	 * Trimming the rightmost's characters.
	 * @param data
	 */
	public void write(String data) {

		if (data.length() >= this.characters) {
			this.data = data.substring(0, this.characters);
		} else {
			this.data = data;
		}

	}
	
	/**
	 * Resets this port to contain nothing.
	 */
	public void clear(){
		data = "";
	}

}

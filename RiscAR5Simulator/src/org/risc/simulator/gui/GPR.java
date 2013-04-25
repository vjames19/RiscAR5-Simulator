package org.risc.simulator.gui;

import org.risc.simulator.processor.Processor;
import org.risc.simulator.util.NumberUtils;

import javax.swing.*;
import java.awt.*;

/**
 * This class contains the general purpose register in order to display in the UI window.
 */
public class GPR extends JPanel {

	private static final long serialVersionUID = -245370144964905410L;
	private JTextField[] registers;
	private JLabel[] registersLabels;
	private Color bgColor;

	/**
	 * Creates a new general purpose register object to display in the UI window.
	 */
	public GPR() {
		initComponents();
	}

	/**
	 * This method initializes the registers fields default values and position in the UI window.
	 */
	private void initComponents() {
		this.bgColor = new Color(255, 255, 255);
		this.registers = new JTextField[8];
		this.registersLabels = new JLabel[8];
		setLayout(new FlowLayout(FlowLayout.LEADING));
		setLocation(30, 110);
		setSize(100, 310);

		for (int i = 0; i < 8; i++) {
			this.registersLabels[i] = new JLabel("R" + i);
			this.registers[i] = new JTextField(6);
			this.registers[i].setEditable(false);
			this.registers[i].setBackground(bgColor);
			add(registersLabels[i]);
			add(registers[i]);
		}
		updateRegistersValues();
	}

	/**
	 * This method updates the values of the general purpose registers.
	 */
	public void updateRegistersValues() {
		for (int i = 0; i < Processor.NUMBER_OF_GPR; i++) {
			this.registers[i].setText(NumberUtils.intToTwosComplementString
					(UI.processor.getRegister(Processor.GPR_PREFIX + i).getData(), 8) + "");
		}
	}

	/**
	 * This method sets the value of register R0.
	 * @param data The new value of register R0.
	 */
	public void setR0(String data) {
		this.registers[0].setText(data);
	}

	/**
	 * This method sets the value of register R1.
	 * @param data The new value of register R1.
	 */
	public void setR1(String data) {
		this.registers[1].setText(data);
	}

	/**
	 * This method sets the value of register R2.
	 * @param data The new value of register R2.
	 */
	public void setR2(String data) {
		this.registers[2].setText(data);
	}

	/**
	 * This method sets the value of register R3.
	 * @param data The new value of register R3.
	 */
	public void setR3(String data) {
		this.registers[3].setText(data);
	}

	/**
	 * This method sets the value of register R4.
	 * @param data The new value of register R4.
	 */
	public void setR4(String data) {
		this.registers[4].setText(data);
	}

	/**
	 * This method sets the value of register R5.
	 * @param data The new value of register R5.
	 */
	public void setR5(String data) {
		this.registers[5].setText(data);
	}

	/**
	 * This method sets the value of register R6.
	 * @param data The new value of register R6.
	 */
	public void setR6(String data) {
		this.registers[6].setText(data);
	}

	/**
	 * This method sets the value of register R7.
	 * @param data The new value of register R7.
	 */
	public void setR7(String data) {
		this.registers[7].setText(data);
	}

}
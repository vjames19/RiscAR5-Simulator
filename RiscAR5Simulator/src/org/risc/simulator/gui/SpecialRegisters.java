package org.risc.simulator.gui;

import org.risc.simulator.processor.Processor;
import org.risc.simulator.util.NumberUtils;

import javax.swing.*;
import java.awt.*;

/**
 * This class contains the fields of the special registers for the UI.
 */
public class SpecialRegisters extends JPanel {

	private static final long serialVersionUID = 6621320209986528631L;
	private JTextField ir;
	private JLabel irLabel;
	private JTextField pc;
	private JLabel pcLabel;
	private JTextField acc;
	private JLabel accLabel;
	private JTextField sr;
	private Color bgColor;
	private JLabel srLabel;

	/**
	 * Creates new SpecialRegister object.
	 */
	public SpecialRegisters() {
		initComponents();
	}

	/**
	 * This method sets the special register fields "IR", "PC", "ACC" and "SR" and corresponding position in UI window.
	 */
	private void initComponents() {
		setLayout(new FlowLayout(FlowLayout.CENTER));
		setLocation(0, 30);
		setSize(850, 50);
		this.bgColor = new Color(255, 255, 255);

		this.irLabel = new JLabel("IR");
		ir = new JTextField(15);
		ir.setText(NumberUtils.intToTwosComplementString(
				UI.processor.getRegister(Processor.IR).getData(), 16)
				+ "");
		ir.setEditable(false);
		ir.setBackground(bgColor);
		add(irLabel);
		add(ir);

		pcLabel = new JLabel("PC");
		pc = new JTextField(6);
		pc.setText(NumberUtils.intToTwosComplementString(
				UI.processor.getRegister(Processor.PC).getData(), 8)
				+ "");
		pc.setEditable(false);
		pc.setBackground(bgColor);
		add(pcLabel);
		add(pc);

		accLabel = new JLabel("ACC");
		acc = new JTextField(6);
		acc.setText(NumberUtils.intToTwosComplementString(
				UI.processor.getRegister(Processor.ACC).getData(), 8)
				+ "");
		acc.setEditable(false);
		acc.setBackground(bgColor);
		add(accLabel);
		add(acc);

		srLabel = new JLabel("SR");
		sr = new JTextField(6);
		sr.setText(NumberUtils.intToTwosComplementString(
				UI.processor.getRegister(Processor.SR).getData(), 4)
				+ "");
		sr.setEditable(false);
		sr.setBackground(bgColor);
		add(srLabel);
		add(sr);
	}

	/**
	 * This method sets the value of the "IR" field.
	 * @param data The new value of "IR" field.
	 */
	public void setIR(String data) {
		this.ir.setText(data);
	}

	/**
	 * This method sets the value of the "PC" field.
	 * @param data The new value of "PC" field.
	 */
	public void setPC(String data) {
		this.pc.setText(data);
	}

	/**
	 * This method sets the value of the "ACC" field.
	 * @param data The new value of "ACC" field.
	 */
	public void setACC(String data) {
		this.acc.setText(data);
	}

	/**
	 * This method sets the value of the "SR" field.
	 * @param data The new value of "SR" field.
	 */
	public void setSR(String data) {
		this.sr.setText(data);
	}

	/**
	 * This method updates the value of the special registers fields.
	 */
	public void updateValues() {
		ir.setText(NumberUtils.intToTwosComplementString(UI.processor.getRegister(Processor.IR).getData(), 16) + "");
//		System.out.println(ir.getText());
		pc.setText(NumberUtils.intToTwosComplementString(UI.processor.getRegister(Processor.PC).getData(), 8) + "");
//		System.out.println(pc.getText());
		acc.setText(NumberUtils.intToTwosComplementString(UI.processor.getRegister(Processor.ACC).getData(), 8) + "");
//		System.out.println(pc.getText());
		sr.setText(NumberUtils.intToTwosComplementString(UI.processor.getRegister(Processor.SR).getData(), 4) + "");
//		System.out.println(sr.getText());
	}

}
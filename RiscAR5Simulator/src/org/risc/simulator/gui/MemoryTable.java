package org.risc.simulator.gui;

import org.risc.simulator.util.NumberUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * This class contains the table in order to display memory in the UI window.
 */
public class MemoryTable extends JPanel {

	private static final long serialVersionUID = -4128637022122784216L;
	private JTable table;
	private JScrollPane scrollPane;
	private String columnNames[];
	private String values[][];
	private JLabel memoryLabel;
	private DefaultTableModel model;

	/**
	 * Create a new Table for memory display in the UI window
	 */
	protected MemoryTable() {
		setLayout(new FlowLayout(FlowLayout.CENTER));
		setLocation(160, 90);
		setSize(250, 260);
		this.memoryLabel = new JLabel("Memory");
		createColumns();
		createValues();
		this.model = new DefaultTableModel(values, columnNames) {
			private static final long serialVersionUID = 7012936980923649907L;

			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		this.table = new JTable(model);

		// Change the selection colour
		this.table.setSelectionForeground(Color.white);
		this.table.setSelectionBackground(Color.blue);
		this.scrollPane = new JScrollPane(table);
		this.scrollPane.setPreferredSize(new Dimension(200, 230));
		add(memoryLabel);
		add(scrollPane);
	}

	/**
	 * This method sets the default values of the table to 0.
	 */
	private void createValues() {
		this.values = new String[256][2];
		for (int iY = 0; iY < 256; iY++) {
			for (int iX = 0; iX < 2; iX++) {
				if (iX == 0) {
					values[iY][iX] = NumberUtils.intToHexString(iY, 2);
				} else {
					values[iY][iX] = NumberUtils.intToHexString(0, 2) + "";
				}
			}
		}
	}

	/**
	 * This method creates the columns of the table.
	 */
	private void createColumns() {
		this.columnNames = new String[2];
		this.columnNames[0] = "Index";
		this.columnNames[1] = "Values";
	}

	/**
	 * This method updates the values of the table.
	 */
	public void updateMemory() {
		for (int i = 0; i < 256; i++) {
			values[i][1] = NumberUtils.intToHexString(UI.processor.getMemory().getDataAt(i), 2);
//			System.out.println(values[i][1]);
		}
		model.setDataVector(values, columnNames);
		table.revalidate();
		model.fireTableDataChanged();
	}

}
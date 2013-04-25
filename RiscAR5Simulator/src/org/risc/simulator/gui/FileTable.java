package org.risc.simulator.gui;

import org.risc.simulator.processor.Instruction;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.util.List;

/**
 * This class contains the table in order to display the loaded file in the UI window.
 */
public class FileTable extends JPanel {

	private static final long serialVersionUID = 6317947822072124879L;
	private JTable table;
	private TableColumn tcol;
	private JScrollPane scrollPane;
	private String columnNames[];
	private String values[][];
	private JLabel memoryLabel;
	private DefaultTableModel model;

	/**
	 * Creates a new Table for file display in the UI window.
	 */
	protected FileTable() {
		setLayout(new FlowLayout(FlowLayout.CENTER));
		setLocation(440, 90);
		setSize(250, 260);
		this.memoryLabel = new JLabel("Display");
		createColumns();
		createValues();
		this.model = new DefaultTableModel(values, columnNames) {
			private static final long serialVersionUID = -7271849695661689402L;

			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		this.table = new JTable(model);
		tcol = table.getColumnModel().getColumn(0);
		tcol.setCellRenderer(new CustomTableCellRenderer());
		table.setColumnSelectionAllowed(false);
		table.setRowSelectionAllowed(false);
		this.table.setSelectionForeground(Color.white);
		this.table.setSelectionBackground(Color.blue);
		this.scrollPane = new JScrollPane(table);
		this.scrollPane.setPreferredSize(new Dimension(200, 230));
		add(memoryLabel);
		add(scrollPane);
	}

	/**
	 * This method sets the default values of the table to empty.
	 */
	private void createValues() {
		this.values = new String[256][1];
		for (int iY = 0; iY < 256; iY++) {
			values[iY][0] = "";
		}
	}

	/**
	 * This method updates the value of the table based on the list of instructions.
	 * @param ins The list of instructions with the new values of the table.
	 */
	public void update(List<Instruction> ins) {
		for (int i = 0; i < 256; i++) {
			if (i < ins.size())
				values[i][0] = ins.get(i).getOriginalInstruction();
			else
				values[i][0] = "";
		}

		model.setDataVector(values, columnNames);

		table.revalidate();
		tcol = table.getColumnModel().getColumn(0);
		tcol.setCellRenderer(new CustomTableCellRenderer());
		model.fireTableDataChanged();
	}

	/**
	 * This method updates the table and color the selected cells.
	 * @param list The indexes of the cells to be colored.
	 */
	public void updateErrors(List<Integer> list) {
		for (int i = 0; i < list.size(); i++) {
			int index = list.get(i);
//			System.out.println(list.get(i));
			values[index][0] = values[index][0];
		}

		model.setDataVector(values, columnNames);
		table.revalidate();
		tcol = table.getColumnModel().getColumn(0);
		tcol.setCellRenderer(new CustomTableCellRenderer());

		model.fireTableDataChanged();
	}

	/**
	 * This method creates the columns of the table.
	 */
	private void createColumns() {
		this.columnNames = new String[1];
		this.columnNames[0] = "Instructions";
	}

	private class CustomTableCellRenderer extends DefaultTableCellRenderer {

		private static final long serialVersionUID = 8620624398951221107L;

		public Component getTableCellRendererComponent(JTable table, Object obj,
		                                               boolean isSelected, boolean hasFocus,
		                                               int row, int column) {
			Component cell = super.getTableCellRendererComponent(table, obj, isSelected,
					hasFocus, row, column);
			if (table.getValueAt(row, column).toString().contains("*")) {
				cell.setBackground(new Color(255, 100, 100));
			} else {
				cell.setBackground(Color.white);
			}
			return cell;
		}

	}

}
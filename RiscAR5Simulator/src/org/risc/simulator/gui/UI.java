package org.risc.simulator.gui;

import org.risc.simulator.io.FileLoader;
import org.risc.simulator.io.IOChannel;
import org.risc.simulator.processor.Processor;
import org.risc.simulator.processor.RISCAR5Processor;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This class is the user interface for the RISC AR5 simulator
 */
public class UI extends JFrame implements ActionListener {

	private static final long serialVersionUID = -5609563188019332472L;
	private JMenuBar menuBar;
	private JMenu fileMenu;
	private JMenuItem load;
	private JMenuItem exit;

	private JPanel rightPanel;
	private JPanel lowerRightPanel;
	private JPanel upperRightPanel;

	private Color bgColor;
	private JButton loadFile;
	private JButton run;
	private JButton step;

	private JTextField input;
	private JTextField output;
	private JFileChooser fc;
	private FileLoader fileLoader;
	protected static Processor processor;
	private MemoryTable mem;
	private FileTable displayFile;
	private SpecialRegisters specialRegisters;
	private GPR gpr;
	private IOChannel inChannel, outChannel;

	private int memoryIndex;
	private boolean runExecuted;
	private boolean processRunning;

	/**
	 * Create a new RISC AR5 simulator UI
	 */
	public UI() {
		GUI_init();
	}
	/**
	 * This method initializes UI components
	 */
	private void GUI_init() {
		processorInit();
		this.menuBar = new JMenuBar();
		this.rightPanel = new JPanel();
		this.lowerRightPanel = new JPanel();
		this.upperRightPanel = new JPanel();
		this.fileMenu = new JMenu("File");
		this.load = new JMenuItem("Load...");
		this.exit = new JMenuItem("Exit");
		this.bgColor = new Color(255, 255, 255);
		this.loadFile = new JButton("Load File");
		this.run = new JButton("Run");
		this.step = new JButton("Step");
		this.fc = new JFileChooser();
		this.memoryIndex = 0;
		this.runExecuted = false;
		this.mem = new MemoryTable();
		this.displayFile = new FileTable();
		this.specialRegisters = new SpecialRegisters();
		this.gpr = new GPR();

		addItemsInFile();
		addToMenuBar();
		setLayout(null);

		add(gpr);
		add(mem);
		add(displayFile);
		setUpperRightPanel();
		add(upperRightPanel);
		add(specialRegisters);
		setRightPanel();
		add(rightPanel);
		setLowerRightPanel();
		add(lowerRightPanel);
		addListeners();
		setFrame();
	}
	/**
	 * This method initializes processor and I/O channel
	 */
	private void processorInit() {
		UI.processor = new RISCAR5Processor();
		this.inChannel = UI.processor.getInputChannel();
		this.outChannel = UI.processor.getOutputChannel();
		processRunning = false;
	}
	/**
	 * This method set UI window size, title, location and parameters.
	 */
	private void setFrame() {
		setJMenuBar(menuBar);
		setTitle("RISC AR5 Simulator");
		setSize(850, 500);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}
	/**
	 * This method adds items to File Menu.
	 */
	private void addItemsInFile() {
		this.fileMenu.add(load);
		this.fileMenu.add(exit);
	}
	/**
	 * This method adds action listeners to buttons and menu items.
	 */
	private void addListeners() {
		this.loadFile.addActionListener(this);
		this.load.addActionListener(this);
		this.exit.addActionListener(this);
		this.run.addActionListener(this);
		this.step.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == loadFile || e.getSource() == load) {
			fc.showOpenDialog(this);
			if (fc.getSelectedFile() != null) {
				fileLoader = new FileLoader(fc.getSelectedFile());
				if (fileLoader.isFileLoaded()) {
					if ((!fileLoader.isFileValid() && !fileLoader.isFileTooLong())) {
						displayFile.update(fileLoader.getInstructionMapList());
						displayFile.updateErrors(fileLoader.getInstructionMapListErrorIndexes());
						processorInit();
						specialRegisters.updateValues();
						gpr.updateRegistersValues();
						input.setText("00");
						output.setText("0000");
					} else if (fileLoader.isFileTooLong()) {
						JOptionPane.showMessageDialog(this, "File is too long.");
					} else {
						processorInit();
						specialRegisters.updateValues();
						UI.processor.init(fileLoader.getInstructionMapList());
						mem.updateMemory();
						gpr.updateRegistersValues();
						input.setText("00");
						output.setText("0000");
						displayFile.update(fileLoader.getInstructionMapList());
						processRunning = true;
					}
				} else {
					JOptionPane.showMessageDialog(this, "Not supported file.");
				}
			}
		} else if (e.getSource() == run) {
			if (processor.isRunning() && processRunning) {
				handleInput();
				this.runExecuted = true;
				UI.processor.run();
				handleOutput();
				specialRegisters.updateValues();
				gpr.updateRegistersValues();
				mem.updateMemory();
				JOptionPane.showMessageDialog(this, "Simulation finished.");
			} else {
				processRunning = false;
			}
		} else if (e.getSource() == step) {
			if (processor.isRunning() && processRunning) {
				handleInput();
				this.memoryIndex++;
				UI.processor.step();
				handleOutput();
				specialRegisters.updateValues();
				gpr.updateRegistersValues();
				mem.updateMemory();
				if (!processor.isRunning()) {
					JOptionPane.showMessageDialog(this, "Simulation finished.");
				}
			} else {
				processRunning = false;
//				JOptionPane.showMessageDialog(this, "Simulation finished.");
			}
		} else if (e.getSource() == exit) {
			System.exit(0);
		}

	}
	/**
	 * This method sets "Load" button position in the UI window.
	 */
	private void setUpperRightPanel() {
		this.upperRightPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

		this.upperRightPanel.setLocation(720, 30);
		this.upperRightPanel.setSize(100, 50);

		this.upperRightPanel.add(loadFile);
	}
	/**
	 * This method sets "Run" and "Step" buttons position in the UI window.
	 */
	private void setRightPanel() {
		this.rightPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		this.rightPanel.setLocation(720, 110);
		this.rightPanel.setSize(100, 100);

		this.rightPanel.add(run);
		this.rightPanel.add(step);
	}
	/**
	 * This method sets "Input" and "Output" fields position and default values.
	 */
	private void setLowerRightPanel() {
		this.lowerRightPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		this.lowerRightPanel.setLocation(720, 230);
		this.lowerRightPanel.setSize(100, 100);
		JLabel inputLabel = new JLabel("Input");
		input = new JTextField(6);
		input.setDocument(new JTextFieldLimit(2));
		input.setText("00");
//		input.setText(NumberUtils.intToHexString(memory.getDataAt(250, 2), 2));
		input.setEditable(true);
		input.setBackground(bgColor);
		JLabel outputLabel = new JLabel("Output");
		output = new JTextField(6);
		output.setDocument(new JTextFieldLimit(4));
		output.setText("0000");
//		output.setText(NumberUtils.intToHexString(memory.getDataAt(252, 3), 4));
		output.setEditable(false);
		output.setBackground(bgColor);
		this.lowerRightPanel.add(inputLabel);
		this.lowerRightPanel.add(input);
		this.lowerRightPanel.add(outputLabel);
		this.lowerRightPanel.add(output);
	}
	/**
	 * This method gets the keyboard input and validates the entered text.
	 * If the input data is not valid a message is pop in the screen, otherwise the input is handled.
	 */
	private void handleInput() {
		String input = this.input.getText();
		if (input.matches("[0-9A-Za-z]{1,2}")) {
			this.inChannel.write(input);
		} else {
			JOptionPane.showMessageDialog(this, "Incorrect Input Data.");
		}

	}
	/**
	 * This method set the text in the "Output" field based on the user input.
	 */
	private void handleOutput() {
		output.setText(outChannel.readString());
	}

	/**
	 * This method adds the items to the menu bar.
	 */
	private void addToMenuBar() {
		this.menuBar.add(fileMenu);
	}
	/**
	 * This method gets the memory execution index.
	 * @return The memory execution index.
	 */
	public int getMemoryIndex() {
		return memoryIndex;
	}
	/**
	 * This method gets the state of the processor: running  or not running.
	 * @return The state of the processor: running or not running.
	 */
	public boolean isRunExecuted() {
		return runExecuted;
	}
	/**
	 * This method gets the text typed in the "Input" field in the UI window.
	 * @return The text typed in the "Input" field.
	 */
	protected String getInput() {
		return input.getText();
	}
	/**
	 * This method sets the "Output" field in the UI window.
	 */
	protected void setOutput(String data) {
		this.output.setText(data);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				UI ex = new UI();
				ex.setVisible(true);
			}
		});
	}


	class JTextFieldLimit extends PlainDocument {

		private static final long serialVersionUID = -6122017668120182239L;
		private int limit;

		JTextFieldLimit(int limit) {
			super();
			this.limit = limit;
		}

		JTextFieldLimit(int limit, boolean upper) {
			super();
			this.limit = limit;
		}

		public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
			if (str == null)
				return;

			if ((getLength() + str.length()) <= limit) {
				super.insertString(offset, str, attr);
			}
		}
	}

}
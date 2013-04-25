package org.risc.simulator.io;

import org.risc.simulator.processor.Instruction;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This class provides the functionality for loading a file,
 * validating each of its contents and determining if its
 * a valid file for processing and executing operations.
 */
public class FileLoader {

	private boolean fileValid;
	private boolean fileLoaded;
	private boolean fileTooLong;
	private BufferedReader bufferedFile;
	private static final int FILE_INSTRUCTION_LIMIT = 64;
	private List<Instruction> instructionMapList = new ArrayList<Instruction>();
	private List<Integer> instructionMapListErrorIndexes = new ArrayList<Integer>();

	/**
	 * Instantiates a new File loader.
	 * @param file The file that will be parsed.
	 */
	public FileLoader(File file) {
		if (file.isFile()) {
			try {
				this.bufferedFile = new BufferedReader(new FileReader(file));
			} catch (FileNotFoundException e) {
				this.fileLoaded = false;
				return;
			}
			this.fileLoaded = true;
			parseFile();
		} else {
			this.fileLoaded = false;
		}
	}

	/**
	 * Simple getter, specifies if the given file is valid.
	 * @return true if file is valid, false otherwise.
	 */
	public boolean isFileValid() {
		return fileValid;
	}

	/**
	 * Simple getter, specifies if the given file is loaded correctly.
	 * @return true if file is loaded, false otherwise.
	 */
	public boolean isFileLoaded() {
		return fileLoaded;
	}

	/**
	 * Simple getter, specifies if the given file is too long (more than 64 lines).
	 * @return true if file is too long, false otherwise.
	 */
	public boolean isFileTooLong() {
		return fileTooLong;
	}

	/**
	 * Getter providing a list containing all the instructions in the file.
	 * @return The list will all the instructions.
	 */
	public List<Instruction> getInstructionMapList() {
		return instructionMapList;
	}

	/**
	 * Getter providing a list of the indexes of all
	 * the instructions that contain errors in the file.
	 * @return The list with the error indexes.
	 */
	public List<Integer> getInstructionMapListErrorIndexes() {
		return instructionMapListErrorIndexes;
	}

	/**
	 * Private method used to parse through the file
	 * validating each instruction and determining
	 * if the file can be executed and processed.
	 */
	private void parseFile() {
		boolean containsErrors = false;
		boolean hasStopInstruction = false;
		try {
			int count = 0;
			String instructionString;
			boolean instructionValid;
			while ((instructionString = bufferedFile.readLine()) != null) {
				System.out.println(instructionString);
				instructionValid = instructionString.matches("[0-9A-Fa-f]{4}");
				if (instructionValid && count < FILE_INSTRUCTION_LIMIT) {
					Instruction instruction = new Instruction(instructionString);
					if (instruction.isStopInstruction()) {
						instructionMapList.add(count, instruction);
						hasStopInstruction = true;
						break;
					} else {
						instructionMapList.add(count++, instruction);
					}
				} else if (!instructionValid && count < FILE_INSTRUCTION_LIMIT) {
					instructionMapListErrorIndexes.add(count);
					instructionMapList.add(count++, new Instruction("*" + instructionString));
					containsErrors = true;
				} else {
					fileTooLong = true;
					System.err.println("File too long, ignoring instructions after the 64th line.");
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			fileValid = hasStopInstruction && !containsErrors;
			if (bufferedFile != null) {
				try {
					bufferedFile.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
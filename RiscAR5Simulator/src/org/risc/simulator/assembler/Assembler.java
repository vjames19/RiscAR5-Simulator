package org.risc.simulator.assembler;

import org.risc.simulator.processor.Instruction;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
/**
 * RISC AR5 assembler
 * <br> instruction format: mnemonic [register number] [constant]
 * @author Victor J.
 *
 * @version $Revision: 1.0 $
 */
public class Assembler {
	//TODO: Accept comments
	/**
	 * Field assambled.
	 */
	private List<Instruction> assambled;
	/**
	 * Field in.
	 */
	private Scanner in;
	/**
	 * Field lineNumber.
	 */
	private int lineNumber = 1;
	/**
	 * Field set.
	 */
	private InstructionSet set;

	/**
	 * Constructor for Assembler accepts a file with instruction to be assembled.
	 * @param input File
	 * @throws FileNotFoundException if file not found
	 * @throws AssemblerException if the input file is null or is not a file
	 */
	public Assembler(File input) throws FileNotFoundException, AssemblerException {
		if (input != null && input.isFile()) {
			in = new Scanner(input);
			set = InstructionSet.getInstance();
			assambled = new ArrayList<Instruction>();
		} else {
			throw new AssemblerException("Invalid File" + input);
		}
	}

	/**
	 * Parses the file and assembles the instructions
	 * @return the assembled instructions 
	 * @throws AssemblerException if a there is an invalid mnemonic or if an
	 * operand is out of range. 
	 * */
	public List<Instruction> assemble() throws AssemblerException {
		while (in.hasNextLine()) {
			Instruction inst = nextInstruction();
			if (!inst.isValid()) {
				throw new AssemblerException(String.format(
						"Invalid assambled instruction %s in line %d", inst,
						lineNumber));
			}
			assambled.add(inst);
			if (inst.isStopInstruction()) {//shortcut
				System.out.println("found stop instruction");
				return assambled;
			}
			lineNumber++;
		}
		return assambled;
	}

	/**
	 * Parses the next instruction	
	 * @return the assembled instruction 
	 * @throws AssemblerException any error parsing the instruction 
	 * */
	private Instruction nextInstruction() throws AssemblerException {
		String line = in.nextLine().toLowerCase().trim();
		if (!line.matches("([a-zA-z]+)(\\s+[-]{0,1}\\d+){0,2}")) {
			throw new AssemblerException("Invalid format at line " + lineNumber);
		}

		return assembleInstruction(line.split("\\s+"));
	}

	/**
	 * Method assembleInstruction.
	 * @param args String[]
	 * @return Instruction
	 * @throws AssemblerException
	 */
	private Instruction assembleInstruction(String[] args)
			throws AssemblerException {
		AssemblerInstruction a = set.get(args[0]);
		if (a == null) {
			throw new AssemblerException(String.format(
					"Invalid mnemonic %s at line %d", args[0], lineNumber));
		}

		return a.getMode().getInstruction(a.getOpcode(), args);
	}
}

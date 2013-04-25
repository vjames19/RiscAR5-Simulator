package org.risc.simulator.processor;

import org.risc.simulator.instructionSet.ArithmeticLogic;
import org.risc.simulator.instructionSet.LoadStore;
import org.risc.simulator.instructionSet.ProgramFlow;
import org.risc.simulator.io.IOChannel;
import org.risc.simulator.memory.ArrayListMemory;
import org.risc.simulator.memory.Memory;
import org.risc.simulator.memory.Register;
import org.risc.simulator.memory.StatusRegister;
import org.risc.simulator.util.NumberUtils;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RISCAR5Processor implements Processor {

	private Map<String, Register> registers;
	private StatusRegister sr;
	private Memory memory;
	private static final int LOAD_LOCATION = 0;
	private Register acc, ir, pc;
	private boolean run = true;
	private List<Instruction> instructionList;
	private IOChannel in, out;
	private static final int INPUT_LOC = 250, OUTPUT_LOC = 252;
	private static final int INPUT_SIZE = 2, OUTPUT_SIZE = 4;

	public RISCAR5Processor() {
		initComponents();
	}

	private void initComponents() {
		acc = new Register(ACC, REGISTER_WIDTH);
		sr = new StatusRegister(SR);
		pc = new Register(PC, REGISTER_WIDTH);
		ir = new Register(IR, IR_SIZE);
		registers = new HashMap<String, Register>(NUMBER_OF_GPR + 4);

		for (int i = 0; i < NUMBER_OF_GPR; i++) {
			String name = GPR_PREFIX + i;
			registers.put(name, new Register(name, REGISTER_WIDTH));
		}
		registers.put(ir.getName(), ir);
		registers.put(acc.getName(), acc);
		registers.put(sr.getName(), sr);
		registers.put(pc.getName(), pc);
		memory = new ArrayListMemory(MEMORY_SIZE, MEMORY_CELL_SIZE);
		in = new IOChannel(INPUT_SIZE);
		out = new IOChannel(OUTPUT_SIZE);
	}

	@Override
	public Register getRegister(String key) {
		return registers.get(key);
	}

	@Override
	public Collection<Register> getRegisters() {

		return registers.values();
	}

	@Override
	public Memory getMemory() {

		return memory;
	}

	@Override
	public void step() {
		fetchDecodeExecute();
	}

	@Override
	public void run() {
		while (run) {
			fetchDecodeExecute();
		}
	}

	private void fetchDecodeExecute() {
		//fetch
		int location = pc.getData();
		Instruction instruction = new Instruction(memory.getDataAt(location,
				PC_INCREMENT));

		location += PC_INCREMENT;
		pc.setData(location);
		ir.setData(instruction.getInstruction());

		updateDataFromPorts();

		if (!instruction.isValid()) {
			return;
		}

		//get the opcode and select an addressing mode
		//decode and execute
		int opCode = instruction.getOpCode();
		if (opCode >= 6 && opCode <= 9 || opCode >= 16 && opCode <= 19
				|| opCode == 24 || opCode == 31)
			implicit(instruction);
		else if (opCode >= 0 && opCode <= 1 || opCode >= 3 && opCode <= 5
				|| opCode >= 10 && opCode <= 11)
			registerDirect(instruction);
		else if (opCode >= 12 && opCode <= 13)
			direct(instruction);
		else if (opCode == 14)
			immediate(instruction);

		updateDataFromPorts();
	}

	private void updateDataFromPorts() {
		String data = in.readString();
		for (int i = 0; i < data.length(); i++) {
			memory.setDataAt(INPUT_LOC + i, (int) data.charAt(i));
		}

		char[] ar = new char[OUTPUT_SIZE];
		for (int i = 0; i < OUTPUT_SIZE; i++) {
			ar[i] = (char) memory.getDataAt(OUTPUT_LOC + i);
		}

		out.write(new String(ar));

	}

	private void immediate(Instruction instruction) {
		int operand = instruction.getOperand(8, 15);
		switch (instruction.getOpCode()) {
			case 14:
				LoadStore.ldaI(acc, operand, sr);
				break;
			default:
				break;
		}
	}

	private void direct(Instruction instruction) {
		int address = instruction.getOperand(8, 15);
		if (address < 0) {
			address = MEMORY_SIZE + address;
		}
		switch (instruction.getOpCode()) {
			case 12:
				LoadStore.ldaA(acc, memory, address, sr);
				break;
			case 13:
				LoadStore.staA(acc, memory, address);
				break;
			default:
				break;
		}
	}

	private void implicit(Instruction instruction) {
		Register r7 = registers.get(GPR_PREFIX + 7);
		switch (instruction.getOpCode()) {
			case 6:
				ArithmeticLogic.neg(acc, sr);
				break;
			case 7:
				ArithmeticLogic.not(acc, sr);
				break;
			case 8:
				ArithmeticLogic.rlc(acc, sr);
				break;
			case 9:
				ArithmeticLogic.rrc(acc, sr);
				break;
			case 16:
				ProgramFlow.brz(pc, r7, sr);
				break;
			case 17:
				ProgramFlow.brc(pc, r7, sr);
				break;
			case 18:
				ProgramFlow.brn(pc, r7, sr);
				break;
			case 19:
				ProgramFlow.bro(pc, r7, sr);
				break;
			case 24:
				ProgramFlow.nop();
				break;
			case 31:
				ProgramFlow.stop(this);
				break;
			default:
				break;
		}
	}

	private void registerDirect(Instruction instruction) {
		int registerNumber = NumberUtils.getUnsignedValueOf(
				instruction.getInstruction(), 5, 7, Processor.IR_SIZE);
		Register op = registers.get(GPR_PREFIX + registerNumber);
		switch (instruction.getOpCode()) {
			case 0:
				ArithmeticLogic.and(acc, op, sr);
				break;
			case 1:
				ArithmeticLogic.or(acc, op, sr);
				break;
			case 3:
				ArithmeticLogic.addC(acc, op, sr);
				break;
			case 4:
				ArithmeticLogic.sub(acc, op, sr);
				break;
			case 5:
				ArithmeticLogic.mul(acc, op, sr);
				break;
			case 10:
				LoadStore.ldaR(acc, op, sr);
				break;
			case 11:
				LoadStore.staR(acc, op);
				break;
			default:
				break;
		}
	}

	@Override
	public void init(List<Instruction> instructions) {
		this.instructionList = instructions;
		int location = LOAD_LOCATION;
		for (Instruction instruction : instructions) {//load instructions to memory
			int value = instruction.getInstruction();
			memory.setDataAt(location, value, IR_SIZE);
			location += PC_INCREMENT;
		}
	}

	@Override
	public void setRun(boolean run) {
		this.run = run;
	}

	@Override
	public boolean isRunning() {
		return run;
	}

	public List<Instruction> getInstructionList() {
		return instructionList;
	}

	@Override
	public IOChannel getInputChannel() {
		return in;
	}

	@Override
	public IOChannel getOutputChannel() {
		return out;
	}

	@Override
	public Register getRegister(int i) {
		return getRegister(GPR_PREFIX + i);
	}

}
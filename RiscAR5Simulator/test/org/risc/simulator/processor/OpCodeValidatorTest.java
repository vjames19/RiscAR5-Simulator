package org.risc.simulator.processor;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class OpCodeValidatorTest {

	private int opCode;
	private boolean expected;

	public OpCodeValidatorTest(int opCode, boolean expected) {
		this.opCode = opCode;
		this.expected = expected;
	}

	@Parameters
	public static Collection<Object[]> opCodes() {
		Object[][] data = new Object[][]{
				{0, true},
				{1, true},
				{2, false},
				{3, true},
				{4, true},
				{5, true},
				{6, true},
				{7, true},
				{8, true},
				{9, true},
				{10, true},
				{11, true},
				{12, true},
				{13, true},
				{14, true},
				{15, false},
				{16, true},
				{17, true},
				{18, true},
				{19, true},
				{20, false},
				{21, false},
				{22, false},
				{23, false},
				{24, true},
				{25, false},
				{26, false},
				{27, false},
				{28, false},
				{29, false},
				{30, false},
				{31, true},
				{32, false},
				{33, false},
				{34, false},
				{-1, false},
				{-2, false},
				{-15, false},
		};
		return Arrays.asList(data);
	}

	@Test
	public void testOpCodeValidation() {
		assertEquals(expected, Instruction.isOpCodeValid(opCode));
	}

}
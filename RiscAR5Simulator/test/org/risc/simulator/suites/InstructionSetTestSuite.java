package org.risc.simulator.suites;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.risc.simulator.instructionSet.ArithmeticLogicTest;
import org.risc.simulator.instructionSet.FlagManagementTest;
import org.risc.simulator.instructionSet.LoadStoreTest;
import org.risc.simulator.instructionSet.ProgramFlowTest;

@RunWith(Suite.class)
@SuiteClasses(value = {ArithmeticLogicTest.class, LoadStoreTest.class, ProgramFlowTest.class, FlagManagementTest.class})

public class InstructionSetTestSuite {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

}
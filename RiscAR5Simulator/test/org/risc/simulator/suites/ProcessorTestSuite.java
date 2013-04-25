package org.risc.simulator.suites;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.risc.simulator.processor.InstructionTest;
import org.risc.simulator.processor.OpCodeValidatorTest;
import org.risc.simulator.processor.RISCAR5ProcessorTest;

@RunWith(Suite.class)
@SuiteClasses({InstructionTest.class, OpCodeValidatorTest.class, RISCAR5ProcessorTest.class})
public class ProcessorTestSuite {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

}
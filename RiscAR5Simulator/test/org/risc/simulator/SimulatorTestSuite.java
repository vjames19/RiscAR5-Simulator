package org.risc.simulator;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.risc.simulator.suites.*;

@RunWith(Suite.class)
@SuiteClasses({InstructionSetTestSuite.class, IOTestSuite.class, MemoryTestSuite.class, ProcessorTestSuite.class,
		UtilTestSuite.class})
public class SimulatorTestSuite {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

}
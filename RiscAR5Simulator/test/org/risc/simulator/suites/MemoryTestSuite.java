package org.risc.simulator.suites;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.risc.simulator.memory.ArrayListMemoryTest;
import org.risc.simulator.memory.RegisterTest;
import org.risc.simulator.memory.StatusRegisterTest;

@RunWith(Suite.class)
@SuiteClasses({ArrayListMemoryTest.class, StatusRegisterTest.class, RegisterTest.class})
public class MemoryTestSuite {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

}
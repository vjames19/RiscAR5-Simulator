package org.risc.simulator.suites;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.risc.simulator.util.NumberUtilsTest;

@RunWith(Suite.class)
@SuiteClasses({NumberUtilsTest.class})
public class UtilTestSuite {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

}
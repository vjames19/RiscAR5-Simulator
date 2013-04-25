package org.risc.simulator.suites;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.risc.simulator.io.FileLoaderTest;

@RunWith(Suite.class)
@SuiteClasses({FileLoaderTest.class})
public class IOTestSuite {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

}
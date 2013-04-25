package org.risc.simulator.memory;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.risc.simulator.memory.StatusRegister.Flag;

import static org.junit.Assert.assertTrue;

public class StatusRegisterTest {

	private StatusRegister sr;

	@Before
	public void setUp() throws Exception {
		sr = new StatusRegister("sr");
	}

	@After
	public void tearDown() throws Exception {
		sr = null;
	}

	@Test
	public void testGetInitialFlags() {
		for (Flag flag : Flag.values()) {
			assertTrue(sr.getFlagValue(flag) == 0);
		}
	}

	@Test
	public void testSetAllFlagsToOne() {
		for (Flag flag : Flag.values()) {
			sr.setFlagValue(flag, true);
		}
		for (Flag flag : Flag.values()) {
			assertTrue(sr.getFlagValue(flag) == 1);
		}
	}

	@Test
	public void testSetIndividualFlags() {
		int i = 0;
		for (Flag flag : Flag.values()) {
			System.out.println(flag);
			sr.setFlagValue(flag, i % 2 == 0);
			i++;
		}
		System.out.println(sr.getData());
		i = 0;
		for (Flag flag : Flag.values()) {
			int expected = (i % 2 == 0 ? 1 : 0);
			System.out.println(flag);
			assertTrue(sr.getFlagValue(flag) == expected);
			i++;
		}
	}

}
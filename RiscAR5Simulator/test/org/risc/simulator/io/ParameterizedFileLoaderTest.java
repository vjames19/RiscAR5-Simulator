package org.risc.simulator.io;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class ParameterizedFileLoaderTest {

	private FileLoader loader;
	private boolean expected;
	private String path;
	private static String basePath = "TextFiles/";
	public ParameterizedFileLoaderTest(String path, boolean expected ){
		this.path = basePath + path;
		loader = new FileLoader(new File(ParameterizedFileLoaderTest.class.getResource(this.path).getFile()));
		this.expected = expected;
	}
	
	@Parameters
	public static Collection<Object[]> files(){
		Object[][] params = new Object[][]{
				{"moreThan4.txt", false},
				{"illegalText.txt", false},
				{"doesntHaveStop.txt", false},
				{"illegalAfterStop.txt",true}
			
		};
		
		return Arrays.asList(params);
	}
	@Test
	public void testFile() {
		System.out.println("Testing " + path);
		assertEquals(expected, loader.isFileValid());
	}

}

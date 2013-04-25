package org.risc.simulator.io;

import org.junit.Test;

import java.io.File;
import java.util.List;

/**
 * Author: Samy
 */
public class FileLoaderTest {

	private static FileLoader longFile = new FileLoader(new File(FileLoaderTest.class.getResource("TextFiles/longFile.txt")
			.getFile()));
	private static FileLoader validFile = new FileLoader(new File(FileLoaderTest.class.getResource("TextFiles/validFile.txt")
			.getFile()));
	private static FileLoader errorsInFile = new FileLoader(new File(FileLoaderTest.class.getResource("TextFiles/errorsInFile" +
			".txt").getFile()));

	@Test
	public void testIsFileValid() throws Exception {
		assert !longFile.isFileValid();
		assert validFile.isFileValid();
		assert !errorsInFile.isFileValid();
	}

	@Test
	public void testIsFileLoaded() throws Exception {
		assert longFile.isFileLoaded();
		assert validFile.isFileLoaded();
		assert errorsInFile.isFileLoaded();
	}

	@Test
	public void testIsFileTooLong() throws Exception {
		assert longFile.isFileTooLong();
		assert !validFile.isFileTooLong();
		assert !errorsInFile.isFileTooLong();
	}

	@Test
	public void testGetInstructionMapList() throws Exception {
		assert List.class.isInstance(longFile.getInstructionMapList());
		assert List.class.isInstance(validFile.getInstructionMapList());
		assert List.class.isInstance(errorsInFile.getInstructionMapList());
	}

	@Test
	public void testGetInstructionMapListErrorIndexes() throws Exception {
		assert List.class.isInstance(longFile.getInstructionMapListErrorIndexes());
		assert List.class.isInstance(validFile.getInstructionMapListErrorIndexes());
		assert List.class.isInstance(errorsInFile.getInstructionMapListErrorIndexes());
	}

}
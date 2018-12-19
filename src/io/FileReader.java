package io;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FileReader {
	public List<String> readFileLines(String filename) {

		BufferedReader br = null;

		try {
			br = Files.newBufferedReader(Paths.get(filename));
			return br.lines().collect(Collectors.toList());

		} catch (IOException e) {
			System.out.println("File " + filename + " could not be opened.");
			return new ArrayList<>();
		} finally {
			try {
				// In general, whenever you acquired a ressource (like opening a
				// file),
				// you have to close it (if it's not done automatically).
				// If you open in a try-catch-block, always close it in the
				// finally-block,
				// which is always executed
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private List<String> readFileLines2(String filename) {

		// This is a try-with-resources block. It simplifies the above procedure
		// for classes which implement the java.io.Closeable interface.
		// This is just for reference here, I do not require full understanding
		// of what that actually means.

		// A resource acquired in a try-with-resource block initialization, is
		// automatically
		// closed before the execution of catch and/or finally.
		try (BufferedReader br = Files.newBufferedReader(Paths.get(filename))) {
			return br.lines().collect(Collectors.toList());

		} catch (IOException e) {
			return new ArrayList<>();
		} finally {
		}
	}

}

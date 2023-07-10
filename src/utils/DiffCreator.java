package utils;

import java.io.File;
import java.io.IOException;

/**
 * Uses diff-to-html (Version 1.4) to create HTML diffs of two
 * files/directories.
 *
 * @see <a href="https://github.com/cronn-de/diff-to-html">diff-to-html</a>
 * @author Alex Mikulinski
 * @since 07.07.2019
 */
public class DiffCreator {
	/**
	 * Creates html diff for two files or directories. Example:
	 *
	 * <pre>
	 * createDiff("data/test.java", "data/test1.java", "output/diff.html")
	 * </pre>
	 *
	 * Will create "diff.html" in the "output" directory.
	 *
	 * @param _leftFilePath
	 *           Relative path to the left file/directory.
	 * @param _rightFilePath
	 *           Relative path to the right file/directory.
	 * @param _htmlOutputPath
	 *           Relative path to the output html file.
	 */
	public static void createDiff(String _leftFilePath, String _rightFilePath, String _htmlOutputPath) {
		JarExecutor	jarExecutor;
		File			leftFile;
		File			rightFile;
		File			outputFile;

		leftFile		= new File(_leftFilePath);
		rightFile	= new File(_rightFilePath);
		outputFile	= new File(_htmlOutputPath);
		jarExecutor	= new JarExecutor();

		try {
			jarExecutor.execute("diff-to-html-master-1.4", leftFile.getAbsolutePath(), rightFile.getAbsolutePath(),
					outputFile.getAbsolutePath());
		} catch (IOException e) {
			System.err.println("Failed creating diff.");
			e.printStackTrace();
		}
	}
}

package utils;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;

/**
 * This class is used to execute cli commands from Java and read the output and
 * error streams.
 *
 * @author Alex Mikulinski
 * @since 31.07.2019
 */
public class CliExecutor {
	private InputStream	inputStream;
	private InputStream	errorStream;
	private Process		process;

	/**
	 * Executes a cli command with passed arguments and prints errors to error
	 * stream. <br>
	 * Example:
	 *
	 * <pre>
	 * execute("git", "log"); -> results in "git log"
	 * </pre>
	 *
	 * @param _arguments
	 *           Some arguments for the command.
	 * @throws IOException
	 */
	public void execute(String... _arguments) throws IOException {
		StringBuilder command;

		command = new StringBuilder();

		for (String argument : _arguments) {
			command.append(argument);
			command.append(" ");
		}

		try {
			this.process = Runtime.getRuntime().exec(command.toString());
			this.process.waitFor();
		} catch (IOException | InterruptedException _e) {
			throw new IOException("Failed executing \"" + command.toString() + "\"", _e);
		}

		this.inputStream	= this.process.getInputStream();
		this.errorStream	= this.process.getErrorStream();

		this.printErrorStream();
	}

	private String streamToString(InputStream _stream) {
		if (_stream == null) {
			return null;
		}

		try {
			return IOUtils.toString(_stream, "UTF8");
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Prints the input stream from the executed jar to output stream or null if
	 * there is no stream (the jar was not executed?).
	 */
	public void printIntputStream() {
		System.out.println(this.streamToString(this.getInputStream()));
	}

	/**
	 * Prints the input stream from the executed jar to error stream or null if
	 * there is no stream (the jar was not executed?).
	 */
	public void printErrorStream() {
		System.err.println(this.streamToString(this.getErrorStream()));
	}

	/**
	 * Returns the {@link #inputStream} of current {@link CliExecutor}.
	 *
	 * @return The {@link #inputStream} as {@link InputStream}.
	 */
	public InputStream getInputStream() {
		return this.inputStream;
	}

	/**
	 * Returns the {@link #errorStream} of current {@link CliExecutor}.
	 *
	 * @return The {@link #errorStream} as {@link InputStream}.
	 */
	public InputStream getErrorStream() {
		return this.errorStream;
	}

}

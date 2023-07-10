package utils;

import java.io.File;
import java.io.IOException;

import app.Settings;

/**
 * This class is used to execute jars from Java.
 *
 * @author Alex Mikulinski
 * @since 07.07.2019
 */
public class JarExecutor {

	/**
	 * Executes a jar with passed arguments using {@link CliExecutor}. <br>
	 * Example:
	 *
	 * <pre>
	 * execute("DSDGen", "arg1", "arg2");
	 * </pre>
	 *
	 * @param _jarName
	 *           The name for the jar to execute. The jar must exist in
	 *           {@link Settings#RESOURCES_PATH}.
	 * @param _arguments
	 *           Some arguments for the jar.
	 * @throws IOException
	 */
	public void execute(String _jarName, String... _arguments) throws IOException {
		CliExecutor		cliExecutor;
		StringBuilder	command;
		File				resources;

		cliExecutor	= new CliExecutor();
		command		= new StringBuilder();
		resources	= new File(Settings.RESOURCES_PATH);

		// Build up the command "java -jar path/jarname.jar arg1 arg2 argn"
		command.append("java -jar ");
		command.append(resources.getAbsolutePath() + File.separator + _jarName + FileExtension.JAR);
		command.append(" ");

		for (String argument : _arguments) {
			command.append(argument);
			command.append(" ");
		}

		cliExecutor.execute(command.toString());
	}
}

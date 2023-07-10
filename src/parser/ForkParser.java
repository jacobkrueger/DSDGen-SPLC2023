package parser;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.eclipse.egit.github.core.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Provides methods for parsing fork informations retrieved from
 * <a href="https://github.com">GitHub</a> and saved in a JSON file to the model
 * described in {@link Repository}.
 *
 * @author Alex Mikulinski
 * @since 28.07.2019
 */
public class ForkParser {

	/**
	 * Parses a JSON file containing the fork informations for the examined software
	 * project.
	 *
	 * @param _filePath
	 *           The path to the JSON file as {@link String}.
	 * @return A {@link List} of {@link Repository Repositories}.
	 * @throws IOException
	 */
	public List<Repository> parse(String _filePath) throws IOException {
		ObjectMapper	objectMapper;
		File				file;

		file				= new File(_filePath);
		objectMapper	= new ObjectMapper();

		return Arrays.asList(objectMapper.readValue(file, Repository[].class));
	}
}

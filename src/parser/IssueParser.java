package parser;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import model.Issue;

/**
 * Provides methods for parsing {@link Issue}s retrieved from
 * <a href="https://github.com">GitHub</a> and saved in a JSON file to the model
 * described in {@link Issue}.
 *
 * @author Alex Mikulinski
 * @since 20.03.2019
 */
public class IssueParser {

	/**
	 * Parses a JSON file containing the {@link Issue} for the examined software
	 * project.
	 *
	 * @param _filePath
	 *           The path to the JSON file as {@link String}.
	 * @return A {@link List} of {@link Issue}.
	 * @throws IOException
	 */
	public List<Issue> parse(String _filePath) throws IOException {
		ObjectMapper	objectMapper;
		File				file;

		file				= new File(_filePath);
		objectMapper	= new ObjectMapper();

		return Arrays.asList(objectMapper.readValue(file, Issue[].class));
	}
}

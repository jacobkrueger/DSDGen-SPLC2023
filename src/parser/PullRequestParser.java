package parser;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.eclipse.egit.github.core.PullRequest;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Provides methods for parsing {@link PullRequest}s retrieved from
 * <a href="https://github.com">GitHub</a> and saved in a JSON file to the model
 * described in {@link PullRequest}.
 *
 * @author Alex Mikulinski
 * @since 20.03.2019
 */
public class PullRequestParser {

	/**
	 * Parses a JSON file containing the {@link PullRequest}s for the examined
	 * software project.
	 *
	 * @param _filePath
	 *           The path to the JSON file as {@link String}.
	 * @return A {@link List} of {@link PullRequest}.
	 * @throws IOException
	 */
	public List<PullRequest> parse(String _filePath) throws IOException {
		ObjectMapper	objectMapper;
		File				file;

		file				= new File(_filePath);
		objectMapper	= new ObjectMapper();

		return Arrays.asList(objectMapper.readValue(file, PullRequest[].class));
	}
}

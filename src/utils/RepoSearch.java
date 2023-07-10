package utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.SearchRepository;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.RepositoryService;

/**
 * Provides searching for repositories hosted on
 * <a href="https://github.com">GitHub</a> via <a href=
 * "https://github.com/eclipse/egit-github/tree/master/org.eclipse.egit.github.core">GitHub
 * Java API</a>.
 *
 * @author Alex Mikulinski
 * @since 23.02.2019
 */
public class RepoSearch {
	private final String					INFO_FORMATTER	= "%-12s %s\n";

	private GitHubClient					client;
	private List<SearchRepository>	results;

	/**
	 * Creates a new {@link RepoSearch}.
	 *
	 * @param _client
	 *           The used {@link GitHubClient}.
	 */
	public RepoSearch(GitHubClient _client) {
		this.client = _client;
	}

	/**
	 * Searches <a href="https://github.com">GitHub</a> repositories in java with
	 * passed keywords.
	 *
	 * @param _keywords
	 *           Keywords to search for delimited by a " ". (E.g. "foo bar")
	 * @return A List of {@link Repository}.
	 * @throws IOException
	 */
	public List<SearchRepository> search(String _keywords) throws IOException {
		RepositoryService	repositoryService;

		String				keywords;

		keywords = _keywords;

		if (keywords.isBlank()) {
			throw new IllegalArgumentException("A search without keywords is not allowed!");
		}

		repositoryService	= new RepositoryService(this.client);
		this.results		= repositoryService.searchRepositories(keywords, "java");

		return this.results;
	}

	/**
	 * Returns the chosen search result from last search or null if nothing was
	 * found.
	 *
	 * @param _number
	 *           The number of the search result. Starts with 0. If the number is
	 *           higher than the number of results, returns the last result.
	 * @return The chosen search result as {@link SearchRepository} or null if not
	 *         result found.
	 */
	public SearchRepository getResult(int _number) {
		int number;

		if (this.results == null || this.results.isEmpty()) {
			return null;
		}

		number = _number;

		if (number >= this.results.size()) {
			number = this.results.size() - 1;
		}

		return this.results.get(number);
	}

	/**
	 * Returns the {@link Repository} for the chosen search result from last search.
	 *
	 * @param _number
	 *           The number of the search result. Starts with 0. If the number is
	 *           higher than the number of results, returns the last result.
	 * @return The coresponding {@link Repository}.
	 * @throws IOException
	 */
	public Repository getRepo(int _number) throws IOException {
		return this.getRepo(this.getResult(_number));
	}

	/**
	 * Returns the {@link Repository} for the chosen {@link SearchRepository}.
	 *
	 * @param _result
	 *           The search result as {@link SearchRepository}.
	 * @return The coresponding {@link Repository}.
	 * @throws IOException
	 */
	public Repository getRepo(SearchRepository _result) throws IOException {
		RepositoryService repositoryService;

		repositoryService = new RepositoryService(this.client);

		return repositoryService.getRepository(_result);
	}

	/**
	 * Converts the last results to formatted {@link String}s.
	 *
	 * @return The passed results as formatted {@link String}s or null if no results
	 *         found.
	 */
	public List<String> getResults() {
		if (this.results == null) {
			throw new IllegalArgumentException("There are no search results to print.");
		}

		if (this.results.isEmpty()) {
			return null;
		}

		return this.getResults(this.results);
	}

	/**
	 * Converts the passed results to formatted {@link String}s.
	 *
	 * @param _results
	 *           The {@link SearchRepository} results.
	 * @return The passed results as formatted {@link String}s.
	 */
	public List<String> getResults(List<SearchRepository> _results) {
		List<String>				stringResults;
		List<SearchRepository>	results;
		SearchRepository			tempResult;
		String						formatter;
		String						description;
		int							currentNameLength;
		int							maxNameLength;

		results			= _results;
		stringResults	= new ArrayList<>();

		// Determine the max name length
		maxNameLength	= 0;
		for (SearchRepository result : results) {
			currentNameLength = result.getName().length();
			if (currentNameLength > maxNameLength) {
				maxNameLength = currentNameLength;
			}
		}

		// Set the formatter so that the names for repos make a column
		formatter = "%-3s %-" + maxNameLength + "s %s";

		stringResults.add(String.format(formatter, "Nr.", "Name", "Description"));

		for (int i = 0; i < results.size(); i++) {
			tempResult	= results.get(i);
			// Do not allow line breaks in description.
			// Description can be null.
			description	= tempResult.getDescription();
			if (description != null) {
				description = description.replaceAll("\n", " ");
			} else {
				description = "";
			}

			stringResults.add(String.format(formatter, i, tempResult.getName(), description));
		}

		return stringResults;
	}

	/**
	 * Returns a more detailed information for the chosen search result from last
	 * search.
	 *
	 * @param _number
	 *           The number of the search result. Starts with 0. If the number is
	 *           higher than the number of results, shows the last result.
	 * @return Info as {@link String} or null if no info found.
	 * @throws IOException
	 */
	public String getInfo(int _number) throws IOException {
		SearchRepository result;

		result = this.getResult(_number);

		if (result == null) {
			return null;
		}

		return this.getInfo(result);
	}

	/**
	 * Returns a more detailed information for a search result.
	 *
	 * @param _result
	 *           The result as {@link Repository}.
	 * @return Info as {@link String}.
	 * @throws IOException
	 */
	public String getInfo(SearchRepository _result) throws IOException {
		StringBuilder		stringBuilder;
		Repository			repository;
		SearchRepository	result;

		result			= _result;
		repository		= this.getRepo(_result);
		stringBuilder	= new StringBuilder();

		stringBuilder.append(String.format(this.INFO_FORMATTER, "URL", result.getUrl()));
		stringBuilder.append(String.format(this.INFO_FORMATTER, "Owner", result.getOwner()));
		stringBuilder.append(String.format(this.INFO_FORMATTER, "Forks", result.getForks()));
		stringBuilder.append(String.format(this.INFO_FORMATTER, "Homepage", result.getHomepage()));
		stringBuilder.append(String.format(this.INFO_FORMATTER, "Watchers", result.getWatchers()));
		stringBuilder.append(String.format(this.INFO_FORMATTER, "Open issues", result.getOpenIssues()));
		stringBuilder
				.append(String.format(this.INFO_FORMATTER, "Created at", DateHelper.toString(result.getCreatedAt())));
		stringBuilder.append(String.format(this.INFO_FORMATTER, "Pushed at", DateHelper.toString(result.getPushedAt())));

		stringBuilder
				.append(String.format(this.INFO_FORMATTER, "Updated at", DateHelper.toString(repository.getUpdatedAt())));

		return stringBuilder.toString();
	}
}
